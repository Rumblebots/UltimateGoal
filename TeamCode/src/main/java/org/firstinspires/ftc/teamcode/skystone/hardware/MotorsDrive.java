package org.firstinspires.ftc.teamcode.skystone.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class MotorsDrive {
    //Declare all motors publicly accessible
    public DcMotor motorFrontRight;
    public DcMotor motorFrontLeft;
    public DcMotor motorBackRight;
    public DcMotor motorBackLeft;
    //Empty constructor function (might need later)
    public MotorsDrive() {

    }
    //Init method, as with the other motors file, get the motor with the string (use that same string in the configuration on phone)
    public void init(HardwareMap hwMap) {
        motorFrontRight = hwMap.get(DcMotor.class, "FrontRight");
        motorFrontLeft = hwMap.get(DcMotor.class, "FrontLeft");
        motorBackRight = hwMap.get(DcMotor.class, "BackRight");
        motorBackLeft = hwMap.get(DcMotor.class, "BackLeft");
    }
    //Method to reset the encoders and reset the motor model using encoders
    public void resetEncoders() {
        motorFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    //Method to set a target position for encoder enabled motors (for meccannum drive)
    public void setTargetPos(int frontLeft, int frontRight, int backLeft, int backRight) {
        motorFrontLeft.setTargetPosition(frontLeft);
        motorFrontRight.setTargetPosition(frontRight);
        motorBackRight.setTargetPosition(backRight);
        motorBackLeft.setTargetPosition(backLeft);
    }
    //Set all motors to encoder run motors
    public void setRunToPos() {
        motorFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    //Set all motors to normal power driven motors
    public void stopRunToPos() {
        motorFrontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorFrontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
    public void stopAll() {
        // Stop all motion;
        motorBackRight.setPower(0);
        motorBackLeft.setPower(0);
        motorFrontLeft.setPower(0);
        motorFrontRight.setPower(0);
    }
}
