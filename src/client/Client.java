package client;

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
        a.interrupt();
        c.interrupt();
    }
    
    /**
     * Startet die Arbeitsthreads
     */
    public void startJob() {
        System.out.println("IN CLIENT ABFRAGE: " + a.userAccepted());
        if(a.userAccepted()) {
            c = new ThreadC(gui);
            c.start();
        } else {
            a.interrupt();
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
