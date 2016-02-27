package bugtrap03.gui.terminal;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Admin
 */
public class CmdParser {

    public CmdParser() {
        initCmdList();
    }

    private ArrayList<SimpleEntry<String, Cmd>> cmdList;
    private HashMap<String, Cmd> cmdMap;

    /**
     * TODO: Complete initCmdList header
     */
    private void initCmdList() {
        cmdList = new ArrayList<>();
        cmdList.add(new SimpleEntry("login", new LoginCmd()));
        cmdList.add(new SimpleEntry("createproject", new CreateProjectCmd()));

        for (int i = 0; i < cmdList.size(); i++) {
            cmdMap.put(cmdList.get(i).getKey(), cmdList.get(i).getValue());
            cmdMap.put(Integer.toString(i), cmdList.get(i).getValue());
        }

        //Custom abreviations.
        cmdList.add(new SimpleEntry("createproj", new CreateProjectCmd()));
    }

    /**
     * TODO: Complete header.
     * @param command
     * @return
     */
    public Cmd createCmd(String... command) {
        if (command == null || command.length == 0) {
            return new InvalidCmd();
        }

        Cmd cmd = cmdMap.get(command[0]);
        return (cmd != null) ? cmd : new InvalidCmd();
        
        
    }
}
