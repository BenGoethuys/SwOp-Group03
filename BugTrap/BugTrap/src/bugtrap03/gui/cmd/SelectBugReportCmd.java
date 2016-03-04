package bugtrap03.gui.cmd;

import bugtrap03.DataModel;
import bugtrap03.bugdomain.BugReport;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.gui.cmd.general.CancelException;
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
    private String str = "";
    private User user = null;
    private long id = 0;

    public SelectBugReportCmd() {
        this.initList();
    }

    /**
     * This method initialises the list of possible search methods
     */
    private void initList() {
        modeList.add(new AbstractMap.SimpleEntry<>("title", u -> u.getTitle().equals(str)));
        modeList.add(new AbstractMap.SimpleEntry<>("description", u -> u.getDescription().equals(str)));
        modeListExtra.add(new AbstractMap.SimpleEntry<>("desc", u -> u.getDescription().equals(str)));
        modeList.add(new AbstractMap.SimpleEntry<>("user", u -> u.getCreator().equals(user)));
        modeList.add(new AbstractMap.SimpleEntry<>("assigned", u -> u.getUserList().contains(user)));
        modeList.add(new AbstractMap.SimpleEntry<>("uniqueId", u -> u.getUniqueID() == id));
        modeListExtra.add(new AbstractMap.SimpleEntry<>("id", u -> u.getUniqueID() == id));

        for (int i = 0; i < modeList.size(); i++) {
            modeMap.put(modeList.get(i).getKey(), modeList.get(i).getValue());
            modeMap.put(Integer.toString(i), modeList.get(i).getValue());
        }

        for (int i = 0; i < modeListExtra.size(); i++) {
            modeMap.put(modeListExtra.get(i).getKey(), modeListExtra.get(i).getValue());
        }
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
     * @param scan The scanner used to interact with the person.
     * @param model The model used for model access.
     * @param user The {@link User} who wants to executes this command.
     * @return null if there is no result specified.
     * @throws PermissionException When the user does not have sufficient
     *             permissions.
     * @throws CancelException When the users wants to abort the current cmd
     */
    @Override
    public BugReport exec(TerminalScanner scan, DataModel model, User user) throws PermissionException, CancelException {
        scan.println("Please select a search mode: ");
        for (int i = 0; i < this.modeList.size(); i++) {
            scan.println(i + ". " + this.modeList.get(i).getKey());
        }

        Predicate<BugReport> mode = null;
        String modeStr = null;
        do {
            System.out.print("I chose: ");
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
        
        //TODO ask for user input

        //FIXME ask for user input

        ArrayList<BugReport> selected = model.getAllBugReports().parallelStream().filter(mode)
                .collect(Collectors.toCollection(ArrayList::new));
        Collections.sort(selected);

        if (selected.size() > 0) {
            scan.println("Please select a bug report: ");
            scan.println("Available bugReports:");
            for (int i = 0; i < selected.size(); i++) {
                BugReport bugrep = selected.get(i);
                scan.println(i + ". " + bugrep.getTitle() + ", uniqueID: " + bugrep.getUniqueID());
            }

            BugReport bugrep = null;

            do {
                scan.print("I chose: ");
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

            scan.println("You have selected: " + bugrep.getTitle() + " with uniqueId: " + bugrep.getUniqueID());
            return bugrep;
        } else {
            scan.println("No bugreports in the system");
            throw new NoSuchElementException("No bug report in the system, first add a bug report!");
        }
    }
}
