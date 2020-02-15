package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;

import com.ctre.phoenix.motorcontrol.can.*;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANEncoder;


public class Robot extends TimedRobot {
  
  public static Joystick ps4 = new Joystick(0);

  public static Joystick logi = new Joystick(1);

  public static CANSparkMax frontL = new CANSparkMax(1, MotorType.kBrushless);
  public static CANSparkMax frontR = new CANSparkMax(2, MotorType.kBrushless);
  public static CANSparkMax backL = new CANSparkMax(3, MotorType.kBrushless);
  public static CANSparkMax backR = new CANSparkMax(4, MotorType.kBrushless);
  public static CANSparkMax pitcher = new CANSparkMax(5, MotorType.kBrushless);
  public static CANSparkMax belt = new CANSparkMax(6, MotorType.kBrushless);
  public static CANSparkMax topLaunch = new CANSparkMax(7, MotorType.kBrushless);
  public static CANSparkMax bottomLaunch = new CANSparkMax(8, MotorType.kBrushless);
  public static CANSparkMax climber = new CANSparkMax(9, MotorType.kBrushless);
  public static CANSparkMax ballIn = new CANSparkMax(10, MotorType.kBrushless);
  // public static CANSparkMax backR = new CANSparkMax(4, MotorType.kBrushless);
  // public static CANSparkMax backR = new CANSparkMax(4, MotorType.kBrushless);

  public static CANEncoder flEnc = new CANEncoder(frontL);
  public static CANEncoder frEnc = new CANEncoder(frontR);
  public static CANEncoder blEnc = new CANEncoder(backL);
  public static CANEncoder brEnc = new CANEncoder(backR);
  public static CANEncoder beltEnc = new CANEncoder(belt);
  public static CANEncoder topLaunchEnc = new CANEncoder(topLaunch);
  public static CANEncoder botLaunchEnc = new CANEncoder(bottomLaunch);

  public static DigitalInput inSensor = new DigitalInput(0);
  public static DigitalInput outSensor = new DigitalInput(1);
  public static DigitalInput ballIntakeTrigger = new DigitalInput(2);
  
  public static MecanumDrive scoot = new MecanumDrive(frontL, backL, frontR, backR);

  limelight vision = new limelight();

  colorParse colorWheel = new colorParse();

  // autoBlocks basicallyAI = new autoBlocks();

  
  double controlMultiply = 1;
  public static void launch(int mode){
    if(mode == 1){

    }
    else if(mode == 2){

    }
  }
  public static void beltIndexer(){
    if(inSensor.get()){
      //beltDrive.setReference(400, kVelocity)
    }
    else{
      // beltMotor.set(0);
    }
  }
 
  @Override
  public void robotInit() {
  }

  
  @Override
  public void robotPeriodic() {
    
  }

  @Override
  public void autonomousInit() {
   
  }

  
  @Override
  public void autonomousPeriodic() {
    // basicallyAI.fullAuto((int)SmartDashboard.getNumber("StartingSpot", 1));
  }


  
  @Override
  public void teleopPeriodic() {
    if(ps4.getRawButtonReleased(1) ){
      // System.out.println("SHOULD BE FLIPPING");
      controlMultiply = controlMultiply * (-1);
    }    
    
    // System.out.println(controlMultiply);
      
  
    if(ps4.getRawButton(3)){
      vision.camControl();
    }
    else{
      scoot.driveCartesian(ps4.getRawAxis(0) * controlMultiply, -ps4.getRawAxis(1) * controlMultiply, ps4.getRawAxis(2) * controlMultiply);
    // scoot.driveCartesian(roll, crab, rotate);
    }
    if(ps4.getRawButton(4)){
      colorWheel.ColorControl(4);
    }
    if(ps4.getRawButton(5)){
      colorWheel.ColorControl(5);
    }
  }

 
  @Override
  public void testPeriodic() {
  
  }
}
