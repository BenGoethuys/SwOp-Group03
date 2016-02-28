package bugtrap03.gui.terminal;

import bugtrap03.DataController;
import bugtrap03.permission.PermissionException;
import bugtrap03.usersystem.User;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
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
    private HashMap<String, Cmd> cmdMap;

    /**
     * TODO: Complete initCmdList header
     */
    private void initCmdList() {
        cmdList = new ArrayList<>();
        cmdList.add(new SimpleEntry("login", new LoginCmd(this.terminal)));
        cmdList.add(new SimpleEntry("createproject", new CreateProjectCmd()));

        cmdMap = new HashMap<>();
        for (int i = 0; i < cmdList.size(); i++) {
            cmdMap.put(cmdList.get(i).getKey(), cmdList.get(i).getValue());
            cmdMap.put(Integer.toString(i), cmdList.get(i).getValue());
        }

        //Custom abreviations.
        cmdList.add(new SimpleEntry("createproj", new CreateProjectCmd()));
    }

    /**
     * TODO: Complete header.
     *
     * @param command
     * @return
     */
    public void performCmd(TerminalScanner scan, DataController con, User user, String command) throws CancelException, PermissionException {
        if (command == null) {
            new InvalidCmd().exec(scan, con, user);
        }

        Cmd cmd = cmdMap.get(command);
        cmd = (cmd != null) ? cmd : new InvalidCmd();

        cmd.exec(scan, con, user);
    }
}
