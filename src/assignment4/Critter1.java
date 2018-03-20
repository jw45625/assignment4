package assignment4;

import java.util.*;

public class Critter1 extends Critter {

	@Override
	public void doTimeStep() {
		if(getEnergy() > 150) {
			Critter1 child = new Critter1();
			reproduce(child, Critter.getRandomInt(8));
		}
		walk(Critter.getRandomInt(8));
	}

	@Override
	public boolean fight(String opponent) {
		int direction = -1;
		if (getEnergy() > 10) return true;
		else {
			direction = runAwaySpaceAvailable();
			if(direction != -1) {
				run(direction);
			}
		}
		return false;
	}
	
	public String toString() {
		return "1";
	}
	
	public void test (List<Critter> l) {
		
	}
}
