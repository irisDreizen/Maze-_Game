package Server;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Server {
    private int port;
    private int listeningInterval;
    private IServerStrategy serverStrategy;
    private volatile boolean stop;
    private ThreadPoolExecutor threadPool;


    public Server(int port, int listeningInterval, IServerStrategy serverStrategy) {
        this.port = port;
        this.listeningInterval = listeningInterval;
        this.serverStrategy = serverStrategy;

        Configurations.OperateConfiguration();

    }

    public void start() {
        new Thread(() -> {
            runServer();
        }).start();
    }

    private void runServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(listeningInterval);
            this.threadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(Configurations.getNumberOfThreads());
            while (!stop) {
                try {
                    Socket clientSocket = serverSocket.accept(); // blocking call
                    Thread t = new Thread(()->handleClient(clientSocket));
                    threadPool.execute(t);
                } catch (SocketTimeoutException e) {
                  e.printStackTrace();
                }
            }
            threadPool.shutdown();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleClient(Socket clientSocket) {
        try {
            serverStrategy.serverStrategy(clientSocket.getInputStream(), clientSocket.getOutputStream());
            clientSocket.close();
        } catch (IOException e) {
           e.printStackTrace();
        }
    }

    public void stop() {
        stop = true;
    }
}
