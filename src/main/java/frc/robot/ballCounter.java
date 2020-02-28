
package frc.robot;

public class ballCounter {
    double releaseTime;
    public int ballsIn(){
        if(Robot.ballWasFront == true && Robot.outSensor.get() == false 
            && Robot.topLaunchEnc.getVelocity() > 10){
            Robot.ballCount--;
        }
        Robot.ballWasFront = Robot.outSensor.get();
        if(Robot.ballWasBack == true && Robot.inSensor.get() == false 
        && Robot.logi.getRawButton(8) && System.currentTimeMillis() > Robot.countDelay
        && System.currentTimeMillis() < releaseTime){
            Robot.ballCount++;
        }
        Robot.ballWasBack = Robot.inSensor.get();
        if(Robot.ballCount > Robot.lastCount){
            Robot.countDelay = System.currentTimeMillis() + 250;
        }
        if(Robot.logi.getRawButton(8)){
            releaseTime = System.currentTimeMillis() + 750;
        }
        Robot.lastCount = Robot.ballCount;
        return Robot.ballCount;
    }
}
