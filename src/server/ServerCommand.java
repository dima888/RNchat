/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * In der Klasse  sind die  Befehle fuer unseren Server definiert
 * @author abg688
 */
public class ServerCommand {
    
    private UserManagement userManagement;
//    private static String ok = "OK\r\n";
    private static String ok = "OK";
    
    String currentHost = "whatever";
    
    
    //******************SETTER*****************
    synchronized public void setUserManagement(UserManagement userManagement) {
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
    synchronized public String newCommand(String command, Socket socket) {
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
        
        //Nach Sonderzeichen oder Leerzeiche  pruefen             
        boolean SpecialCharacter = username.matches("[a-zA-Z0-9]+");
        if(SpecialCharacter != true) {
            return Error.reasonSpecialCharacter;
        }            
        
        //Pruefen, ob user in unserer liste vorhanden ist, wenn nicht fuege neuen hinzu
        Map<String, String> currentAccountMap = new HashMap();
        if (userManagement.getAccountMap().isEmpty()) {            
            currentAccountMap.put(username, socket.getInetAddress().toString());
        } else {
            for (Map.Entry<String, String> accoutMap : userManagement.getAccountMap().entrySet()) {                
                if (accoutMap.getKey().toLowerCase().compareTo(username.toLowerCase()) == 0) {
                    return Error.reasonUserExists;
                } else {
                    currentAccountMap.put(username, socket.getInetAddress().toString());
                }
            }                    
        }              
        userManagement.setAccountMap(currentAccountMap);
        return ok;
    }
    
    /**
     * Sendet den Clienten eine Liste, wie viele Benutzer eingeloggt sind, sowie die die Hostnamen und die Benutzernamem dazu
     * @return String
     */
    synchronized public String infoCommand() {
        String result  = "LIST " + userManagement.getNumberOfUsers() + " ";        
        for(Map.Entry<String, String> account : userManagement.getAccountMap().entrySet()) {            
            result += account.getValue() + " ";             
            result += account.getKey() + " "; 
        }
//        return result + "\r\n";
        return result;
    }
    
    /**
     * Sendet an den Client Bye, loescht ihn aus der Teilnehmnerliste und schliesst die TCP-Verbindung zu Ihm
     * @return String
     * TODO: LOESCHT WIRKUERLICH, MUSS NOCH DEN RICHTIGEN ZUM LOESCHEN AUS DER LISTE ERMITTELN
     */
    synchronized public String byeComand(Socket socket) throws IOException {        
        String result = "BYE \r\n";
        
        for(Map.Entry<String, String> accountMap : userManagement.getAccountMap().entrySet()) {
            Map<String, String> currentAccountMap = new HashMap();
            if(accountMap.getValue().compareTo(socket.getInetAddress().toString()) == 0) { 
                userManagement.getAccountMap().remove(accountMap.getKey());                
                break;
            }            
        }        
        return result;
    }
    
    /**
     * Diese Methode ueberprueft ob das Kommando 
     * @param String command  - Eingehendes Kommando von Clienten
     * @return  String
     */
    synchronized public String checkCommand(String command) {
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
        return secondPart;
    }
    
    /**
     * Braucht mal fuer die switch case in klasse TCPServer
     * @return 
     */
    public String commandNotExisted() {
        return Error.reasonCommandNotExisted;
    }
    
    public static void main(String[] args) {
        ServerCommand serverCommand = new ServerCommand();
        UserManagement userManagement = new UserManagement();
        serverCommand.setUserManagement(userManagement);     
        
    }
}
