/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pingingserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author seelyv
 */
public class PingingThread extends Thread{
    private Socket inSoc;
    private PrintWriter writeSock;     // Used to write data to socket
    private BufferedReader readSock;   

    public PingingThread(Socket soc)
    {
        inSoc = soc;
        try {
            writeSock = new PrintWriter(inSoc.getOutputStream(), true);
            readSock = new BufferedReader( new InputStreamReader(
                    inSoc.getInputStream() ) );
        } catch (IOException ex) {
            Logger.getLogger(PingingThread.class.getName()).log(Level.SEVERE,
                    null, ex);
        }

    }
    
    public void run()
    {
        boolean quitTime = false;
        while( !quitTime )
        {
           String inLine = readSock.readLine();
           String outLine = inLine + "HaHa!" ;
           writeSock.println( outLine );
           if( inLine.equals("quit"))
              quitTime = true;
        }
        try {
            inSoc.close();
        } catch (IOException ex) {
            Logger.getLogger(PingingThread.class.getName()).log(Level.SEVERE,
                    null, ex);
        }

    }
}
