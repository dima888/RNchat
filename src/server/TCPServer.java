/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author abg688
 */
public class TCPServer {
    
    private final int SEVER_PORT = 50_000;
    
    
    public static void main(String[] args) {
        ServerSocket welcomeSocket;
        Socket connectionSocket;        
        int counter = 0; //Anzahl der verbundenen Thread auf den server
        
        try {
            welcomeSocket = new ServerSocket(50_000);
            
            //Server laeuft und wartet auf eine Verbindung 
            while(true) {
                
                connectionSocket = welcomeSocket.accept(); //Wartet hier, bis einer sich zum server verbindet 
                
                //Neuen Arbeits-Thread erzeugen und den Socket uebergeben
                (new TCPServerThread(++counter, connectionSocket)).start();
            }
            
            
        } catch (IOException ex) {
            System.err.println(ex.toString());
        }
    }
    
    
}
   class TCPServerThread extends Thread {
       
       private int threadNumber;
       private Socket socket;
       
       private BufferedReader inFromClient;
       private DataOutputStream outToClient;
       
       boolean threadRunning = true; //Bei true arbeitet der Thread, bei false wird er beendet 
       
       ServerCommand serverCommand;
       
       //******************Konstructor************************
       public TCPServerThread(int number, Socket socket) {
           this.threadNumber = number;
           this.socket = socket;
       }
       
       //*******************SETTER****************
       public void setServerCommand(ServerCommand serverCommand) {
           this.serverCommand = serverCommand;
       }
       
       
       @Override 
       public void run() {
           String messageBuffer = "";
           
           while(threadRunning) {
                                             
               //Hier kommt die pruefung rein, ob der eingegebene Befehl von Client gueltig ist
               switch(messageBuffer) {
                   case "new":  serverCommand.newCommand(messageBuffer); break;
                       
//                   default: "c";
               }
               
           }
       }
       
       /**
        * Speichert eingehende Nachrichten in den Rueckgabewert der Methode ab
        * @return String
        */
       private String readFromClient() throws IOException {
           String request = inFromClient.readLine();
           System.out.println("Client write to TCPServer: " + request);
           return request;
       }
       
       /**
        * Sendet eine Nachricht an einen Client 
        * @param String reply  - Die Nachricht, die an den Client gesendet wirdS
        */
       private void writeToClient(String reply) throws IOException {
           outToClient.writeBytes(reply + "\n");
           System.out.println("TCPServer write to Client: " + reply);
       }
   }