package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.wristMode;
import frc.robot.subsystems.Wrist;

public class SetWristMode extends CommandBase{
    private Wrist wristSubsystem;
    public wristMode WristMode;
    public Timer setWristtimer;
    public double setWristTime;
    

    public SetWristMode(Wrist wristSubsystem, wristMode wristMode, double setWristTime) {
        this.wristSubsystem = wristSubsystem;

        this.WristMode = wristMode;

        this.setWristTime = setWristTime;

        addRequirements(wristSubsystem);
    }

    @Override
    public void initialize() {
        wristSubsystem.stop();
        setWristtimer.reset();
        setWristtimer.start();
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

    @Override
    public boolean isFinished() {
        if (setWristtimer.get() < setWristTime){
            return false;
        } else {
            return true;
        }
    }
}
