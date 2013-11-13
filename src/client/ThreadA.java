package client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ThreadA extends Thread {
    
    //**************************** ATTRIBUTE ********************************
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
    public ThreadA(String userName, String SERVER_IP) {
        this.userName = userName;
        this.SERVER_IP = SERVER_IP;
    }
    //************************* PUBLIC METHODEN *****************************
    
    @Override
    public void run() {
        try {
           //Mit Server connecten
           clientSocket = new Socket(SERVER_IP, SERVER_PORT);
           System.out.println("CONNECTEN ERFOLGREICH --> NACHRICHT AUS THREAD A");
            
           //Streams initialisieren
           outToServer = new DataOutputStream(clientSocket.getOutputStream());
           inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
           
           //Chatnamen an Server schicken --> BEISPIEL: NEW Flah
           writeToServer("NEW" + userName);
           
           //Antwort des Servers auslesen
           if(readFromServer().equals("OK")) {
               userAccepted = true;
           }
           
           //User akzeptiert und nicht interrupted
           while(! isInterrupted() && userAccepted) {
               
           }
        } catch (UnknownHostException ex) {
            System.err.println(ex);
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
    
    /**
     * Getter für Flag
     * @return boolean - true wenn der benutzerName acceptiert wurde, sonst false
     */
    public boolean userAccepted() {
        return false;
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
