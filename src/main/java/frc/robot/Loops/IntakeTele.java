package frc.robot.Loops;

import com.ctre.phoenix.ILoopable;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.PS4Controller;
import frc.robot.Robots.RobotMap;
import frc.robot.Robots.Subsystems;
import frc.robot.Subsystems.groundPosition;

public class IntakeTele implements ILoopable{
    PS4Controller opController;
    TalonFX lowerIntake;
    groundPosition groundPosition;

    public IntakeTele(){
        opController = RobotMap.opController;
        lowerIntake = RobotMap.lowerIntake;
        groundPosition = Subsystems.groundPosition;
    }

    @Override
    public void onStart() {
        lowerIntake.set(ControlMode.PercentOutput, 0);
    }

    @Override
    public void onLoop() {
        //Lower Intake
        if (opController.getR2Button()){
            groundPosition.set(groundPosition.Sucking);
            lowerIntake.set(ControlMode.PercentOutput, -.40);
        }
        else if (opController.getR1Button()){
            lowerIntake.set(ControlMode.PercentOutput, -.30);
        }
        else{
            groundPosition.set(groundPosition.Stored);
            lowerIntake.set(ControlMode.PercentOutput, 0);
        }
    }

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public void onStop() {
        lowerIntake.set(ControlMode.PercentOutput, 0);
        groundPosition.set(groundPosition.Stored);
    }
    
}
