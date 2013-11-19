/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author abg688
 */
public class TCPServer {
    
    private final int SEVER_PORT = 50_000;
    
    ServerSocket welcomeSocket;
    Socket connectionSocket;
    ServerCommand serverCommand;

    int counter = 0; //Anzahl der verbundenen Thread auf den server
    
           //*******************SETTER****************
    public void setServerCommand(ServerCommand serverCommand) {
        this.serverCommand = serverCommand;
    }
    
    public void startServer() {                        
        
        try {
            welcomeSocket = new ServerSocket(SEVER_PORT);
            
            //Server laeuft und wartet auf eine Verbindung 
            while(true) {
                
                System.out.println("TCP Server lauscht auf Port: " + SEVER_PORT);
                
                /*
                 * Blockiert MainThread, wartet auf Verbindungsanfrage --> nach
                 * Verbindungsaufbau Standard-Socket erzeugen und
                 * connectionSocket zuweisen
                 */
                connectionSocket = welcomeSocket.accept(); //Wartet hier, bis einer sich zum server verbindet 
                
                //Neuen Arbeits-Thread erzeugen und den Socket uebergeben
                (new TCPServerThread(++counter, connectionSocket, serverCommand)).start();
            }
                        
        } catch (IOException ex) {
            System.err.println(ex.toString());
        }
    }            
}

   class TCPServerThread extends Thread {
       
       private int threadNumber;
       private Socket socket;
       
       private BufferedReader inFromClient; //Liest Daten von Client ein 
       private DataOutputStream outToClient; //Daten senden zum Client
       
       boolean threadRunning = true; // Server laeuft so lange der Flag auf true ist
       boolean login = false;
       
       ServerCommand serverCommand;
       
       //******************Konstructor************************
       public TCPServerThread(int number, Socket socket, ServerCommand serverCommand) {
           this.threadNumber = number;
           this.socket = socket;
           this.serverCommand = serverCommand;
           this.serverCommand.currentHost = socket.getInetAddress().toString();
       }       
              
       @Override
       public void run() {
           System.out.println("Client Nummer: " + threadNumber + " bei getretten");
           String messageBuffer;
           try {
               /* Socket-Basisstreams durch spezielle Streams filtern */
               inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
               outToClient = new DataOutputStream(socket.getOutputStream());

               while (threadRunning) {
 
                  System.out.println("Server wartet auf eine Nachricht");
                   messageBuffer = convertStringToUTF8(readFromClient()); //Warte auf eingehende Nachricht von Client
                   System.out.println("Server hat Nachricht bekommen");


                   //Hier kommt die pruefung rein, ob der eingegebene Befehl von Client gueltig ist
                   checkCommand(messageBuffer);

               }

           } catch (IOException ex) {
               Logger.getLogger(TCPServerThread.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
       
       /**
        * Speichert eingehende Nachrichten in den Rueckgabewert der Methode ab
        * @return String
        */
       private String readFromClient() throws IOException {
               String request = inFromClient.readLine() + "\n";
               System.out.println("Client write to TCPServer: " + request);
               return request;
       }
       
       /**
        * Sendet eine Nachricht an einen Client 
        * @param String reply  - Die Nachricht, die an den Client gesendet wird
        * TODO: HIER KOMMT EINE EXCEPTION, WENN DER BYE BEFEHL AUSGEFUEHRT WIRD
        */
       private void writeToClient(String reply) throws IOException {
               outToClient.writeBytes(reply + "\n");
           System.out.println("TCPServer write to Client: " + reply);
       }
       
       /**
        * Prueft die Commandes nach ihrer Gueltigkeit
        * @param message 
        */
       private void checkCommand(String message) {                     
           try {
               String loginPuffer = "";
               if(login == false) {
                  switch(serverCommand.checkCommand(message)) {
                  case "new":  loginPuffer = (serverCommand.newCommand(message, socket)); writeToClient(loginPuffer); break;                    
//                  case "info":  writeToClient(Error.reasonNowAllowed); break;
                  case "info":  writeToClient(convertStringToUTF8(Error.reasonNowAllowed)); break;
                  case "bye":  writeToClient(convertStringToUTF8(Error.reasonNowAllowed)); break;                           
                  default: writeToClient(convertStringToUTF8(serverCommand.commandNotExisted()));
                 }
               } else {
                   switch(serverCommand.checkCommand(message)) {
                   case "new":  writeToClient(convertStringToUTF8(Error.reasonNowAllowed)); break;
                   case "info":  writeToClient(convertStringToUTF8(serverCommand.infoCommand())); break;
                   case "bye":  writeToClient(convertStringToUTF8(serverCommand.byeComand(socket))); socket.close();  break;                       
                   default: writeToClient(convertStringToUTF8(serverCommand.commandNotExisted()));
                    }
               }
               
               //User einloggen
               //if(loginPuffer.compareTo("OK\r\n") == 0) {
               if(loginPuffer.compareTo("OK") == 0) {
                   System.out.println("Setzte login auf true");
                   login = true;
               }
               System.out.println(login);

           } catch (IOException ex) {
               Logger.getLogger(TCPServerThread.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
       
       private String convertStringToUTF8(String string) {
        return new String(string.getBytes(), Charset.forName("UTF-8"));
       }
   }