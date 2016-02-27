package bugtrap03;

import bugtrap03.gui.terminal.Terminal;
import java.util.Scanner;

/**
 *
 * @author Group 03
 */
public class Main {

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        model = new DataModel();
        controller = new DataController(model);

        initDemoSystem(controller);

        Terminal terminal = new Terminal(controller);
        terminal.openView();
    }

    private static DataModel model;
    private static DataController controller;

    public static void initDemoSystem(DataController con) {
        model.createAdministrator("curt", "Frederick", "Sam", "Curtis");
        model.createIssuer("doc", "John", "Doctor");
        model.createIssuer("charlie", "Charles", "Arnold", "Berg");
        model.createDeveloper("major", "Joseph", "Mays");
        model.createDeveloper("maria", "Maria", "Carney");
        
        
    }

}
