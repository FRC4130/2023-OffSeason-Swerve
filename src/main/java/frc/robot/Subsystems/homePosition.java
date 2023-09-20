package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.Robots.RobotMap;

public class homePosition {
    public DoubleSolenoid armPos;
    public final Value In = Value.kReverse;
    public final Value Out = Value.kForward;

    public homePosition(){
        armPos = RobotMap.airIntake;
        armPos.set(In);
    }

    public void set(Value v1){
        armPos.set(v1);
    }

    public void disable(){
        armPos.set(Value.kOff);
    }
}
