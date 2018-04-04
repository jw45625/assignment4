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
import javafx.scene.shape.Polygon.*;

public class View extends Application{
	private double size; 
	private GridPane grid;
	
	@Override
	public void start(Stage primaryStage) { // primaryStage is created by Java VM
		
	}
	public View() {
		grid = new GridPane();
		FlowPane viewRootPane = new FlowPane();
		
		
		if(Params.world_height > 100) {
			size = 8;
		}
		else {
			size = (double) 800 / Params.world_height;
		}
		
		Stage viewStage = new Stage();
		viewRootPane.getChildren().addAll(grid);
	    Scene viewScene = new Scene(viewRootPane, 1600, 1000);
	     
	    viewStage.setScene(viewScene);
	    viewStage.setTitle("Critter World");
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
	
	public void clearGrid() {
		grid.getChildren().clear();
	}
	
	public void paintShape(int index, int x, int y, Color fillColor, Color strokeColor) {
		switch(index) {
			case 0: Shape s0 = new Circle(size/2);
					s0.setFill(fillColor);
					grid.add(s0, x, y);
					break;
			case 1: Shape s1 = new Rectangle(size, size);
					s1.setFill(fillColor);
					grid.add(s1, x, y);
					break;
			case 2:	Polygon s2 = new Polygon();
					s2.getPoints().addAll(new Double[]{
							0.0, 0.0, 
							size/2 , size,
							size, 0.0
							});
					s2.setFill(fillColor);
					grid.add(s2, x, y);
					break;
			case 3:	Polygon s3 = new Polygon();
					s3.getPoints().addAll(new Double[]{
							size/2, 0.0, 
							size , size/2,
							size/2 , size,
							0.0, size/2
							});
							s3.setFill(fillColor);
							grid.add(s3, x, y);
			break;
			case 4:	Polygon s4 = new Polygon();
					s4.getPoints().addAll(new Double[]{
							size/7, size, 
							size/2, 0.0,
							(6*size/7), size,
							0.0, size/3,
							size, size/3
							});
					s4.setFill(fillColor);
					grid.add(s4, x, y);
		}
		
		
		
	}
	
}
