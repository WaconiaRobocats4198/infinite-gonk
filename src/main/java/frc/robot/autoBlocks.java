package frc.robot;

import edu.wpi.first.wpilibj.drive.MecanumDrive;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.CANEncoder;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.shuffleboard.*;


public class autoBlocks {
    limelight autoLime = new limelight();

    launchAngler launcheyBoi = new launchAngler();
    public void forward(double inches){
        //double startPos = Robot.flEnc.getPosition();
        double rotations =inches/(8 * Math.PI);

        double encTicks = rotations * 42;

        double time = System.currentTimeMillis();
        // if(Robot.flEnc.getPosition() <  startPos + encTicks){
        //     if(System.currentTimeMillis() < time + 500){
        //         Robot.scoot.driveCartesian(0, ((System.currentTimeMillis()-time) / 500) * 0.7 + 0.3, 0);
        //     }
        //     else if(Robot.flEnc.getPosition() <  (startPos + encTicks) - 200){
        //         Robot.scoot.driveCartesian(0, 1-(((startPos + encTicks)-Robot.flEnc.getPosition())/200) * 0.7, 0);
        //     }
        //     else{
        //         Robot.scoot.driveCartesian(0, 1, 0);
        //     }
        // }
        // else{
        //     Robot.scoot.driveCartesian(0, 0, 0);
        // }
    }
    public void arc(double degrees, double radius){

    }
    public void fullAuto(int position){
        if(position == 1){
            autoCam();
            launcheyBoi.tip();
        }
    }
    public void autoCam(){
        if(autoLime.xTranslate > 0){
            
        }
        else if(autoLime.xTranslate < 0){
            
        }
    }

}
