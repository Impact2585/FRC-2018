package org.usfirst.frc.team2585.input;

/**
 * Operator controls for the robot
 */
public abstract class InputMethod {
	/**
	 * @return a boolean denoting whether or not there should be intakes from the right
	 */
	public boolean shouldIntakeRight() {
		return false;
	}
	/**
	 * @return a boolean denoting whether or not there should be intakes from the left
	 */
	public boolean shouldIntakeLeft() {
		return false;
	}
	
}
