package bugtrap03.model;

import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.VersionID;
import bugtrap03.bugdomain.usersystem.Developer;
import java.util.GregorianCalendar;

/**
 *
 * @author Group 03
 */
class CloneProjectModelCmd extends ModelCmd {

    /**
     * Creates a {@link ModelCmd} that can clone the given project and set a few attributes when executed.
     *
     * @param model The DataModel to add the clone project to.
     * @param cloneSource The project to clone from.
     * @param versionID The versionID for the clone project.
     * @param lead The lead developer for the clone project.
     * @param startDate The startDate for the clone project.
     * @param budgetEstimate The budgetEstimate for the clone project.
     *
     * @throws IllegalArgumentException When model == null
     * @throws IllegalArgumentException When the given cloneSource is null or terminated
     */
    CloneProjectModelCmd(DataModel model, Project cloneSource, VersionID versionID, Developer lead, GregorianCalendar startDate,
            long budgetEstimate) throws IllegalArgumentException {
        if (model == null) {
            throw new IllegalArgumentException("The model passed to CloneProjectModelCmd was a null reference.");
        }

        if (cloneSource == null){
            throw new IllegalArgumentException("The given cloneSource is null");
        }
        if (cloneSource.isTerminated()){
            throw new IllegalArgumentException("The given cloneSource is terminated");
        }

        this.model = model;
        this.cloneSource = cloneSource;
        this.versionID = versionID;
        this.lead = lead;
        this.startDate = startDate;
        this.budgetEstimate = budgetEstimate;
    }

    private final DataModel model;
    private final Project cloneSource;
    private final VersionID versionID;
    private final Developer lead;
    private final GregorianCalendar startDate;
    private final long budgetEstimate;
    
    private boolean isExecuted = false;
    
    private Project clone;

    /**
     * Clone the given {@link Project} and set a few attributes.
     *
     * @return The resulting clone.
     * @throws IllegalArgumentException Check @see.
     * @throws IllegalStateException When this ModelCmd was already executed.
     * @see Project#cloneProject(VersionID, Developer, GregorianCalendar, long)
     */
    @Override
    Project exec() throws IllegalArgumentException, IllegalStateException {
        if (this.isExecuted()) {
            throw new IllegalStateException("The CloneProjectModelCmd was already executed.");
        }

        clone = cloneSource.cloneProject(versionID, lead, startDate, budgetEstimate);
        if (clone != null) {
            model.addProject(clone);
        }
        isExecuted = true;
        return clone;
    }

    @Override
    boolean undo() {
        if (!this.isExecuted()) {
            return false;
        }
        model.deleteProject(clone);
        return true;
    }

    @Override
    boolean isExecuted() {
        return isExecuted;
    }

    @Override
    public String toString() {
        return "Cloned Project " + this.cloneSource.getName();
    }

}
