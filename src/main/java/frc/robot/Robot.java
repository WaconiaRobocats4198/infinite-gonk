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

  public static WPI_TalonSRX frontLeft = new WPI_TalonSRX(4);
  public static WPI_TalonSRX frontRight = new WPI_TalonSRX(5);
  public static WPI_TalonSRX backLeft = new WPI_TalonSRX(6);
  public static WPI_TalonSRX backRight = new WPI_TalonSRX(7);

  public static CANSparkMax frontL = new CANSparkMax(1, MotorType.kBrushless);
  public static CANSparkMax frontR = new CANSparkMax(2, MotorType.kBrushless);
  public static CANSparkMax backL = new CANSparkMax(3, MotorType.kBrushless);
  public static CANSparkMax backR = new CANSparkMax(4, MotorType.kBrushless);

  public static CANEncoder flEnc = new CANEncoder(frontL);
  public static CANEncoder frEnc = new CANEncoder(frontR);
  public static CANEncoder blEnc = new CANEncoder(backL);
  public static CANEncoder brEnc = new CANEncoder(backR);
  
  public static MecanumDrive scoot = new MecanumDrive(frontLeft, backLeft, frontRight, backRight);

  limelight vision = new limelight();

  colorParse colorWheel = new colorParse();

  autoBlocks basicallyAI = new autoBlocks();

  public static void launch(){
    
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
    basicallyAI.fullAuto((int)SmartDashboard.getNumber("StartingSpot", 1));
  }

  
  @Override
  public void teleopPeriodic() {
  
    if(ps4.getRawButton(3)){
      vision.camControl();
    }
    else{
      System.out.println(ps4.getRawAxis(2));
      scoot.driveCartesian(ps4.getRawAxis(0), -ps4.getRawAxis(1), ps4.getRawAxis(2));
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
