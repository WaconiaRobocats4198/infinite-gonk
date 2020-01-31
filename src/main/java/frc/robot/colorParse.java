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
        String previousColor = colorOutput(scan.getBlue(), scan.getGreen(), scan.getRed());

        if(button == 4){
            do{
                if(previousColor != colorOutput(scan.getBlue(), scan.getGreen(), scan.getRed())){
                    //motor.set()
                    counts ++;
                    previousColor = colorOutput(scan.getBlue(), scan.getGreen(), scan.getRed());
                }
            }while(counts <= 15);
        }
        else if(button == 5){
            int setter =colorConvert(colorSet);
            do{
                //motor.set()
                setter = setter - colorConvert(colorOutput(scan.getBlue(), scan.getGreen(), scan.getRed()));
            }while(setter != 2 || setter != -2);
        }
    }
    String colorOutput(int blue, int green, int red){
        if(red < 0 && red > -10 && green < 0 && green > -10 && blue < 0 && blue > -10){
            return "R";
        }
        else if(red < 0 && red > -10 && green < 0 && green > -10 && blue < 0 && blue > -10){
            return "G";
        }
        else if(red < 0 && red > -10 && green < 0 && green > -10 && blue < 0 && blue > -10){
            return "B";
        }
        else if(red < 0 && red > -10 && green < 0 && green > -10 && blue < 0 && blue > -10){
            return "Y";
        }
        
        return "non";
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
