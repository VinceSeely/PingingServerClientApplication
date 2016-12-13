/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pingingserver;

/**
 *
 * @author seelyv rajlamar
 */
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
public class PingingServer {

    /**
     * @param args the command line arguments
     */
    ServerSocket serverSocket;
    private final static int PORT_NUMBER = 5777;

    public static void main(String[] args) 
    {
        try
        {
          PingingServer server = new PingingServer ();    
          server.run();
 
        }
        catch(Exception ex)
        {
           System.out.println("Error: " + ex);
        }
        
    }
    public void run()
    {
        try
        {
            DatagramSocket serverSocket = new DatagramSocket(PORT_NUMBER);
            while(true)
            {
               DatagramPacket request = new DatagramPacket(new byte[1024], 1024);
               serverSocket.receive(request);
               PingingThread ping = new PingingThread(serverSocket, request);
               ping.start();
            }
        }
        catch(Exception ex)    
        {
           System.out.println("Error: " + ex);
        }
    }
    
}
