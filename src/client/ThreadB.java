package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Set;

public class ThreadB extends Thread {
    
    //**************************** ATTRIBUTE **********************************
    public final int SERVER_PORT = 50_001;
    public static final int BUFFER_SIZE = 1024;
    private DatagramSocket clientSocket; // UDP-Socketklasse
    private InetAddress serverIpAddress; // IP-Adresse des Zielservers
    
    private ChatApplication gui;
    private String message;
    private String userName;
    
    //*************************** KONSTRUKTOR *********************************
    public ThreadB(ChatApplication gui, String message, String userName) {
        this.gui = gui;
        this.message = message;
        this.userName = userName;
    }
    
    //************************** PUBLIC METHODEN ******************************
    @Override
    public void run() {
        try{
            //Set der Hostnames beschaffen
            Set<String> hostNames = gui.getHostnames();
            
            //Über Alle Hostnames iterieren und Nachricht schicken
            for(String hostname : hostNames) {
                clientSocket = new DatagramSocket();
                //IP wird ermitteln durch den Hostnamen
                serverIpAddress = InetAddress.getByName(hostname);
                writeToServer(userName + ": " + message);
                clientSocket.close();
            }
        } catch(IOException e) {
            System.err.println(e);
        }
    }
    
    //*********************** PRIVATE METHODEN ********************************
    private void writeToServer(String sendString) throws IOException {
        /* Sende den String als UDP-Paket zum Server */

        /* String in Byte-Array umwandeln */
        byte[] sendData = sendString.getBytes();

        /* Paket erzeugen */
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,
                serverIpAddress, SERVER_PORT);
        /* Senden des Pakets */
        clientSocket.send(sendPacket);

        System.out.println("UDP Client has sent the message: " + sendString);
    }
}
