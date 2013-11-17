/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

/**
 *
 * @author foxhound
 */
public class ServerFactory {
    
    private TCPServer server = new TCPServer();
    private ServerCommand command = new ServerCommand();
    private UserManagement management = new UserManagement();
    
    public void startServer() {
        
        server.setServerCommand(command);
        command.setUserManagement(management);                
        server.startServer();      
    }       
}
