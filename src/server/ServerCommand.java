/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.util.Scanner;

/**
 * In der Klasse  sind die  Befehle fuer unseren Server definiert
 * @author abg688
 */
public class ServerCommand {
    
    private static String ok = "OK\n";
    
    
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
            return Error.reasonEmpty;
        }
        
                
        String result = "";
        
        return result;
    }
    
    /**
     * Sendet den Clienten eine Liste, wie viele Benutzer eingeloggt sind, sowie die die Hostnamen und die Benutzernamem dazu
     * @return String
     */
    public String infoCommand() {
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
    
    /**
     * Diese Methode ueberprueft ob das Kommando 
     * @param String command  - Eingehendes Kommando von Clienten
     * @return  String
     */
    public String checkCommand(String command) {
        Scanner scanner = new Scanner(command);
        String firstPartOfcommand = scanner.next();
        firstPartOfcommand = firstPartOfcommand.toLowerCase();
        return firstPartOfcommand;
    }
    
    public String commandNotExisted() {
        return Error.reasonCommandNotExisted;
    }
    
    public static void main(String[] args) {
//        ServerCommand s = new ServerCommand();
//        System.out.println(s.checkCommand("NEW Huantochnter"));
        
    }
}
