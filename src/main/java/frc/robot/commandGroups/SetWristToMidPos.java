package frc.robot.commandGroups;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.wristMode;
import frc.robot.commands.SetIntakeMode;
import frc.robot.commands.SetWristMode;
import frc.robot.subsystems.Wrist;

public class SetWristToMidPos extends SequentialCommandGroup{
    Wrist wristSub;
    SetWristMode SetWristMode;
    SetIntakeMode setIntakeMode;
    wristMode wristMode0;

    public SetWristToMidPos(Wrist wristSub){
        this.wristSub = wristSub;

        addCommands(
            new SetWristMode(wristSub, wristMode.mid, 3),
            new WaitCommand(0.5)
        );
    }
}
