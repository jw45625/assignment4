package assignment4;

import java.util.List;
import java.util.Scanner;
import java.io.*;
import java.lang.reflect.Method;
import javafx.scene.input.MouseEvent;

import javafx.application.*;

import javafx.stage.Stage;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.awt.event.MouseEvent.*;

import javafx.scene.control.Button;

import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;

import javafx.scene.text.*;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.control.TextField;

public class View extends Application{
	private int size; 
	private GridPane grid;
	private TextField statsOutput;
	
	@Override
	public void start(Stage primaryStage) { // primaryStage is created by Java VM
		
	}
	public View() {
		grid = new GridPane();
		statsOutput = new TextField();
		statsOutput.setPrefSize(300, 100);
		FlowPane viewRootPane = new FlowPane();
		statsOutput.setText("0");
		
		
		if(Params.world_height > 100) {
			size = 8;
		}
		else {
			size = 800 / Params.world_height;
		}
		
		Stage viewStage = new Stage();
		viewRootPane.getChildren().addAll(grid, statsOutput);
	    Scene viewScene = new Scene(viewRootPane, 1600, 1000);
	     
	    viewStage.setScene(viewScene);
	    viewStage.setTitle("PooP");
	    viewStage.show();
	}
	
	public void paintGridLines() {
		for (int r = 0; r < Params.world_height; r++)
			for (int c = 0; c < Params.world_width; c++) {
				Shape s = new Rectangle(size, size);
				s.setFill(null);
				s.setStroke(Color.BLUEVIOLET);
				grid.add(s, c, r);
			}

	}
	
	public void viewRunStats(String statsInfo) {
		System.out.println(statsInfo);
		statsOutput.setText(statsInfo);
	}
	
	/*
	public void display() {
		for(int i = 0 ; i < Params.world_height ; i++) {
			System.out.print("|");
			
			for(int j = 0 ; j < Params.world_width ; j++) {
				boolean displayedCritter = false;
				for(Critter c : population) {
					if((c.x_coord == j) && (c.y_coord == i) && (displayedCritter == false)) {
						displayedCritter = true;
						System.out.print(c);
					}
				}
				if(displayedCritter == false) {
					System.out.print(" ");
				}
			}
			
			System.out.println("|");
		}
	}
	*/


}
