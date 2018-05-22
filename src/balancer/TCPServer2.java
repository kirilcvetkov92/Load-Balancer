/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package balancer;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Kiril
 */
public class TCPServer2 {
    public static void main(String argv[]) throws Exception {
        ServerSocket welcomeSocket = new ServerSocket(2222);
        while (true) {
            Socket connectionSocket = welcomeSocket.accept();
            ParallelTask parallelTask = new ParallelTask(connectionSocket, 2);
            parallelTask.start();
        }
    }
}
