package xyz.morrisblog.webservercmd;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by morri on 17/4/17.
 */
public class Server {
    private ServerSocket serverSocket;
    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;
    private int port;

    /**
     * Constructor for initializing a Server
     *
     * @param port The port is which the server should listen to
     * @param ip   The ip is which the server runs on, default is 127.0.0.1
     */
    private Server(int port, byte[] ip) {
        this.port = port;

        try {
            serverSocket = new ServerSocket(port, 1, InetAddress.getByAddress(ip));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Factory method to get a new server using specified ip address
     *
     * @param port listening port
     * @param ip   listening ip
     * @return a new server listening ip:port
     */
    public static Server getNewServerByIp(int port, byte[] ip) {
        return new Server(port, ip);
    }

    /**
     * Factory method to get a new server using 127.0.0.1
     *
     * @param port listening port
     * @return a new server listening 127.0.0.1:port
     */
    public static Server getDefaultServer(int port) {
        return new Server(port, new byte[]{127, 0, 0, 1});
    }

    public void start() {
        while (true) {
            try {
                socket = serverSocket.accept();
                inputStream = socket.getInputStream();
                outputStream = socket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }
}
