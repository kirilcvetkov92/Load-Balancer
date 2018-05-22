package balancer;

import java.io.*;
import java.net.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Kiril Cvetkov
 */

public class Balancing extends Thread {

    private Socket balancerSocket;
    public Socket serverSocket;
    private String serverHost;
    private int serverPort;
    private boolean forwardingProcessActive = false;

    public Balancing(Socket balancerSocket, String serverHost, int serverPort) {
        this.balancerSocket = balancerSocket;
        this.serverHost = serverHost;
        this.serverPort = serverPort;
    }

    public void run() {
        InputStream balancerIn;
        OutputStream balancerOut;
        InputStream serverIn;
        OutputStream serverOut;
        try {
            if (NodeManager.getInstance().getSocketList().isEmpty()) {
                serverSocket = new Socket();
                serverSocket.connect(new InetSocketAddress(serverHost, serverPort));
                serverSocket.setKeepAlive(true);
            } else {
                serverSocket = NodeManager.getInstance().getSocketList().poll();
            }

            balancerSocket.setKeepAlive(true);

            balancerIn = balancerSocket.getInputStream();
            balancerOut = balancerSocket.getOutputStream();

            serverIn = serverSocket.getInputStream();
            serverOut = serverSocket.getOutputStream();
        } catch (IOException ioe) {
            try {
                this.close();
            } catch (IOException ex) {
                Logger.getLogger(Balancing.class.getName()).log(Level.SEVERE, null, ex);
            }
            return;
        }

        forwardingProcessActive = true;

        PackageForwarding balancerForward = new PackageForwarding(this, balancerIn, serverOut, "bIn+Sout");
        balancerForward.start();

        PackageForwarding serverForward = new PackageForwarding(this, serverIn, balancerOut, "sIn+bOut");
        serverForward.start();
    }

    public synchronized void close() throws IOException {
        balancerSocket.close();

        if (forwardingProcessActive) {
            String msg = "Balancer: " + getBalancerSocket().getLocalPort() + " " + getBalancerSocket().getPort() + " \nServer:"
                    + getServerSocket().getLocalPort() + " " + getServerSocket().getPort() + "\n\n";
            NodeManager.getInstance().writeLog(msg);
            forwardingProcessActive = false;
        }
    }

    public int getServerPort() {
        return this.serverPort;
    }

    public Socket getServerSocket() {
        return this.serverSocket;
    }

    public Socket getBalancerSocket() {
        return this.balancerSocket;
    }
}
