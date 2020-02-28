package frc.robot;

import com.revrobotics.CANPIDController;
import com.revrobotics.ControlType;

public class launchAngler {
    limelight ranger = new limelight();
    double startAngle = 50;
    boolean launchLast = false;
    boolean centered = false;
    double atRange = -1;
    double delayTimer = 0;
    public double positionTarget;
    public void tip(){
        // System.out.println(centered);
        // if(ranger.xTranslate > ranger.offsetCalculator() + 2){
        //     centered = false;
        // }
        // else if(ranger.xTranslate < ranger.offsetCalculator() - 2){
        //     centered = false;
        // }   
        // else{
        //    centered = true;
        // }
        if(ranger.rangeFinder() > atRange + 1 || ranger.rangeFinder() < atRange -1){
            atRange = ranger.rangeFinder();
        }
        System.out.println("atRange");
        if(ranger.rangeFinder() > 70 && ranger.rangeFinder() < 266){
            // System.out.println(howFar + " distance");
            Robot.targetAngle = 0.0006925*Math.pow(atRange, 2)+-0.24*(atRange)+54.34;
            // System.out.println(Robot.targetAngle + " targetAngle");
            angleSet(Robot.targetAngle);
        }
        else{
            if(Robot.logi.getRawAxis(0) < 0.05 && Robot.logi.getRawAxis(0) > -0.05){
                Robot.pitcherPID.setReference(0, ControlType.kVelocity);
              }
            else{
                Robot.pitcher.set(Robot.logi.getRawAxis(0)*0.25);
            }
        }
        
    }
    public void angleSet(double targetAngle){
        // pitchEnc.getPosition()*(-360/71) + 50
        positionTarget = (targetAngle-50)/(-5.07);
        System.out.println(positionTarget + " positionTarget");

        if(Robot.pitchEnc.getPosition() > positionTarget + 1){
            Robot.pitcher.set(-0.25);
          }
          else if(Robot.pitchEnc.getPosition() < positionTarget - 1){
            Robot.pitcher.set(0.25);
          }
          else{
            Robot.pitcherPID.setI(6e-8);
            Robot.pitcherPID.setP(5e-5);
            Robot.pitcherPID.setReference((-.25*Robot.pitchEnc.getPosition()), ControlType.kVelocity);
          }
        if(Math.abs(Robot.pitchEnc.getPosition() - positionTarget) < 0.2){
            Robot.launchStatus = true;
        }
    }
    public void innerSet(){
        double innerHoleAngle = 21;
        angleSet(21);
    }
    public void autoLaunchTime(){
        if(Robot.launchStatus == true && launchLast == false){
            Robot.launchCountdown = System.currentTimeMillis() + Robot.launchWait;
            Robot.ballCount = 0;
        }
        launchLast = Robot.launchStatus;
        
    }



}
