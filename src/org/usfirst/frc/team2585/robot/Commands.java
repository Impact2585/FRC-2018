package org.usfirst.frc.team2585.robot;

import org.usfirst.frc.team2585.systems.WheelSystem;

import edu.wpi.first.wpilibj.DriverStation;

/**
 * A container class for the different commands that can be passed to the autonomous executor
 */
public class Commands {
	
	private static Environment environ;
	private static WheelSystem drivetrain;
	
	private String gameData;
	private int location;
	
	/**
	 * Constructor that sets the environment and the required systems
	 * @param e the environment of the robot
	 */
	public Commands(Environment env) {
		environ = env;

		drivetrain = (WheelSystem) environ.getSystem(Environment.WHEEL_SYSTEM);
		
		// Location of the driverStation: 1, 2, 3; L, M, R
		location = DriverStation.getInstance().getLocation(); 
		gameData = DriverStation.getInstance().getGameSpecificMessage();
	}
	
	/**
	 * Move the robot forward with no rotation
	 */
	private static void driveForward() {
		drivetrain.driveWithRotation(0.8, 0.0);
	}
	
	/**
	 * Turn the robot left with no forward movement
	 */
	private static void turnLeft() {
		drivetrain.driveWithRotation(0.0, -0.2);
	}
	
	/**
	 * Turn the robot right with no forward movement
	 */
	private static void turnRight() {
		drivetrain.driveWithRotation(0.0, 0.2);
	}
	
	/**
	 * Set the forward movement and rotation to 0
	 */
	private static void stop() {
		drivetrain.driveWithRotation(0, 0);
	}
	
	
	/**
	 * Autonomous command that drives according to its position and the side of its switch
	 */
	public class Main implements AutonomousCommand {
		private static final int timeToDriveStraight = 3100;
		
		private void runFromMiddleToLeft(long timeElapsed) {
		}
		
		private void runFromMiddleToRight(long timeElapsed) {
		}
		
		private void runFromLeftSide(long timeElapsed) {
		}
		
		private void runFromRightSide(long timeElapsed) {
		}
		
		private void runStraight(long timeElapsed) {
			if (timeElapsed < timeToDriveStraight) {
				driveForward();
			} else {
				stop();
			}
		}
		
		/* (non-Javadoc)
		 * @see org.usfirst.frc.team2585.AutonomousCommand#execute(long)
		 */
		@Override
		public void execute(long timeElapsed) {
			if (location == 2 && gameData.charAt(0) == 'R') {
				//if in the middle with the switch on the right, try to deposit a cube
				runFromMiddleToRight(timeElapsed);
				
			} else if (location == 2) {
				//if in middle with switch on left, try to deposit a cube
				runFromMiddleToLeft(timeElapsed);
				
			} else if (location == 1 && gameData.charAt(0) == 'L'){

				//if on left side with switch on left side, try to deposit a cube
				runFromLeftSide(timeElapsed);
				
			} else if (location == 3 && gameData.charAt(0) == 'R'){
				//if on right side with switch on right side, try to deposit a cube
				runFromRightSide(timeElapsed);
				
			} else {
				//else, run straight
				runStraight(timeElapsed);
			}
		}
	}
	
	/**
	 * Autonomous command that drives straight no matter what
	 */
	public class Straight implements AutonomousCommand {
		private static final int timeToDriveStraight = 3100;
		/* (non-Javadoc)
		 * @see org.usfirst.frc.team2585.AutonomousCommand#execute(long)
		 */
		@Override
		public void execute(long timeElapsed) {
			if (timeElapsed < timeToDriveStraight) {
				driveForward();
			} else {
				stop();
			}
		}
	}
	
	/**
	 * Autonomous command that does nothing
	 */
	public class None implements AutonomousCommand {
		@Override
		public void execute(long timeElapsed) {
			stop();
		}
	}
}
