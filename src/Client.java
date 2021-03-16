
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Client extends MessageListener {

    ArrayList<Line> lines = new ArrayList<>();
    ArrayList<Line> alllines = new ArrayList<>();
    Panel MyPanel = new Panel();

    Point firstPoint;
    Point secondPoint;
    int kolichestvo = 0;

    public static void main(String[] args) throws IOException {
        Client client = new Client();
        client.start(client.MyPanel, client);
    }

    public void start(Panel MyPanel, Client c) throws IOException {

        if (lines.size() > 0) {
            MyPanel.lines.addAll(lines);
            lines.clear();
        }
        String host = "127.0.0.1";
        int port = 10391;
        Socket socket = new Socket(host, port);

        StreamWorker postman = new StreamWorker(socket.getInputStream(), socket.getOutputStream());
        postman.addListener(this);
        postman.start();

        MyPanel.setPostman(postman);

        //int i = 0;
        while (true) {

/*
           // for (int i = 0; i < MyPanel.lines.size(); i++) {
            if ((MyPanel.lines.size() > i)&&(MyPanel.lines.get(i) != null)){
                postman.sendMessage("Segment "
                        + MyPanel.lines.get(i).firstPoint.x
                        + " "
                        + MyPanel.lines.get(i).firstPoint.y
                        + " "
                        + MyPanel.lines.get(i).secondPoint.x
                        + " "
                        + MyPanel.lines.get(i).secondPoint.y);
                i = i + 1;
          }
            if (lines.size() > 0) {
                MyPanel.lines.addAll(lines);
                lines.clear();
            }
            MyPanel.drawMe();
            System.out.println(kolichestvo);
  */
        }


    }

    @Override
    public void onMessage(String text) {
        System.out.println(text);
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
            MyPanel.lines.add(testline);
            MyPanel.drawMe();
        }

        if (token0.equals("Segments")) {
            while (true) {
                String token1 = tokenizer.nextToken();
                firstp.x = Integer.parseInt(token1);
                String token2 = tokenizer.nextToken();
                firstp.y = Integer.parseInt(token2);
                String token3 = tokenizer.nextToken();
                secondp.x = Integer.parseInt(token3);
                String token4 = tokenizer.nextToken();
                secondp.y = Integer.parseInt(token4);

                Line testline = new Line(firstp, secondp);
                lines.add(testline);
            }
        }


    }

    @Override
    public void onMessageAndPanel(String text, Panel MyPanel) throws IOException {
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
            MyPanel.lines.add(testline);

        }
    }

    @Override
    public void onDisconnect() {

    }

    public void addNewLine(Panel MyPanel, Line line) {
        MyPanel.lines.add(line);
    }

    public void addNewLines(Panel MyPanel, ArrayList<Line> lines) {
        MyPanel.lines.addAll(lines);
    }
}