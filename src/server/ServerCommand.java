/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.util.Map;
import java.util.Scanner;

/**
 * In der Klasse  sind die  Befehle fuer unseren Server definiert
 * @author abg688
 */
public class ServerCommand {
    
    private UserManagement userManagement;
    private static String ok = "OK\n";
    
    
    //******************SETTER*****************
    public void setUserManagement(UserManagement userManagement) {
        this.userManagement = userManagement;        
    }
    
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
        
        //Username aus den comamnd scheiden
        String username = getISecondPartFromCommand(command);
        
        //Pruefen ob username length > 20 ist
        if(username.length() > 20) {
            return Error.reasonUsernameToLong;
        }       
        
        //Nach Sonderzeichen pruefen
        int i;
//        for(i = 0; i < username.toCharArray().length; i++) {
//            if(username.toCharArray()[0] == u00FCbertrag) {
//                return Error.reasonBlankInUsername;
//            }
//        }                
        
        //Pruefen ob in username leerzeichen enthalten sind
        for(i = 0; i < username.toCharArray().length; i++) {
            if(username.toCharArray()[i] == 0x20) { 
                return Error.reasonBlankInUsername;
            }
        }             
        
        //Pruefen, ob user in unserer liste vorhanden ist
        if (userManagement.getAccountMap().isEmpty()) {
            userManagement.doUserInAccountMap(username, "whatever");
        } else {
            for (Map.Entry<String, String> accoutMap : userManagement.getAccountMap().entrySet()) {
                if (accoutMap.getKey().toLowerCase().compareTo(username.toLowerCase()) == 0) {
                    System.out.println("Fuege benutzer ein");
                    return Error.reasonUserExists;
                } else {
                    System.out.println("Fuege benutzer ein");
                    userManagement.doUserInAccountMap(username, "whatever");
                }
            }
        }

        
        return ok;
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
    
    /**
     * Hollt fuer uns den inhalt nach einen Schluesselwort(new ....)
     * @param command
     * @return 
     */
    private String getISecondPartFromCommand(String command) {
        String secondPart = "";
        Scanner scanner = new Scanner(command);
        scanner.next(); 
       String secondPartPsydo = scanner.nextLine();
       
       int i, j;
       for(i = 0; i < secondPartPsydo.toCharArray().length; i++) {
           if(secondPartPsydo.toCharArray()[i] != 0x20) {
               
               for(j = i; j < secondPartPsydo.toCharArray().length; j++) {
                   secondPart += secondPartPsydo.toCharArray()[j];
               }
               break;
           }
       }
       
        System.out.println(secondPart);
        return secondPart;
    }
    
    public String commandNotExisted() {
        return Error.reasonCommandNotExisted;
    }
    
    public static void main(String[] args) {
        ServerCommand serverCommand = new ServerCommand();
        UserManagement userManagement = new UserManagement();
        serverCommand.setUserManagement(userManagement);
        
    }
}
