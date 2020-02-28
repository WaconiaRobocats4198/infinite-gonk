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
        if(Robot.ps4.getRawButton(4)){direction = -1;}
        else{direction = 1;}
        while(Robot.ps4.getRawButton(5)){
            Robot.climbWinch.set(0.6 * direction);
        }
        while(Robot.ps4.getRawButton(6)){
            Robot.climbWinch.set(-0.4);
        }
    }

}
