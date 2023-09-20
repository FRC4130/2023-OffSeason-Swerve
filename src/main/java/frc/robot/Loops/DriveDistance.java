package frc.robot.Loops;

import com.ctre.phoenix.ILoopable;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Robots.RobotMap;
import frc.robot.Robots.Subsystems;
import frc.robot.Subsystems.DriveTrain;
public class DriveDistance implements ILoopable {
	
    
    private double distanceNative;
	private double distanceInches;
	private DriveTrain _drive;
	private double acceptableError = 1000; //1000;
	
	private int 	cruiseVelocity	=	22000;
	private int 	acceleration	=	12000;
	
	public DriveDistance(double inches) {
		
		System.out.println("Drive Distance task has been created.");
		
		distanceInches = inches;
		_drive = Subsystems.driveTrain;

	}
	
	@Override
	public void onStart() {
		
		_drive.resetSensors();
		_drive.setNeutralMode(NeutralMode.Brake);
		
		distanceNative = _drive.distanceToRotations(distanceInches);
		
		if (cruiseVelocity > 0 && acceleration > 0) {
			_drive.setMagic(cruiseVelocity, acceleration);
		}
		
	}

	@Override
	public void onLoop() {
		
        _drive.setPos(distanceNative);
		
	}

	@Override
	public boolean isDone() {
		
		boolean leftAtPos = Math.abs(distanceNative - _drive.getLeftPos()) <= acceptableError;
		boolean rightAtPos = Math.abs(distanceNative - _drive.getRightPos()) <= acceptableError;
		
		SmartDashboard.putNumber("Left Error", distanceNative - _drive.getLeftPos());
		SmartDashboard.putNumber("Right Error", distanceNative - _drive.getRightPos());
		
		if (leftAtPos && rightAtPos) {
			System.out.println("[Info] Finished Driving for Distance");
			System.out.println("[WARNING] The DriveTrain is still in the Motion Magic Control Mode");
			return true;
		}
		
		return false;
		
	}

	@Override
	public void onStop() {
		
		System.out.println("[WARNING] Driving for distance was stopped");
		System.out.println("[WARNING] The DriveTrain is still in the Motion Magic Control Mode");
		
	}

}