package frc.robot.Loops;

import com.ctre.phoenix.ILoopable;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.sensors.Pigeon2;

import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robots.RobotMap;

public class DriveStraight implements ILoopable{
    Pigeon2 pigeon;

    TalonFX leftDrive;
    TalonFX leftDrive2;
    TalonFX rightDrive;
    TalonFX rightDrive2;

    PS4Controller controller;

    double kPgain = 0.04; // % throttle per degree of error
    double kDgain = 0.0004; // % throttle per angle velocity dps
    double kMaxCorrectionRatio = 0.30; //cap corrective turning throttle
    double targetAngle = 0;

    final int kTimeoutMs = 30;

    @Override
    public void onStart() {
        pigeon = RobotMap.pigeon;
        final int kTimeoutMs = 30;
        pigeon.setYaw(0.0, kTimeoutMs);
        controller = RobotMap.driverController;

        leftDrive = RobotMap.leftDrive;
        leftDrive2 = RobotMap.leftDrive2;
        rightDrive = RobotMap.rightDrive;
        rightDrive2 = RobotMap.rightDrive2;

        leftDrive2.follow(leftDrive);
        rightDrive2.follow(rightDrive);
    }   

    @Override
    public void onLoop() {
        double yaw = pigeon.getYaw();
        double currentAngle = yaw;

        SmartDashboard.putNumber("Battery Voltage", RobotController.getBatteryVoltage());
        SmartDashboard.putNumber("Pigeon Yaw", pigeon.getYaw());
        SmartDashboard.putNumber("Pigeon Device ID", pigeon.getDeviceID());

        // Zero / Reset Pigeon
        if(controller.getPSButton()){
            //Zero Yaw, has to be done using pigeon, not motor controller
            pigeon.setYaw(0, kTimeoutMs);
            pigeon.setAccumZAngle(0, kTimeoutMs);

            System.out.println("=============================");
            System.out.println("Yaw and Accumulated Z Zero'ed");
            System.out.println("=============================");
            System.out.println();
        }

        if(controller.getSquareButton()) {

            double forwardThrottle = ((controller.getRawAxis(1) + controller.getRawAxis(5))/2);
            double turnThrottle = (targetAngle - currentAngle) * kPgain * kDgain;
            
            double left = forwardThrottle - turnThrottle;
            double right = forwardThrottle + turnThrottle;

            leftDrive.set(ControlMode.PercentOutput, left * -1);
            rightDrive.set(ControlMode.PercentOutput, right * -1);

            SmartDashboard.putNumber("Turn Throttle", turnThrottle);
            SmartDashboard.putNumber("Forward Throttle", forwardThrottle);
            SmartDashboard.putNumber("Target Angle", targetAngle);
            
        }
    }

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public void onStop() {}
    
}
