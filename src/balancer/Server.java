/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package balancer;

import java.util.StringTokenizer;

/**
 *
 * @author nov
 */
public class Server {

    public String _ipAddress;
    public byte[] ipAddress;
    private byte[] macAddress;
    public int port;
    
    public Server(String ip, int port) {
        this._ipAddress = ip;
        this.port=port;
    }

    public void tokenizeIp(String _ipAddress) {
        StringTokenizer st = new StringTokenizer(_ipAddress, ".");
        ipAddress = new byte[st.countTokens()];
        int i = 0;
        while (st.hasMoreTokens()) {
            String s = st.nextToken();
            ipAddress[i++] = (byte)Integer.parseInt(s);
        }
    }

    public void tokenizeMac(String _MacAddress) {
        StringTokenizer st = new StringTokenizer(_MacAddress, "- :");
        macAddress = new byte[st.countTokens()];
        int i = 0;
        while (st.hasMoreTokens()) {
            macAddress[i++] =(byte)Integer.parseInt(st.nextToken(), 16); //hexadecimal MAc
        }
    }

    public byte[] getIpAddress() {
        return ipAddress;
    }

    public byte[] getMacAddress() {
        return macAddress;
    }
    public String toString()
    {
        return this._ipAddress+" "+this.port;
    }
}
