/****
 * Made by Tejas Mehta
 * Made on Thursday, February 20, 2020
 * File Name: Distance
 * Package: org.firstinspires.ftc.teamcode.hardware.v2.Sensors*/
package org.firstinspires.ftc.teamcode.hardware.v2.Sensors;

import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Distance {
    private String sensorName;
    private DistanceSensor distanceSensor;
    public Distance(String sensorName) {
        this.sensorName = sensorName;
    }
    public void init(HardwareMap hwMap) {
        distanceSensor = hwMap.get(DistanceSensor.class, sensorName);
    }
    public double getDistanceCm() {
        return distanceSensor.getDistance(DistanceUnit.CM);
    }
    public double getDistanceIn() {
        return distanceSensor.getDistance(DistanceUnit.INCH);
    }
}
