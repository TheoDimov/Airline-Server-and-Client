// Group:  	   	Ilir Asllani, Theo Dimov, Leonny Correa
// Course:     	Software Engineering
// Assignment: 	Project - Fordham Airlines
// Class: 		AlertBox.java - part of Airline Client
// Description: This class simply provides an alert box display in the instance of an error.

package pckgOne;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

public class AlertBox 
{
	public static void display(String title, String message) 
	{
		Stage window = new Stage();
		
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setMinWidth(300);
		
		Label label = new Label();
		label.setText(message);
		Button closeButton = new Button("OK");
		closeButton.setOnAction(e -> window.close());
		
		VBox layout = new VBox(10);
		layout.getChildren().addAll(label, closeButton);
		layout.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
	}
}
