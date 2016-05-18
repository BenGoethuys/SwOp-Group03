package bugtrap03.gui.cmd;

import bugtrap03.bugdomain.HealthAlgorithm;
import bugtrap03.bugdomain.HealthAlgorithm1;
import bugtrap03.bugdomain.HealthAlgorithm2;
import bugtrap03.bugdomain.HealthAlgorithm3;
import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.usersystem.Developer;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.gui.cmd.general.CancelException;
import bugtrap03.gui.cmd.general.GetUserOfTypeCmd;
import bugtrap03.gui.terminal.TerminalScanner;
import bugtrap03.model.DataModel;
import purecollections.PList;

/**
 * This command represents the use case for showing the performance metrics of a developer
 *
 * @author Group 03
 */
public class ShowPerformanceMetricsCmd implements Cmd<Object> {

    /**
     * Execute use case 4.20, choose a Developer and show performance metrics.
     * <br>
     * <br>4.20 Use Case: Show Performance Metrics
     * <br>1. The user indicates he wants to inspect the performance of a developer.
     * <br>2. The system shows a list of all developers.
     * <br>3. The user selects a developer.
     * <br>4. The system shows the details of the developer together with the performance metrics discussed in 3.3.6.
     * <p>
     * <br>-- Reporting --
     * <br>The number of Duplicate bug reports submitted by the developer
     * <br>The number of NotABug bug reports submitted by the developer
     * <br>The total number of bug reports submitted by the developer
     * <br>-- Leadership --
     * <br>The health indicator of every project in which the developer is lead
     * <br>-- Test skills --
     * <br>The average number of lines of code for each submitted tests
     * <br>The total number of tests submitted
     * <br>-- Problem solving --
     * <br>The number of Closed bug reports the developer is assigned to
     * <br>The number of unnished (i.e. New, Assigned or Under Review) bug reports the developer is assigned to
     * <br>The average lines of code for each submitted patch
     * <br>The total number of patches submitted
     *
     * @param scan The scanner used to interact with the person.
     * @param model The model used for model access.
     * @param dummy A dummy value.
     * @return null
     * @throws CancelException When the users wants to abort the current cmd
     * @throws IllegalArgumentException When scan or model == null.
     */
    @Override
    public Object exec(TerminalScanner scan, DataModel model, User dummy) throws CancelException, IllegalArgumentException {
        if (scan == null || model == null) {
            throw new IllegalArgumentException("scan and model musn't be null.");
        }

        // 1. The user indicates he wants to inspect the performance of a developer.
        // 2. The system shows a list of all developers.
        // 3. The user selects a developer.
        scan.println("Please select a developer to show the performance metrics of.");
        Developer dev = (new GetUserOfTypeCmd<>(Developer.class)).exec(scan, model, null);

        if (dev == null) {
            throw new CancelException("No developers available. Cancelled operation.");
        }

        //4. The system shows the details of the developer together with the performance metrics discussed in 3.3.6.
        showDetails(scan, dev);
        showPerformanceMetrics(scan, model, dev);

        return null;
    }

    /**
     * Print the details of the given developer.
     *
     * @param scan The scanner used to print the details. Must not be null.
     * @param dev The developer to print the details of. Must not be null.
     */
    private void showDetails(TerminalScanner scan, Developer dev) {
        scan.println("--Developer details--");
        scan.println("name: " + dev.getFullName());
    }

    /**
     * Print the performance metrics for a certain developer.
     *
     * @param scan The scanner used to print the metrics. Must not be null.
     * @param model The DataModel used to gather statistics with. Must not be null.
     * @param dev The developer to print the metrics of. Must not be null.
     */
    private void showPerformanceMetrics(TerminalScanner scan, DataModel model, Developer dev) {
        //Report
        long nbDuplicateBRSub = model.getNbDuplicateBRsSubmitted(dev);
        long nbNotABugBRSub = model.getNbNotABugReportBRsSubmitted(dev);
        long nbBRSub = model.getNbBRSubmitted(dev);
        //Leadership
        String indicators = getAllHealthIndicators(model.getAllProjectsOfLead(dev),
                new HealthAlgorithm1(), new HealthAlgorithm2(), new HealthAlgorithm3());
        //Test Skills
        double avgLinesTest = dev.getStats().getAvgLinesOfTestsSubmitted();
        long nbTests = dev.getStats().getNbTestsSubmitted();
        //Problem Solving
        long nbClosedBRAssigned = model.getNbClosedBRForDev(dev);
        long nbUnfinishedBRAssigned = model.getNbUnfinishedBRForDev(dev);
        double avgLinesPatch = dev.getStats().getAvgLinesOfPatchesSubmitted();
        long nbPatches = dev.getStats().getNbPatchesSubmitted();

        scan.println("--Performance Metrics--");

        scan.println("- Reporting -");
        scan.println("Duplicate bug reports submitted: " + nbDuplicateBRSub);
        scan.println("NotABug bug reports submitted: " + nbNotABugBRSub);
        scan.println("Bug reports submitted: " + nbBRSub);

        scan.println("- Leadership -");
        scan.println(indicators);
        scan.println("- Test Skills -");
        scan.println("Average lines each test: " + avgLinesTest);
        scan.println("Tests submitted: " + nbTests);

        scan.println("- Problem Solving -");
        scan.println("Assigned to closed bug reports: " + nbClosedBRAssigned);
        scan.println("Assigned to unfinished bug reports: " + nbUnfinishedBRAssigned);
        scan.println("Average lines each patch: " + avgLinesPatch);
        scan.println("Patches submitted: " + nbPatches);
    }

    /**
     * Get the health indicators of the given projects in String form.
     * <br> For each project the indicator based on every algorithm will be added.
     * 
     * @param projects The projects to get the indicators for.
     * @param ha The health algorithms used for the indicators.
     * @return The String containing the indicators for every project for every algorithm.
     */
    private String getAllHealthIndicators(PList<Project> projects, HealthAlgorithm... ha) {
        StringBuilder str = new StringBuilder();

        for (Project proj : projects) {
            str.append(proj.getName()).append(":\n");
            for (HealthAlgorithm a : ha) {
                str.append("\t").append(a).append(": ");
                str.append(proj.getIndicator(a)).append("\n");
            }
        }

        return str.toString();
    }
}
