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
		drivetrain.driveWithGyro(0.8, 0.0);
	}
	
	/**
	 * Turn the robot left with no forward movement
	 */
	private static void turnLeft() {
		drivetrain.driveWithGyro(0.0, -0.2);
	}
	
	/**
	 * Turn the robot right with no forward movement
	 */
	private static void turnRight() {
		drivetrain.driveWithGyro(0.0, 0.2);
	}
	
	/**
	 * Set the forward movement and rotation to 0
	 */
	private static void stop() {
		drivetrain.driveWithGyro(0, 0);
	}
	
	
	/**
	 * Autonomous command that drives according to its position and the side of its switch
	 */
	public class Main implements AutonomousCommand {
		
		private static final double crossLine = 15;
		private static final double reachSwitchSide = 4;
		
		private void runStraight(double distanceTraveled) {
			if (distanceTraveled < crossLine) {
				driveForward();
			} else {
				stop();
			}
		}
		
		private void runFromMiddle(double distanceTraveled){
			if(gameData.charAt(0) == 'L'){
				
			} else if(gameData.charAt(0) == 'R'){
				
			}
		}
		private void runFromSide(double distanceTraveled){
			if(gameData.charAt(0) == 'L' && location == 1){

			} else if(gameData.charAt(0) == 'R' && location == 3){
				
			} else {
				runStraight(distanceTraveled);
			}
		}
		
		/* (non-Javadoc)
		 * @see org.usfirst.frc.team2585.AutonomousCommand#execute(double)
		 */
		@Override
		public void execute(double distanceTraveled) {
			if (location == 1 || location == 3) {
				runFromSide(distanceTraveled);
			} else if (location == 2) {
				runFromMiddle(distanceTraveled);
			} else {
				runStraight(distanceTraveled);
			}
		}
	}
	
	/**
	 * Autonomous command that drives straight no matter what
	 */
	public class Straight implements AutonomousCommand {
		private static final double crossLine = 457;
		/* (non-Javadoc)
		 * @see org.usfirst.frc.team2585.AutonomousCommand#execute(double)
		 */
		@Override
		public void execute(double distanceTraveled) {
			if (distanceTraveled < crossLine) {
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
		/* (non-Javadoc)
		 * @see org.usfirst.frc.team2585.robot.AutonomousCommand#execute(double)
		 */
		@Override
		public void execute(double distanceTraveled) {
			stop();
		}
	}
}
