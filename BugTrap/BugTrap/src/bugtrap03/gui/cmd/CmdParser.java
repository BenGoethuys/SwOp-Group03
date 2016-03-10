package bugtrap03.gui.cmd;

import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.gui.cmd.general.CancelException;
import bugtrap03.gui.cmd.general.ClearCmd;
import bugtrap03.gui.cmd.general.HelpCmd;
import bugtrap03.gui.cmd.general.InvalidCmd;
import bugtrap03.gui.terminal.Terminal;
import bugtrap03.gui.terminal.TerminalScanner;
import bugtrap03.model.DataModel;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class parses the user input to executable commands and executes them
 *
 * @author Group 03
 */
public class CmdParser {

    public CmdParser(Terminal terminal) throws IllegalArgumentException {
        if (terminal == null) {
            throw new IllegalArgumentException("CmdParser needs a non-null reference for Terminal.");
        }
        this.terminal = terminal;
        initCmdList();
    }

    private Terminal terminal;
    private ArrayList<SimpleEntry<String, Cmd>> cmdList;
    private ArrayList<SimpleEntry<String, Cmd>> cmdListExtra;
    private HashMap<String, Cmd> cmdMap;

    /**
     * Initialize the cmdList.
     */
    private void initCmdList() {
        cmdList = new ArrayList<>();
        cmdList.add(new SimpleEntry<>("help", new HelpCmd(cmdList)));
        cmdList.add(new SimpleEntry<>("clear", new ClearCmd()));
        cmdList.add(new SimpleEntry<>("login", new LoginCmd(this.terminal)));
        cmdList.add(new SimpleEntry<>("createproject", new CreateProjectCmd()));
        cmdList.add(new SimpleEntry<>("updateproject", new UpdateProjectCmd()));
        cmdList.add(new SimpleEntry<>("deleteproject", new DeleteProjectCmd()));
        cmdList.add(new SimpleEntry<>("showprojectdetails", new ShowProjectCmd()));
        cmdList.add(new SimpleEntry<>("createsubsystem", new CreateSubsystemCmd()));
        cmdList.add(new SimpleEntry<>("createbugreport", new CreateBugReportCmd()));
        cmdList.add(new SimpleEntry<>("selectbugreport", new SelectBugReportCmd()));
        cmdList.add(new SimpleEntry<>("inspectbugreport", new InspectBugReportCmd()));
        cmdList.add(new SimpleEntry<>("createcomment", new CreateCommentCmd()));
        cmdList.add(new SimpleEntry<>("assigntobugreport", new AssignToBugReportCmd()));
        cmdList.add(new SimpleEntry<>("assigntoproject", new AssignToProjectCmd()));
        cmdList.add(new SimpleEntry<>("updatebugreport", new UpdateBugReportCmd()));

        cmdMap = new HashMap<>();
        for (int i = 0; i < cmdList.size(); i++) {
            cmdMap.put(cmdList.get(i).getKey(), cmdList.get(i).getValue());
            cmdMap.put(Integer.toString(i), cmdList.get(i).getValue());
        }

        cmdListExtra = new ArrayList<>();
        //Custom abbreviations.
        cmdListExtra.add(new SimpleEntry<>("createproj", new CreateProjectCmd()));
        cmdListExtra.add(new SimpleEntry<>("updateproj", new UpdateProjectCmd()));
        cmdListExtra.add(new SimpleEntry<>("deleteproj", new DeleteProjectCmd()));
        cmdListExtra.add(new SimpleEntry<>("delproj", new DeleteProjectCmd()));
        cmdListExtra.add(new SimpleEntry<>("showprojdetails", new ShowProjectCmd()));
        cmdListExtra.add(new SimpleEntry<>("showprojdet", new ShowProjectCmd()));
        cmdListExtra.add(new SimpleEntry<>("createsubsys", new CreateSubsystemCmd()));
        cmdListExtra.add(new SimpleEntry<>("createbugrep", new CreateBugReportCmd()));
        cmdListExtra.add(new SimpleEntry<>("selectbugrep", new SelectBugReportCmd()));
        cmdListExtra.add(new SimpleEntry<>("inspectbugrep", new InspectBugReportCmd()));
        cmdListExtra.add(new SimpleEntry<>("createcom", new CreateCommentCmd()));
        cmdListExtra.add(new SimpleEntry<>("showbugreport", new InspectBugReportCmd()));
        cmdListExtra.add(new SimpleEntry<>("showbugrep", new InspectBugReportCmd()));
        cmdListExtra.add(new SimpleEntry<>("assigntobugrep", new CreateCommentCmd()));
        cmdListExtra.add(new SimpleEntry<>("assigntoproj", new AssignToProjectCmd()));
        cmdListExtra.add(new SimpleEntry<>("updatebugrep", new UpdateBugReportCmd()));

        for (int i = 0; i < cmdListExtra.size(); i++) {
            cmdMap.put(cmdListExtra.get(i).getKey(), cmdListExtra.get(i).getValue());
        }
    }

    /**
     * Performs the {@link Cmd} associated with the given command. When the
     * given command string is not associated with any {@link Cmd} an
     * {@link InvalidCmd} is executed.
     *
     * @param command The string that would initiate a certain command.
     */
    public void performCmd(TerminalScanner scan, DataModel model, User user, String command) throws CancelException, PermissionException {
        if (command == null) {
            new InvalidCmd().exec(scan, model, user);
        }

        Cmd cmd = cmdMap.get(command);
        cmd = (cmd != null) ? cmd : new InvalidCmd();

        cmd.exec(scan, model, user);
    }
}
