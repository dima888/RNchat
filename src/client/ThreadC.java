package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ThreadC extends Thread {

    //**************************** ATTRIBUTE **********************************
    private final int SERVER_PORT = 50_001;
    private final int BUFFER_SIZE = 1024;
    private DatagramSocket serverSocket; // UDP-Socketklasse
    private InetAddress receivedIPAddress; // IP-Adresse des Clients
    private int receivedPort; // Port auf dem Client
    
    private ChatApplication gui;
    
    //*************************** KONSTRUKTOR *********************************
    public ThreadC(ChatApplication gui) {
        this.gui = gui;
    }

    //************************** PUBLIC METHODEN ******************************
    @Override
    public void run() {
        try {
            //UDP-Socket erzeugen (KEIN VERBINDUNGSAUFBAU!)
            //Socket wird an den ServerPort gebunden
            serverSocket = new DatagramSocket(SERVER_PORT);
            System.out.println("UDP Server: Waiting for connection - listening UDP port "
                    + SERVER_PORT);

            while (!isInterrupted()) {
                String message = readFromClient();
                gui.showReceivedMessage(message);
            }
        } catch (SocketException ex) {
            Logger.getLogger(ThreadC.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ThreadC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //*********************** PRIVATE METHODEN ********************************
    private String readFromClient() throws IOException {
        /* Liefere den nächsten String vom Server */
        String receiveString = "";

        /* Paket für den Empfang erzeugen */
        byte[] receiveData = new byte[BUFFER_SIZE];
        DatagramPacket receivePacket = new DatagramPacket(receiveData, BUFFER_SIZE);

        /* Warte auf Empfang eines Pakets auf dem eigenen Server-Port */
        serverSocket.receive(receivePacket);

        /* Paket erhalten --> auspacken und analysieren */
        receiveString = new String(receivePacket.getData(), 0,
                receivePacket.getLength());
        receivedIPAddress = receivePacket.getAddress();
        receivedPort = receivePacket.getPort();

        System.out.println("UDP Server got from Client: " + receiveString);

        return receiveString;
    }
}
