import java.io.IOException;

public abstract class MessageListener {


    public abstract void onMessage(String text) throws IOException;

    public abstract void onMessageAndPanel(String text, Panel MyPanel) throws IOException;


    public abstract void onDisconnect();

    public void onException(Exception e) {
        e.printStackTrace();
    }

}