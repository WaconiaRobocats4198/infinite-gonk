package frc.robot;

import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;

import edu.wpi.first.wpilibj.DriverStation;

public class colorParse {
    I2C.Port i2Color = I2C.Port.kOnboard;

    ColorSensorV3 scan = new ColorSensorV3(i2Color);

    String colorSet = DriverStation.getInstance().getGameSpecificMessage();

    void ColorControl(int button){
        int counts = 0;
        int previousColor = colorConvert(colorOutput(scan.getBlue(), scan.getGreen(), scan.getRed()));

        if(button == 4){
            do{
                if(colorConvert(colorOutput(scan.getBlue(), scan.getGreen(), scan.getRed())) != previousColor 
                    && colorConvert(colorOutput(scan.getBlue(), scan.getGreen(), scan.getRed())) != 0){
                    //motor.set()
                    counts ++;
                    previousColor = colorConvert(colorOutput(scan.getBlue(), scan.getGreen(), scan.getRed()));
                }
            }while(counts <= 28);
        }
        else if(button == 5){
            int setter =colorConvert(colorSet);
            do{
                //motor.set()
                setter = setter - colorConvert(colorOutput(scan.getBlue(), scan.getGreen(), scan.getRed()));
            }while(setter != 2 || setter != -2);
        }
    }
    String colorOutput(double cBlue, double cGreen, double cRed){
        // double redBlue = cRed/cBlue;
        // double redGreen = cRed/cGreen;
        // double blueGreen = cBlue/cGreen; 
        double blueRed = cBlue/cRed;
        // double greenRed = cGreen/cRed;
        // double greenBlue = cGreen/cBlue;
        
        if(cGreen > cRed && blueRed > 1.8){
            // System.out.println("BLUE");
            return "B";
        }
        else if(cGreen > cBlue && cGreen > cRed){
            if(cRed > cBlue){
                return "Y";
            }
            else{
                return "G";
            }
        }
        else if(cGreen > cBlue && cGreen < cRed ) {
            return "R";
        }
        else{
            return "oops";
        }
    }
    int colorConvert (String input){
        if(input == "B"){
            return 1;
        }
        else if(input == "G"){
            return 2;
        }
        else if(input == "R"){
            return 3;
        }
        else if(input == "Y"){
            return 4;
        }
        else{
            return 0;
        }
    }
}
