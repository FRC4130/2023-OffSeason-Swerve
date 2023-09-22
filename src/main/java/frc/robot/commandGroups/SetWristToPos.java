package frc.robot.commandGroups;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.wristMode;
import frc.robot.commands.SetWristMode;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Wrist;

public class SetWristToPos extends ParallelCommandGroup{
    Wrist wristSub;
    SetWristMode SetWristMode;
    wristMode wristMode0;

    public SetWristToPos(Wrist wristSub){
        this.wristSub = wristSub;

        addCommands(
            new SetWristMode(wristSub, wristMode0.mid)
        );
    }
}
