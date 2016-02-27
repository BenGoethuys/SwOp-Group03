package bugtrap03.gui.terminal;

import bugtrap03.DataController;
import java.util.Scanner;

/**
 *
 * @author Admin
 */
public class Terminal {
    
    
    public Terminal(DataController con) {
        this.con = con;
        this.parser = new CmdParser();
    }
    
    private DataController con;
    private CmdParser parser;
    
    public void openView() {
        
        //Login
        //TODO: Finish parser.getLoginCmd()
        
        Scanner scan = new Scanner(System.in);
        System.out.println(scan.hasNextInt());
        System.out.println(scan.hasNextLine());
        
        
        
    }
    
}
