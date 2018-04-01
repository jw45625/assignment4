package assignment4;
/* CRITTERS Main.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * Jonathan Wu
 * jw45625
 * 15465
 * Fall 2016
 */

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

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;



/*
 * Usage: java <pkgname>.Main <input file> test
 * input file is optional.  If input file is specified, the word 'test' is optional.
 * May not use 'test' argument without specifying input file.
 */
public class Main extends Application {
	 
    static Scanner kb;	// scanner connected to keyboard input, or input file
    private static String inputFile;	// input file, used instead of keyboard input if specified
    static ByteArrayOutputStream testOutputString;	// if test specified, holds all console output
    private static String myPackage;	// package of Critter file.  Critter cannot be in default pkg.
    private static boolean DEBUG = false; // Use it or not, as you wish!
    static PrintStream old = System.out;	// if you want to restore output to console


    // Gets the package name.  The usage assumes that Critter and its subclasses are all in the same package.
    static {
        myPackage = Critter.class.getPackage().toString().split(" ")[1];
    }

    /**
     * Main method.
     * @param args args can be empty.  If not empty, provide two parameters -- the first is a file name, 
     * and the second is test (for test output, where all output to be directed to a String), or nothing.
     */
    public static void main(String[] args) { 
    	
        if (args.length != 0) {
            try {
                inputFile = args[0];
                kb = new Scanner(new File(inputFile));			
            } catch (FileNotFoundException e) {
                System.out.println("USAGE: java Main OR java Main <input file> <test output>");
                e.printStackTrace();
            } catch (NullPointerException e) {
                System.out.println("USAGE: java Main OR java Main <input file>  <test output>");
            }
            if (args.length >= 2) {
                if (args[1].equals("test")) { // if the word "test" is the second argument to java
                    // Create a stream to hold the output
                    testOutputString = new ByteArrayOutputStream();
                    PrintStream ps = new PrintStream(testOutputString);
                    // Save the old System.out.
                    old = System.out;
                    // Tell Java to use the special stream; all console output will be redirected here from now
                    System.setOut(ps);
                }
            }
        } else { // if no arguments to main
            kb = new Scanner(System.in); // use keyboard and console
        }

        /* Do not alter the code above for your submission. */
        /* Write your code below. */
        
        // System.out.println("GLHF");
        
        boolean quitInput = false;
        String[] inputs;
        String inputString = "";
        String command;
        String className = myPackage;
        String seedString;
        String countString;
        int count = 1;
        int seed;
        
        launch(args);
        
        /*
        while(quitInput == false) {
        	System.out.print("critters>");
        	try {
        		inputString = kb.nextLine();
        		inputString = inputString.trim();
        		inputs = inputString.split("\\s+");
        		
            	command = inputs[0];
            	if(command.equals("quit")) {
            		if(inputs.length == 1) {
            			quitInput = true;
            		}
            		else {
            			throw new Exception();
            		}
            	}
            	else if(command.equals("show")) {
            		if(inputs.length == 1) {
            			Critter.displayWorld();
            		}
            		else {
            			throw new Exception();
            		}
            	}
            	else if(command.equals("step")) {
            		if(inputs.length > 2) {
            			throw new Exception();
            		}
            		
            		if(inputs.length > 1) {
            			countString = inputs[1];
            			count = Integer.parseInt(countString);
            			System.out.println(count);
        			}
            		else {
            			count = 1;
            		}
            		
            		for(int i = 0 ; i < count ; i++) {
            			Critter.worldTimeStep();
            		}
            	}
            	else if(command.equals("seed")) {
            		if(inputs.length > 2) {
            			throw new Exception();
            		}
            		
            		seedString = inputs[1];
            		seed = Integer.parseInt(seedString);
            		Critter.setSeed(seed);
            	}
            	else if(command.equals("make")) {
            		if(inputs.length > 3) {
            			throw new Exception();
            		}
            		
            		if(inputs.length >= 2) {	
            			if((inputs[1].charAt(0) >= 'a') && inputs[1].charAt(0) <= 'z'){
                			className = ((char)(inputs[1].charAt(0) + 'A' - 'a')) + inputs[1].substring(1);
                		}
                		else {
                			className = inputs[1];
                		}
            		}
            		
            		if(inputs.length == 3) {
            			countString = inputs[2];
            			count = Integer.parseInt(countString);
            		}
            		else {
            			count = 1;
            		}
            		
            		for(int i = 0; i < count; i++) {
            			Critter.makeCritter(className);
            		}
            	}
            	else if(command.equals("stats")) {
            		if(inputs.length > 2) {
            			throw new Exception();
            		}
            		
            		if((inputs[1].charAt(0) >= 'a') && inputs[1].charAt(0) <= 'z'){
            			className = ((char)(inputs[1].charAt(0) + 'A' - 'a')) + inputs[1].substring(1);
            		}
            		else {
            			className = inputs[1];
            		}
            		
            		Class<?> critterClass = Class.forName(myPackage + "." + className);
            		
            		Critter crit = (Critter) critterClass.newInstance();
            		
            		Method method = critterClass.getMethod("runStats", List.class);
            		
            		method.invoke(crit, Critter.getInstances(className));
            	}
            	else {
            		System.out.println("invalid command: " + inputString);
            	}
        	}
        	catch(Exception e) {
        		System.out.println("error processing: " + inputString);
        		System.out.println(e);
        	}
        	catch(Error er) {
        		System.out.println("error processing: " + inputString);
        		System.out.println(er);
        	}
        	
        }
        
      
        */
        
        /* Write your code above */
        
        /*
        try{
        	
        	for(int i = 0 ; i < 100 ; i++) {
        		Critter.makeCritter("assignment4.Algae");
        	}
        	for(int i = 0 ; i < 25 ; i++) {
        		Critter.makeCritter("assignment4.Craig");
        	}
        }
        catch(InvalidCritterException ice) {
        	System.out.println(ice);
        }
        */

        System.out.flush();

    }
    
    @Override
	 public void start(Stage primaryStage) {
		 primaryStage.setTitle("Java FX Demo Program");
		 Critter.displayWorld();
		 
	     HBox quitPane = new HBox();
	     HBox stepPane = new HBox();
	     HBox makePane = new HBox();
	     HBox showPane = new HBox();
	     HBox statsPane = new HBox();
	     HBox seedPane = new HBox();
	     StackPane outputTextPane = new StackPane();
	     
	     Button quitButton = new Button("quit");
	     Label quitLabel = new Label("Press To Quit");
	     quitPane.getChildren().addAll(quitLabel, quitButton);
	     quitPane.resize(10000, 10000);

	     Button stepButton = new Button("step");
	     Label stepLabel = new Label("Press to step given amount of times");
	     TextField stepText = new TextField();
	     stepText.setText("0");
	     stepPane.getChildren().addAll(stepLabel, stepText, stepButton);
	     stepPane.resize(10000, 10000);
	     
	     Button showButton = new Button("show");
	     Label showLabel = new Label("Press to show the world");
	     showPane.getChildren().addAll(showLabel, showButton);
	     showPane.resize(10000, 10000);
	     
	     Button makeButton = new Button("make");
	     ComboBox<String> makeCritterBox = new ComboBox<>();
	     Label makeLabel = new Label("Press to make # of chosen Critter");
	     TextField makeText = new TextField(); 
	     makeText.setText("0");
	     makeCritterBox.getItems().addAll("Algae", "Craig", "Critter1", "Critter2");
	     makeCritterBox.getSelectionModel().selectFirst();
	     makePane.getChildren().addAll(makeLabel, makeCritterBox, makeText, makeButton);
	     makePane.resize(10000, 10000);
	     
	     Button seedButton = new Button("seed");
	     Label seedLabel = new Label("Press to give seed value");
	     TextField seedText = new TextField(); 
	     seedText.setText("0");
	     seedPane.getChildren().addAll(seedLabel, seedText, seedButton);
	     seedPane.resize(10000, 10000);
	     
	     Button statsButton = new Button("stats");
	     ComboBox<String> statsCritterBox = new ComboBox<>();
	     statsCritterBox.getItems().addAll("Algae", "Craig", "Critter1", "Critter2");
	     statsCritterBox.getSelectionModel().selectFirst();
	     Label statsLabel = new Label("Press to see stats of choosen Critters");
	     statsPane.getChildren().addAll(statsLabel, statsCritterBox, statsButton);
	     statsPane.resize(10000, 10000);
	     
	     Label outputText = new Label();
	     outputTextPane.getChildren().add(outputText);
	     
	     makeButton.setOnAction(e-> {
	    	 String className = makeCritterBox.getValue();
    		 try{
    			 Critter.makeCritter(className);
    			 int count = Integer.parseInt(makeText.getText());
    			 
    			 for(int i = 0; i < count ; i++) {
    				 Critter.makeCritter(className);
    			 }
    			 
    			 makeText.setText("0");
    		 }
    		 catch(Exception exep) {
    			 System.out.println(exep);
    		 }
	     });
	     
	     stepButton.setOnAction(e-> {
    		 try{
    			 int count = Integer.parseInt(stepText.getText());
    			 
    			 for(int i = 0; i < count ; i++) {
    				 Critter.worldTimeStep();
    			 }
    			 
    			 stepText.setText("0");
    		 }
    		 catch(Exception exep) {
    			 System.out.println(exep);
    		 }
	     });
	     
	     statsButton.setOnAction(e-> {
	    	 
	    	 try{
	    		 String className = statsCritterBox.getValue();
		    	 Class<?> critterClass = Class.forName(myPackage + "." + className);
	     		
		    	 Critter crit = (Critter) critterClass.newInstance();
	     		
		    	 Method method = critterClass.getMethod("runStats", List.class);
	     		
		    	 method.invoke(crit, Critter.getInstances(className));
	    		 Critter.runStats(Critter.getInstances(className));
	    		 //outputText.setText(old.);
	    	 }
	    	 catch(Exception exep) {
	    		 System.out.println(exep);
	    	 }
	     });
	     
	     quitButton.setOnAction(e-> {
	    	 System.exit(0);
	     });
	     
	     seedButton.setOnAction(e-> {
	    	 int seed = Integer.parseInt(seedText.getText());
	    	 Critter.setSeed(seed);
	    	 seedText.setText("0");
	     });
	     
	     showButton.setOnAction(e-> {
	    	 Critter.displayWorld();
	     });
	        
	     VBox primaryRootPane = new VBox();
	     Scene primaryScene = new Scene(primaryRootPane, 600, 500);
	     primaryRootPane.getChildren().addAll(makePane, showPane, stepPane, statsPane, seedPane, quitPane);
	     
	     primaryStage.setScene(primaryScene);
	     primaryStage.sizeToScene();
	     primaryStage.show();
	 }
    
    private void drawShapes(GraphicsContext gc) {
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(5);
        gc.strokeLine(40, 10, 10, 40);
        gc.fillOval(10, 60, 30, 30);
        gc.strokeOval(60, 60, 30, 30);
        gc.fillRoundRect(110, 60, 30, 30, 10, 10);
        gc.strokeRoundRect(160, 60, 30, 30, 10, 10);
        gc.fillArc(10, 110, 30, 30, 45, 240, ArcType.OPEN);
        gc.fillArc(60, 110, 30, 30, 45, 240, ArcType.CHORD);
        gc.fillArc(110, 110, 30, 30, 45, 240, ArcType.ROUND);
        gc.strokeArc(10, 160, 30, 30, 45, 240, ArcType.OPEN);
        gc.strokeArc(60, 160, 30, 30, 45, 240, ArcType.CHORD);
        gc.strokeArc(110, 160, 30, 30, 45, 240, ArcType.ROUND);
        gc.fillPolygon(new double[]{10, 40, 10, 40},
                       new double[]{210, 210, 240, 240}, 4);
        gc.strokePolygon(new double[]{60, 90, 60, 90},
                         new double[]{210, 210, 240, 240}, 4);
        gc.strokePolyline(new double[]{110, 140, 110, 140},
                          new double[]{210, 210, 240, 240}, 4);
    }
}
