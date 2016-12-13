/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pingingserver;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author seelyv
 */
public class PingingThread extends Thread{
    private DatagramSocket inSoc;
    private DatagramPacket request ;
    //private PrintWriter writeSock;     // Used to write data to socket
    //private BufferedReader readSock;   
    final private double LOSS_RATE = .3;
    final private int AVERAGE_DELAY = 100;//milliseconds

    public PingingThread(DatagramSocket soc,DatagramPacket req)
    {
        
        inSoc = soc;
        request = req;
        /* try {
        writeSock = new PrintWriter(inSoc.getOutputStream(), true);
        readSock = new BufferedReader( new InputStreamReader(
        inSoc.getInputStream() ) );
        } catch (IOException ex) {
        Logger.getLogger(PingingThread.class.getName()).log(Level.SEVERE,
        null, ex);
        }*/
    }

    
    public void run()
    {
        boolean quitTime = false;
        try {
            Random random = new Random(new Date().getTime());
            printRequestData();
            if (random.nextDouble() < LOSS_RATE)
            {
                System.out.println("reply not sent");
            }
            else{
                Thread.sleep((int) (random.nextDouble() * 2 * AVERAGE_DELAY));
                DatagramPacket request = new DatagramPacket(new byte[1024], 1024);
                InetAddress clientHost = request.getAddress();
                int clientPort = request.getPort();
                byte[] buf = request.getData();
                DatagramPacket reply = new DatagramPacket(buf, buf.length, clientHost, clientPort);
                inSoc.send(reply);
                System.out.println("   Reply sent.");
            }        
            //inSoc.close();
        } catch (InterruptedException ex) {
            Logger.getLogger(PingingThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PingingThread.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    private void printRequestData() throws IOException{
      byte[] buf = request.getData();
      ByteArrayInputStream bais = new ByteArrayInputStream(buf);
      InputStreamReader isr = new InputStreamReader(bais);
      BufferedReader br = new BufferedReader(isr);
      String line = br.readLine();
      System.out.println(
         "Received from " +
         request.getAddress().getHostAddress() +
         ": " +
         new String(line) );
    }
}
