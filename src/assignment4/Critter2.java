package assignment4;

import java.util.*;

public class Critter2 extends Critter {

	@Override
	public void doTimeStep() {
		walk(0);
	}

	@Override
	public boolean fight(String opponent) {
		if (getEnergy() > 10) return true;
		return false;
	}
	
	public String toString() {
		return "2";
	}
	
	public void test (List<Critter> l) {
		
	}
}
