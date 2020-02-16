
package frc.robot;

public class ballCounter {
    public int ballsIn(){
        if(Robot.ballWasFront == true && Robot.outSensor.get() == false){
            Robot.ballCount--;
        }
        Robot.ballWasFront = Robot.outSensor.get();
        if(Robot.ballWasBack == true && Robot.outSensor.get() == false){
            Robot.ballCount++;
        }
        Robot.ballWasFront = Robot.inSensor.get();
        return Robot.ballCount;
    }
}
