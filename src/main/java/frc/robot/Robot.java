package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;

import com.ctre.phoenix.motorcontrol.can.*;

import com.revrobotics.CANSparkMaxLowLevel;


public class Robot extends TimedRobot {
  
  public static Joystick ps4 = new Joystick(0);

  public static WPI_TalonSRX frontLeft = new WPI_TalonSRX(4);
  public static WPI_TalonSRX frontRight = new WPI_TalonSRX(5);
  public static WPI_TalonSRX backLeft = new WPI_TalonSRX(6);
  public static WPI_TalonSRX backRight = new WPI_TalonSRX(7);
  
  public static boolean InverseControls;
  public static MecanumDrive scoot = new MecanumDrive(frontLeft, backLeft, frontRight, backRight);

  limelight vision = new limelight();

  colorParse colorWheel = new colorParse();
 
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
    
  }


  
  @Override
  public void teleopPeriodic() {
    int controlMultiply = 1;
    if(ps4.getRawButtonReleased(1)){
      controlMultiply = controlMultiply * -1;
    }    
      
  
    if(ps4.getRawButton(3)){
      System.out.println("Button pressed");
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
