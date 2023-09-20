package frc.robot.subsystems;

import javax.swing.text.Position;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandPS4Controller;

public class Intake extends SubsystemBase{
    public WPI_TalonFX intake;

    public Intake(){
        intake = new WPI_TalonFX(9, "CTRE Chain");
        intake.setNeutralMode(NeutralMode.Brake);
    }

    public void resetPosition(){
        intake.setSelectedSensorPosition(0);
    }

    public void setSpeedIntake(double speed){
        intake.set(ControlMode.PercentOutput, speed);
    }

    public void intaking(){
        setSpeedIntake(-0.5);
    }

    public void intakeslow(){
        setSpeedIntake(-0.1);
    }

    public void outtaking(){
        setSpeedIntake(0.8);
    }

    public void stop(){
        setSpeedIntake(0);
    }

}
