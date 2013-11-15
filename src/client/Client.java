package client;

/**
 * Schnittstelle zwischen Arbeitsthreads und GUI
 */
public class Client {
    
    //*************************** ATTRIBUTE ***********************************
    private ThreadA a;
    private ThreadB b;
    private ThreadC c;
    
    private ChatApplication gui;
    private String ip;
    private String userName;
    
    //********************* KONSTRUKTOR ***************************************
    public Client(ChatApplication gui) {
        this.gui = gui;
    }
    
    //************************* PUBLIC METHODEN *******************************
    /**
     * Baut eine Verbindung zu einem Server auf
     * @param String userName - erwartet einen userNamen
     * @param ip - erwartet einen Hostnamen oder IP zu der eine Verbindung
     *             aufgebaut werden soll
     */
    public void connect(String userName, String ip) {
        this.ip = ip;
        this.userName = userName;
        a = new ThreadA(gui, userName, ip);
        a.start();
    }
    
    /**
     * Terminiert die laufenden Threads
     */
    public void interruptThreads() {
        //Falls nicht eingeloggt --> nicht auf button gedrückt --> connect nicht aufgerufen
        if(a != null) {
            a.interrupt();
        }
        
        //Falls nicht erfolgreich eingeloggt --> gui --> startJob nicht ausgeführt
        if(c != null) {
            c.interrupt();
        }
    }
    
    /**
     * Startet die Arbeitsthreads
     */
    public void startJob() {
        if(a.userAccepted()) {
            c = new ThreadC(gui);
            c.start();
        }
    }
    
    /**
     * Sendet eine Nachricht an alle angemeldeten Benutzer beim Server
     * @param String message  - erwartet eine Nachricht die gesendet werden soll
     */
    public void sendMessage(String message) {
        b = new ThreadB(gui, message, userName);
        b.start();
    }
}
