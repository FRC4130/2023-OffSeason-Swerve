package frc.robot.Loops;

import com.ctre.phoenix.ILoopable;

import frc.robot.Robots.Subsystems;
import frc.robot.Subsystems.groundPosition;

public class Intake implements ILoopable{
    private groundPosition groundPosition;
    private double durriationMs = 1500;
    private double endTimeMs = 0;

    public Intake(){
        groundPosition = Subsystems.groundPosition;
    }

    public Intake(double durriationMs){
        groundPosition = Subsystems.groundPosition;
        this.durriationMs = durriationMs;
    }

    @Override
    public void onStart() {
        System.out.print("[Info] Start Intaking for ");
        System.out.print(durriationMs);
        endTimeMs = System.currentTimeMillis() + durriationMs;
    }

    @Override
    public void onLoop() {
        groundPosition.set(groundPosition.Sucking);
    }

    @Override
    public boolean isDone() {
        if ( System.currentTimeMillis() >= endTimeMs) {
			groundPosition.set(groundPosition.Stored);
			return true;
		}
        return false;
    }

    @Override
    public void onStop() {
        System.out.println("[WARNING] Stopped Intaking");
    }
}
