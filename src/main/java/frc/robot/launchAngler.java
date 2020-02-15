package frc.robot;
import com.revrobotics.CANPIDController;
import com.revrobotics.ControlType;

public class launchAngler {
    limelight ranger = new limelight();
    double range = ranger.rangeFinder();
    double startAngle;
    boolean launchLast = false;
    public double positionTarget;
    public void tip(){
        
    }
    public void angleSet(double targetAngle){
        Robot.pitcherPID.setP(1e-2);
        Robot.pitcherPID.setI(1e-6);

        positionTarget = (targetAngle - startAngle)/5.07;

        Robot.pitcherPID.setReference(positionTarget, ControlType.kPosition);
        if(Math.abs(Robot.pitchEnc.getPosition() - positionTarget) < 0.2){
            Robot.launchStatus = true;
        }
    }
    public void innerSet(){
        double innerHoleAngle = 21;
        angleSet(21);
    }
    public void autoLaunchTime(){
        if(Robot.launchStatus == true && launchLast == false){
            Robot.launchCountdown = System.currentTimeMillis() + Robot.launchWait;
            Robot.ballCount = 0;
        }
        launchLast = Robot.launchStatus;
        
    }



}
