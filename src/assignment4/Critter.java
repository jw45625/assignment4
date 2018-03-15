package assignment4;
/* CRITTERS Critter.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * <Student1 Name>
 * <Student1 EID>
 * <Student1 5-digit Unique No.>
 * <Student2 Name>
 * <Student2 EID>
 * <Student2 5-digit Unique No.>
 * Slip days used: <0>
 * Fall 2016
 */


import java.util.List;

/* see the PDF for descriptions of the methods and fields in this class
 * you may add fields, methods or inner classes to Critter ONLY if you make your additions private
 * no new public, protected or default-package code or data can be added to Critter
 */


public abstract class Critter {
	private static String myPackage;
	private	static List<Critter> population = new java.util.ArrayList<Critter>();
	private static List<Critter> babies = new java.util.ArrayList<Critter>();

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
	
	private boolean moveAttempted;
	
	protected final void walk(int direction) {
		move(direction, 1, Params.walk_energy_cost);
	}
	
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
	private final void move(int direction, int speed, int energyCost) {
		if(moveAttempted == false) {
			if(direction == 0) {
				x_coord = (x_coord + speed) % Params.world_width;
			}
			
			if(direction == 1) {
				x_coord = (x_coord + speed) % Params.world_width;
				y_coord = (y_coord + Params.world_width - speed) % Params.world_width;
			}
			
			if(direction == 2) {
				y_coord = (y_coord + Params.world_width - speed) % Params.world_width;
			}
			
			if(direction == 3) {
				x_coord = (x_coord + Params.world_width - speed) % Params.world_width;
				y_coord = (y_coord + Params.world_width - speed) % Params.world_width;
			}
			
			if(direction == 4) {
				x_coord = (x_coord + Params.world_width - speed) % Params.world_width;
			}
			
			if(direction == 5) {
				x_coord = (x_coord + Params.world_width - speed) % Params.world_width;
				y_coord = (y_coord + speed) % Params.world_height;
			}
			
			if(direction == 6) {
				y_coord = (y_coord + speed) % Params.world_height;
			}
			
			if(direction == 7) {
				x_coord = (x_coord + speed) % Params.world_width;
				y_coord = (y_coord + speed) % Params.world_height;
			}
			
			moveAttempted = true;
		}

		energy = energy - energyCost;
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
	 * @param critter_class_name
	 * @throws InvalidCritterException
	 */
	public static void makeCritter(String critter_class_name) throws InvalidCritterException {
		try {
			Class c = Class.forName(critter_class_name); 
			
			Critter newCritter = (Critter) c.newInstance();
			
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
	 * @return List of Critters.
	 * @throws InvalidCritterException
	 */
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
		List<Critter> result = new java.util.ArrayList<Critter>();
		try {
			Class c = Class.forName(critter_class_name);
			
			Critter testCritter = (Critter) c.newInstance();
			
			for(Critter currentCritter : population) {
				if(testCritter.toString().equals(currentCritter.toString())) {
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
	 * @param critters List of Critters.
	 */
	public static void runStats(List<Critter> critters) {
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
		// Complete this method.
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
		for(int i = 0 ; i < population.size() ; i++) {
			critterOne = population.get(i);
			critterOne.doTimeStep();
			critterOne.energy = critterOne.energy - Params.rest_energy_cost;
			if(critterOne.energy <= 0) {
				population.remove(critterOne);
				i--;
			}
		}
		
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
		
		//loop to create more algae
		for(int i = 0 ; i < Params.refresh_algae_count ; i++) {
			try{
				makeCritter(myPackage + ".Algae");
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
			if(critterOne.energy == 0) {
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
		System.out.print("+");
		
		for(int j = 0 ; j < Params.world_width ; j++) {
			System.out.print("-");
		}
		
		System.out.println("+");
		
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
		
		System.out.print("+");
		
		for(int j = 0 ; j < Params.world_width ; j++) {
			System.out.print("-");
		}
		
		System.out.println("+");
		

	}
}