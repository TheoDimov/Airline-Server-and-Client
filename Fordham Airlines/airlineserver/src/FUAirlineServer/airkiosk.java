// Group:  	   	Ilir Asllani, Theo Dimov, Leonny Correa
// Course:     	Software Engineering
// Assignment: 	Project - Fordham Airlines
// Class: 		airkiosk.java - part of Server Client
// Description: This class provides many methods and variables used
//				by the server client to update stats.

package FUAirlineServer;

public class airkiosk 
{
///////////////////// Global Variable Declarations //////////////////////
	String name;
	int totalTickets;
	static int transactionCount;
	int size;
	static boolean full = false;
	static String fullmsg = "Flight is not full";
	static double totalDollars;
	static int[][] flights = new int[6][100];		
	{
		for (int x = 0; x < 6; x++) 
		{
		    for (int y = 0; y < 100; y++) 
		    {
		        flights[x][y] = 0;
		    }
		}
	};
	static int[] seats = new int[]{0,0,0,0,0,0};

///////////////////// Method Definitions ///////////////////////////////
	public airkiosk(String n, int tr, int ticks, double val) 
	{
		name   				= n;
		transactionCount 	= tr;
		totalTickets 		= ticks;
		totalDollars 		= val;
	}

	public String toString() 
	{
		return name + " : " + " TransCount = " + transactionCount + " Tickets = " + totalTickets + "  Dollars = " + totalDollars;
	}

	public void incrementTrans() 
	{
		transactionCount++;
	}

	public void addTickets(int c)
	{
		totalTickets = totalTickets + c;
	}

	public void addDollars(double d) 
	{
		totalDollars = totalDollars + d;
	}
	
	public static void flightSched(int flight)
	{
		int i = 0;
		int max = 100;
		
		while (flights[flight][i] != 0)
		{
			if (i == max)
			{	
				full = true;
				break;
			}
			else
				i++;
		}
		if (full == false)
		{
			flights[flight][i] = 1;
		
			seats[flight] = 0;
			
		    for (int y = 0; y < 100; y++) 
		    {
		        if (flights[flight][y] == 1)
		        {
		        	seats[flight] += 1;
		        }	
		    }
		    
		    for (int r = 0; r < 6; r++) 
			{
			    for (int z = 0; z < 100; z++) 
			    {
			        System.out.print(flights[r][z]);
			    }
			    System.out.print("\n");
			    System.out.println(seats[r]);
			}
		}
		else
		{
			fullmsg = "Flight is full";
		}
	}
	
}
