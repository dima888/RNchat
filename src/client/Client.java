package client;

public class Client {
    
    //*************************** ATTRIBUTE ***********************************
    private ThreadA a;
    private ThreadB b;
    private ThreadC c;
    
    private ChatApplication gui;
    
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
        a = new ThreadA(gui, userName, ip);
        a.start();
    }
    
    /**
     * Terminiert die laufenden Threads
     */
    public void interruptThreads() {
        a.interrupt();
        b.interrupt();
        c.interrupt();
    }
    
    /**
     * Startet die Arbeitsthreads
     */
    public void startJob() {
        if(a.userAccepted()) {
            b = new ThreadB(gui);
            c = new ThreadC(gui);
            
            b.start();
            c.start();
        } else {
            a.interrupt();
            //System.err.println("Benutzername wurde nicht akzeptiert");
        }
    }
    
    //************************ PRIVATE METHODEN ******************************
}
