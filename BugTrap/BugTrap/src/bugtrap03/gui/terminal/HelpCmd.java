/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bugtrap03.gui.terminal;

import bugtrap03.DataModel;
import bugtrap03.usersystem.User;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Admin
 */
public class HelpCmd implements Cmd {

    /**
     * Create a HelpCmd with a List of commands to use for printing. Any
     * modification of the provided list will be reflected on the list used by
     * this class.
     *
     * @param cmdList The list of commands used for printing. Any modification
     * will be reflected.
     */
    public HelpCmd(ArrayList<SimpleEntry<String, Cmd>> cmdList) {
        if (cmdList == null) {
            this.cmdList = new ArrayList();
        } else {
            this.cmdList = cmdList;
        }
    }

    private ArrayList<SimpleEntry<String, Cmd>> cmdList;

    /**
     * Print a 'list' of all possible commands currently in the map passed to
     * the constructor.
     *
     * @param dummy1
     * @param dummy2 
     * @param dummy3
     * 
     * @return null.
     */
    @Override
    public Object exec(TerminalScanner dummy1, DataModel dummy2, User dummy3) {
        System.out.println("List of possible commands:");
        for (SimpleEntry cmdEntry : cmdList) {
            System.out.println(cmdEntry.getKey());
        }

        return null;
    }

}
