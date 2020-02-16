package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;

import com.ctre.phoenix.motorcontrol.can.*;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;


public class Robot extends TimedRobot {
  
  public static Joystick ps4 = new Joystick(0);

  public static Joystick logi = new Joystick(1);

  public static CANSparkMax frontL = new CANSparkMax(1, MotorType.kBrushless);
  public static CANSparkMax frontR = new CANSparkMax(2, MotorType.kBrushless);
  public static CANSparkMax backL = new CANSparkMax(11, MotorType.kBrushless);
  public static CANSparkMax backR = new CANSparkMax(12, MotorType.kBrushless);
  public static CANSparkMax pitcher = new CANSparkMax(10, MotorType.kBrushless);
  public static CANSparkMax belt = new CANSparkMax(5, MotorType.kBrushless);
  public static CANSparkMax topLaunch = new CANSparkMax(6, MotorType.kBrushless);
  public static CANSparkMax bottomLaunch = new CANSparkMax(7, MotorType.kBrushless);
  // public static CANSparkMax climber = new CANSparkMax(9, MotorType.kBrushless);
  public static CANSparkMax ballIn = new CANSparkMax(4, MotorType.kBrushed);
  public static CANSparkMax climbWinch = new CANSparkMax(3, MotorType.kBrushless);
  // public static CANSparkMax backR = new CANSparkMax(4, MotorType.kBrushless);

  public static CANEncoder flEnc = new CANEncoder(frontL);
  public static CANEncoder frEnc = new CANEncoder(frontR);
  public static CANEncoder blEnc = new CANEncoder(backL);
  public static CANEncoder brEnc = new CANEncoder(backR);
  public static CANEncoder pitchEnc = new CANEncoder(pitcher);
  public static CANEncoder beltEnc = new CANEncoder(belt);
  public static CANEncoder topLaunchEnc = new CANEncoder(topLaunch);
  public static CANEncoder botLaunchEnc = new CANEncoder(bottomLaunch);

  public static CANPIDController pitcherPID = new CANPIDController(pitcher);
  public static CANPIDController uSpeedControl = new CANPIDController(bottomLaunch);
  public static CANPIDController lSpeedControl = new CANPIDController(topLaunch);

  public static DigitalInput inSensor = new DigitalInput(0);
  public static DigitalInput outSensor = new DigitalInput(1);
  
  public static MecanumDrive scoot = new MecanumDrive(frontL, backL, frontR, backR);

  public static AnalogInput zero = new AnalogInput(0);

  limelight vision = new limelight();

  colorParse colorWheel = new colorParse();

  launchAngler sniper = new launchAngler();

  public static double kP = 1e-4; 
  public static double kI = 3e-7;
  public static double kD = 0; 
  public static double kIz = 0; 
  public static double kFF = 0; 
  public static double kMaxOutput = 1; 
  public static double kMinOutput = -1;
  public static double maxRPM = 5700;

  public static double pAdjust = -4;

  public static double launchCountdown;
  public static double launchWait;
  public static boolean launchStatus;

  public static boolean ballWasFront = false;
  public static int ballCount;
  public static boolean ballWasBack = false;

  public static int ballsLeft;

  ballCounter ballsOut = new ballCounter();

  // autoBlocks basicallyAI = new autoBlocks();

  
  double controlMultiply = 1;

  public static void launch(int mode){
    uSpeedControl.setP(kP);
    uSpeedControl.setI(kI);
    uSpeedControl.setD(kD);
    uSpeedControl.setIZone(kIz);
    uSpeedControl.setFF(kFF);
    uSpeedControl.setOutputRange(kMinOutput, kMaxOutput);

    lSpeedControl.setP(kP);
    lSpeedControl.setI(kI);
    lSpeedControl.setD(kD);
    lSpeedControl.setIZone(kIz);
    lSpeedControl.setFF(kFF);
    lSpeedControl.setOutputRange(kMinOutput, kMaxOutput);

    uSpeedControl.setReference(5400, ControlType.kVelocity);
    lSpeedControl.setReference(5400, ControlType.kVelocity);
    if(System.currentTimeMillis() <= launchCountdown &&
      launchStatus == true && ballCount > 0 && mode == 2){
        //beltindexer.set(min, kVelocity)
      
    }
    else if(System.currentTimeMillis() <= launchCountdown &&
      launchStatus == true && ballCount > ballsLeft && mode == 2){

      }
  }

  public static void beltIndexer(){
    if(inSensor.get()){
      //beltDrive.setReference(400, kVelocity)
    }
    else{
      belt.set(0);
    }
  }
 
  @Override
  public void robotInit() {
    ballIn.setInverted(true);
    topLaunch.setInverted(true);
    bottomLaunch.setInverted(true);
  }

  
  @Override
  public void robotPeriodic() {
    
  }

  @Override
  public void autonomousInit() {
   
  }

  
  @Override
  public void autonomousPeriodic() {
    ballsOut.ballsIn();
    // basicallyAI.fullAuto((int)SmartDashboard.getNumber("StartingSpot", 1));
  }


  
  @Override
  public void teleopPeriodic() {
    ballsOut.ballsIn();
    if(logi.getRawButton(3)){
      pitcher.set(logi.getRawAxis(1)*0.5);
    }
    else if(ps4.getRawButton(2)){
      vision.camControl();
      sniper.tip();
    }
    else{
      pitcher.set(0);
    }
    
    if(logi.getRawButton(6) && logi.getRawButton(3)){
      belt.set(0.3);
    }
    else if(inSensor.get() == false){
      beltIndexer();
    }
    else if(logi.getRawButtonPressed(2) || logi.getRawButtonPressed(1)){
      launchCountdown = System.currentTimeMillis() + launchWait;
      ballsLeft = ballCount - 1;
    }
    else if(logi.getRawButton(2)){
      launch(1);
    }
    else if(logi.getRawButton(1)){
      launch(2);
    }
    else if(logi.getRawButton(7) && logi.getRawButton(3)){
      belt.set(-0.3);
    }
    else{
      belt.set(0);
    }


    if(logi.getRawButton(8)){
      ballIn.set(1);
    }
    else if(logi.getRawButton(9)){
      ballIn.set(-1);
    }
    else{
      ballIn.set(0);
    }

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
