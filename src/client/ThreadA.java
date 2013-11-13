package client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.net.Socket;

public class ThreadA {
    
    //IP und Port benötigt, um sich mit einem Server zu connecten
    private final String SERVER_IP = "localhost";
    private final int SERVER_PORT = 50_000;
    
    //TCP - Verbindungssocket
    private Socket clientSocket;
    
    //Ein - und Ausgabestreams
    private DataOutputStream outToServer;
    private BufferedReader inFromServer;
    
//                //Mit Server connecten
//            clientSocket = new Socket(SERVER_IP, SERVER_PORT);
//            
//            //Streams initialisieren
//            outToServer = new DataOutputStream(clientSocket.getOutputStream());
//            inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
}
