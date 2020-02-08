// Class: 	socketUtils.java - part of Airline Client
// Description: This class deals with sending and recieving messages to and from the server
//		and provides functions used by the other classes.

package pckgOne;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

public class socketUtils 
{
/////////////////// Global Objects //////////////////////
	Socket clientSocket=null;
    DataOutputStream outToServer=null;
    BufferedReader inFromServer=null;

///////////////// Socket Connect Check //////////////////
	public boolean socketConnect()
	{
		String ipAddress, portString;
		int portNumber;
		boolean rc=false;
		
		try 
		{
			File file = new File("config.txt");
	        if (file.exists())
	        {
	           BufferedReader br = new BufferedReader(new FileReader("config.txt"));
	           
               ipAddress  = br.readLine();
               portString = br.readLine();
                          
               portNumber = Integer.parseInt(portString);
               br.close();
	        }
	        else
	        {
	           ipAddress  = "localhost";
	           portNumber = 3333;
	        }
  
           clientSocket  = new Socket(ipAddress, portNumber);
           // added
           System.out.println("Connected");
           
           outToServer   = new DataOutputStream(clientSocket.getOutputStream());
           inFromServer  = new BufferedReader(
   	                       new InputStreamReader(clientSocket.getInputStream()));
           
           rc= true;
		}
		catch (ConnectException ex)
		{
			ex.printStackTrace();
		}					
		catch (UnknownHostException ex)
	    {
			ex.printStackTrace();
	    }
		catch (IOException ex) 
	    {
			ex.printStackTrace();
	    }
		
		return rc;
	}
////////////////// Connect Check End ////////////////////

////////////////// Message Send Check ///////////////////
	public boolean sendMessage(String msg)
	{
		boolean rc=false;
		
		try 
		{
			outToServer.writeBytes(msg + "\r\n");
			rc = true;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return rc;
	}
////////////////// Message End Check ////////////////////
	
/////////////////// Message Read ////////////////////////
	public String recvMessage()
	{
		String msg=null;
		
		try
		{
			msg = inFromServer.readLine();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return msg;
	}
///////////////////// Read End //////////////////////////
	
///////////////// Socket Close Check ////////////////////
	public boolean closeSocket()
	{
		boolean rc=false;
		
		try
		{
			clientSocket.close();
            rc=true;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		return rc;
	}
/////////////////// Close Check End /////////////////////
}
