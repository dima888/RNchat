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
    
    public static String reasonUser = error + "the user command is incorrect ...\r\n";
    public static String reasonUserExists = error + "user allready exists ...\r\n";
    public static String reasonEmpty = error + "Command wars empty ...\r\n";
    public static String reasonCommandNotExisted = error + "Command not existed ...\r\n";
    public static String reasonUsernameToLong = error + "username max length = 20 ...\r\n";
    public static String reasonBlankInUsername = error + "blank in username is not allowed ...\r\n";
    public static String reasonSpecialCharacter = error + "special character or blank not allowed ...\r\n";
    public static String reasonNowAllowed = error + "command in this state not allowed ...\r\n";
}
