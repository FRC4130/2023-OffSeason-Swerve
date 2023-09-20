package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Constants.wristMode;
import frc.robot.subsystems.Wrist;

public class SetWristMode extends CommandBase{
    private Wrist wristSubsystem;
    private wristMode WristMode;

    public SetWristMode(Wrist wristSubsystem, wristMode wristMode) {
        this.wristSubsystem = wristSubsystem;

        this.WristMode = wristMode;

        addRequirements(wristSubsystem);
    }

    @Override
    public void initialize() {
        wristSubsystem.stop();
    }

    @Override
    public void execute() {
        switch(WristMode) {
            case home:
                wristSubsystem.home();
            break;
            case low:
                wristSubsystem.low();
            break;
            case mid:
                wristSubsystem.mid();
            break;
            case resetpos:
                wristSubsystem.resetPosition();
            break;
            case stop:
                wristSubsystem.stop();
            break;
        }
    }

    @Override
    public void end(boolean interrupted) {
        // armSubsystem.stop();
    }
}
