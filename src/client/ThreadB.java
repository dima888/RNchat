package client;

public class ThreadB extends Thread {

    private ChatApplication gui;
    
    public ThreadB(ChatApplication gui) {
        this.gui = gui;
    }
    
    @Override
    public void run() {
    }
}
