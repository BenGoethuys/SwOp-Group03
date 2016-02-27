package bugtrap03.gui.terminal;

/**
 *
 * @author Admin
 */
public class CmdParser {
    
    
    
    public Cmd createCmd(String... command) {
        if(command == null || command.length == 0) {
            return new InvalidCmd();
        }
        
        switch(command[0].toLowerCase()) {
            case "login": 
                return new LoginCmd();
                
            case "createproject":
                return new CreateProjectCmd();
            
                
            default:
                return new InvalidCmd();       
        }

    }
    
    
}
