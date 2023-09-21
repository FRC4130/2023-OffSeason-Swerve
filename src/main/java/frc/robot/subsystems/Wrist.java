package frc.robot.subsystems;

import javax.swing.text.Position;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandPS4Controller;
import frc.robot.Robot;
import frc.robot.RobotContainer;

public class Wrist extends SubsystemBase{
    public WPI_TalonFX wrist;

    public Wrist(){
        wrist = new WPI_TalonFX(17, "CTRE Chain");

        wrist.selectProfileSlot(0, 0);
        wrist.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 15);
        wrist.config_kP(0, 0.1);
        wrist.configClosedLoopPeakOutput(0, 0.6);
        wrist.configAllowableClosedloopError(0, 750);
        wrist.setNeutralMode(NeutralMode.Brake);
        wrist.configPeakOutputReverse(-1);
        wrist.configMotionAcceleration(4000);
        wrist.configMotionCruiseVelocity(5500);
    }

    public void doMagic(int position){
        wrist.configMotionAcceleration(8000);
        wrist.configMotionCruiseVelocity(8500);
        wrist.set(ControlMode.MotionMagic, position);
    }

    public void resetPosition(){
        wrist.setSelectedSensorPosition(0);
    }

    public void setSpeedWrist(double speed){
        wrist.set(ControlMode.PercentOutput, speed);
    }

    public void home(){
        doMagic(0);
    }

    public void low(){
        doMagic(-47300);
    }

    public void mid(){
        doMagic(-13300);
    }

    public void stop(){
        setSpeedWrist(0);
        home();
    }

}
