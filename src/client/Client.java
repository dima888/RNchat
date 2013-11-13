package client;

public class Client {
    
    private ThreadA a;
    private ThreadB b;
    private ThreadC c;
    
    private boolean userAccepted = false;
    
    public Client(String userName, String ip) {
        a = new ThreadA(userName, ip);
        b = new ThreadB();
        c = new ThreadC();
        startJob();
    }
    
    private void startJob() {
        a.start();
        if(a.userAccepted()) {
            userAccepted = true;
            b.start();
            c.start();
        } else {
            a.interrupt();
        }
    }
    
    public void interruptThreads() {
        a.interrupt();
        b.interrupt();
        c.interrupt();
    }
    
    public boolean getUserAccepted() {
        return userAccepted;
    }
}
