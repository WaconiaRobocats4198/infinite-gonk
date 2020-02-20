package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.Solenoid;

import com.ctre.phoenix.motorcontrol.can.*;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.shuffleboard.*;

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
  public static CANSparkMax backL = new CANSparkMax(12, MotorType.kBrushless);
  public static CANSparkMax backR = new CANSparkMax(11, MotorType.kBrushless);
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


  public static CANPIDController frPID = new CANPIDController(frontR);
  public static CANPIDController flPID = new CANPIDController(frontL);
  public static CANPIDController brPID = new CANPIDController(backR);
  public static CANPIDController blPID = new CANPIDController(backL);
  public static CANPIDController pitcherPID = new CANPIDController(pitcher);
  public static CANPIDController uSpeedControl = new CANPIDController(bottomLaunch);
  public static CANPIDController lSpeedControl = new CANPIDController(topLaunch);
  public static CANPIDController beltPID = new CANPIDController(belt);

  public static DigitalInput inSensor = new DigitalInput(0);
  public static DigitalInput outSensor = new DigitalInput(1);
  
  public static MecanumDrive scoot = new MecanumDrive(frontL, backL, frontR, backR);

  public static DigitalInput zero = new DigitalInput(2);

  limelight vision = new limelight();

  // colorParse colorWheel = new colorParse();

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
  public static boolean launchStatus = true;

  public static boolean ballWasFront = false;
  public static int ballCount = 0;
  public static boolean ballWasBack = false;

  public static SendableChooser autoChoice = new SendableChooser<>();
  public static int primeAuto = 1;
  public static int universalAuto = 2;

  public static double actualEnc;
  public static double encOffset;
  
  public static double currentPos;

  public static int ballsLeft;

  ballCounter ballsOut = new ballCounter();

  autoBlocks basicallyAI = new autoBlocks();

  // public ComplexWidget auto =
  //   tab.add("Auto", autoChoice);

  
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


    System.out.println(ballCount>ballsLeft);
    if(ballCount > ballsLeft){
      uSpeedControl.setReference(3000, ControlType.kVelocity);
      lSpeedControl.setReference(3000, ControlType.kVelocity);
      if(System.currentTimeMillis() >= launchCountdown &&
        launchStatus == true && mode == 2
        && logi.getRawButton(1)){
          //beltindexer.set(min, kVelocity)
          belt.set(0.3);
        
      }
      else if(System.currentTimeMillis() >= launchCountdown &&
          launchStatus == true && ballCount > ballsLeft && mode == 1
          && logi.getRawButton(2)){
        belt.set(0.3);
      }
    }
    else{
      topLaunch.set(0);
      bottomLaunch.set(0);
      belt.set(0);
    }
  }

  public static void beltIndexer(){
    if(inSensor.get()){
      // beltDrive.setReference(400, kVelocity)
      if(ballCount < 4){
        currentPos = beltEnc.getPosition() + 0.01;
      }
      else{
        currentPos = beltEnc.getPosition() + 0.0001;
      }
    }
  }
 
  @Override
  public void robotInit() {
    autoChoice.addOption("6 Ball", primeAuto);
    autoChoice.addOption("Universal Auto", universalAuto);
    Shuffleboard.getTab("Preround")
      .add("Auto Choice", autoChoice)
      .withWidget(BuiltInWidgets.kComboBoxChooser);
    ballIn.setInverted(true);
    topLaunch.setInverted(true);
    bottomLaunch.setInverted(true);
    belt.setInverted(true);
    pitcher.setInverted(true);
    currentPos = beltEnc.getPosition();
    basicallyAI.moveOn = 0;
    frEnc.setPosition(0);
    flEnc.setPosition(0);
    blEnc.setPosition(0);
    brEnc.setPosition(0);
    
    frPID.setP(kP);
    frPID.setI(kI);
    frPID.setD(kD);
    frPID.setIZone(kIz);
    frPID.setFF(kFF);
    frPID.setOutputRange(kMinOutput, kMaxOutput);

    brPID.setP(kP);
    brPID.setI(kI);
    brPID.setD(kD);
    brPID.setIZone(kIz);
    brPID.setFF(kFF);
    brPID.setOutputRange(kMinOutput, kMaxOutput);

    flPID.setP(kP);
    flPID.setI(kI);
    flPID.setD(kD);
    flPID.setIZone(kIz);
    flPID.setFF(kFF);
    flPID.setOutputRange(kMinOutput, kMaxOutput);

    blPID.setP(kP);
    blPID.setI(kI);
    blPID.setD(kD);
    blPID.setIZone(kIz);
    blPID.setFF(kFF);
    blPID.setOutputRange(kMinOutput, kMaxOutput);

  }

  
  @Override
  public void robotPeriodic() {
    
  }

  @Override
  public void autonomousInit() {
   
  }

  
  @Override
  public void autonomousPeriodic() {
    // ballsOut.ballsIn();
    // int finalChoice = (int)autoChoice.getSelected();
    // basicallyAI.fullAuto(finalChoice);
  }


  
  @Override
  public void teleopPeriodic() {
    // frontR.set(0.2);
    // System.out.println(logi.getRawAxis(0));
    // System.out.println(pitchEnc.getPosition() + " pitchEnc");
    // System.out.println(inSensor.get());
    if(zero.get()){
      pitchEnc.setPosition(0);
    }
    // System.out.println(pitchEnc.getPosition());
    // System.out.println(vision.rangeFinder() + " range");
    // System.out.println(inSensor.get() + " in " + outSensor.get() + " out");
    ballsOut.ballsIn();
    beltIndexer();
    // System.out.println(outSensor.get() + " out, was " + ballWasFront);
    // System.out.println(ballCount);
    pitcherPID.setP(1e-5);
    pitcherPID.setI(1e-7);
    
    if (logi.getRawButtonPressed(11)) {
      basicallyAI.fullAuto(3);
    }
    
    if(logi.getRawButton(3)){
      if(logi.getRawButton(6)){
        belt.set(0.3);
      }
      else if(logi.getRawButton(7)){
        belt.set(-0.3);
      }
      else{
        belt.set(0);
      }
      if(logi.getRawButton(1)){
        topLaunch.set(0.2);
        bottomLaunch.set(0.2);
      }
      else{
        topLaunch.set(0);
        bottomLaunch.set(0);
      }
      
    }
    else if(logi.getRawButtonPressed(2)){
      launchCountdown = System.currentTimeMillis() + launchWait;
      ballsLeft = ballCount - 1;
    }
    else if(logi.getRawButtonPressed(1)){
      launchCountdown = System.currentTimeMillis() + launchWait;
      ballsLeft = 0;
    }
    else if(logi.getRawButton(2)){
      launch(1);
    }
    else if(logi.getRawButton(1)){
      launch(2);
    }
    else{
      if(beltEnc.getPosition() < currentPos){
        belt.set(0.25);
      }
      else{
        belt.set(0);
      }
      topLaunch.set(0);
      bottomLaunch.set(0);
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

    if(ps4.getRawButtonReleased(3)){
      // System.out.println("SHOULD BE FLIPPING");
      controlMultiply = controlMultiply * (-1);
    }    
    
    // System.out.println(controlMultiply);
      
  
    if(ps4.getRawButton(2)){
      vision.camControl();
      sniper.tip();
    }
    else{
      if(logi.getRawButton(10)){
        if(pitchEnc.getPosition() > 1){
          pitcher.set(-0.25);
        }
        else if(pitchEnc.getPosition() < -1){
          pitcher.set(0.25);
        }
        else{
          pitcher.set((-.25*pitchEnc.getPosition()));
        }
      }
      else if(logi.getRawAxis(0)  < 0.05 && logi.getRawAxis(0) > -0.05){
        pitcherPID.setReference(0, ControlType.kVelocity);
      }
      else{
        pitcher.set(logi.getRawAxis(0)*0.25);
      }
      scoot.driveCartesian(ps4.getRawAxis(0) * controlMultiply, -ps4.getRawAxis(1) * controlMultiply, ps4.getRawAxis(2) * controlMultiply);
    // scoot.driveCartesian(roll, crab, rotate);
    }
    // if(ps4.getRawButton(4)){
    //   colorWheel.ColorControl(4);
    // }
    // if(ps4.getRawButton(5)){
    //   colorWheel.ColorControl(5);
    // }
    
  }

 
  @Override
  public void testPeriodic() {
  
  }
}
