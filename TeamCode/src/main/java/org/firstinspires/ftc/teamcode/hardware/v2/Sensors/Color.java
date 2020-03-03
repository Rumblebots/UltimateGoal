package org.firstinspires.ftc.teamcode.hardware.v2.Sensors;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Color {
    private String sensorName;
    private ColorSensor colorSensor;
    public Color(String sensorMapName) {
        sensorName = sensorMapName;
    }
    public void init(HardwareMap hwMap) {
        colorSensor = hwMap.get(ColorSensor.class, sensorName);
    }
    public int getARGB() {
        return colorSensor.argb();
    }
    public int getGreen() {
        return colorSensor.green();
    }
    public int getRed() {
        return colorSensor.red();
    }
    public int getBlue() {
        return colorSensor.blue();
    }
    public int getAlpha() {
        return colorSensor.alpha();
    }
}
