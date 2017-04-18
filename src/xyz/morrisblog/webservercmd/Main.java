package xyz.morrisblog.webservercmd;

public class Main {

    public static void main(String[] args) {
        // write your code here
        Server server = Server.getDefaultServer(3000);
        server.start();
    }
}
