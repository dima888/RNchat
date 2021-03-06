/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author abg688
 */
public class UserManagement {
  //Wenn der User in try catch raus fliegt, dann soll er auch aus der hashmap entfernt werden
    //alt gedrueckt halten und  404  eintioppen, geht das Programm kaputt
    //Problemloesung: String zur , byte array casten, uebergeben und konventieren auf utf8 
    //TODO: Pruefen ob user doppelt vorhanden ist
    private Map<String, String> accountMap = new HashMap();
    
    //************GETTER************
    synchronized Map<String, String> getAccountMap() {                        
        return accountMap;                
    }
    
    //***********SETTER**************
    synchronized  void setAccountMap(Map<String, String> newAccountMap) {
        accountMap.putAll(newAccountMap);
    }
    
    /**
     * Tut einen User in unsere accountMap rein, wenn der noch nicht in der accountMap vorhanden ist
     * @param String user  - User der auf unseren Server connected
     */
    synchronized void doUserInAccountMap(String user, String hostname) {
        accountMap.put(user, hostname) ;    
    }
    
    /**
     * Gibt der Anzahl der user aus
     * @return 
     */
     synchronized public int getNumberOfUsers() {
        return accountMap.size();
    }
}
