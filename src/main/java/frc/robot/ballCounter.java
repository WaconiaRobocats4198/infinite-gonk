/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * Add your docs here.
 */
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
