package frc.robot;

import com.revrobotics.*;
import com.ctre.phoenix.motorcontrol.can.*;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.shuffleboard.*;

public class limelight{
      final Integer forceOff = 1;
      final Integer forceBlink = 2;
      final Integer forceOn = 3;
  
      final Integer VisionProccesing = 0;
      final Integer DrivingCamera = 1;
  
      final Integer PIPMain = 1;
      final Integer sideBySide = 0;
      final Integer PIPSec = 2;

      final Integer mainPipe = 0;
      final Integer rightPipe = 2;
      final Integer leftPipe = 1;
    
      NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
      NetworkTableEntry tx = table.getEntry("tx");
      NetworkTableEntry tv = table.getEntry("tv");
      NetworkTableEntry ta = table.getEntry("ta");
      NetworkTableEntry ts = table.getEntry("ts");
      NetworkTableEntry tlong = table.getEntry("tlong");
      NetworkTableEntry tshort = table.getEntry("tshort");
  
      NetworkTableEntry ledMode = table.getEntry("ledMode");
      NetworkTableEntry camMode = table.getEntry("camMode");
      NetworkTableEntry stream = table.getEntry("stream");
      NetworkTableEntry pipeline = table.getEntry("pipeline");

      ShuffleboardTab Limit = Shuffleboard.getTab("Limit");
      NetworkTableEntry dashxOff = 
        Limit.add("xOffset",0.0)
        .getEntry();
      NetworkTableEntry dashTarget =
        Limit.add("Target",false)
        .getEntry();
      final double visionSpeed = .666;
      final double turnval = .4;
    public void camControl(){
        double xTranslate = tx.getDouble(0.0);
        int isTarget = (int)tv.getDouble(0.0);
        System.out.println(isTarget);
        if(isTarget != 0){
          System.out.println(xTranslate);
            if(xTranslate <=-1){
                System.out.println(-0.4*Math.pow(-xTranslate/54, 0.5));
                Robot.scoot.driveCartesian(Robot.ps4.getRawAxis(0), -Robot.ps4.getRawAxis(1),-0.4*Math.pow(-xTranslate/54, 0.5));
                // Robot.scoot.driveCartesian(Robot.ps4.getRawAxis(0), -Robot.ps4.getRawAxis(1), -0.2);
            }
            else if(xTranslate >=1){
              System.out.println(0.4*Math.pow(xTranslate/54, 0.5));
                Robot.scoot.driveCartesian(Robot.ps4.getRawAxis(0), -Robot.ps4.getRawAxis(1), 0.4*Math.pow(xTranslate/54, 0.5));
                // Robot.scoot.driveCartesian(Robot.ps4.getRawAxis(0), -Robot.ps4.getRawAxis(1), 0.2);
            }
            else{
              Robot.scoot.driveCartesian(Robot.ps4.getRawAxis(0), -Robot.ps4.getRawAxis(1), Robot.ps4.getRawAxis(2)); 
            }
        }
        else{
          Robot.scoot.driveCartesian(Robot.ps4.getRawAxis(0), -Robot.ps4.getRawAxis(1), Robot.ps4.getRawAxis(2));
        }

    }
    int camDistance(){
      return -1;
    }
}
