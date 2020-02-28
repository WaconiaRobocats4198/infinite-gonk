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
    public double lastRotation;
    public void autoBelt(){
        if(Robot.beltEnc.getPosition() < Robot.currentPos){
            Robot.belt.set(0.5);
          }
          else if(Robot.beltEnc.getPosition() > Robot.currentPos
             && Robot.inSensor.get() == false){
            Robot.belt.set((Robot.currentPos-Robot.beltEnc.getPosition())/10);
          }
          else{
            Robot.belt.set(0);
          }
    }
    public void autoIntake(boolean running){
        if(running){
            if(Robot.pitchEnc.getPosition() > 0.5){
                Robot.pitcher.set(-0.25);
            }
            else if(Robot.pitchEnc.getPosition() < -0.5){
                Robot.pitcher.set(0.25);
            }
            else{
                Robot.pitcher.set((-.5*Robot.pitchEnc.getPosition()));
            }
            Robot.pitcherIn.set(1);
            Robot.intake.set(0.3);
        }
        else{
            Robot.pitcherIn.set(0);
            Robot.intake.set(0);
        }
    }
    limelight autoLime = new limelight();
    int stage = -1;
    launchAngler launcheyBoi = new launchAngler();
    boolean limeTarget = false;
    public void straight(double inches){
        if(Robot.stageStart){
            Robot.startPos = Robot.frEnc.getPosition();
            Robot.stageStart = false;
        }
        double encTicks = distanceToEnc(inches);
        if(Robot.flEnc.getPosition() <  Robot.startPos + encTicks){
            Robot.scoot.driveCartesian(0, 1, 0);
        }
        else{
            Robot.scoot.driveCartesian(0, 0, 0);
            stage++;
            Robot.stageStart = true;
        }
    }
    void rotate(double degree){
        double yawTarget = 0;
        Robot.pigeon.getYawPitchRoll(Robot.gyroRead);
        if(Robot.stageStart){
            yawTarget = Robot.gyroRead[0] + degree;
            Robot.stageStart = false;
        }
  
        if(Math.abs(Robot.gyroRead[0]- yawTarget) >= 0.2){
          Robot.pigeon.getYawPitchRoll(Robot.gyroRead);
          if(Robot.gyroRead[0] > yawTarget + 10){
            Robot.scoot.driveCartesian(0, 0, 0.2);
          }
          else if(Robot.gyroRead[0] < yawTarget - 10){
            Robot.scoot.driveCartesian(0, 0, -0.2);
          }
          else{
            Robot.scoot.driveCartesian(0, 0, (yawTarget - Robot.gyroRead[0]) * 0.01);
          }
          
        }
        else{
          Robot.scoot.driveCartesian(0, 0, 0);
          stage++;
          Robot.stageStart = true;
        }
      }
    // public void arc(double degrees, double radius, String direction){
    //     System.out.println(degrees/360);
    //     double outerDistance = (radius + 9.75) * 2 * Math.PI;
    //     // System.out.println(outerDistance/(8*Math.PI) * 12.75);
    //     double innerDistance = (radius - 9.75) * 2 * Math.PI;
        
    //     double proportion = degrees/360;
    //     double speedRatio = innerDistance/outerDistance;
    //     double leftEnc = 0;
    //     double rightEnc = 0;
    //     boolean sideSpeed = true;
    //     int multiple = 1;
        
    //     System.out.println(distanceToEnc(outerDistance) * proportion + " outer rotations");
    //     System.out.println(Robot.frEnc.getPosition());
    //     if (direction == "right"){
    //         leftEnc = Robot.flEnc.getPosition() + (distanceToEnc(outerDistance) * proportion);
    //         rightEnc = Robot.frEnc.getPosition() + (distanceToEnc(innerDistance) * proportion);
    //         sideSpeed = true;
    //         multiple = -1;
    //     }
    //     else if(direction == "left"){
    //         // System.out.println("lefty");
    //         rightEnc = (Robot.frEnc.getPosition() + (distanceToEnc(outerDistance) * proportion));
    //         leftEnc = Robot.flEnc.getPosition() + (distanceToEnc(innerDistance) * proportion);
    //         // System.out.println(rightEnc + " right, " + leftEnc + " left");
    //         sideSpeed = false;
    //         multiple = 1;
    //     }
    //     // System.out.println(speedRatio);
    //     while(Math.abs(Robot.flEnc.getPosition() - leftEnc) >= 0.5 ||
    //      Math.abs(Robot.frEnc.getPosition() - rightEnc) >= 0.5){
    //         // System.out.println(Robot.frEnc.getPosition() + " right " + Robot.flEnc.getPosition() + " left");
    //         System.out.println(Robot.flEnc.getPosition());
    //         System.out.println(rightEnc + " target");
    //         if(sideSpeed == false){
    //             Robot.flPID.setReference(3000*speedRatio, ControlType.kVelocity);
    //             Robot.blPID.setReference(3000*speedRatio, ControlType.kVelocity);
    //             Robot.frPID.setReference(-3000, ControlType.kVelocity);
    //             Robot.brPID.setReference(-3000, ControlType.kVelocity);
    //         }
    //         else{
    //             Robot.flPID.setReference(3000, ControlType.kVelocity);
    //             Robot.blPID.setReference(3000, ControlType.kVelocity);
    //             Robot.frPID.setReference(-3000*speedRatio, ControlType.kVelocity);
    //             Robot.brPID.setReference(-3000*speedRatio, ControlType.kVelocity);
    //         }
    //     }
    //     Robot.frontL.set(0);
    //     Robot.frontR.set(0);
    //     Robot.backL.set(0);
    //     Robot.backR.set(0);

    // }

    public void fullAuto(int position){
        while(System.currentTimeMillis() < Robot.autoDelay + 250){
            Robot.pitcher.set(-0.2);
        }
        if(position == 1){
            switch (stage){
                case -1:
                    Robot.pitcher.set(-0.3);
                    if(Robot.zero.get()){
                        Robot.pitcher.set(0);
                        Robot.pigeon.getYawPitchRoll(Robot.gyroRead);
                        lastRotation = Robot.gyroRead[0];
                        stage++;
                    }
                break;
                case 0:
                    Robot.scoot.driveCartesian(0, 0, -0.1);
                    if(autoLime.isTarget != 0){
                        Robot.scoot.driveCartesian(0, 0, 0);
                        stage++;
                    }
                break;
                case 1:
                    autoCam();
                    if(limeTarget){
                        stage++;
                        limeTarget = false;
                    }
                break;
                case 2:
                    launcheyBoi.tip(); 
                    launcheyBoi.autoLaunchTime();
                    Robot.launch(2);
                    if(Robot.ballCount >= 0){
                        stage++;
                    }
                break;
                case 3:
                    Robot.pigeon.getYawPitchRoll(Robot.gyroRead);
                    rotate(Robot.gyroRead[0]-lastRotation);
                break;
                case 4:
                    autoBelt();
                    autoIntake(true);
                    straight(-194);
                    Robot.pigeon.getYawPitchRoll(Robot.gyroRead);
                    lastRotation = Robot.gyroRead[0];
                break;
                case 5:
                    autoIntake(false);
                    autoCam();
                    if(limeTarget){
                        stage++;
                        limeTarget = false;
                    }
                break;
                case 6:
                    launcheyBoi.tip();
                    launcheyBoi.autoLaunchTime();
                    Robot.launch(2);
                break;
                case 7:
                    rotate(Robot.gyroRead[0]-lastRotation);
                    launcheyBoi.angleSet(0);
                case 8:
                    straight(-200);
                break;
                default:
            }
        }
        else if(position == 2){
            switch (stage){
                case -1:
                    Robot.pitcher.set(-0.3);
                    if(Robot.zero.get()){
                        Robot.pitcher.set(0);
                        stage++;
                    }
                break;
                case 0:
                    straight(-35);
                break;
                case 1:
                    if(autoLime.xTranslate > 1 || autoLime.xTranslate < -1){
                        Robot.scoot.driveCartesian(0, 1, 0);
                    }
                    else{
                        Robot.scoot.driveCartesian(0, 0, 0);
                        stage++;
                    }
                break;
                case 2:
                    launcheyBoi.tip();
                    launcheyBoi.autoLaunchTime();
                    Robot.launch(2);
                break;
                case 3:
                    straight(-200);
                break;
                default:
            }
        }
        else if(position == 3){
            switch(stage){
                case -1:
                Robot.pitcher.set(-0.3);
                if(Robot.zero.get()){
                    Robot.pitcher.set(0);
                    stage++;
                }
                break;
                case 0:
                    straight(-35);
                break;
                default:
            }
        }
    }
    public void autoCam(){
        if(autoLime.xTranslate > autoLime.offsetCalculator() + 1){
            autoLime.camControl();
        }
        else if(autoLime.xTranslate < autoLime.offsetCalculator() -1){
            autoLime.camControl();
        }
        else{
            limeTarget = true;
        }
    }
    public double distanceToEnc(double distance){
        double rotations = (distance/(8*Math.PI))*12.75;
        return rotations;
    }

}
