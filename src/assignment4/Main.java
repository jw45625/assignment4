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

import java.awt.*;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import java.lang.reflect.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import java.util.*;



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
    boolean animating = false;
    int stepsPerFrame;
    String statsString = "";

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
        
        launch(args);
        
        System.out.flush();

    }
    
    public static void showFiles(File[] files) {
	    for (File file : files) {
	        if (file.isDirectory()) {
	            System.out.println("Directory: " + file.getName());
	            showFiles(file.listFiles()); // Calls same method again.
	        } else {
	            System.out.println("File: " + file.getName());
	        }
	    }
	}
 
    
    @Override
	 public void start(Stage primaryStage) {
		 primaryStage.setTitle("Java FX Demo Program");
		 Critter.displayWorld();
		 
	     VBox quitPane = new VBox();
	     quitPane.setBackground(new Background(new BackgroundFill(Color.BISQUE, CornerRadii.EMPTY, Insets.EMPTY)));
	     VBox stepPane = new VBox();
	     stepPane.setBackground(new Background(new BackgroundFill(Color.BISQUE, CornerRadii.EMPTY, Insets.EMPTY)));
	     VBox makePane = new VBox();
	     makePane.setBackground(new Background(new BackgroundFill(Color.BISQUE, CornerRadii.EMPTY, Insets.EMPTY)));
	     VBox showPane = new VBox();
	     showPane.setBackground(new Background(new BackgroundFill(Color.ALICEBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
	     VBox statsPane = new VBox();
	     statsPane.setBackground(new Background(new BackgroundFill(Color.ALICEBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
	     VBox seedPane = new VBox();
	     seedPane.setBackground(new Background(new BackgroundFill(Color.BISQUE, CornerRadii.EMPTY, Insets.EMPTY)));
	     VBox startAnimationPane = new VBox();
	     VBox stopAnimationPane = new VBox();
	     HBox animationPane = new HBox();
	     animationPane.getChildren().addAll(startAnimationPane, stopAnimationPane);
	     animationPane.setBackground(new Background(new BackgroundFill(Color.ALICEBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
	     StackPane outputTextPane = new StackPane();
	     
	     Button quitButton = new Button("quit");
	     Label quitLabel = new Label("Press To Quit");
	     quitPane.getChildren().addAll(quitLabel, quitButton);

	     Button stepButton = new Button("step");
	     Label stepLabel = new Label("Press to step given amount of times");
	     TextField stepText = new TextField();
	     stepText.setPromptText("#");
	     stepPane.getChildren().addAll(stepLabel, stepText, stepButton);
	     
	     Button showButton = new Button("show");
	     Label showLabel = new Label("Press to show the world");
	     showPane.getChildren().addAll(showLabel, showButton);
	     
	     Button makeButton = new Button("make");
	     ComboBox<String> makeCritterBox = new ComboBox<>();
	     Label makeLabel = new Label("Press to make # of chosen Critter");
	     TextField makeText = new TextField(); 
	     makeText.setPromptText("#");
	     TextField makeCritterText = new TextField(); 
	     makeCritterText.setPromptText("Critter");
	     makePane.getChildren().addAll(makeLabel, makeCritterText, makeText, makeButton);
	     
	     Button seedButton = new Button("seed");
	     Label seedLabel = new Label("Press to give seed value");
	     TextField seedText = new TextField(); 
	     seedText.setPromptText("#");
	     seedPane.getChildren().addAll(seedLabel, seedText, seedButton);
	     seedPane.resize(10000, 10000);
	     
	     Button statsButton = new Button("stats");
	     ComboBox<String> statsCritterBox = new ComboBox<>();
	     TextField statsCritterText = new TextField(); 
	     statsCritterText.setPromptText("Critter");
	     Label statsLabel = new Label("Press to see stats of choosen Critters");
	     statsPane.getChildren().addAll(statsLabel, statsCritterText, statsButton);
	     
	     Button animationButton = new Button("Start Animate");
	     Button stopAnimationButton = new Button("Stop Animation");
	     Label startAnimationLabel = new Label("Press to start animation");
	     Label stopAnimationLabel = new Label("Press to stop animation");
	     ComboBox<String> animationNumBox = new ComboBox<>();
	     animationNumBox.getItems().addAll("2", "3", "5", "10");
	     animationNumBox.getSelectionModel().selectFirst();
	     startAnimationPane.getChildren().addAll(startAnimationLabel, animationNumBox, animationButton);
	     stopAnimationPane.getChildren().addAll(stopAnimationLabel, stopAnimationButton);
	     
	     Label statsOutput = new Label();
	     statsOutput.setWrapText(true);
	     statsOutput.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
	     statsOutput.setPrefSize(600,100);
	     statsOutput.setText("Stats");
	     
	     
	     stepsPerFrame = Integer.parseInt(animationNumBox.getValue());
    	 Timeline tl = new Timeline();
    	 tl.setCycleCount(Animation.INDEFINITE);
    	 KeyFrame critterAnimation = new KeyFrame(Duration.seconds(0.5), 
    			new EventHandler<ActionEvent>() {
    		 		public void handle(ActionEvent event) {
	           			for(int i = 0 ; i < stepsPerFrame ; i++) {
	           				Critter.worldTimeStep();
	           			}
	           			Critter.displayWorld();
    		 		}
	           	});

	     tl.getKeyFrames().add(critterAnimation);
	     
	     FlowPane statsOutputPane = new FlowPane();
	     statsOutputPane.getChildren().add(statsOutput);
	     
	     makeButton.setOnAction(e-> {
	    	 String className = makeCritterText.getText();
    		 try{
    			 int count = Integer.parseInt(makeText.getText());
    			 
    			 for(int i = 0 ; i < count ; i++) {
    				 Critter.makeCritter(className);
    			 }
    			 
    			 Critter.displayWorld();
    			 makeText.setPromptText("#");
    			 makeCritterText.setPromptText("Critter");
    		 }
    		 catch(Exception exep) {
    			 statsOutput.setText("Error: invalid Critter or Num");
    		 }
	     });
	     
	     stepButton.setOnAction(e-> {
    		 try{
    			 int count = Integer.parseInt(stepText.getText());
    			 
    			 for(int i = 0 ; i < count ; i++) {
    				 Critter.worldTimeStep();
    			 }
    			 Critter.displayWorld();
    			 if(!statsString.equals("")) {
    				 Class<?> critterClass = Class.forName(myPackage + "." + statsString);
    		     		
    		    	 Critter crit = (Critter) critterClass.newInstance();
    	     		
    		    	 Method method = critterClass.getMethod("runStats", List.class);
    		    	 
    	    		 String statsInfo = (String) method.invoke(crit, Critter.getInstances(statsString));
    	    		 statsOutput.setText(statsInfo);
    			 }
    			 stepText.setPromptText("0");
    		 }
    		 catch(Exception exep) {
    			 System.out.println(exep);
    		 }
	     });
	     
	     statsButton.setOnAction(e-> {
	    	 try{
	    		 String className = statsCritterText.getText();
		    	 Class<?> critterClass = Class.forName(myPackage + "." + className);
	     		
		    	 Critter crit = (Critter) critterClass.newInstance();
	     		
		    	 Method method = critterClass.getMethod("runStats", List.class);
		    	 
	    		 String statsInfo = (String) method.invoke(crit, Critter.getInstances(className));
	    		 statsOutput.setText(statsInfo);
	    		 statsString = className;
	    		 statsCritterText.setPromptText("Critter");
	    	 }
	    	 catch(Exception exep) {
	    		 statsOutput.setText("Error: invalid Critter or Num");
	    	 }
	     });
	     
	     quitButton.setOnAction(e-> {
	    	 System.exit(0);
	     });
	     
	     seedButton.setOnAction(e-> {
	    	 int seed = Integer.parseInt(seedText.getText());
	    	 Critter.setSeed(seed);
	    	 seedText.setPromptText("#");
	     });
	     
	     showButton.setOnAction(e-> {
	    	 Critter.displayWorld();
	     });
	     
	     animationButton.setOnAction(e->{
	    	 makeButton.setDisable(true);
	    	 stepButton.setDisable(true);
	    	 quitButton.setDisable(true);
	    	 stepButton.setDisable(true);
		     showButton.setDisable(true);
		     statsButton.setDisable(true);
		     seedButton.setDisable(true);
		     stepsPerFrame = Integer.parseInt(animationNumBox.getValue());
		     tl.play();
	    	 
	     });
	        
	     stopAnimationButton.setOnAction(e->{
		     makeButton.setDisable(false);
	    	 stepButton.setDisable(false);
	    	 quitButton.setDisable(false);
	    	 stepButton.setDisable(false);
		     showButton.setDisable(false);
		     statsButton.setDisable(false);
		     seedButton.setDisable(false);
		     tl.pause();
	     });
	     VBox primaryRootPane = new VBox();
	     Scene primaryScene = new Scene(primaryRootPane, 600, 800);
	     primaryRootPane.getChildren().addAll(makePane, showPane, stepPane, statsPane, seedPane, animationPane, quitPane, statsOutputPane);
	     
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
