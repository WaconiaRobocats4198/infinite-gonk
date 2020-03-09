package frc.robot;

//import edu.wpi.first.wpilibj.Joystick;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANPIDController;

public class climb{
    void winch(){
        int direction;
        if(Robot.logi.getRawButton(3)){direction = -1;}
        else{direction = 1;}
        while(Robot.logi.getRawButton(10)){
            Robot.fricWheel.set(-1 * direction);
        }
        while(Robot.logi.getRawButton(11)){
            Robot.climbWinch.set(-1 * direction);
        }
    }

    

}
