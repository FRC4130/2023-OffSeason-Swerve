package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Wrist;

public class JoystickArm extends CommandBase{
    private Wrist wristSubsystem;
    private DoubleSupplier speed;

    public JoystickArm(Wrist wristSubsystem, DoubleSupplier speed){
        this.wristSubsystem = wristSubsystem;
        this.speed = speed;

        addRequirements(wristSubsystem);
    }

    @Override
    public void initialize() {
        wristSubsystem.setSpeedWrist(0);
    }

    @Override
    public void execute() {
        wristSubsystem.setSpeedWrist(speed.getAsDouble()*0.25);
    }

    @Override
    public void end(boolean interrupted) {
        wristSubsystem.setSpeedWrist(0);
    }
}
