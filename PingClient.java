/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;

/**
 *
 * @author lamar
 */
public class PingClient 
{
    ServerSocket soc;
  
    public static void main(String[] args)
    {
       try
       {
          PingClient server = new PingClient();    
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
            DatagramSocket soc = new DatagramSocket();
            long TimeOut = System.currentTimeMillis();
            for(int i = 0; i < 10; i++)
            {
                //String message = "Ping" + i +" " + TimeOut + "\n";
               DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
               soc.send(packet);
               System.out.println("sent");
               //ClientInterface ping = new ClientInterface(addr, port, payload);
               DatagramPacket rPackets = new DatagramPacket(new byte[1024], 1024);
               soc.receive(rPackets);
               soc.setSoTimeout(1000);
            }
            
        }
        catch(Exception ex)
        {
            System.out.println("Error: " + ex);
        }
        
    }
}
