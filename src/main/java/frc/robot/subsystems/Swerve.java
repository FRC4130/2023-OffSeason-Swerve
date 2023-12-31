package frc.robot.subsystems;

import frc.robot.SwerveModule;
// import com.swervedrivespecialties.swervelib.SwerveModule;
import frc.robot.Constants.Swerve.Mod0;
import frc.robot.Constants.Swerve.Mod1;
import frc.robot.Constants.Swerve.Mod2;
import frc.robot.Constants.Swerve.Mod3;
import frc.lib.util.DriftCorrection;
import frc.robot.Constants;

import com.ctre.phoenix.sensors.Pigeon2;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.CTREConfigs;

public class Swerve extends SubsystemBase {
    public SwerveDriveOdometry swerveOdometry;
    public SwerveModule[] mSwerveMods;
    public Pigeon2 gyro;

    public Swerve() {
        gyro = new Pigeon2(Constants.Swerve.pigeonID, "CTRE Chain");
        gyro.setYaw(0);
        zeroGyro(0);
        
        mSwerveMods = new SwerveModule[] {
            new SwerveModule(0, Constants.Swerve.Mod0.constants),
            new SwerveModule(1, Constants.Swerve.Mod1.constants),
            new SwerveModule(2, Constants.Swerve.Mod2.constants),
            new SwerveModule(3, Constants.Swerve.Mod3.constants)
        };

        swerveOdometry = new SwerveDriveOdometry(Constants.Swerve.swerveKinematics, getYaw(), getPositions());
    }

    /**
     * Function used to actually drive the robot
     * @param translation XY drive values
     * @param rotation Rotation value
     * @param fieldRelative True -> fieldOriented
     * @param isOpenLoop True
     */
    public void drive(Translation2d translation, double rotation, boolean fieldRelative, boolean isOpenLoop) {

        SwerveModuleState[] swerveModuleStates =
            Constants.Swerve.swerveKinematics.toSwerveModuleStates(DriftCorrection.driftCorrection(
                fieldRelative ? ChassisSpeeds.fromFieldRelativeSpeeds(
                                    translation.getX(), 
                                    translation.getY(), 
                                    rotation, 
                                    getYaw()
                                )
                                : new ChassisSpeeds(
                                    translation.getX(), 
                                    translation.getY(), 
                                    rotation), 
                                swerveOdometry.getPoseMeters())
                                );
        SwerveDriveKinematics.desaturateWheelSpeeds(swerveModuleStates, Constants.Swerve.maxSpeed);

        for(SwerveModule mod : mSwerveMods){
            mod.setDesiredState(swerveModuleStates[mod.moduleNumber], isOpenLoop);
        }
    }
    
    public void drive(SwerveModuleState[] states) {
        if (SmartDashboard.getBoolean("Debug Swerve", false)) {
            System.out.println("In drive SwerveModule[0]: " + states[0]);
            System.out.println("In drive SwerveModule[1]: " + states[1]);
            System.out.println("In drive SwerveModule[2]: " + states[2]);
            System.out.println("In drive SwerveModule[3]: " + states[3]);
        }
        // this.states = states;
        SwerveDriveKinematics.desaturateWheelSpeeds(states, Constants.AutoConstants.kMaxSpeedMetersPerSecond);

        mSwerveMods[0].setDesiredState(states[0], true);
        mSwerveMods[1].setDesiredState(states[1], true);
        mSwerveMods[2].setDesiredState(states[2], true);
        mSwerveMods[3].setDesiredState(states[3], true);
        // mSwerveMods[0].set(states[0].speedMetersPerSecond / Constants.AutoConstants.kMaxSpeedMetersPerSecond * Constants.Swerve.kMaxVoltage,
        //         states[0].angle.getRadians());
        // mSwerveMods[1].set(states[1].speedMetersPerSecond / Constants.AutoConstants.kMaxSpeedMetersPerSecond * Constants.Swerve.kMaxVoltage,
        //         states[1].angle.getRadians());
        // mSwerveMods[2].set(states[2].speedMetersPerSecond / Constants.AutoConstants.kMaxSpeedMetersPerSecond * Constants.Swerve.kMaxVoltage,
        //         states[2].angle.getRadians());
        // mSwerveMods[3].set(states[3].speedMetersPerSecond / Constants.AutoConstants.kMaxSpeedMetersPerSecond * Constants.Swerve.kMaxVoltage,
        //         states[3].angle.getRadians());
    }

    /* Used by SwerveControllerCommand in Auto */
    public void setModuleStates(SwerveModuleState[] desiredStates) {
        SwerveDriveKinematics.desaturateWheelSpeeds(desiredStates, Constants.Swerve.maxSpeed);
        
        for(SwerveModule mod : mSwerveMods){
            mod.setDesiredState(desiredStates[mod.moduleNumber], false);
        }
    }    

    /**
     * @return XY of robot on field
     */
    public Pose2d getPose() {
        return swerveOdometry.getPoseMeters();
    }

    /**
     * Use to reset odometry to a certain known pose or to zero
     * @param pose Desired new pose
     */
    public void resetOdometry(Pose2d pose) {
        swerveOdometry.resetPosition(getYaw(), getPositions(), pose);
    }

    public void resetOdometry(Pose2d pose, Rotation2d yaw) {
        swerveOdometry.resetPosition(yaw, getPositions(), pose);
    }

    public SwerveModuleState[] getStates(){
        SwerveModuleState[] states = new SwerveModuleState[4];
        for(SwerveModule mod : mSwerveMods){
            states[mod.moduleNumber] = mod.getState();
        }
        return states;
    }

    /**
     * @return Swerve Module positions
     */
    public SwerveModulePosition[] getPositions(){
        SwerveModulePosition[] positions = new SwerveModulePosition[4];
        for(SwerveModule mod : mSwerveMods){
            positions[mod.moduleNumber] = mod.getPosition();
        }
        return positions;
    }

    /**
     * Use to reset angle to certain known angle or to zero
     * @param angle Desired new angle
     */
    public void zeroGyro(double angle){
        gyro.setYaw(angle);
    }

    public Rotation2d getYaw() {
        return (Constants.Swerve.invertGyro) ? Rotation2d.fromDegrees(360 - gyro.getYaw()) : Rotation2d.fromDegrees(gyro.getYaw());
    }
    

    @Override
    public void periodic(){
        swerveOdometry.update(getYaw(), getPositions());
        SmartDashboard.putNumber("Gyro Angle", getYaw().getDegrees());

        for(SwerveModule mod : mSwerveMods){
            SmartDashboard.putNumber("Mod " + mod.moduleNumber + " Cancoder", mod.getCanCoder().getDegrees());
            SmartDashboard.putNumber("Mod " + mod.moduleNumber + " Integrated", mod.getState().angle.getDegrees());
            SmartDashboard.putNumber("Mod " + mod.moduleNumber + " Velocity", mod.getState().speedMetersPerSecond);    
            SmartDashboard.putNumber("Mod " + mod.moduleNumber + " Drive Temp", mod.getTemp(1));
            SmartDashboard.putNumber("Mod " + mod.moduleNumber + " Angle Temp", mod.getTemp(2));
            SmartDashboard.putNumber("Mod " + mod.moduleNumber + " Setpoint", mod.getDesired());
        }

        SmartDashboard.putString("XY Coord", "(" + getPose().getX() + ", " + getPose().getY() + ")");

    }

    public SwerveDriveKinematics geKinematics() {
        return Constants.Swerve.swerveKinematics;
    }
}