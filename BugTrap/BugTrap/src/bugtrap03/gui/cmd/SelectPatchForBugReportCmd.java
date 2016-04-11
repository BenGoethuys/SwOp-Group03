/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bugtrap03.gui.cmd;

import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.gui.cmd.general.CancelException;
import bugtrap03.gui.terminal.TerminalScanner;
import bugtrap03.model.DataModel;

/**
 *
 * @author Group 03
 */
public class SelectPatchForBugReportCmd implements Cmd {

    /**
     * Creates a {@link Cmd} for selecting a patch from the list of patches of a bugReport.
     *
     * @param bugReport The bugRep to select from.
     */
    public SelectPatchForBugReportCmd(BugReport bugReport) {
        this.bugReport = bugReport;
    }

    /**
     * Creates a {@link Cmd} for selecting a patch from the list of patches of a bugReport. A scenario to select the bug
     * report is included.
     */
    public SelectPatchForBugReportCmd() {
    }

    private BugReport bugReport;

    /**
     *
     * @param scan
     * @param model
     * @param user
     * @return
     * @throws PermissionException
     * @throws CancelException When the users wants to abort the current cmd
     * @throws IllegalArgumentException When scan == null
     */
    @Override
    public String exec(TerminalScanner scan, DataModel model, User user) throws PermissionException, CancelException, IllegalArgumentException {
        if (scan == null) {
            throw new IllegalArgumentException("scan musn't be null.");
        }

    }

}
