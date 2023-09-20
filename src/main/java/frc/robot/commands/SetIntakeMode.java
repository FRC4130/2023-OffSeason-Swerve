package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Constants.intakeMode;
import frc.robot.subsystems.Intake;

public class SetIntakeMode extends CommandBase{
    private Intake intakeSubsystem;
    private intakeMode intakeMode;

    public SetIntakeMode(Intake intakeSubsystem, intakeMode intakeMode) {
        this.intakeSubsystem = intakeSubsystem;

        this.intakeMode = intakeMode;

        addRequirements(intakeSubsystem);
    }

    @Override
    public void initialize() {
        intakeSubsystem.stop();
    }

    @Override
    public void execute() {
        switch(intakeMode) {
            case intaking:
                intakeSubsystem.intaking();
            break;
            case intakeslow:
                intakeSubsystem.intakeslow();
            break;
            case outtaking:
                intakeSubsystem.outtaking();
            break;
            case resetpos:
                intakeSubsystem.resetPosition();
            break;
            case stop:
                intakeSubsystem.stop();
            break;
        }
    }

    @Override
    public void end(boolean interrupted) {
        
    }
}
