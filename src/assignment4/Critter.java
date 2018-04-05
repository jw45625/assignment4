package assignment4;
/* CRITTERS Critter.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * Jonathan Wu
 * jw45625
 * 15465
 * Fall 2016
 */


import java.util.List;
import javafx.scene.shape.*;

/* see the PDF for descriptions of the methods and fields in this class
 * you may add fields, methods or inner classes to Critter ONLY if you make your additions private
 * no new public, protected or default-package code or data can be added to Critter
 */


public abstract class Critter {
	/* NEW FOR PROJECT 5 */
	public enum CritterShape {
		CIRCLE,
		SQUARE,
		TRIANGLE,
		DIAMOND,
		STAR
	}
	
	/* the default color is white, which I hope makes critters invisible by default
	 * If you change the background color of your View component, then update the default
	 * color to be the same as you background 
	 * 
	 * critters must override at least one of the following three methods, it is not 
	 * proper for critters to remain invisible in the view
	 * 
	 * If a critter only overrides the outline color, then it will look like a non-filled 
	 * shape, at least, that's the intent. You can edit these default methods however you 
	 * need to, but please preserve that intent as you implement them. 
	 */
	public javafx.scene.paint.Color viewColor() { 
		return javafx.scene.paint.Color.WHITE; 
	}
	
	public javafx.scene.paint.Color viewOutlineColor() { return viewColor(); }
	public javafx.scene.paint.Color viewFillColor() { return viewColor(); }
	
	public abstract CritterShape viewShape(); 
	
	private static String myPackage;
	private	static List<Critter> population = new java.util.ArrayList<Critter>();
	private static List<Critter> babies = new java.util.ArrayList<Critter>();
	private static View viewer = new View();
	
	// Gets the package name.  This assumes that Critter and its subclasses are all in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}
	
	private static java.util.Random rand = new java.util.Random();
	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}
	
	public static void setSeed(long new_seed) {
		rand = new java.util.Random(new_seed);
	}
	
	
	/* a one-character long string that visually depicts your critter in the ASCII interface */
	public String toString() { return ""; }
	
	private int energy = 0;
	protected int getEnergy() { return energy; }
	
	private int x_coord;
	private int y_coord;
	
	/**
	 * boolean value to check if this Critter moved this step
	 */
	private boolean moveAttempted;
	
	/**
	 * will try to walk in the direction given
	 * @param direction is from 0 to 7 which indicates the direction the Critter will move
	 */
	protected final void walk(int direction) {
		move(direction, 1, Params.run_energy_cost);
	}
	
	/**
	 * will try to walk in the direction given
	 * @param direction is from 0 to 7 which indicates the direction the Critter will move
	 */
	protected final void run(int direction) {
		move(direction, 2, Params.run_energy_cost);
	}
	
	/**
	 * Moving the Critter in a given direction
	 * @param direction is the direction of the movement. From 0 to 7. 0 is going right. 1 is going right and up. 2 is going up. 
	 * As direction increases, the actual direction of the movement changes counterclockwise
	 * @param speed is the how many spaces in that direction the Critter will go
	 * @param energyCost is the cost of energy of this movement
	 */
	private void move(int direction, int speed, int energyCost) {
		int dx;
		int dy;
		int []distanceMoved;
		distanceMoved = getDistanceMoved(direction, speed);
		dx = distanceMoved[0];
		dy = distanceMoved[1];
		if(dx > 0) {
			x_coord = (x_coord + dx) % Params.world_width;
		}
		else if(dx < 0){
			x_coord = ((x_coord + Params.world_width) + dx) % Params.world_width;
		}
		
		if(dy > 0) {
			y_coord = (y_coord + dy) % Params.world_height;
		}
		else if(dy < 0) {
			y_coord = ((y_coord + Params.world_height) + dy) % Params.world_height;
		}
		if(!moveAttempted) {
			energy = energy - energyCost;
			
			moveAttempted = true;
		}
	}
	
	/**
	 * get the change position for the cell in the given direction
	 * @param direction is the direction of the movement. From 0 to 7. 0 is going right. 1 is going right and up. 2 is going up. 
	 * As direction increases, the actual direction of the movement changes counterclockwise
	 * @param speed is the how many spaces in that direction the Critter will go
	 */
	private int[] getDistanceMoved(int direction, int speed) {
		int dx = 0;
		int dy = 0;
		int[] distanceMoved = new int[2];
		if(direction == 0) {
			dx = new Integer(speed);
		}
		
		if(direction == 1) {
			dx = new Integer(speed);
			dy = new Integer(-1 * speed);
		}
		
		if(direction == 2) {
			dy = new Integer(-1 * speed);
		}
		
		if(direction == 3) {
			dx = new Integer(-1 * speed);
			dy = new Integer(-1 * speed);
		}
		
		if(direction == 4) {
			dx = new Integer(-1 * speed);
		}
		
		if(direction == 5) {
			dx = new Integer(-1 * speed);
			dy = new Integer(speed);
		}
		
		if(direction == 6) {
			dy = new Integer(speed);
		}
		
		if(direction == 7) {
			dx = new Integer(speed);
			dy = new Integer(speed);
		}
		
		distanceMoved[0] = dx;
		distanceMoved[1] = dy;
		
		return distanceMoved;
	}
	
	/**
	 * Creates an offspring if the parent have enough energy in the give direction
	 * @param offspring is the offspring Critter that is trying to be reproduced
	 * @param direction is the direction the offspring will appear in relation to the parent
	 */
	protected final void reproduce(Critter offspring, int direction) {
		if(energy >= Params.min_reproduce_energy) {
			offspring.energy = energy / 2;
			offspring.x_coord = x_coord;
			offspring.y_coord = y_coord;
			energy = energy - offspring.energy;
			
			offspring.move(direction, 1, 0);
			babies.add(offspring);
		}
		else {
			return;
		}
	}

	public abstract void doTimeStep();
	public abstract boolean fight(String oponent);
	
	/**
	 * create and initialize a Critter subclass.
	 * critter_class_name must be the unqualified name of a concrete subclass of Critter, if not,
	 * an InvalidCritterException must be thrown.
	 * (Java weirdness: Exception throwing does not work properly if the parameter has lower-case instead of
	 * upper. For example, if craig is supplied instead of Craig, an error is thrown instead of
	 * an Exception.)
	 * @param critter_class_name is the String with the name of the Critter
	 * @throws InvalidCritterException is thrown if there in no Crtter with the give name
	 */
	public static void makeCritter(String critter_class_name) throws InvalidCritterException {
		try {
			Class critterClass = Class.forName(myPackage + "." + critter_class_name); 
			
			Critter newCritter = (Critter) critterClass.newInstance();
			
			newCritter.x_coord = getRandomInt(Params.world_width);
					
			newCritter.y_coord = getRandomInt(Params.world_height);
			
			newCritter.energy = Params.start_energy;
			
			newCritter.moveAttempted = false;
			
			population.add(newCritter);
		}
		catch(Exception e) {
			throw new InvalidCritterException(critter_class_name);
		}
	}
	
	/**
	 * Gets a list of critters of a specific type.
	 * @param critter_class_name What kind of Critter is to be listed.  Unqualified class name.
	 * @return results with is a List of Critters.
	 * @throws InvalidCritterException is the given critter name is not a available Critter
	 */
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
		List<Critter> result = new java.util.ArrayList<Critter>();
		try {
			Class critterClass = Class.forName(myPackage + "." + critter_class_name);
			
			Critter testCritter = (Critter) critterClass.newInstance();
			
			for(Critter currentCritter : population) {
				if(testCritter.getClass().isInstance(currentCritter)) {
					result.add(currentCritter);
				}
			}
		}
		catch(Exception e) {
			throw new InvalidCritterException(critter_class_name);
		}
		
		return result;
	}
	
	/**
	 * Prints out how many Critters of each type there are on the board.
	 * @param critters is a List of Critters.
	 */
	public static String runStats(List<Critter> critters) {
		/*
		System.out.print("" + critters.size() + " critters as follows -- ");
		java.util.Map<String, Integer> critter_count = new java.util.HashMap<String, Integer>();
		for (Critter crit : critters) {
			String crit_string = crit.toString();
			Integer old_count = critter_count.get(crit_string);
			if (old_count == null) {
				critter_count.put(crit_string,  1);
			} else {
				critter_count.put(crit_string, old_count.intValue() + 1);
			}
		}
		String prefix = "";
		for (String s : critter_count.keySet()) {
			System.out.print(prefix + s + ":" + critter_count.get(s));
			prefix = ", ";
		}
		System.out.println();		
		*/
		///*
		String statsInfo = "";
		
		statsInfo = statsInfo.concat("" + critters.size() + " critters as follows -- " + "\n");
		java.util.Map<String, Integer> critter_count = new java.util.HashMap<String, Integer>();
		for (Critter crit : critters) {
			String crit_string = crit.toString();
			Integer old_count = critter_count.get(crit_string);
			if (old_count == null) {
				critter_count.put(crit_string,  1);
			} else {
				critter_count.put(crit_string, old_count.intValue() + 1);
			}
		}
		String prefix = "";
		for (String s : critter_count.keySet()) {
			statsInfo = statsInfo.concat(prefix + s + ":" + critter_count.get(s));
			prefix = ", ";
		}
		
		statsInfo = statsInfo.concat("\n");
		
		return statsInfo;
		//*/
	}
	
	/* the TestCritter class allows some critters to "cheat". If you want to 
	 * create tests of your Critter model, you can create subclasses of this class
	 * and then use the setter functions contained here. 
	 * 
	 * NOTE: you must make sure that the setter functions work with your implementation
	 * of Critter. That means, if you're recording the positions of your critters
	 * using some sort of external grid or some other data structure in addition
	 * to the x_coord and y_coord functions, then you MUST update these setter functions
	 * so that they correctly update your grid/data structure.
	 */
	static abstract class TestCritter extends Critter {
		protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
		}
		
		protected void setX_coord(int new_x_coord) {
			super.x_coord = new_x_coord;
		}
		
		protected void setY_coord(int new_y_coord) {
			super.y_coord = new_y_coord;
		}
		
		protected int getX_coord() {
			return super.x_coord;
		}
		
		protected int getY_coord() {
			return super.y_coord;
		}
		

		/*
		 * This method getPopulation has to be modified by you if you are not using the population
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.
		 */
		protected static List<Critter> getPopulation() {
			return population;
		}
		
		/*
		 * This method getBabies has to be modified by you if you are not using the babies
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.  Babies should be added to the general population 
		 * at either the beginning OR the end of every timestep.
		 */
		protected static List<Critter> getBabies() {
			return babies;
		}
	}

	/**
	 * Clear the world of all critters, dead and alive
	 */
	public static void clearWorld() {
		population.clear();
		babies.clear();
	}
	
	/**
	 * does a time step for all critters
	 */
	public static void worldTimeStep() {
		Critter critterOne;
		Critter critterTwo;
		boolean critterOneEngages = false;
		boolean critterTwoEngages = false;
		int critterOneRoll = 0;
		int critterTwoRoll = 0;
		
		//loop for doTimeStep for each Critter
		try {
			for(int i = 0 ; i < population.size() ; i++) {
				critterOne = population.get(i);
				critterOne.doTimeStep();
				critterOne.energy = critterOne.energy - Params.rest_energy_cost;
				if(critterOne.energy <= 0) {
					population.remove(critterOne);
					i--;
				}
			}
		}
		catch(Error e) {
			System.out.println("1");
		}
		
		try {
			//loop for encounters
			for(int i = 0 ; i < population.size(); i++) {
				critterOne = population.get(i);
				for(int j = i + 1 ; j < population.size() ; j++) {
					critterTwo = population.get(j);
					if((critterOne.x_coord == critterTwo.x_coord) && (critterOne.y_coord == critterTwo.y_coord)) {
						critterOneEngages = critterOne.fight(critterTwo.toString());
						critterTwoEngages = critterTwo.fight(critterOne.toString());
						
						if(critterOneEngages == true) {
							critterOneRoll = getRandomInt(critterOne.energy + 1);
						}
						else {
							critterOneRoll = 0;
						}
						
						if(critterTwoEngages == true) {
							critterTwoRoll = getRandomInt(critterTwo.energy + 1);
						}
						else {
							critterTwoRoll = 0;
						}
						
						if((critterOne.x_coord == critterTwo.x_coord) && (critterOne.y_coord == critterTwo.y_coord)) { //checks if a critter ran away
							if(critterOneRoll >= critterTwoRoll) {
								//System.out.print(critterOne + "starting energy" + critterOne.energy);
								critterOne.energy = critterOne.energy + (critterTwo.energy / 2);
								critterTwo.energy = 0;
								//System.out.println(critterOne + "beats" + critterTwo + "new energy" + critterOne.energy);
							}
							else {
								//System.out.print(critterTwo + "starting energy" + critterTwo.energy);
								critterTwo.energy = critterTwo.energy + (critterOne.energy / 2);
								critterOne.energy = 0;
								//System.out.println(critterTwo + "beats" + critterOne + "new energy" + critterTwo.energy);
							}
						}
					}
					
				}
			}
		}
		catch(Error e) {
			System.out.println("2");
		}
		
		
		//loop to create more algae
		for(int i = 0 ; i < Params.refresh_algae_count ; i++) {
			try{
				makeCritter("Algae");
			}
			catch(InvalidCritterException ice){
				System.out.println(ice);
			}
		}
		
		//loop to add babies to population
		for(int i = 0 ; i < babies.size() ; i++) {
			population.add(babies.get(i));
		}
		babies.clear();
		
		for(int i = 0 ; i < population.size() ; i++) {
			critterOne = population.get(i);
			if(critterOne.energy <= 0) {
				population.remove(critterOne);
				i--;
			}
			else {
				critterOne.moveAttempted = false;
			}
		}
	}
	
	/**
	 * displays the world of critters and border
	 * 
	 */
	public static void displayWorld() {
		viewer.clearGrid();
		
		viewer.paintGridLines();
		
		
		for(int i = 0 ; i < Params.world_height ; i++) {
			for(int j = 0 ; j < Params.world_width ; j++) {
				boolean critterDisplayed = false;
				for(Critter crit : population) {
					if((crit.x_coord == j) && (crit.y_coord == i) && (!critterDisplayed)) {
						critterDisplayed = true;
						int index = -1;
						Shape s;
						switch(crit.viewShape()) {
							case CIRCLE: 	index = 0;
										 	break;
							case SQUARE: 	index = 1;
										 	break;
							case TRIANGLE: 	index = 2;
										   	break;
							case DIAMOND: 	index = 3;
										  	break;
							case STAR: 		index = 4;
											break;
						}
						viewer.paintShape(index, j, i, crit.viewColor(), crit.viewOutlineColor());
					}
				}
			}
		}
	}
	
	
	
	/**
	 * look in the cell in the given direction and give steps contains a Critter
	 * @param direction is the direction of the movement. From 0 to 7. 0 is going right. 1 is going right and up. 2 is going up. 
	 * As direction increases, the actual direction of the movement changes counterclockwise
	 * @param steps is true for two step and false for single steps
	 * @return the Critter string of the the Critter of the cell or null is it is empty
	 */
	protected final String look(int direction, boolean steps) {
		int speed = 0;
		int compare_x = 0;
		int compare_y = 0;
		
		if(steps) {
			speed = 1;
		}

		int[] distanceMoved = getDistanceMoved(direction, speed);
		
		int dx = distanceMoved[0];
		int dy = distanceMoved[1];
		
		if(dx > 0) {
			compare_x = (compare_x + dx) % Params.world_width;
		}
		else if(dx < 0){
			compare_x = ((compare_x + Params.world_width) + dx) % Params.world_width;
		}
		
		if(dy > 0) {
			compare_y = (compare_y + dy) % Params.world_height;
		}
		else if(dy < 0){
			compare_y = ((compare_y + Params.world_height) + dy) % Params.world_height;
		}
		
		for(Critter crit : population) {
			if((crit.x_coord == compare_x) && (crit.y_coord == compare_y)) {
				return crit.toString();
			}
		}
		
		return "null";
	}
	
	/**
	 * checks the Critter's surroundings to see if there a space to run away to.
	 * @return direction which is an available direction
	 */
	protected final int runAwaySpaceAvailable() {
		int directionAvailable = -1;
		int[] directionsNotAllowed = {-1, -1, -1, -1, -1, -1, -1, -1};
		int directionIndex = 0;
		
		for(int i = 0; i < population.size() ; i++) {
			Critter testCritter = population.get(i);
			if((testCritter.x_coord == x_coord + 1) && (testCritter.y_coord == y_coord)) {
				directionsNotAllowed[0] = 0;
			}
			
			if((testCritter.x_coord == x_coord + 1) && (testCritter.y_coord == y_coord - 1)) {
				directionsNotAllowed[1] = 1;
			}
			
			if((testCritter.x_coord == x_coord) && (testCritter.y_coord == y_coord - 1)) {
				directionsNotAllowed[2] = 2;
			}
			
			if((testCritter.x_coord == ((x_coord + Params.world_width) - 1) % Params.world_width) && (testCritter.y_coord == y_coord - 1)) {
				directionsNotAllowed[3] = 3;
			}
			
			if((testCritter.x_coord == ((x_coord + Params.world_width) - 1) % Params.world_width) && (testCritter.y_coord == y_coord)) {
				directionsNotAllowed[4] = 4;
			}
			
			if((testCritter.x_coord == ((x_coord + Params.world_width) - 1) % Params.world_width) && (testCritter.y_coord == y_coord + 1)) {
				directionsNotAllowed[5] = 5;
			}
			
			if((testCritter.x_coord == x_coord) && (testCritter.y_coord == y_coord + 1)) {
				directionsNotAllowed[6] = 6;
			}
			
			if((testCritter.x_coord == x_coord + 1) && (testCritter.y_coord == y_coord + 1)) {
				directionsNotAllowed[7] = 7;
			}
		}
		
		for(int i = 0; i < 8; i++) {
			if(directionsNotAllowed[i] == -1) {
				directionAvailable = i;
			}
		}
		
		return directionAvailable;
	}
}
