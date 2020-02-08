// Group:  	   	Ilir Asllani, Theo Dimov, Leonny Correa
// Course:     	Software Engineering
// Assignment: 	Project - Fordham Airlines
// Class: 		mainServer.java - part of server client
// Description: The main display of the server client.

package FUAirlineServer;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;

import java.awt.Color;
import javax.swing.JTextArea;
import java.awt.Font;

public class mainServer extends JFrame 
{
////////////Global Var Declarations ///////////////
	public static JTextArea textArea;
	public static JTextArea textArea_1;
	public static JTextArea textArea_2;
	public static JTextArea textArea_3;
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public static int x;

//////////////////Main Launch ////////////////////
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
		public void run() 
		{
			try 
			{
				mainServer frame = new mainServer();
				frame.setVisible(true);
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		});
	}
////////////////////Main End /////////////////////
	
///////////////// Sock Server Method //////////////
	private void startSockServer() 
	{
		Thread refreshWeatherThread = new Thread()
		{
			public void run() 
			{
				airlinesocket.runSockServer();
			}
		};
		refreshWeatherThread.start();
	}
//////////////////Sock Server End ////////////////  

/////////////// Real Time Clock Method ////////////
	private void startRealTimeClock() 
	{
		Thread refreshClock = new Thread()
		{
			public void run() 
			{
				while (true) 
				{
					Date date = new Date();
					String str = String.format("    %tc", date);
		
					textArea_1.setText("");
					textArea_1.append(str);
					textArea_1.repaint();
		
					try 
					{
						sleep(2000L);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		refreshClock.start();
	}
///////////////////// Clock End ///////////////////

/////////////// Start Stat List Method ////////////
	private void startStatList()
	{	
		Thread statList = new Thread()
		{
			
			public void run()
			{   
				while (true)
				{	 			      
					String str = Integer.toString(airlinesocket.numOfConnections);
					textArea_3.setText("");
					textArea_3.append("Tickets Sold: " + str + "\nTotal Profit: $" + airlinesocket.profit + "\nFlights --------- Seats Reserved\nNYC - JFK to LA - LAX:\t " + airlinesocket.seats[0] + "/100"
							           + "\nLA - LAX to NYC - JFK:\t " + airlinesocket.seats[1] + "/100" + "\nLA - LAX to CHI - OHIA:\t " + airlinesocket.seats[2] + "/100"
							           + "\nNYC - JFk to CHI - OHIA: " + airlinesocket.seats[3] + "/100" + "\nCHI - OHIA to NYC - JFK: " + airlinesocket.seats[4] + "/100"
							           + "\nCHI - OHIA to LA - LAX:\t " + airlinesocket.seats[5] + "/100");
					textArea_3.repaint();
				   
					try
					{
						sleep(2000L);
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
				} // end while true 
			}
		};
		statList.start();
	}
/////////////// Stat List Method End //////////////
	
////////////////////Creates Frame ////////////////
	public mainServer()
	{
		//////////// Main Frame Attributes ///////////////
		setTitle("Airline Socket Server");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 929, 520);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		getContentPane().setBackground(Color.RED);
		contentPane.setLayout(null);
		//////////////// Main Frame End //////////////////

		/////////////////// Main Display /////////////////
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBounds(10, 11, 531, 360);
		contentPane.add(panel);
		
		textArea = new JTextArea();
		textArea.setFont(new Font("Monospaced", Font.BOLD, 16));
		textArea.setEditable(false);
		textArea.setRows(15);
		textArea.setColumns(46);
		panel.add(textArea);
		
		JScrollPane txt_more_info_pane = new JScrollPane(textArea);  
	    txt_more_info_pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
	    txt_more_info_pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	    panel.add(txt_more_info_pane);
		//////////////// Main Display End ////////////////
	    
	    ////////////////// Right Display /////////////////
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setBounds(551, 110, 350, 261);
		contentPane.add(panel_1);
		
		textArea_3 = new JTextArea();
		textArea_3.setFont(new Font("Monospaced", Font.BOLD, 14));
		textArea_3.setEditable(false);
		textArea_3.setRows(12);
		textArea_3.setColumns(34);
		panel_1.add(textArea_3);
		
		JScrollPane txt_more_info_pane_2 = new JScrollPane(textArea_3);  
	    txt_more_info_pane_2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
	    txt_more_info_pane_2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	    panel_1.add(txt_more_info_pane_2);
		
		////////////// Right Display End /////////////////
		
		///////////////// Button ////////////////////////
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(20, 400, 103, 109);
		contentPane.add(panel_2);
		panel_2.setBackground(Color.RED);
		JButton btnNewButton_1 = new JButton("EXIT");
		btnNewButton_1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{  
				System.exit(0);
				;
			}
		});
		panel_2.add(btnNewButton_1);
		
		////////////////////Button End //////////////////////
		
		////////////////Time Upper Right /////////////////////
		JPanel panel_7 = new JPanel();
		panel_7.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_7.setBounds(551, 11, 350, 88);
		contentPane.add(panel_7);
		
		textArea_1 = new JTextArea();
		textArea_1.setEditable(false);
		textArea_1.setFont(new Font("Monospaced", Font.BOLD, 14));
		textArea_1.setRows(2);
		textArea_1.setColumns(36);
		panel_7.add(textArea_1);
		
		JScrollPane txt_more_info_pane_3 = new JScrollPane(textArea_1);  
	    txt_more_info_pane_3.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
	    txt_more_info_pane_3.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	    panel_7.add(txt_more_info_pane_3);
		/////////////////// Time End ////////////////////////
		
		///////////////// Bottom Display ////////////////////
		JPanel panel_8 = new JPanel();
		panel_8.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_8.setBounds(126, 382, 777, 73);
		contentPane.add(panel_8);
		
		textArea_2 = new JTextArea();
		textArea_2.setEditable(false);
		textArea_2.setRows(3);
		textArea_2.setColumns(70);
		panel_8.add(textArea_2);
		
		JScrollPane txt_more_info_pane_4 = new JScrollPane(textArea_2);  
	    txt_more_info_pane_4.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
	    txt_more_info_pane_4.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	    panel_8.add(txt_more_info_pane_4);
		//////////////////// Bottom End ////////////////////
		
		startSockServer();
		startRealTimeClock();
		startStatList();
		
		this.setLocationRelativeTo(null);
	}
///////////////// Frame End ///////////////////////
}
