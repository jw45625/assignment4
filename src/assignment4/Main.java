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


/*
 * Usage: java <pkgname>.Main <input file> test
 * input file is optional.  If input file is specified, the word 'test' is optional.
 * May not use 'test' argument without specifying input file.
 */
public class Main {

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
            			//System.out.println(count);
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
        	}
        	catch(Error er) {
        		System.out.println("error processing: " + inputString);
        	}
        	
        }
        
      
        
        
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
}
