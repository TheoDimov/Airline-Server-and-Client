// Group:  	   	Ilir Asllani, Theo Dimov, Leonny Correa
// Course:     	Software Engineering
// Assignment: 	Project - Fordham Airlines
// Class: 		airlinesocket.java - part of Server Client
// Description: Provides the connections and updates data for the main server from messages from clients. 

package FUAirlineServer;

import java.io.IOException;
import java.io.BufferedReader;
import java.net.BindException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.SocketException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;
import java.io.InputStreamReader;
import java.io.PrintStream;

public class airlinesocket implements Runnable
{
	////////////////////Global Variables Declaration ///////////////////////
	Socket csocket;
	String ipString;
	char threadType;
	static Vector<String> vec = new Vector<String>(5);  
 
	public static Hashtable<String, airkiosk> clients = new Hashtable<String, airkiosk>();
	  
	static final String newline = "\n";
	static int first_time = 1;
	static int port_num = 3333;
	static int numOfConnections = 0;
	static int numOfMessages = 0;
	static int max_connections = 5;
	static int numOfTransactions = 0; 
	static double profit = 0.0;
	static int flightNum = 0;
	static int[] seats = new int[]{0,0,0,0,0,0};

	   
	////////////////////// Socket Constructor /////////////////////////////////
	airlinesocket(Socket csocket, String ip) 
	{
		this.csocket  = csocket;
		this.ipString = ip;
	}
	////////////////////// Constructor End ////////////////////////////////////

	////////////////////// Socket Server Run //////////////////////////////////
	public static void runSockServer()   // throws Exception
	{
	   boolean sessionDone = false;  
	   ServerSocket ssock = null;
		
	   // Opening
	   try {
			ssock = new ServerSocket(port_num);
	   } catch (BindException e) {
		   	e.printStackTrace();
	   } catch (IOException e) {
			e.printStackTrace();
	   }
	   
		// Update the status text area to show progress of program
		try {
			InetAddress ipAddress = InetAddress.getLocalHost();
			mainServer.textArea.append("IP Address : " + ipAddress.getHostAddress() + newline);
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}

		mainServer.textArea.append("Listening on port " + port_num + newline);
		mainServer.textArea.setCaretPosition(mainServer.textArea.getDocument().getLength());
		mainServer.textArea.repaint();

		// initialize the hash table
		clients.put("airkiosk#1", new airkiosk("airkiosk#1", 0, 0, 0.0));
		clients.put("airkiosk#2", new airkiosk("airkiosk#2", 0, 0, 0.0));
		clients.put("airkiosk#3", new airkiosk("airkiosk#3", 0, 0, 0.0));
		
		sessionDone = false;
		while (sessionDone == false) 
		{
			Socket sock = null;
			try {
				// blocking system call
				sock = ssock.accept();
			} catch (IOException e) {
				e.printStackTrace();
			}

			// update the status text area to show progress of program
			mainServer.textArea.append("Client Connected : " + sock.getInetAddress() + newline);
			mainServer.textArea.setCaretPosition(mainServer.textArea.getDocument().getLength());
			mainServer.textArea.repaint();

			new Thread(new airlinesocket(sock, sock.getInetAddress().toString())).start();
		}

		// Closing
		try {
			ssock.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	 
	////////////////////// Socket Server End //////////////////////////////////
	
	//////////////////////Hash Operation Method //////////////////////////////
	static synchronized void hashOperation(char type, String key, String ticks, String d) 
	{
		switch (type) 
		{
			case 'T':
				if (clients.containsKey(key) == true) 
				{
					clients.get(key).incrementTrans();
					clients.get(key).addTickets(Integer.parseInt(ticks));
					clients.get(key).addDollars(Double.parseDouble(d));
				}
			break;
		}
	}
	////////////////////////////Hash End /////////////////////////////////////
	 
	//////////////////////getAllTransactions() Method ////////////////////////
	public static String getAllTransactions() 
	{
		String rs = "";
		
		Iterator<String> ks = clients.keySet().iterator();
		while (ks.hasNext()) 
		{
			String key = ks.next();
			rs = rs + clients.get(key.toString()) + "\r\n";
		}
		
		return rs;
	}
	////////////////////////////getAll End ///////////////////////////////////

	//////////////////////////// run() Thread Method //////////////////////////
	public void run()
	{
		// This is the thread code that ALL clients will run()
	 
		try
	   {
		  boolean session_done = false; 
	      long threadId;
	      String clientString;
	      String keyString = "";
	    
	      threadId = Thread.currentThread().getId();
	      
	      numOfConnections++;
	      
	      mainServer.textArea.append("Num of Connections = " + numOfConnections + newline);
	      mainServer.textArea.setCaretPosition(mainServer.textArea.getDocument().getLength());
	      mainServer.textArea.repaint();
	      
	      
	      // Deals with bottom display which displays every new ip connected + thread id
	      keyString = ipString + ":" + threadId;
	      if (vec.contains(keyString) == false)
	      {
	    	    int counter = 0;
	        	vec.addElement(keyString);
	        	
	        	mainServer.textArea_2.setText("");
	        	Enumeration<String> en = vec.elements();
	        	while (en.hasMoreElements())
	        	{
	        		mainServer.textArea_2.append(en.nextElement() + " || ");
	        		
	        		if (++counter >= 6)
	        		{
	        			mainServer.textArea_2.append("\r\n");
	        			counter = 0;
	        		}
	        	}
  	            mainServer.textArea_2.repaint();
	       }
	       
	      PrintStream pstream = new PrintStream (csocket.getOutputStream());
	      BufferedReader rstream = new BufferedReader(new InputStreamReader(csocket.getInputStream())); 
	      while (session_done == false)
	      {
	       	if (rstream.ready())   // check for any data messages
	       	{
	       		  numOfTransactions++;
	              clientString = rstream.readLine();
	              
	              if (clientString.contains("*"))
	              {
		   	           mainServer.textArea.append(clientString + newline);
		     	       mainServer.textArea.setCaretPosition(mainServer.textArea.getDocument().getLength());
		     	       mainServer.textArea.repaint();
	              }
	              
	              if (clientString.contains("Profit: $"))
	              {
	            	  profit = profit + Double.parseDouble(clientString.replaceAll("[^\\d.]", ""));
	              }
	              
	              if (clientString.contains("FP&& "))
	              {
	            	  flightNum = Integer.parseInt(clientString.replaceAll("[^\\d]", ""));
	            	  airkiosk.flightSched(flightNum - 1);
	            	  if (airkiosk.fullmsg == "Flight is not full")
	            		  seats[flightNum - 1] = airkiosk.seats[flightNum - 1];
	            	  else
	            		  pstream.println(airkiosk.fullmsg);
	              }
	              
	              if (clientString.length() > 128)
	              {
	           	   session_done = true;
	           	   continue;
	              }
	              
	              if (clientString.contains("quit"))
	              {
	                 session_done = true;
	              }
	              else if (clientString.contains("QUIT"))
	              {
	                 session_done = true;
	              }
	              else if (clientString.contains("Quit"))
	              {
	                 session_done = true;
	              }
	              
	              else if (clientString.contains("Query>"))
	              {
	            	  String tokens[] = clientString.split("\\>");
	            	  
	            	  if (clients.containsKey(tokens[1]) == true)
	            	  {
	            		  pstream.println(clients.get(tokens[1]).toString());  
	            	  }
	            	  else
	            	  {
	            		  pstream.println("NACK : ERROR : No such kiosk number!");
	            	  }
	              }
	              else if (clientString.contains("Transaction>"))
	              {
	            	  String tokens[] = clientString.split("\\>");
	            	  String args[]   = tokens[1].split("\\,");
	            	  
	            	  if (clients.containsKey(args[0]) == true)
	            	  {
	            		  hashOperation('T', args[0], args[1], args[2]);
	            		  
	            		  pstream.println("ACK");
	            	  }
	            	  else
	            	  {
	            		  pstream.println("NACK : ERROR : No such kiosk number!");
	            	  }
	              }
	              else if (clientString.contains("Configure>"))
	              {
	            	  String tokens[] = clientString.split("\\>");
	            	  
	            	  if (tokens.length == 2)
	            	  {
	            	     clients.put(tokens[1], new airkiosk(tokens[1], 0, 0, 0.0));
	            	     
	            	     pstream.println("ACK");
	            	  }
	            	  else
	            	  {
	            		  pstream.println("NACK : ERROR : Invalid number of parameters!");
	            	  }
	              }
	              else if (clientString.contains("Date>"))
	              {
	            	numOfMessages++;
	            	  
	            	// Create an instance of SimpleDateFormat used for formatting 
	            	// the string representation of date (month/day/year)
	            	DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

	            	// Get the date today using Calendar object.
	            	Date today = Calendar.getInstance().getTime();
	            	
	            	// Using DateFormat format method we can create a string 
	            	// representation of a date with the defined format.
	            	String reportDate = df.format(today);

	            	// Print what date is today!
	            	pstream.println("Num Of Messages : " + numOfMessages + "   Simple Date: " + reportDate);
	              }
	              else
	              {
	            	  pstream.println("NACK : ERROR : No such command!");
	              }
	       	   }
	         			    		        	
	           Thread.sleep(500);
	          
	        }    // end while loop
	      
			     
            keyString = ipString + ":" + threadId;
	      
	        if (vec.contains(keyString) == true)
	        {
	        	int counter = 0;
	        	vec.removeElement(keyString);
	        	
	        	mainServer.textArea_2.setText("");
	        	Enumeration<String> en = vec.elements();
	        	while (en.hasMoreElements())
	        	{        		     		
                    mainServer.textArea_2.append(en.nextElement() + " || ");
	        		
	        		if (++counter >= 6)
	        		{
	        			mainServer.textArea_2.append("\r\n");
	        			counter = 0;
	        		}
	        	}

  	            mainServer.textArea_2.repaint();
	        }
	      
	        numOfConnections--;

	        // close client socket
	        csocket.close();
	       
	        // update the status text area to show progress of program
		     mainServer.textArea.append("Child Thread : " + threadId + " : is Exiting!!!" + newline);
		     mainServer.textArea.append("Num of Connections = " + numOfConnections);
		     mainServer.textArea.setCaretPosition(mainServer.textArea.getDocument().getLength());
		     mainServer.textArea.repaint();
		     
	     } // end try  
	 
	     catch (SocketException e)
	     {
		  // update the status text area to show progress of program
	      mainServer.textArea.append("ERROR : Socket Exception!" + newline);
	      mainServer.textArea.setCaretPosition(mainServer.textArea.getDocument().getLength());
	      mainServer.textArea.repaint();
	     }
	     catch (InterruptedException e)
	     {
		  // update the status text area to show progress of program
	      mainServer.textArea.append("ERROR : Interrupted Exception!" + newline);
	      mainServer.textArea.setCaretPosition(mainServer.textArea.getDocument().getLength());
	      mainServer.textArea.repaint();
	     }
	     catch (UnknownHostException e)
	     {
		  // update the status text area to show progress of program
	      mainServer.textArea.append("ERROR : Unkonw Host Exception" + newline);
	      mainServer.textArea.setCaretPosition(mainServer.textArea.getDocument().getLength());
	      mainServer.textArea.repaint();
	     }
	     catch (IOException e) 
	     {
	     // update the status text area to show progress of program
	      mainServer.textArea.append("ERROR : IO Exception!" + newline);
	      mainServer.textArea.setCaretPosition(mainServer.textArea.getDocument().getLength());
	      mainServer.textArea.repaint();       
	     }     
	     catch (Exception e)
	     { 
		  numOfConnections--;
		  
		  // update the status text area to show progress of program
	      mainServer.textArea.append("ERROR : Generic Exception!" + newline);
	      mainServer.textArea.setCaretPosition(mainServer.textArea.getDocument().getLength());
	      mainServer.textArea.repaint(); 
	     }
	   
	  }  // end run() thread method
	//////////////////////////// run() Thread End /////////////////////////////
	
}
