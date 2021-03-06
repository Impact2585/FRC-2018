package org.usfirst.frc.team2585.robot;

import org.impact2585.lib2585.RunnableExecutor;
import org.usfirst.frc.team2585.systems.Initializable;
import org.usfirst.frc.team2585.systems.WheelSystem;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutonomousExecutor extends RunnableExecutor implements Initializable {

	private static final long serialVersionUID = -3678926207508995014L;
	
	private Environment env;
	protected WheelSystem drivetrain;
	private long initialTime;
	public long timeElapsed;
	public AutonomousCommand task;
	
	/* (non-Javadoc)
	 * @see org.usfirst.frc.team2585.systems.Initializable#init(org.usfirst.frc.team2585.Environment)
	 */
	@Override
	public void init(Environment environ) {
		env = environ;

		drivetrain = (WheelSystem) env.getSystem(Environment.WHEEL_SYSTEM);
		resetTime();
	}
	
	public void setTask(AutonomousCommand command) {
		task = command;
	}
	
	/**
	 * @return a long denoting the time that has passed since initialization
	 * because initialization should be at the beginning of the execution, it is the time it has been running
	 */
	private long findTimeElapsed() {
		return System.currentTimeMillis() - initialTime;
	}
	
	/**
	 * Update the timeElapsed
	 */
	public void updateTime() {
		timeElapsed = findTimeElapsed();
		SmartDashboard.putNumber("Auton Time Elapsed", timeElapsed);
	}
	
	/**
	 * Reset the elapsed time
	 */
	public void resetTime() {
		initialTime = System.currentTimeMillis();
		timeElapsed = 0;
	}
		
	/* (non-Javadoc)
	 * @see org.impact2585.lib2585.RunnableExecuter#execute()
	 */
	public void execute() {
		updateTime();
		boolean shouldResetTime = task.execute(timeElapsed); // Execute is an abstract method
		if (shouldResetTime) {
			resetTime();
		}
	}
}
