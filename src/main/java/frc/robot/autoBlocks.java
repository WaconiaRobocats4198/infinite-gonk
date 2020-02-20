package frc.robot;

import com.revrobotics.ControlType;

// import edu.wpi.first.wpilibj.drive.MecanumDrive;
// import com.revrobotics.CANSparkMaxLowLevel.MotorType;
// import com.revrobotics.CANSparkMax;
// import com.revrobotics.CANEncoder;
// import edu.wpi.first.networktables.NetworkTable;
// import edu.wpi.first.networktables.NetworkTableEntry;
// import edu.wpi.first.networktables.NetworkTableInstance;
// import edu.wpi.first.wpilibj.shuffleboard.*;


public class autoBlocks {
    limelight autoLime = new limelight();
    int moveOn;
    launchAngler launcheyBoi = new launchAngler();
    public void straight(double inches){
        double startPos = Robot.flEnc.getPosition();
        double encTicks = distanceToEnc(inches);

        while(Robot.flEnc.getPosition() <  startPos + encTicks){
            Robot.scoot.driveCartesian(0, 1, 0);
        }
        Robot.scoot.driveCartesian(0, 0, 0);
    }
    public void arc(double degrees, double radius, String direction){
        System.out.println("calculating arc");
        double outerDistance = (radius + 9.75) * 2 * Math.PI;
        double innerDistance = (radius - 9.75) * 2 * Math.PI;

        double speedRatio = innerDistance/outerDistance;
        double leftEnc = 0;
        double rightEnc = 0;
        boolean sideSpeed = true;
        int multiple = 1;

        if (direction == "right"){
            leftEnc = Robot.flEnc.getPosition() + distanceToEnc(outerDistance) * (degrees/360);
            rightEnc = Robot.frEnc.getPosition() + distanceToEnc(innerDistance) * (degrees/360);
            sideSpeed = true;
            multiple = -1;
        }
        else if(direction == "left"){
            // System.out.println("lefty");
            rightEnc = (Robot.frEnc.getPosition() + (outerDistance) * (degrees/360));
            leftEnc = (Robot.flEnc.getPosition() + (innerDistance) * (degrees/360));
            System.out.println(rightEnc + " right, " + leftEnc + " left");
            sideSpeed = false;
            multiple = 1;
        }
        System.out.println(speedRatio);
        System.out.println(rightEnc);
        while(Robot.flEnc.getPosition() < -multiple * leftEnc || Robot.frEnc.getPosition() < multiple * rightEnc){
            // System.out.println(Robot.frEnc.getPosition() + " right " + Robot.flEnc.getPosition() + " left");
            if(sideSpeed == false){
                Robot.flPID.setReference(-2000*speedRatio, ControlType.kVelocity);
                Robot.blPID.setReference(-2000*speedRatio, ControlType.kVelocity);
                Robot.frPID.setReference(-2000, ControlType.kVelocity);
                Robot.brPID.setReference(-2000, ControlType.kVelocity);
            }
            else{
                Robot.flPID.setReference(-2000, ControlType.kVelocity);
                Robot.blPID.setReference(-2000, ControlType.kVelocity);
                Robot.frPID.setReference(-2000*speedRatio, ControlType.kVelocity);
                Robot.brPID.setReference(-2000*speedRatio, ControlType.kVelocity);
            }
        }
        Robot.frontL.set(0);
        Robot.frontR.set(0);
        Robot.backL.set(0);
        Robot.backR.set(0);

    }
    public void fullAuto(int position){
        if(position == 1){
            autoCam();
            do{
                launcheyBoi.tip();
                launcheyBoi.autoLaunchTime();
                Robot.launch(3);
            }while(Robot.ballCount <3);
            straight(-70);
            autoCam();
            launcheyBoi.tip();
            straight(-200);
        }
        else if(position == 2){
            straight(-35);
            if(autoLime.xTranslate > 1 || autoLime.xTranslate < -1){
                Robot.scoot.driveCartesian(0, 1, 0);
            }
            else{
                Robot.scoot.driveCartesian(0, 0, 0);
            }
            launcheyBoi.innerSet();
            straight(-200);
        }
        else if(position == 3){
            System.out.println("attempting");
            arc(90, 24, "left");
        }
    }
    public void autoCam(){
        if(autoLime.xTranslate > 0){
            
        }
        else if(autoLime.xTranslate < 0){
            
        }
    }
    public double distanceToEnc(double distance){
        double encCount;
        double rotations = (distance/(8 * Math.PI))*12.75;
       
        return rotations;
    }

}
