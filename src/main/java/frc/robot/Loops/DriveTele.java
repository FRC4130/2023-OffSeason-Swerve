package frc.robot.Loops;

import com.ctre.phoenix.ILoopable;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.PS4Controller;
import frc.robot.Robots.RobotMap;
import frc.robot.Robots.Subsystems;
import frc.robot.Subsystems.DriveTrain;

public class DriveTele implements ILoopable{

    DriveTrain _drive;
    PS4Controller _controller;

    public DriveTele(){
        _drive = Subsystems.driveTrain;
        _controller = RobotMap.driverController;
    }

    public void onStart(){
        _drive.setNeutralMode(NeutralMode.Brake);
    }

    public void onLoop(){
        double steer = _controller.getRightY();
        double drive = _controller.getLeftY();
        steer *= 1;
        drive *= 1;
        _drive.RobotMovement(drive, steer);
    }

    public void onStop(){
        _drive.RobotMovement(0, 0);
    }
    
    public boolean isDone(){
        return false;}
}
