package client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ThreadA extends Thread {
    
    //**************************** ATTRIBUTE ********************************
    private ChatApplication gui;
    
    private final String userName;
    
    //IP und Port benötigt, um sich mit einem Server zu connecten
    private final String SERVER_IP;
    private final int SERVER_PORT = 50_000;
    
    //TCP - Verbindungssocket
    private Socket clientSocket;
    
    //Ein - und Ausgabestreams
    private DataOutputStream outToServer;
    private BufferedReader inFromServer;
    
    private Map<String, String> userMap = new HashMap<>();
    
    private boolean userAccepted = false;
    
    //************************ KONSTRUKTOR **********************************
    public ThreadA(ChatApplication gui, String userName, String SERVER_IP) {
        this.gui = gui;
        this.userName = userName;
        this.SERVER_IP = SERVER_IP;
    }
    //************************* PUBLIC METHODEN *****************************
    
    @Override
    public void run() {
        try {
           //Mit Server connecten
           System.out.println("Verbindung zum Server herstellen --> Nachricht aus THREAD A");
           clientSocket = new Socket(SERVER_IP, SERVER_PORT);
           System.out.println("CONNECTEN ERFOLGREICH --> NACHRICHT AUS THREAD A");
            
           //Streams initialisieren
           outToServer = new DataOutputStream(clientSocket.getOutputStream());
           inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
           
           //Chatnamen an Server schicken --> BEISPIEL: NEW Flah
           writeToServer("NEW " + userName);
           
           //Antwort des Servers auslesen
//           if(readFromServer().equals("OK")) {
//               gui.userAccepted();
//               userAccepted = true;
//           } else {
//               gui.userNotAccepted();
//           }
           
           //User akzeptiert und nicht interrupted
           while(! isInterrupted() && userAccepted) {
               //Liste der aktuell angemeldeten Users anfordern
               writeToServer("INFO");
               //Antwort des Servers
               String answer = readFromServer();
               //Usersliste in der GUI aktualisieren
               this.sleep(5000);
           }
        } catch (UnknownHostException ex) {
            System.err.println(ex);
        } catch (IOException ex) {
            System.err.println(ex);
        } catch (InterruptedException ex) {
            this.interrupt();
        }
    }
    
    /**
     * Getter für Flag
     * @return boolean - true wenn der benutzerName acceptiert wurde, sonst false
     */
    public boolean userAccepted() {
        return false;
    }
    
    private void refreshUsersList(String usersList) {
        Map<String, String> usersMap = new HashMap<>();
        Scanner scanner = new Scanner(usersList);
        scanner.next(); //Liefert --> LIST
        
        int i = Integer.parseInt(scanner.next()); //Liefert --> ANZAHL der folgenden Tupel
        for(; i > 0; i--) {
            usersMap.put(scanner.next(), scanner.next());
        }
        
        
    }
           
    //********************** PRIVATE METHODEN ******************************
    private void writeToServer(String request) throws IOException {
        /* Sende eine Zeile zum Server */
        outToServer.writeBytes(request + '\n');
        System.out.println("TCP Client has sent the message: " + request);
    }

    private String readFromServer() throws IOException {
        /* Lies die Antwort (reply) vom Server */
        String reply = inFromServer.readLine();
        System.out.println("TCP Client got from Server: " + reply);
        return reply;
    }
}
