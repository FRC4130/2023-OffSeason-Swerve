package frc.robot;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;

import com.ctre.phoenix.schedulers.ConcurrentScheduler;
import com.ctre.phoenix.schedulers.SequentialScheduler;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Loops.DriveDistance;
import frc.robot.Loops.DriveStraight;
import frc.robot.Robots.Loops;
import frc.robot.Robots.RobotMap;
import frc.robot.Robots.Subsystems;

public class Robot extends TimedRobot {

  ConcurrentScheduler teleop;

  SequentialScheduler red;
  SequentialScheduler blue;

  Alliance side;

  String[] pos = {"Basic", "Basic Plus", "Advanced", "Pro", "Pro Plus"};

  int posi = 0;

  @Override
  public void robotInit() {
    RobotMap.Init();
    Subsystems.Init();
    teleop = new ConcurrentScheduler();
    Loops.sTeleop(teleop);
    teleop.startAll();
  }

  @Override
  public void robotPeriodic() {
    SmartDashboard.putString("Auton", pos[posi]);
    SmartDashboard.putNumber("Match Time", DriverStation.getMatchTime());
  }

  @Override
  public void autonomousInit() {
    red = new SequentialScheduler(0);
    blue = new SequentialScheduler(0);
    SmartDashboard.putString("Alliance", side.toString());

    if(side == Alliance.Red){
      switch(posi){
        //Basic - Cone 2nd Level, preload & park charging station
        case 0:
          red.add(new DriveDistance(15));
        break;
        //Basic Plus - Basic + Cube middle lower
        case 1:
        break;
        //Advanced - Cone 2nd Level in coop & level charging station
        case 2:
        break;
        //Pro - Start at wall, play as many as possible
        case 3:
        break;
        //Pro Plus - Pro + substation
        case 4:
        break;
      }
      red.start();
    }
    else{
      switch(posi){
        //Basic - Cone 2nd Level, preload & park charging station
        case 0:
        break;
        //Basic Plus - Basic + Cube middle lower
        case 1:
        break;
        //Advanced - Cone 2nd Level in coop & level charging station
        case 2:
        break;
        //Pro - Start at wall, play as many as possible
        case 3:
        break;
        //Pro Plus - Pro + substation
        case 4:
        break;
      }
    blue.start();
    }
  }
  @Override
  public void autonomousPeriodic() {
    if(side == Alliance.Red){
      red.process();
    }else{
      blue.process();
    }
  }

  @Override
  public void teleopInit() {}

  @Override
  public void teleopPeriodic() {
    teleop.process();
  }

  @Override
  public void disabledInit() {
    side = DriverStation.getAlliance();
  }

  @Override
  public void disabledPeriodic(){
if(RobotMap.driverController.getL3Button()){
  posi++;
}}

  @Override
  public void testInit() {}

  @Override
  public void testPeriodic() {}
}
