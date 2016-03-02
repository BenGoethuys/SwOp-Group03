package bugtrap03.gui.cmd;

import bugtrap03.DataModel;
import bugtrap03.bugdomain.BugReport;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.gui.cmd.general.CancelException;
import bugtrap03.gui.terminal.TerminalScanner;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

/**
 * Created by Ben on 02/03/2016.
 */
public class SelectBugReport implements Cmd {

    private ArrayList<AbstractMap.SimpleEntry<String, Predicate<BugReport>>> modeList;
    private ArrayList<AbstractMap.SimpleEntry<String, Predicate<BugReport>>> modeListExtra;
    private HashMap<String, Predicate<BugReport>> modeMap = new HashMap<>();
    private String str = "";
    private User user = null;
    private long id = 0;

    public SelectBugReport() {
        this.initList();
    }

    private void initList(){
        modeList.add(new AbstractMap.SimpleEntry<String, Predicate<BugReport>>("title", u -> u.getTitle().equals(str)));
        modeList.add(new AbstractMap.SimpleEntry<String, Predicate<BugReport>>("description", u -> u.getDescription().equals(str)));
        modeListExtra.add(new AbstractMap.SimpleEntry<String, Predicate<BugReport>>("desc", u -> u.getDescription().equals(str)));
        modeList.add(new AbstractMap.SimpleEntry<String, Predicate<BugReport>>("user", u -> u.getCreator().equals(user)));
        modeList.add(new AbstractMap.SimpleEntry<String, Predicate<BugReport>>("assigned", u -> u.getUserList().contains(user)));
        modeList.add(new AbstractMap.SimpleEntry<String, Predicate<BugReport>>("uniqueId", u -> u.getUniqueID() == id));
        modeListExtra.add(new AbstractMap.SimpleEntry<String, Predicate<BugReport>>("id", u -> u.getUniqueID() == id));

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
     *
     * @param scan  The scanner used to interact with the person.
     * @param model The model used for model access.
     * @param user  The {@link User} who wants to executes this command.
     * @return null if there is no result specified.
     * @throws PermissionException When the user does not have sufficient
     *                             permissions.
     * @throws CancelException     When the users wants to abort the current cmd
     */
    @Override
    public Object exec(TerminalScanner scan, DataModel model, User user) throws PermissionException, CancelException {
        scan.println("Please select a search mode: ");
        for (int i = 0; i < this.modeList.size(); i++) {
            scan.println(i + ". " + this.modeList.get(i));
        }

        Predicate<BugReport> mode = null;
        do{
            System.out.print("I chose: ");
            if (scan.hasNextInt()) { // by index
                int index = scan.nextInt();// input
                if (index >= 0 && index < this.modeList.size()) {
                    mode = this.modeList.get(index).getValue();
                } else {
                    scan.println("Invalid input.");
                }
            } else { // by name
                String input = scan.nextLine(); // input
                mode = modeMap.get(input);
                if (mode == null) {
                    scan.println("Invalid input.");
                }
            }
        } while (mode == null);

        //TODO
        return null;
    }
}
