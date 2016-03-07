/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bugtrap03.gui.cmd.general;

import bugtrap03.model.DataModel;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.gui.cmd.Cmd;
import bugtrap03.gui.terminal.TerminalScanner;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;

/**
 * This command prints all the commandos supplied when executed
 * @author Vincent Derkinderen
 */
public class HelpCmd implements Cmd {

    /**
     * Create a HelpCmd with a List of commands to use for printing. Any
     * modification of the provided list will be reflected on the list used by
     * this class.
     *
     * @param cmdList The list of commands used for printing. Any modification
     *                will be reflected.
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
     * @param scan The scanner used to print to.
     * @param dummy2 doesn't matter
     * @param dummy3 doesn't matter
     * @return null.
     */
    @Override
    public Object exec(TerminalScanner scan, DataModel dummy2, User dummy3) throws IllegalArgumentException {
        if(scan == null) {
            throw new IllegalArgumentException("HelpCmd requires a non null reference as scan");
        }
        scan.println("List of possible commands:");
        for (SimpleEntry cmdEntry : cmdList) {
            scan.println(cmdEntry.getKey().toString());
        }

        return null;
    }

}
