package bugtrap03.gui.terminal;

import java.util.Scanner;

/**
 *
 * @author Admin
 */
public class Terminal {
    
    private static final String WELCOME_MESSAGE = "Welcome ";
    
    public void openView() {
        
        Scanner scan = new Scanner(System.in);
        System.out.println(scan.hasNextInt());
        System.out.println(scan.hasNextLine());
        
        
        
    }
    
}
