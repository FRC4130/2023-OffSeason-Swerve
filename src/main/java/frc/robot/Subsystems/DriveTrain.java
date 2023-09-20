package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.sensors.Pigeon2;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robots.RobotMap;

public class DriveTrain {
    public static TalonFX leftDrive;
    public static TalonFX leftDrive2;
    public static TalonFX rightDrive;
    public static TalonFX rightDrive2;
    
    public static Pigeon2 pigeon;

    private final int kTimeoutMs = 10;
    
    public DriveTrain(){
        leftDrive = RobotMap.leftDrive;
        leftDrive2 = RobotMap.leftDrive2;
        rightDrive = RobotMap.rightDrive;
        rightDrive2 = RobotMap.rightDrive2;

        pigeon = RobotMap.pigeon;

        leftDrive2.follow(leftDrive);
        rightDrive2.follow(rightDrive);

        //Senors
        leftDrive.setSensorPhase(true);
        leftDrive.setInverted(true);
        leftDrive2.setInverted(InvertType.FollowMaster);
        leftDrive.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 5);

        rightDrive.setSensorPhase(false);
        rightDrive.setInverted(false);
        rightDrive2.setInverted(InvertType.FollowMaster);
        rightDrive.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 5);

        leftDrive.set(ControlMode.PercentOutput, 0);
        rightDrive.set(ControlMode.PercentOutput, 0);
    }

    public void setNeutralMode(NeutralMode nm){
        leftDrive.setNeutralMode(nm);
        leftDrive2.setNeutralMode(nm);
        rightDrive.setNeutralMode(nm);
        rightDrive2.setNeutralMode(nm);
    }

    public void RobotMovement(double leftThrottle, double rightThrottle){
        leftDrive.set(ControlMode.PercentOutput, leftThrottle * -1);
        rightDrive.set(ControlMode.PercentOutput, rightThrottle * -1);
    }

    public void arcade(double throttle, double turn){
		RobotMovement(throttle+turn, throttle-turn);	
	}

    public void setPos(double nativeUnits){
        leftDrive.set(ControlMode.MotionMagic, nativeUnits);
        rightDrive.set(ControlMode.MotionMagic, nativeUnits);
    }

    public double distanceToRotations(double inches){
        //return ( ( (2048*25) * inches ) / 92 )*(51.25/17);
        return ((320.733 * 3) * inches); //500.733
    }

    public double getLeftPos(){
        return leftDrive.getSelectedSensorPosition();
    }

    public double getRightPos(){
        return rightDrive.getSelectedSensorPosition();
    }

    public void setMagic(int cruiseVelocity, int acceleration){
        leftDrive.configMotionCruiseVelocity(cruiseVelocity, kTimeoutMs);
        leftDrive.configMotionAcceleration(acceleration, kTimeoutMs);

        rightDrive.configMotionCruiseVelocity(cruiseVelocity, kTimeoutMs);
        rightDrive.configMotionAcceleration(acceleration, kTimeoutMs);
    }

    public void resetSensors(){
        leftDrive.setSelectedSensorPosition(0);
        rightDrive.setSelectedSensorPosition(0);
    }

    public double getHeading(){
        return pigeon.getYaw();
    }

    public void smartDashboard(){
        SmartDashboard.putNumber("Left Velocity", leftDrive.getSelectedSensorVelocity());
        SmartDashboard.putNumber("Left Pos", leftDrive.getSelectedSensorPosition());

        SmartDashboard.putNumber("Right Velocity", rightDrive.getSelectedSensorVelocity());
        SmartDashboard.putNumber("Right Pos", rightDrive.getSelectedSensorPosition());
    }
}
