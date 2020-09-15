package org.firstinspires.ftc.teamcode.skystone.hardware;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Servos {
    //Publicly declare all servos and CRservos
    public Servo intakeLeft;
    public Servo intakeRight;
    public Servo capstoneRelease;
    public Servo blockGrabberPush;
    public Servo blockGrabberAlign;
    public Servo grabber;
    public Servo foundationRight;
    public Servo foundationLeft;
    public CRServo autonRed;
    public CRServo autonBlue;
    public CRServo intake45InLeft;
    public CRServo intake45InRight;
    public CRServo stackerHorizontal;
    //Initialization method, link all items to their hardware counterpart with the string(should be the same in the phone)
    public void init(HardwareMap hwMap) {
        intakeLeft = hwMap.get(Servo.class, "intakeLeft");
        intakeRight = hwMap.get(Servo.class, "intakeRight");
        capstoneRelease = hwMap.get(Servo.class, "capstoneRelease");
        blockGrabberPush = hwMap.get(Servo.class, "blockGrabberPush");
        blockGrabberAlign = hwMap.get(Servo.class, "blockGrabberAlign");
        grabber = hwMap.get(Servo.class, "grabber");
        foundationLeft = hwMap.get(Servo.class, "foundationLeft");
        foundationRight = hwMap.get(Servo.class, "foundationRight");
        autonRed = hwMap.get(CRServo.class, "autonRed");
        autonBlue = hwMap.get(CRServo.class, "autonBlue");
        intake45InLeft = hwMap.get(CRServo.class, "intake45InLeft");
        intake45InRight = hwMap.get(CRServo.class, "intake45InRight");
        stackerHorizontal = hwMap.get(CRServo.class, "stackerHorizontal");
    }
}
