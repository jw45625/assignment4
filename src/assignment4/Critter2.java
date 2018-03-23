package assignment4;

/* CRITTERS Critter2.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * Jonathan Wu
 * jw45625
 * 15465
 * Fall 2016
 */

import java.util.*;

/**
 * Critter2 make a critter that waits for Algae to grow on it then eat it.
 * this critter will try to run from all fights that are not with algae
 * Critter 2 will try to reproduce as much as possible
 * @author jonat
 *
 */
public class Critter2 extends Critter {
	/**
	 * the time step for Critter2
	 * will try to reproduce as much as possible
	 */
	@Override
	public void doTimeStep() {
		Critter1 child = new Critter1();
		reproduce(child, Critter.getRandomInt(8));
		//walk(Critter.getRandomInt(8));
	}
	
	/**
	 * will try to run from all fights that are not with Algae
	 */
	@Override
	public boolean fight(String opponent) {
		int direction = -1;
		if (opponent.equals("@")) {
			return true;
		}
		else {
			direction = runAwaySpaceAvailable();
			if(direction != -1) {
				walk(direction);
				System.out.println("Critter2 ran away");
			}
		}
		
		return false;
	}
	
	/**
	 * prints the character 2 which is the symbol for Critter2
	 */
	public String toString() {
		return "2";
	}
	
}
