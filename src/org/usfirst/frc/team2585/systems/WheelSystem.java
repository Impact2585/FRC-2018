package org.usfirst.frc.team2585.systems;

import org.impact2585.lib2585.RampedSpeedController;
import org.usfirst.frc.team2585.robot.Environment;
import org.usfirst.frc.team2585.robot.RobotMap;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This system controls the drivetrain of the robot
 */
public class WheelSystem extends RobotSystem {
	private RampedSpeedController rightDrive;
	private RampedSpeedController leftDrive;
	
	private ADXRS450_Gyro gyro;
	
	private double targetAngle = 0.0;
	
	private final double DEADZONE = 0.2;
	
	private final double FORWARD_MULTIPLIER = 0.65;
	private final double ROTATION_RATE = 4.0;
	private final double DERIVATIVE_MULTIPLIER = 0.1;
	private final double CORRECTION_MULTIPLIER = 0.01;
	private final double MAX_CORRECTION = 0.6;
	
	public static boolean IS_TEST_SYSTEM = false;
	
		
	/* (non-Javadoc)
	 * @see org.usfirst.frc.team2585.systems.Initializable#init(org.usfirst.frc.team2585.Environment)
	 */
	@Override
	public void init(Environment environ) {
		super.init(environ);

		leftDrive = new RampedSpeedController(new Spark(RobotMap.LEFT_DRIVE_MOTOR));
		rightDrive = new RampedSpeedController(new Spark(RobotMap.RIGHT_DRIVE_MOTOR));
		
		gyro = new ADXRS450_Gyro();
		targetAngle = getGyroAngle();
	}
	
	/**
	 * Pass the user inputs to the drive train to run the motors the appropriate amounts
	 */
	public void run() {
		double forwardInput = -input.forwardAmount(); // reverse direction of driving
		forwardInput = (Math.abs(forwardInput) > DEADZONE)? forwardInput * FORWARD_MULTIPLIER : 0;
		
		double rotationInput = input.rotationAmount();
		rotationInput = (Math.abs(rotationInput) > DEADZONE)? rotationInput : 0;
		
		driveWithGyro(forwardInput, rotationInput);
	}
	
	/**
	 * Drive using the gyro to keep track of the angle
	 * @param forwardInput the amount to drive forward
	 * @param rotationInput the amount to adjust the targetAngle
	 */
	public void driveWithGyro(double forwardInput, double rotationInput) {		
		// Adjust Target Angle
		targetAngle += rotationInput * ROTATION_RATE;
		
		// Rotate towards target angle
		double rate = getGyroRate() * DERIVATIVE_MULTIPLIER;
		double correction = (targetAngle - getGyroAngle() - rate) * CORRECTION_MULTIPLIER;
		
		// Min correction
		if (Math.abs(correction) < 0.001) correction = 0;
		// Max correction
		if (Math.abs(correction) > MAX_CORRECTION) {
			correction = Math.copySign(MAX_CORRECTION, correction);
		}
		
		arcadeDrive(forwardInput, correction);
		
		if (!IS_TEST_SYSTEM) {
			SmartDashboard.putNumber("Gyro Angle",  getGyroAngle());
			SmartDashboard.putNumber("Gyro Target Angle",  targetAngle);
			SmartDashboard.putNumber("Gyro Rate",   getGyroAngle());
		}
	}
	
	/**
	 * @param leftSpeed the speed to set the left side to
	 * @param rightSpeed the speed to set the right side to
	 */
	private void setSideSpeeds(double leftSpeed, double rightSpeed) {
		if (Math.abs(leftSpeed) > 1) leftSpeed = Math.copySign(1, leftSpeed);
		if (Math.abs(rightSpeed) > 1) rightSpeed = Math.copySign(1, rightSpeed);
		
		// Both sides driving in same direction
		// If wiring doesn't explicitly compensate for different sides, must make rightSpeed negative
		leftDrive.updateWithSpeed(leftSpeed);
		rightDrive.updateWithSpeed(rightSpeed);
		
		if (!IS_TEST_SYSTEM) {
			SmartDashboard.putNumber("LEFT SPEED RAW", leftSpeed);
			SmartDashboard.putNumber("RIGHT SPEED RAW", rightSpeed);
			
			SmartDashboard.putBoolean("SPEEDS ARE EQUAl", leftSpeed == rightSpeed);
			SmartDashboard.putBoolean("SPEEDS ARE OPPOSITE", leftSpeed == -rightSpeed);
		}
	}
	
	/**
	 * @param forward the amount to drive forward
	 * @param rotation the amount to rotate
	 */
	protected void arcadeDrive(double forward, double rotation) {
		double leftSpeed = forward + rotation;
		double rightSpeed = forward - rotation;
		setSideSpeeds(leftSpeed, rightSpeed);
	}
	
	/**
	 * @return the current angle that the robot is facing from the gyroscope
	 */
	protected double getGyroAngle() {
		return gyro.getAngle();
	}
	
	/**
	 * @return the rate that the robot is rotating from the gyroscope
	 */
	public double getGyroRate() {
		return gyro.getRate();
	}
	
	/**
	 * Reset the angle of the gyroscope
	 */
	public void resetGyro() {
		gyro.reset();
	}
	
	/* (non-Javadoc)
	 * @see org.usfirst.frc.team2585.systems.RobotSystem#stop()
	 */
	@Override
	public void stop() {
		setSideSpeeds(0.0, 0.0);
	}

	/* (non-Javadoc)
	 * @see org.impact2585.lib2585.Destroyable#destroy()
	 */
	@Override
	public void destroy() {
		leftDrive.destroy();
		rightDrive.destroy();
	}
}
