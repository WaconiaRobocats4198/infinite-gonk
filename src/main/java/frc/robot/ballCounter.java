
package frc.robot;

public class ballCounter {
    public int ballsIn(){
        if(Robot.ballWasFront == true && Robot.outSensor.get() == false 
            && Robot.topLaunchEnc.getVelocity() > 10){
            Robot.ballCount--;
        }
        Robot.ballWasFront = Robot.outSensor.get();
        if(Robot.ballWasBack == true && Robot.inSensor.get() == false 
        && Robot.logi.getRawButton(8)){
            Robot.ballCount++;
        }
        Robot.ballWasBack = Robot.inSensor.get();
        return Robot.ballCount;
    }
}
