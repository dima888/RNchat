/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

/**
 *
 * @author abg688
 */
public class Error {
    
    private static String error = "ERROR ";
    
    public static String reasonUser = error + "the user command is incorrect";
    public static String reasonUserExists = error + "user allready exists";
    public static String reasonEmpty = error + "Command wars empty";
    public static String reasonCommandNotExisted = error + "Command not existed";
}
