package bugtrap03.gui.cmd;

import bugtrap03.model.DataModel;
import bugtrap03.bugdomain.BugReport;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.Developer;
import bugtrap03.bugdomain.usersystem.Issuer;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.gui.cmd.general.CancelException;
import bugtrap03.gui.cmd.general.GetLongCmd;
import bugtrap03.gui.cmd.general.GetStringCmd;
import bugtrap03.gui.cmd.general.GetUserOfTypeCmd;
import bugtrap03.gui.terminal.TerminalScanner;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * This class handles the SelectBugReport scenario Created by Ben Goethuys on
 * 02/03/2016.
 */
public class SelectBugReportCmd implements Cmd {

    private ArrayList<AbstractMap.SimpleEntry<String, Predicate<BugReport>>> modeList = new ArrayList<>();
    private ArrayList<AbstractMap.SimpleEntry<String, Predicate<BugReport>>> modeListExtra = new ArrayList<>();
    private HashMap<String, Predicate<BugReport>> modeMap = new HashMap<>();
    private HashMap<String, Cmd> cmdMap = new HashMap<>();
    private Object o;

    public SelectBugReportCmd() {
        this.initList();
    }

    /**
     * This method initialises the list of possible search methods
     */
    private void initList() {
        modeList.add(new AbstractMap.SimpleEntry<>("title", u -> u.getTitle().toLowerCase().contains(((String) o).toLowerCase())));
        modeList.add(new AbstractMap.SimpleEntry<>("description", u -> u.getDescription().toLowerCase().contains(((String) o).toLowerCase())));
        modeListExtra.add(new AbstractMap.SimpleEntry<>("desc", u -> u.getDescription().toLowerCase().contains(((String) o).toLowerCase())));
        modeList.add(new AbstractMap.SimpleEntry<>("creator", u -> u.getCreator().equals(o)));
        modeList.add(new AbstractMap.SimpleEntry<>("assigned", u -> u.getUserList().contains((Developer) o)));
        modeList.add(new AbstractMap.SimpleEntry<>("uniqueId", u -> new Long(u.getUniqueID()).equals(o)));
        modeListExtra.add(new AbstractMap.SimpleEntry<>("id", u -> new Long(u.getUniqueID()).equals(o)));

        for (int i = 0; i < modeList.size(); i++) {
            modeMap.put(modeList.get(i).getKey(), modeList.get(i).getValue());
            modeMap.put(Integer.toString(i), modeList.get(i).getValue());
        }
        for (int i = 0; i < modeListExtra.size(); i++) {
            modeMap.put(modeListExtra.get(i).getKey(), modeListExtra.get(i).getValue());
        }

        cmdMap.put("title", new GetStringCmd());
        cmdMap.put("description", new GetStringCmd());
        cmdMap.put("desc", new GetStringCmd());
        cmdMap.put("creator", new GetUserOfTypeCmd<>(Issuer.class));
        cmdMap.put("assigned", new GetUserOfTypeCmd<>(Developer.class));
        cmdMap.put("uniqueId", new GetLongCmd());
        cmdMap.put("id", new GetLongCmd());
    }

    /**
     * Execute this command and possibly return a result.
     * <p>
     * <br>
     * 1. The system shows a list of possible searching modes:
     * <UL>
     * <LI>Search for bug reports with a specific string in the title or
     * description</LI>
     * </UL>
     * <UL>
     * <LI>Search for bug reports filed by some specific user</LI>
     * </UL>
     * <UL>
     * <LI>Search for bug reports assigned to specific user</LI>
     * </UL>
     * <UL>
     * <LI>. . .</LI>
     * </UL>
     * <br>
     * 2. The issuer selects a searching mode and provides the required search
     * parameters. <br>
     * 3. The system shows an ordered list of bug reports that matched the
     * search query. <br>
     * 4. The issuer selects a bug report from the ordered list.
     *
     * @param scan The {@link scanner} used to interact with the person.
     * @param model The {@link DataModel} used for model access.
     * @param user The {@link User} who wants to executes this command.
     * @return The {@link BugReport} selected by the person.
     * @throws PermissionException When the user does not have sufficient
     * permissions.
     * @throws CancelException When the users wants to abort the current cmd
     */
    @Override
    public BugReport exec(TerminalScanner scan, DataModel model, User user) throws PermissionException, CancelException {
        while (true) {
            scan.println("Please select a search mode: ");
            for (int i = 0; i < this.modeList.size(); i++) {
                scan.println(i + ". " + this.modeList.get(i).getKey());
            }

            Predicate<BugReport> mode = null;
            String modeStr = null;
            do {
                scan.print("I choose: ");
                if (scan.hasNextInt()) { // by index
                    int index = scan.nextInt();// input
                    if (index >= 0 && index < this.modeList.size()) {
                        mode = this.modeList.get(index).getValue();
                        modeStr = this.modeList.get(index).getKey();
                    } else {
                        scan.println("Invalid input.");
                    }
                } else { // by name
                    String input = scan.nextLine(); // input
                    mode = modeMap.get(input);
                    modeStr = input;
                    if (mode == null) {
                        scan.println("Invalid input.");
                    }
                }
            } while (mode == null);

            // ask user for search term
            scan.println("Please enter the required search term ...");
            this.o = this.cmdMap.get(modeStr).exec(scan, model, user);

            //parallel can conflict w tests.
            ArrayList<BugReport> selected = model.getAllBugReports().parallelStream().filter(mode)
                    .collect(Collectors.toCollection(ArrayList::new));
            Collections.sort(selected);

            //select bugreport
            if (selected.size() > 0) {
                scan.println("Please select a bug report: ");
                scan.println("Available bugReports:");
                for (int i = 0; i < selected.size(); i++) {
                    BugReport bugrep = selected.get(i);
                    scan.println(i + ". " + bugrep.getTitle() + "\t -UniqueID: " + bugrep.getUniqueID());
                }

                BugReport bugrep = null;

                do {
                    scan.print("I choose: ");
                    if (scan.hasNextInt()) { // by index
                        int index = scan.nextInt();// input
                        if (index >= 0 && index < selected.size()) {
                            bugrep = selected.get(index);
                        } else {
                            scan.println("Invalid input.");
                        }
                    } else { // by name
                        String input = scan.nextLine(); // input
                        try {
                            bugrep = selected.parallelStream().filter(u -> u.getTitle().equals(input)).findFirst().get();
                        } catch (NoSuchElementException ex) {
                            scan.println("Invalid input.");
                        }
                    }
                } while (bugrep == null);

                scan.println("You have selected: " + bugrep.getTitle() + "\t -UniqueID: " + bugrep.getUniqueID());
                return bugrep;
            } else {
                scan.println("No bugreports in the system match the search term");
            }
        }
    }
}
