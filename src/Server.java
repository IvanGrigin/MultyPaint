import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Server extends MessageListener {
    ArrayList<StreamWorker> postmans = new ArrayList<>();
    ArrayList<Line> linesOfServer = new ArrayList<>();
    ArrayList<String> textsOfServer = new ArrayList<>();


    public void run() throws IOException {
        ServerSocket server = new ServerSocket(10391);

        while (true) {
            Socket client = server.accept();
            StreamWorker postman = new StreamWorker(client.getInputStream(), client.getOutputStream());
            postman.addListener(this);
            postman.sendMessage("Welcome, this Server for you");
            for (int i = 0; i < textsOfServer.size(); i = i + 1){
                postman.sendMessage(textsOfServer.get(i));
            }
            postman.start();
            postmans.add(postman);

        }
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.run();
    }


    @Override
    public void onMessage(String text){
        textsOfServer.add(text);
        StringTokenizer tokenizer = new StringTokenizer(text);

        Point firstp = new Point();
        Point secondp = new Point();

        String token0 = tokenizer.nextToken();
        if (token0.equals("Segment")) {
            String token1 = tokenizer.nextToken();
            firstp.x = Integer.parseInt(token1);
            String token2 = tokenizer.nextToken();
            firstp.y = Integer.parseInt(token2);
            String token3 = tokenizer.nextToken();
            secondp.x = Integer.parseInt(token3);
            String token4 = tokenizer.nextToken();
            secondp.y = Integer.parseInt(token4);

            Line testline = new Line(firstp, secondp);
            linesOfServer.add(testline);
        }

        for (int i = 0; i < postmans.size(); i++) {
            StreamWorker postman1 = postmans.get(i);
            postman1.sendMessage(text);
        }
        System.out.println(text);
    }

    @Override
    public void onMessageAndPanel(String text, Panel MyPanel) throws IOException {

    }

    @Override
    public void onDisconnect() {
        System.out.println("Клиент разорвал соединение");
    }


}

