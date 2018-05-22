/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package balancer;

/**
 *
 * @author Kiril
 */

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;


class ParallelTask extends Thread {

    Socket connectionSocket;
    String clientSentence;
    String capitalizedSentence;
    int number;

    public ParallelTask (Socket s, int number) {
        this.connectionSocket = s;
        this.number = number;
    }

    public void run() {
        while (true) {
            BufferedReader inFromClient = null;
            try {
                inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
                clientSentence = inFromClient.readLine();
                if (clientSentence != null) {
                    System.out.println("Received: " + clientSentence);
                    capitalizedSentence = clientSentence.toUpperCase() + (number) + '\n';
                    outToClient.writeBytes(capitalizedSentence);
                } else {
                    connectionSocket.close();
                    break;
                }
            } catch (IOException ex) {
                Logger.getLogger(ParallelTask .class.getName()).log(Level.SEVERE, null, ex);
            } finally {
            }
        }
    }
}

public class TCPServer1 {
    public static void main(String argv[]) throws Exception {

        ServerSocket welcomeSocket = new ServerSocket(1111);
        while (true) {
            Socket connectionSocket = welcomeSocket.accept();
            ParallelTask  k = new ParallelTask(connectionSocket, 1);
            k.start();
        }

    }
}
