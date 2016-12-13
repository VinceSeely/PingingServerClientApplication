/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author seelyv
 */
public class ClientInterface {
    private final static int SERVER_SOCKET_NUM = 5777;
    private static int requestNum = 0;
    private final static int numberRequests = 10;
    private final static int TIME_OUT = 1000;
    private static long startTime;
    private int minRTT = 100000;
    private int maxRTT = -1;
    private int avgRTT = 0;
    
    public static void main(String[] args)
    {
        while (requestNum < numberRequests){
            PingMessage(getIP(), getPort(), getPayload());
        }
    }
    
    public static void PingMessage(InetAddress addr, int port, String payload)   {
        try {
            DatagramSocket socket = new DatagramSocket(5555);
            socket.setSoTimeout(TIME_OUT);
            String packageData = addr + " " + port + " " + payload;
            DatagramPacket temp = new DatagramPacket(new byte[packageData.length()], packageData.length());
            temp.setData(packageData.getBytes());
            socket.send(temp);
        } catch (SocketException ex) {
            Logger.getLogger(ClientInterface.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ClientInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static InetAddress getIP() //get the destination IP address 
    {
        try {
            return InetAddress.getLocalHost();
        } catch (UnknownHostException ex) {
            Logger.getLogger(ClientInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static int getPort()
    {
        return SERVER_SOCKET_NUM;
    }
    
    public static String getPayload(){
        Date date = new Date();
        startTime = date.getTime();
        String  retval = "PING" + " " + requestNum + " " + startTime;
        requestNum++;
        return retval;
    }
            
}
