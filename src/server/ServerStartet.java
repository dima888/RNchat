/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

/**
 *
 * @author foxhound
 */
public class ServerStartet {
    
    public static void main(String[] args) {
        ServerFactory serverFactory = new ServerFactory();
        
        serverFactory.startServer();
    }
}
