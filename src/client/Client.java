package client;

public class Client {
    
    private ThreadA a;
    private ThreadB b;
    private ThreadC c;
    
    private ChatApplication gui;
    
    public Client(ChatApplication loginGUI) {
        this.gui = gui;
    }
    
    public void connect(String userName, String ip) {
        a = new ThreadA(gui, userName, ip);
        a.start();
    }
    
     public void interruptThreads() {
        a.interrupt();
        b.interrupt();
        c.interrupt();
    }
    
    private void startJob() {
        a.start();
        if(a.userAccepted()) {
            b.start();
            c.start();
        } else {
            a.interrupt();
            System.err.println("Benutzername wurde nicht akzeptiert");
        }
    }
}
