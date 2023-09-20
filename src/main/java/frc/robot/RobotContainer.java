// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandPS4Controller;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.intakeMode;
import frc.robot.Constants.wristMode;
import frc.robot.autos.*;
import frc.robot.commands.*;
import frc.robot.subsystems.*;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  /* Controllers */
  private final CommandPS4Controller controller = new CommandPS4Controller(0);
  private final CommandPS4Controller opController = new CommandPS4Controller(1);

  /* Driver Buttons */
  private final Trigger zeroSwerve = controller.options();
  
  /* Subsystems */
  public static final Swerve s_Swerve = new Swerve();
  private static final Wrist wristSubsystem = new Wrist();
  private static final Intake intakeSubsystem = new Intake();

  //Auto Chooser
  SendableChooser<Command> m_AutoChooser = new SendableChooser<>();

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {

    s_Swerve.setDefaultCommand(new TeleopSwerve(s_Swerve, controller, true));
    intakeSubsystem.setDefaultCommand(new SetIntakeMode(intakeSubsystem, intakeMode.intakeslow));
    
    // Configure the button bindings
    configureButtonBindings();
    
    //Add autonoumous options to chooser
    m_AutoChooser.setDefaultOption("None", Autos.none());
    m_AutoChooser.addOption("PathPlanner Example", Autos.exampleAuto());

    SmartDashboard.putData(m_AutoChooser);

    wristSubsystem.setDefaultCommand(new JoystickArm(wristSubsystem, () -> opController.getLeftY()));
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {

    opController.povUp().toggleOnTrue(new SetWristMode(wristSubsystem, wristMode.home).until(() -> (Math.abs(controller.getLeftY()) > 0.2)));
    opController.povRight().toggleOnTrue(new SetWristMode(wristSubsystem, wristMode.low).until(() -> (Math.abs(opController.getLeftY()) > 0.2)));
    opController.povDown().toggleOnTrue(new SetWristMode(wristSubsystem, wristMode.mid).until(() -> (Math.abs(opController.getLeftY()) > 0.2)));
    opController.square().whileTrue(new SetIntakeMode(intakeSubsystem, intakeMode.intaking).until(() -> (Math.abs(opController.getLeftY()) > 0.2)));
    opController.triangle().whileTrue(new SetIntakeMode(intakeSubsystem, intakeMode.outtaking).until(() -> (Math.abs(opController.getLeftY()) > 0.2)));
    opController.R1().whileTrue(new SetWristMode(wristSubsystem, wristMode.resetpos));
    
    //Button to reset swerve odometry and angle
    zeroSwerve
      .onTrue(new InstantCommand(() -> s_Swerve.zeroGyro(0))
      .alongWith(new InstantCommand(() -> s_Swerve.resetOdometry(new Pose2d(0.0, 0.0, Rotation2d.fromDegrees(0))))));
      
  }

  public void teleInit(){
    wristSubsystem.resetPosition();
    intakeSubsystem.resetPosition();
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return m_AutoChooser.getSelected();
  }
}
