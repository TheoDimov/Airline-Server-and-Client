// Class: 	ClientApp.java - part of Airline Client
// Description: This the main class for the Airline Client. It consists of provides all the elements
//				of the Client Display.

package pckgOne;

import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundImage;
import java.time.LocalDate;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.*;
import java.time.format.DateTimeFormatter;

public class mainClient extends Application
{	
//////////////////////// Global Variable Declarations /////////////////////////////////
	Label laFlight, laLocation, laDestination, laTicket, laType, laBlank, laBlank2, laBlank3, laDepart, laReturn, laInfo, laFullName, laTotal, laPrice;
	TextField tfFullName;
	TextArea taInfo;
	Button btnReserve, btnExit;
	DatePicker dateDepart, dateReturn;	
	HBox radios;
	RadioButton rbEcon, rbBusi;
	ToggleGroup seat;
	
	double tickProfit;

	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
	LocalDate localDate   = LocalDate.now();
		
	// combo box declaration of string values
	ComboBox<String> cbDepart, cbDestination;

/////////////////// Primary Stage Method //////////////////////
	@Override
	public void start(Stage primaryStage) 
	{		
		// labels for prepared data entries
		laBlank			= new Label("\n \n");
		laBlank2		= new Label("\n");
		laBlank3		= new Label("\n");
		laFullName		= new Label("Full Name :  ");
		laFlight 		= new Label("Flight Path    -    ");
		laLocation 		= new Label("Location : ");
		laDestination 	= new Label("   	  Destination : ");
		laTicket		= new Label("Ticket Type   -    ");
		laType 			= new Label("Seat Type : ");
		laDepart 		= new Label("Depart : ");
		laReturn		= new Label("    		   Return : ");
		laInfo 			= new Label("Ticket Information:  ");
		laTotal  		= new Label("Total : $");
		laPrice  		= new Label("0");

		dateDepart    	= new DatePicker();
		dateReturn		= new DatePicker();
		

		// font for labels from data entries
		laFullName.setStyle("-fx-font-weight: bold");
		laFlight.setStyle("-fx-font-weight: bold");
		laLocation.setStyle("-fx-font-weight: bold");
		laDestination.setStyle("-fx-font-weight: bold");
		laTicket.setStyle("-fx-font-weight: bold");
		laType.setStyle("-fx-font-weight: bold");
		laDepart.setStyle("-fx-font-weight: bold");
		laReturn.setStyle("-fx-font-weight: bold");
		laInfo.setStyle("-fx-font-weight: bold");
		laTotal.setStyle("-fx-font-weight: bold");
		laPrice.setStyle("-fx-font-weight: bold");

		
		// radio button plus H box and insets
		radios 			= new HBox();
		radios.setPrefWidth(10);
		radios.setSpacing(5);
		radios.setPadding(new Insets(0, 0, 0, 0));
		
		//create toggle group and radio button for seat type
		seat 			= new ToggleGroup();	
		rbEcon 			= new RadioButton("Economy");
		rbEcon.setToggleGroup(seat);

		rbBusi 			= new RadioButton("Business");
		rbBusi.setToggleGroup(seat);
		
		radios.getChildren().addAll(rbEcon, rbBusi);
							
		rbEcon.setSelected(true);

		// text fields for data entries that require # entry
		tfFullName	     = new TextField();
		
		// set prompt hints for text fields to suggest entry
		tfFullName.setPromptText("John Smith");
					
		// text area for ticket information
		taInfo 			= new TextArea();
		taInfo.setEditable(false);
		 				
		// concluding buttons
		btnReserve		= new Button("Reserve");
		btnExit 		= new Button("Exit");     
		
		//--------------------DROP DOWN----------------------
		// strings for drop down box
	
		cbDepart = new ComboBox<>();
		cbDepart.getItems().addAll(
				"NYC - JFK",
				"LA - LAX",
				"CHI - OHIA"
		);
					
		cbDestination = new ComboBox<>();
		cbDestination.getItems().addAll(
				"NYC - JFK",
				"LA - LAX",
				"CHI - OHIA"
		);
		
		
		GridPane root 	= new GridPane();
		root.setAlignment(Pos.CENTER);
		root.setPadding(new Insets(10, 10, 10, 10));
		

		root.add(laBlank,   		   1, 0);
		root.add(laFullName,   		   1, 1);
		root.add(tfFullName,   		   2, 1);
		root.add(laBlank2,   		   1, 2);
		root.add(laFlight,     		   0, 3);
		root.add(laLocation,   		   1, 3);
		root.add(cbDepart,     		   2, 3);
		root.add(laDestination,		   3, 3);
		root.add(cbDestination,		   4, 3);
		root.add(laBlank3,   		   1, 4);
		root.add(laTicket,    		   0, 5);
		root.add(laType,     	       1, 5);
		root.add(radios,     	       2, 5);
		root.add(laDepart,    		   1, 6);
		root.add(dateDepart,   		   2, 6);
		root.add(laReturn,    		   3, 6);
		root.add(dateReturn,   		   4, 6);
		root.add(btnReserve,   		   3, 7);
		root.add(btnExit,      		   4, 7);
		root.add(laTotal,      		   2, 8);
		root.add(laPrice,      		   4, 8);
		root.add(laInfo,       		   0, 9);
		root.add(taInfo,         2, 9, 4, 2);
			
		setPreferences();
		assignActions();
		
		Scene scene 	= new Scene(root,700,700);
				
		Image img			  = new Image("/resources/FALbgimg.png");
		BackgroundImage bgImg = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, 
		new BackgroundSize(1500, 800, false, false, false, true));
		root.setBackground(new Background(bgImg));
				
		primaryStage.setTitle("Fordham Airlines");
		primaryStage.setScene(scene);
		primaryStage.setHeight(650);
		primaryStage.setWidth(700);
		primaryStage.setResizable(false);
		primaryStage.show();
	}
///////////////////// Primary Stage End ///////////////////////
	
/////////////////// Preferences Method ////////////////////////
    private void setPreferences() 
    {
    	// method separates main controls width and height
    	laBlank.setPrefHeight(50);
    	laFullName.setPrefWidth(150);
    	tfFullName.setPrefWidth(150);
    	laFlight.setPrefWidth(150);
    	laLocation.setPrefWidth(150);
    	cbDepart.setPrefWidth(150);
    	laDestination.setPrefWidth(150);
    	cbDestination.setPrefWidth(100);
    	laTicket.setPrefWidth(150);
    	laType.setPrefWidth(150);
    	radios.setPrefWidth(150);
    	laDepart.setPrefHeight(50);
    	laDepart.setPrefWidth(150);
    	cbDepart.setPrefWidth(150);	 
    	laReturn.setPrefWidth(150);
    	cbDestination.setPrefWidth(150);
    	laInfo.setPrefWidth(150);
    	taInfo.setPrefHeight(150);
    	taInfo.setPrefWidth(150);	 
    	btnReserve.setPrefWidth(75);
    	btnExit.setPrefWidth(75);	
    }
///////////////////// Preferences End /////////////////////////
    
//////////////////// Button Actions Method ////////////////////
	public void assignActions()
	{
		    // Method assigns action events to controls
	    	btnExit.setOnAction(e -> btnCode(e));
	    	btnReserve.setOnAction(e -> btnCode(e));
	}
////////////////////// Button Actions End /////////////////////

///////////////// Reservation Actions Method //////////////////
	public void btnCode(ActionEvent e)
	{
		// Method deals with action codes for data entries,radio button, and other controls
		
		// local variables to store values from gui
		String fullName, fromPort, toPort, rbSeat, tickCost, tickInfo;
		boolean rc, validName, validPort, validDate;		// boolean values for user validation functions
		double tickAdd = 0; 									// ticket adder
		int[] flights = new int[]{1,2,3,4,5,6};
		int flightPath = 0;
		boolean allgood = true; 
		
		// storing text field entries into local variables
		fullName 	= tfFullName.getText();
		fromPort 	= cbDepart.getValue();
		toPort 		= cbDestination.getValue();
		rbSeat		= "";
		
 		// Radio Button Info Update
        if(rbEcon.isSelected()) {
        	rbSeat = "Economy";
        }
        else if(rbBusi.isSelected()) {
        	rbSeat = "Business";
        }
		
        if(e.getSource() == btnReserve) // e is the button that is clicked
        {   
        	if(fromPort == "NYC - JFK" && toPort == "LA - LAX")
        		flightPath = flights[0];
	        else if (fromPort == "LA - LAX" && toPort == "NYC - JFK")
	        	flightPath = flights[1];
	        else if (fromPort == "LA - LAX" && toPort == "CHI - OHIA")
	        	flightPath = flights[2];
	        else if (fromPort == "NYC - JFK" && toPort == "CHI - OHIA")
	        	flightPath = flights[3];
	        else if (fromPort == "CHI - OHIA" && toPort == "NYC - JFK")
	        	flightPath = flights[4];
	        else if (fromPort == "CHI - OHIA" && toPort == "LA - LAX")
	        	flightPath = flights[5];    	
        	
        	LocalDate depart  = dateDepart.getValue();
        	LocalDate rebound = dateReturn.getValue();   	
        	
        	validDate = isDateValid(depart, rebound, localDate);
        	if(validDate == true) 
        	{
        		depart = dateDepart.getValue();
            	rebound = dateReturn.getValue(); 
        	}
        	else 
        	{
        		depart = null;
        		rebound = null;
        		allgood = false;
        	}
        	
        	// check for name validation
        	validName = isNameValid(tfFullName, fullName);
        	if(validName == true) 
        	{
        		fullName = tfFullName.getText();
        	}
        	else {
        		fullName = "";
        		allgood = false;
        	}
        	
        	validPort = isPortValid(fromPort, toPort);
        	if(validPort == true) 
        	{
        		fromPort 	= cbDepart.getValue();
       			toPort 		= cbDestination.getValue();
        				
       			if(toPort == "NYC - JFK" || toPort == "LA - LAX") 
       			{
       				laPrice.setText("250");
      				tickCost = laPrice.getText();
               		tickAdd = Double.parseDouble(tickCost);
               		if(rbSeat == "Business") {
               			tickAdd *= 1.5;
               			laPrice.setText(Double.toString(tickAdd));
               		}
               		tickProfit += tickAdd;
       			}
       			else {
        			laPrice.setText("150");
        			tickCost = laPrice.getText();
               		tickAdd = Double.parseDouble(tickCost);
               		if(rbSeat == "Business") {
               			tickAdd *= 1.5;
               			laPrice.setText(Double.toString(tickAdd));
               		}
               		tickProfit += tickAdd;
       			}
 
       			
        	}
            else {
             	fromPort 	= "";
               	toPort 		= "";
               	allgood = false;
            }
            
        	
        	if (allgood == true)
			{
	        	// what we print on the client side for ticket summary
	        	taInfo.setText("Full Name: " + fullName + "\n" + "Departing From: " + fromPort + "\n" +
	        			"Destination To: " + toPort + "\n" + "Seat Type: " + rbSeat + "\n" + "Depart Date: " 
	        			+ depart + "\n" + "Return Date: " + rebound + "\n" + "Price: $" + laPrice.getText());
	        	
	        	// What to send to our transaction log
	        	fileIO wrFile = new fileIO();    
	        	wrFile.wrTransactionData("\nFull Name: " + fullName + "\n" + "Departing From: " + fromPort + "\n" 
	        		+ "Destination To: " + toPort + "\n" + "Seat Type: " + rbSeat + "\n" + "Depart Date: " + depart + "\n" +
	        		"Return Date: " + rebound); 
	
	        	
	        	// what we want to send to the server for ticket summary
	        	tickInfo = "********Ticket Information********\n*Full Name: " + fullName + 
	        			   "\n*Departing From: " + fromPort + "\n*Destination To: " + toPort +
	        			   "\n" + "*Seat Type: " + rbSeat + "\n*Depart Date: " + depart +
	        			   "\n*Return Date: " + rebound + "\n" + "*Total: $" + laPrice.getText() + 
	        			   "\n**********************************"; 
	        	
	            String str2 = Double.toString(tickAdd);
				String total = ("Profit: $" + str2);	
				
        		socketUtils sock = new socketUtils();
				rc = sock.socketConnect();
				
	        	if(rc == true)
	        	{
	        		sock.sendMessage(tickInfo);
	        		sock.sendMessage(total);
	        		sock.sendMessage("FP&& "+ Integer.toString(flightPath));
	        		sock.recvMessage();
	        		if (sock.recvMessage() == "Flight is not full")
	        		{
	        			AlertBox.display("Error", "Sorry flight you have reserved is full.");
	        		}
	        	} 
			}
        }

        if(e.getSource() == btnExit) 
        {
            System.exit(0);	        	
        }

    }
///////////////////////// Reserve End /////////////////////////
	
//////////////////////// Name Check Method ////////////////////
    private boolean isNameValid(TextField input, String data) 
    {
    	// checks if data formats are valid
    	String valid;
    	valid = data.replaceAll("\\s+", "");
    	
   		if (valid.matches("[a-zA-Z]+") == true && valid.length() > 3){
    		return true;
    	}
    	
   		else{
    		AlertBox.display("Error", "Not a valid name.");
    		return false;
    	}
    }
///////////////////////// Name Check End //////////////////////

//////////////////////// Airport Check Method /////////////////
    private boolean isPortValid(String depart, String destination) 
    {
    	// checks if ports are same for logical error check
    	if(depart != destination) {
    		return true;
    	}
    	else {
    		AlertBox.display("Error", "Cannot leave and arrive to same port. Please correct.");
    		return false;
    	}
    }
///////////////////////// Airport Check End ///////////////////
    
//////////////////////// Date Check Method /////////////////////
    private boolean isDateValid(LocalDate depart, LocalDate rebound, LocalDate today) 
    {
    	// checks if return and arrival dates are valid
    	if (depart != null && rebound != null && depart.compareTo(today) >= 0 && rebound.compareTo(depart) >= 0)
    	{
    			return true;
    	}
    	else 
    	{
    		AlertBox.display("Error", "Departure and arrival dates invalid. Please correct.");
    		return false;
    	}
    }
////////////////////////// Date Check End //////////////////////

//////////////////////////// Launch ////////////////////////////
	public static void main(String[] args)
	{
		launch(args);
	}

}
