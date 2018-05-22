/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package balancer;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Kiril
 */
public class NodeManager {

    private Queue<Integer> portList;
    private static NodeManager instance = null;
    private Queue<Socket> socketList;

    PrintWriter writer;

    private NodeManager() throws FileNotFoundException, UnsupportedEncodingException {
        portList = new LinkedList<Integer>();
        socketList = new LinkedList<Socket>();
    }

    public static synchronized NodeManager getInstance() throws FileNotFoundException, UnsupportedEncodingException {
        if (instance == null) {
            instance = new NodeManager();
        }
        return instance;
    }

    public void writeLog(String s) throws FileNotFoundException, UnsupportedEncodingException, IOException {
        writer = new PrintWriter(new BufferedWriter(new FileWriter("output.txt", true)));
        writer.append(s);
        writer.close();
    }

    public void terminateLog(String s) {
        writer.close();
    }

    public Queue<Integer> getPortList() {
        return this.portList;
    }

    public Queue<Socket> getSocketList() {
        return this.socketList;
    }

}
