package frc.robot.Loops;

import com.ctre.phoenix.ILoopable;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robots.Subsystems;
import frc.robot.Subsystems.DriveTrain;
import frc.robot.Subsystems.groundPosition;

public class DriveAndIntake implements ILoopable{
    
    //DriveTrain
	private double distanceNative;
	private double distanceInches;
	private DriveTrain _drive;
	private double acceptableError = 1000; //1000;
	
	private int 	cruiseVelocity	=	22000;
	private int 	acceleration	=	12000;

    //Intake
    private groundPosition _position;
    private double durriationMs = 1500;
    private double endTimeMs = 0;
	long downTime = 0;
	
	public DriveAndIntake(double inches, double durriationMs) {
		
		System.out.println("Drive and Intake task has been created.");
		
		distanceInches = inches;
		_drive = Subsystems.driveTrain;

		_position = Subsystems.groundPosition;
        this.durriationMs = durriationMs;

	}

	public DriveAndIntake(double inches){
		
		distanceInches = inches;
		_drive = Subsystems.driveTrain;

		_position = Subsystems.groundPosition;
	}
	
	@Override
	public void onStart() {
		
		_drive.resetSensors();
		_drive.setNeutralMode(NeutralMode.Brake);
		
		distanceNative = _drive.distanceToRotations(distanceInches);
		
		if (cruiseVelocity > 0 && acceleration > 0) {
			_drive.setMagic(cruiseVelocity, acceleration);
		}

		endTimeMs = System.currentTimeMillis() + durriationMs;
		downTime = System.currentTimeMillis() + 200;
	}

	@Override
	public void onLoop() {
		
        _drive.setPos(distanceNative);
		_position.set(_position.Sucking);
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
            if ( System.currentTimeMillis() >= endTimeMs) {
                _position.set(_position.Stored);
                return true;
            }
		}	

		return false;		
	}

	@Override
	public void onStop() {
		
		System.out.println("[WARNING] Driving for distance was stopped");
		System.out.println("[WARNING] The DriveTrain is still in the Motion Magic Control Mode");
		
	}
}
