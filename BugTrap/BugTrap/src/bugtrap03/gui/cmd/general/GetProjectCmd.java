package bugtrap03.gui.cmd.general;

import bugtrap03.model.DataModel;
import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.gui.cmd.Cmd;
import bugtrap03.gui.terminal.TerminalScanner;
import purecollections.PList;

/**
 * This command represents a sub use case where the user wants to select a
 * project
 *
 * @author Group 03
 */
public class GetProjectCmd implements Cmd {

    /**
     * Default provided constructor. This implies that
     * {@link #exec(TerminalScanner, DataModel, User)} uses the projectList
     * provided by the given {@link DataModel}.
     */
    public GetProjectCmd() {
    }

    /**
     *
     * Create a GetProjectCmd with a specific list of projects used to provide
     * the user as a list of options during the select process.
     *
     * @see GetProjectCmd#setOptionsList(PList)
     */
    public GetProjectCmd(PList<Project> projectOptionList) {
        setOptionsList(projectOptionList);
    }

    /**
     * Set the project list used for the select process to projectOptionList.
     * When this list is set to null the list of projects from model will be
     * used during the select process.
     *
     * @param projectOptionList The new projectOptionList.
     */
    public void setOptionsList(PList<Project> projectOptionList) {
        this.specificList = projectOptionList;
    }

    private PList<Project> specificList;

    /**
     * Get a Project chosen by the person by presenting him a list of projects.
     * The option list used will be determined by the list passed to
     * {@link #GetProjectCmd(purecollections.PList)} or
     * {@link #setOptionsList(purecollections.PList)}. When this list is set to
     * null the list of projects in the passed {@link DataModel} will be used.
     * <p>
     * <br>
     * 1. The system shows a list of existing projects. <br>
     * 2. The person selects an existing project of the list.
     *
     * @param scan The scanner used to interact with the person.
     * @param model The model used for model access.
     * @param dummy3 Doesn't matter
     * @return The chosen project.
     * @throws CancelException When the users wants to abort the current cmd
     * @see Cmd#exec(TerminalScanner, DataModel, User)
     */
    @Override
    public Project exec(TerminalScanner scan, DataModel model, User dummy3) throws CancelException {
        // show all projects

        PList<Project> projectList;
        if (specificList == null) {
            projectList = model.getProjectList();
        } else {
            projectList = specificList;
        }

        // Select project of list
        Project proj = new GetObjectOfListCmd<>(projectList,
                (u -> (u.getName() + " version: " + u.getVersionID().toString())),
                ((u, input) -> ((u.getName() + u.getVersionID()).toString().equals(input) ||
                        (u.getName() + " " + u.getVersionID()).toString().equals(input))))
                .exec(scan, model, null);

        scan.println("You have chosen:");
        scan.println(proj.getDetails());

        return proj;
    }

}
