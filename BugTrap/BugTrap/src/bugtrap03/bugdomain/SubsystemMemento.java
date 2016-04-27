package bugtrap03.bugdomain;

import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.bugreport.BugReportMemento;
import java.util.HashMap;
import purecollections.PList;

/**
 * A (partial) memento of the subsystem.
 * <p>
 * <br> This stores the versionID, name, description, children (and their state), parent (excl state) and milestone,
 * direct bugReports (and their state).
 *
 * @author Group 03
 */
public class SubsystemMemento extends AbstractSystemMemento {

    SubsystemMemento(VersionID versionID, String name, String description, PList<Subsystem> children, AbstractSystem parent, Milestone milestone, PList<BugReport> bugReports, boolean isTerminated) {
        super(versionID, name, description, children, parent, milestone, isTerminated);

        this.bugReportList = bugReports;

        bugReportMementos = new HashMap<>();
        for (BugReport bugReport : bugReports) {
            bugReportMementos.put(bugReport, bugReport.getMemento());
        }
    }

    private final PList<BugReport> bugReportList;
    private final HashMap<BugReport, BugReportMemento> bugReportMementos;

    PList<BugReport> getBugReportList() {
        return this.bugReportList;
    }

    /**
     * Restore the state of the direct bug Reports by restoring their memento.
     */
    void restoreBugReports() {
        for (BugReport bugRep : bugReportList) {
            BugReportMemento mem = bugReportMementos.get(bugRep);

            if (mem != null) {
                bugRep.setMemento(mem);
            }
        }
    }

}