package assignment4;

/* CRITTERS Critter1.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * Jonathan Wu
 * jw45625
 * 15465
 * Fall 2016
 */

import java.util.*;
/**
 * Critter1 will wonder around looking for food
 * Critter1 will try to fight unless it is low on energy.
 * @author jonat
 *
 */
public class Critter1 extends Critter {
	
	/**
	 * the time step for the Critter1
	 * walk unless it is low on energy
	 */
	@Override
	public void doTimeStep() {
		if(getEnergy() > 50) {
			Critter1 child = new Critter1();
			reproduce(child, Critter.getRandomInt(8));
		}
		if(getEnergy() > 50) {
			walk(Critter.getRandomInt(8));
		}
	}

	/**
	 * The fight function for Critter1
	 * it will try to fight unless it is low on energy
	 */
	@Override
	public boolean fight(String opponent) {
		int direction = -1;
		if (getEnergy() > 50) {
			return true;
		}
		else {
			direction = runAwaySpaceAvailable();
			if(direction != -1) {
				walk(direction);
			}
		}
		return false;
	}
	
	/**
	 * Prints a "1"
	 */
	public String toString() {
		return "1";
	}
	
	/**
	 * get the total amount of Critters
	 * also displays the amount Critters that might reproduce depends on the min_reproduce_energy in Params
	 * @param critters1
	 */
	public static void runStats(List<Critter> critters1) {
		int canReproduce = 0;
		System.out.println(critters1.size() + " total Critter1s");
		for(int i = 0; i < critters1.size() ; i++) {
			if(critters1.get(i).getEnergy() >= 50) {
				canReproduce++;
			}
		}
		
		System.out.println(canReproduce + " Critter1s might reproduce");
	}
	
}
