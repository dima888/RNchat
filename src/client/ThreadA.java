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

/**
 * Ist für die Kommunikation mit dem Server zuständig
 * Alle 5 Sekunden wird die aktuelle Teilnehmerliste vom Server
 * geholt und angezeigt
 */
public class ThreadA extends Thread {
    
    //**************************** ATTRIBUTE **********************************
    private ChatApplication gui;
    private final String userName;
    private Map<String, String> userMap = new HashMap<>();
    private boolean userAccepted = false;
    
    //IP und Port benötigt, um sich mit einem Server zu connecten
    private final String SERVER_IP;
    private final int SERVER_PORT = 50_000;
    
    //TCP - Verbindungssocket
    private Socket clientSocket;
    
    //Ein - und Ausgabestreams
    private DataOutputStream outToServer;
    private BufferedReader inFromServer;
    
    //*************************** KONSTRUKTOR *********************************
    public ThreadA(ChatApplication gui, String userName, String SERVER_IP) {
        this.gui = gui;
        this.userName = userName;
        this.SERVER_IP = SERVER_IP;
    }
    
    //************************** PUBLIC METHODEN ******************************
    @Override
    public void run() {
        try {
           //Mit Server connecten
           clientSocket = new Socket(SERVER_IP, SERVER_PORT);
            
           //Streams initialisieren
           outToServer = new DataOutputStream(clientSocket.getOutputStream());
           inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
           
           //Chatnamen an Server schicken --> BEISPIEL: NEW Flah
           writeToServer("NEW " + userName);
           String answer = readFromServer();
           
           //Antwort des Servers auslesen
           if(answer.equals("OK")) {
               userAccepted = true;
               gui.userAccepted();
           } else {
               gui.userDeclined(answer);
           }
           
            //User akzeptiert und nicht interrupted
            while ((!this.isInterrupted()) && userAccepted) {
                //Liste der aktuell angemeldeten Users anfordern
                writeToServer("INFO");
                //Antwort des Servers
                answer = readFromServer();
                //Aktuallisert die userList in der GUI
                refreshUsersList(answer);
                //5 Sekunden schlafen / warten
                this.sleep(5000);
            }
        } catch (UnknownHostException ex) {
            System.err.println("EXCEPTION IN THREAD A: " + ex);
        } catch (IOException ex) {
            gui.userDeclined("Verbindung zum Server nicht möglich!");
        } catch (InterruptedException ex) {
            try {
                writeToServer("BYE");
                this.interrupt();
            } catch (IOException ex1) {
                System.err.println("THREAD A KONNTE SICH NICHT ABMELDEN");
            }
        }
    }
    
    /**
     * Getter für Flag
     * @return boolean - true wenn der benutzerName acceptiert wurde, sonst false
     */
    public boolean userAccepted() {
        return userAccepted;
    }
           
    //*********************** PRIVATE METHODEN ********************************
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
    
    /**
     * Aktualisiert die Liste der angemeldeten Users in der GUI
     * @param String usersList - erwartet die Antwort des Servers auf die Anfrage
     * INFO 141.22.31.155
     */
    private void refreshUsersList(String usersList) {
        Map<String, String> usersMap = new HashMap<>();
        Scanner scanner = new Scanner(usersList);
        scanner.next(); //Liefert --> LIST
        int i = Integer.parseInt(scanner.next()); //Liefert --> ANZAHL der folgenden Tupel
        for(; i > 0; i--) {
            usersMap.put(scanner.next(), scanner.next());
        }
        
        gui.setUsers(usersMap);
    }
}
