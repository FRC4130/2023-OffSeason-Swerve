package frc.robot.Robots;

import frc.robot.Subsystems.DriveTrain;
import frc.robot.Subsystems.groundPosition;

public class Subsystems {
    public static DriveTrain driveTrain;
    public static groundPosition groundPosition;

    public static void Init(){
        driveTrain = new DriveTrain();
        groundPosition = new groundPosition();
    }
}
