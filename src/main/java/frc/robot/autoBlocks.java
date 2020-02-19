package frc.robot;

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
    boolean moveOn;
    launchAngler launcheyBoi = new launchAngler();
    public void straight(double inches){
        double startPos = Robot.flEnc.getPosition();
        double encTicks = distanceToEnc(inches);

        if(Robot.flEnc.getPosition() <  startPos + encTicks){
            Robot.scoot.driveCartesian(0, 1, 0);
        }
        else{
            Robot.scoot.driveCartesian(0, 0, 0);
        }
    }
    public void arc(double degrees, double radius, String direction){
        double outerDistance = (radius + 9.75) * 2 * Math.PI;
        double innerDistance = (radius - 9.75) * 2 * Math.PI;

        double speedRatio = innerDistance/outerDistance;
        double leftEnc = 0;
        double rightEnc = 0;
        boolean sideSpeed = true;

        if (direction == "right"){
            leftEnc = Robot.flEnc.getPosition() + distaneToEnc(outerDistance) * (degrees/360);
            rightEnc = Robot.frEnc.getPosition() + distaneToEnc(innerDistance) * (degrees/360);
            sideSpeed = true;
        }
        else if(direction == "left"){
            rightEnc = Robot.frEnc.getPosition() + (outerDistance) * (degrees/360);
            leftEnc = Robot.flEnc.getPosition() + (innerDistance) * (degrees/360);
            sideSpeed = false;
        }

        if(Robot.flEnc.getPosition() < leftEnc){
            if(sideSpeed == true){
                Robot.frontL.set(1);
                Robot.backL.set(1);
            }
            else{
                Robot.frontL.set(speedRatio);
                Robot.backL.set(speedRatio);
            }
        }
        if(Robot.frEnc.getPosition() < rightEnc){
            if(sideSpeed == false){
                Robot.frontR.set(1);
                Robot.backR.set(1);
            }
            else{
                Robot.frontR.set(speedRatio);
                Robot.backR.set(speedRatio);
            }
        }

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
        if(position == 2){
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
    }
    public void autoCam(){
        if(autoLime.xTranslate > 0){
            
        }
        else if(autoLime.xTranslate < 0){
            
        }
    }
    public double distaneToEnc(double distance){
        double encCount;
        double rotations =distance/(8 * Math.PI);
       
        return rotations;
    }

}
