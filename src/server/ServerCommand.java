/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

/**
 * In der Klasse  sind die  Befehle fuer unseren Server definiert
 * @author abg688
 */
public class ServerCommand {
    
    private static String ok = "OK\r\n";
    private static String error = "ERROR ";
    private static String reasonUser = "the user command is incorrect";
    private static String reasonEmpty = "Command wars empty";
    
    /**
     * Nimmt den Benutzername von Client entgegen mit den Zusatz new 
     * Der Client möchte sich unter dem angegeben Chat-Namen anmelden. Der Chat-Name darf keine
     * Sonderzeichen und Leerzeichen enthalten! 
     * Example: new Spiderman
     * Exception -> nap Spiderman (nap != new)
     * @return String
     */
    //TODO:
    public String newCommand(String command) {
        //Precondition
        if(command.isEmpty()) {
            return error + reasonEmpty;
        }
        
                
        String result = "";
        
        return result;
    }
    
    /**
     * Sendet den Clienten eine Liste, wie viele Benutzer eingeloggt sind, sowie die die Hostnamen und die Benutzernamem dazu
     * @return String
     */
    public String listCommand() {
        String result  = "";
        
        return result;
    }
    
    /**
     * Sendet an den Client Bye, loescht ihn aus der Teilnehmnerliste und schliesst die TCP-Verbindung zu Ihm
     * @return String
     */
    //TODO:
    public String byeComand() {
        String result = "BYE";
        
        return result;
    }
    
}
