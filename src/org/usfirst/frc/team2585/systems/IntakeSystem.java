package org.usfirst.frc.team2585.systems;

import org.usfirst.frc.team2585.robot.Environment;
import org.usfirst.frc.team2585.robot.RobotMap;

import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Victor;

//This system intakes the power cubes and loads them on the robot
public class IntakeSystem extends RobotSystem implements Runnable {
	
		SpeedController intakeRightMotor;
		SpeedController intakeLeftMotor;
		
		//These numbers need to be adjusted after testing
		static double intakeMotorSpeed = 0.9;

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.usfirst.frc.team2585.systems.RobotSystem#init(org.usfirst.frc.team2585.
		 * robot.Environment)
		 */
		@Override
		public void init(Environment environ) {
			super.init(environ);

			intakeRightMotor = new Victor(RobotMap.INTAKE_MOTOR_RIGHT);
			intakeLeftMotor = new Victor(RobotMap.INTAKE_MOTOR_LEFT);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Runnable#run()
		 */
		public void run() {
			// runs right, left or both intake motors
			if (input.shouldIntakeRight() || input.shouldIntakeLeft()) {
				setMotorSpeed(intakeMotorSpeed);
			}
			else {
				setMotorSpeed(0);
			}
		}

		/**
		 * @param intakeSpeed is the speed to set the motor to
		 */
		public void setMotorSpeed(double intakeSpeed) {
			intakeRightMotor.set(intakeSpeed);
			intakeLeftMotor.set(intakeSpeed);
		}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.impact2585.lib2585.Destroyable#destroy()
	 */
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		if (intakeRightMotor instanceof PWM) {
			((PWM) intakeRightMotor).free();
		if (intakeLeftMotor instanceof PWM) {
			((PWM) intakeLeftMotor).free();
		}
		}
	}

	// Eclipse forced this piece of code
	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

}