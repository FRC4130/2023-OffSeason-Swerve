package frc.robot.Robots;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.sensors.Pigeon2;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

public class RobotMap {

    //Motors Objects
    public static TalonFX leftDrive;
    public static TalonFX rightDrive;
    public static TalonFX rightDrive2;
    public static TalonFX leftDrive2;
    public static TalonFX lowerIntake;
    public static TalonFX upperIntake;
    public static TalonFX armLeft;
    public static TalonFX armRight;

    //Controller Objects
    public static PS4Controller driverController;
    public static PS4Controller opController;

    //Solenoids Objects
    public static DoubleSolenoid groundIntake;
    public static DoubleSolenoid airIntake;

    //Pigeon Objects
    public static Pigeon2 pigeon;

    public static frc.robot.Subsystems.groundPosition groundPosition;

    //Motor IDs
    public static final int kLeftDriveID = 1;
    public static final int kLeftDrive2ID = 2;
    public static final int kRightDriveID = 3;
    public static final int kRightDrive2ID = 4;
    public static final int kGroundIntakeID = 8;
    public static final int kAirIntakeID = 9;
    public static final int kArmLeftID = 11;
    public static final int kArmRightID = 12;

    //Solenoid IDs
    public static final int kIntakeInID = 1;
    public static final int kIntakeOutID = 2;
    public static final int kArmInID = 5;
    public static final int kArmOutID = 6;

    //Controller IDs
    public static final int kDriverControllerID = 0;
    public static final int kOpControllerID = 1;

    public static final int kPiegonID = 13;

    public static void Init() {
        //Drivetrain
        leftDrive = new TalonFX(kLeftDriveID, "CTRE Chain");
        rightDrive = new TalonFX(kRightDriveID, "CTRE Chain");
        rightDrive2 = new TalonFX(kRightDrive2ID, "CTRE Chain");
        leftDrive2 = new TalonFX(kLeftDrive2ID, "CTRE Chain");
        lowerIntake = new TalonFX(kGroundIntakeID, "CTRE Chain");
        upperIntake = new TalonFX(kAirIntakeID, "CTRE Chain");
        armLeft = new TalonFX(kAirIntakeID, "CTRE Chain");
        armRight = new TalonFX(kAirIntakeID, "CTRE Chain");

        //Controllers
        driverController = new PS4Controller(kDriverControllerID);
        opController = new PS4Controller(kOpControllerID);

        //Solenoids
        groundIntake = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 1, 2);
        airIntake = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 6, 5);

        //Pigeon and CANdle
        pigeon = new Pigeon2(kPiegonID);
    }
}

