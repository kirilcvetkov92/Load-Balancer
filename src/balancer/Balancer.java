/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package balancer;

import java.io.*;
import java.net.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Balancer {

    public int balancerPort;
    public String balancerHost;

    public Balancer(int balancerPort, String balancerHost) {
        this.balancerPort = balancerPort;
        this.balancerHost = balancerHost;
    }

    public String getBalancerHost() {
        return balancerHost;
    }

    public void setBalancerHost(String balancerHost) {
        this.balancerHost = balancerHost;
    }

    public int getBalancerPort() {
        return balancerPort;
    }

    public void setBalancerPort(int balancerPort) {
        this.balancerPort = balancerPort;
    }

    public static void main(String[] args) throws IOException {

        Queue<Server> serverNodes = new LinkedList<Server>();

        BufferedReader in = new BufferedReader(new FileReader("config"));
        in.readLine();

        String serverIp = in.readLine();
        int portNumber = Integer.parseInt(in.readLine());

        in.readLine();
        Balancer balancer = new Balancer(portNumber, serverIp);

        while (in.ready()) {
            serverNodes.add(new Server(in.readLine(), Integer.parseInt(in.readLine())));
        }
        in.close();
        
        ServerSocket socket = new ServerSocket(balancer.balancerPort);
        for (;;) {
            Socket balancerSocket = socket.accept();
            Server server = serverNodes.poll();

            serverNodes.add(server);
            String serverHost = server._ipAddress;

            int serverPort = server.port;

            Balancing thread = new Balancing(balancerSocket, serverHost, serverPort);
            thread.start();
        }
    }
}
