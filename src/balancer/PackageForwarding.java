/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package balancer;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author nov
 */
public class PackageForwarding extends Thread {

    private static final int BUFFER_SIZE = 65536;
    InputStream input;
    OutputStream output;
    Balancing thread;
    int packet;
    String s;

    public PackageForwarding(Balancing thread, InputStream input, OutputStream output, String s) {
        this.input = input;
        this.output = output;
        this.thread = thread;
        packet = 0;
        this.s = s;
    }

    public void run() {
        try {
            try {
                for (;;) {
                    byte[] bufferPackage = new byte[BUFFER_SIZE];
                    packet = input.read(bufferPackage);
                    if (packet == -1) {
                        NodeManager.getInstance().getSocketList().add(thread.getServerSocket());
                        NodeManager.getInstance().getPortList().add(thread.getServerPort());
                        break;
                    }
                    output.write(bufferPackage, 0, packet); //1.ReadServer   2.WriteBalancer
                    output.flush();
                }
            } catch (IOException ex) {
                Logger.getLogger(PackageForwarding.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.thread.close();

        } catch (IOException ex) {
            Logger.getLogger(PackageForwarding.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
