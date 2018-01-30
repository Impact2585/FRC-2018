package org.usfirst.frc.team2585.robot;

import org.impact2585.lib2585.RunnableExecutor;
import org.usfirst.frc.team2585.systems.Initializable;
import org.usfirst.frc.team2585.systems.WheelSystem;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;

public class AutonomousExecutor extends RunnableExecutor implements Initializable {

	private static final long serialVersionUID = -3678926207508995014L;
	
	private Encoder encoder;
	private Environment env;
	protected WheelSystem drivetrain;
	private double distanceTraveled;
	public AutonomousCommand task;
	
	/* (non-Javadoc)
	 * @see org.usfirst.frc.team2585.systems.Initializable#init(org.usfirst.frc.team2585.Environment)
	 */
	@Override
	public void init(Environment environ) {
		env = environ;
		drivetrain = (WheelSystem) env.getSystem(Environment.WHEEL_SYSTEM);
		encoder = new Encoder(0, 1, true, EncodingType.k4X);
		encoder.setMaxPeriod(.1);
		encoder.setMinRate(10);
		encoder.setDistancePerPulse(5);
		encoder.setReverseDirection(false);
		encoder.setSamplesToAverage(7);
		resetDistance();
	}
	
	public void setTask(AutonomousCommand command) {
		task = command;
	}
	
	/**
	 * @return a double denoting the distance that has passed since the last reset
	 */
	private double findDistanceTraveled() {
		//to test; unknown units
		return encoder.getDistance();
	}
	
	/**
	 * Update the distanceTraveled
	 */
	public void updateDistance() {
		distanceTraveled = findDistanceTraveled();
	}
	
	/**
	 * Reset the traveled distance
	 */
	public void resetDistance() {
		distanceTraveled = 0;
		encoder.reset();
	}
		
	/* (non-Javadoc)
	 * @see org.impact2585.lib2585.RunnableExecuter#execute()
	 */
	public void execute() {
		updateDistance();
		task.execute(distanceTraveled); // Execute is an abstract method
	}
}
