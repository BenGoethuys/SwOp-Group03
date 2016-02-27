package bugtrap03;

import bugtrap03.gui.terminal.Terminal;
import bugtrap03.usersystem.Administrator;

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
        DataModel model = new DataModel();
        DataController controller = new DataController(model);
        
        initDemoSystem(controller);
        
        Terminal terminal = new Terminal(controller);
        terminal.openView();
    }
    
    public static void initDemoSystem(DataController con) {
        
        
        
        
    }

            
            
}
