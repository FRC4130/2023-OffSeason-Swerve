package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.Robots.RobotMap;

public class groundPosition {
    public DoubleSolenoid groundPos;
    public final Value Stored = Value.kReverse;
    public final Value Sucking = Value.kForward;

    public groundPosition(){
        groundPos = RobotMap.groundIntake;
        groundPos.set(Stored);
    }

    public void set(Value v1){
        groundPos.set(v1);
    }

    public void disable(){
        groundPos.set(Value.kOff);
    }
}
