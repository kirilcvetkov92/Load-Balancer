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

import java.io.*;
import java.net.*;

class TcpClient1 {

    public static void main(String argv[]) throws Exception {

        String sentence;
        String modifiedSentence;
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

        try {
            long startTime = System.nanoTime();
            for (int k = 0; k <5000; k++) {
                Socket clientSocket = new Socket();
                for (int i = 0; i < 3; i++) {

                    DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
                    BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                    sentence = "aaaaaaaaaaaaaaaaaaaaaaaaa"+'\n';

                    outToServer.write(sentence.getBytes(), 0, sentence.length());
                    modifiedSentence = inFromServer.readLine();
                }
                clientSocket.close();
            }

            long endTime = System.nanoTime();
            long duration = (endTime - startTime);
            System.out.println("Duration " + duration / 1000000000f);

        } catch (Exception e) {
            System.out.println(e);
        }

    }

}
