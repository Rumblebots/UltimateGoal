package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.RobotCore.Utils;
import org.firstinspires.ftc.teamcode.hardware.v2.Motors_Aux;
import org.firstinspires.ftc.teamcode.hardware.v2.Motors_Drive;
import org.firstinspires.ftc.teamcode.hardware.v2.Motors_Drive.motorPowerObject;
import org.firstinspires.ftc.teamcode.hardware.v2.Sensors.Sensors;
import org.firstinspires.ftc.teamcode.hardware.v2.Servos;

public class MeccanumDriveV2 extends LinearOpMode {
    private Motors_Drive motorsDrive = new Motors_Drive();
    private Motors_Aux motorsAux = new Motors_Aux();
    private Servos servos = new Servos();
    private Sensors sensors = new Sensors();
    private Motors_Aux.TeleOp motorsAuxTeleOp = motorsAux.new TeleOp();
    private Motors_Aux.TeleOpEncoders motorsAuxTeleOpEncoders = motorsAux.new TeleOpEncoders();
    private TouchSensor lifterTouch;
    private TouchSensor extenderTouch;
    private Utils utils = new Utils();
    private Utils.Toggle gripperToggle = utils.new Toggle(false);
    private Utils.Toggle foundationToggle = utils.new Toggle(false);
    private Utils.Toggle capstoneToggle = utils.new Toggle(false);
    private Utils.Shifter lifterShifter = utils.new Shifter(1, 6);
    private Modes extenderMode;
    private Modes lifterMode;
    private int encoderAdjustment;
    private int adjustedEncoderPosition;

    private motorPowerObject yalign(double right, double left) {
        motorPowerObject motorPowerObject = motorsDrive.new motorPowerObject();
        if (right >= 10 && left >= 10) {
            motorPowerObject.frontRight = 0.4;
            motorPowerObject.backRight = 0.4;
            motorPowerObject.frontLeft = 0.4;
            motorPowerObject.backLeft = 0.4;
        } else if (right - 1 < left && left < right + 1) {
            motorPowerObject.frontRight = 0;
            motorPowerObject.backRight = 0;
            motorPowerObject.frontLeft = 0;
            motorPowerObject.backLeft = 0;
        } else if (right > left) {
            motorPowerObject.frontRight = 0;
            motorPowerObject.backRight = 0;
            motorPowerObject.frontLeft = 0.4;
            motorPowerObject.backLeft = 0.4;
        } else if (left > right) {
            motorPowerObject.frontRight = 0.4;
            motorPowerObject.backRight = 0.4;
            motorPowerObject.frontLeft = 0;
            motorPowerObject.backLeft = 0;
        }
        return motorPowerObject;
    }

    private motorPowerObject xalign(double distance) {
        motorPowerObject motorPowerObject = motorsDrive.new motorPowerObject();
        if (8 - 0.5 <= distance && distance <= 8 + 0.5) {
            motorPowerObject.frontRight = 0;
            motorPowerObject.backRight = 0;
            motorPowerObject.frontLeft = 0;
            motorPowerObject.backLeft = 0;
        } else if (distance < 8 - 2) {
            // Strafe right
            motorPowerObject.frontLeft = 0.45;
            motorPowerObject.backRight = 0.45;
            motorPowerObject.frontRight = -0.45;
            motorPowerObject.backLeft = -0.45;
        } else if (distance > 8 + 2) {
            // Strafe left
            motorPowerObject.frontLeft = -0.45;
            motorPowerObject.backRight = -0.45;
            motorPowerObject.frontRight = 0.45;
            motorPowerObject.backLeft = 0.45;
        } else if (distance < 8 - 1) {
            // Strafe right
            motorPowerObject.frontLeft = 0.4;
            motorPowerObject.backRight = 0.4;
            motorPowerObject.frontRight = -0.4;
            motorPowerObject.backLeft = -0.4;
        } else if (distance > 8 + 1) {
            // Strafe left
            motorPowerObject.frontLeft = -0.4;
            motorPowerObject.backRight = -0.4;
            motorPowerObject.frontRight = 0.4;
            motorPowerObject.backLeft = 0.4;
        } else if (distance < 8 - 0.5) {
            // Strafe right
            motorPowerObject.frontLeft = 0.35;
            motorPowerObject.backRight = 0.35;
            motorPowerObject.frontRight = -0.35;
            motorPowerObject.backLeft = -0.35;
        } else if (distance > 8 + 0.5) {
            // Strafe left
            motorPowerObject.frontLeft = -0.35;
            motorPowerObject.backRight = -0.35;
            motorPowerObject.frontRight = 0.35;
            motorPowerObject.backLeft = 0.35;
        }
        return motorPowerObject;
    }

    private motorPowerObject meccanum(Gamepad gamepad) {
        motorPowerObject motorPowerObject = motorsDrive.new motorPowerObject();
        double motorSpeedDiv = 2;
        if (gamepad.right_trigger > 0) motorSpeedDiv = (1.01 - gamepad.right_trigger) * 2;
        motorPowerObject.frontRight = -gamepad.left_stick_y + gamepad.left_stick_x + gamepad.right_stick_x;
        motorPowerObject.frontLeft = gamepad.left_stick_y + gamepad.left_stick_x + gamepad.right_stick_x;
        motorPowerObject.backRight = -gamepad.left_stick_y - gamepad.left_stick_x + gamepad.right_stick_x;
        motorPowerObject.backLeft = gamepad.left_stick_y - gamepad.left_stick_x + gamepad.right_stick_x;
        motorPowerObject.frontRight /= motorSpeedDiv;
        motorPowerObject.frontLeft /= motorSpeedDiv;
        motorPowerObject.backRight /= motorSpeedDiv;
        motorPowerObject.backLeft /= motorSpeedDiv;
        return motorPowerObject;
    }

    private void writeMotorValues(motorPowerObject motor) {
        Motors_Drive.FrontRight.setPower(motor.frontRight);
        Motors_Drive.FrontLeft.setPower(motor.frontLeft);
        Motors_Drive.BackRight.setPower(motor.backRight);
        Motors_Drive.BackLeft.setPower(motor.backLeft);
    }

    private void setLifterPower(double power) {
        motorsAuxTeleOp.SetStackerDirection(power);
    }

    private boolean setLifterPowerBasedOnEncoders(double current) {
        double target = /* noelia is cool */ motorsAuxTeleOpEncoders.GetTargetPos();
        double distanceFromTarget = target - current;
        if (Math.abs(distanceFromTarget) < 50) {
            setLifterPower(0);
            return true;
        } else {
            setLifterPower(Range.clip(distanceFromTarget * 0.001, -1, 1));
            return false;
        }
    }

    public void runOpMode() {
        motorsDrive.init(hardwareMap);
        motorsAux.init(hardwareMap);
        servos.init(hardwareMap);
        sensors.init(hardwareMap);
        lifterTouch = hardwareMap.touchSensor.get("liftertouch");
        extenderTouch = hardwareMap.touchSensor.get("extenderlimit");
        waitForStart();
        while (opModeIsActive()) {
            motorPowerObject motorPower;
            // vv yalign vv
            if (gamepad1.a)
                motorPower = yalign(sensors.distanceSensorRight.getDistanceCm(), sensors.distanceSensorLeft.getDistanceCm());
                // vv xalign vv
            else if (gamepad1.b) motorPower = xalign(sensors.alignDistance.getDistanceCm());
                // vv meccanum vv
            else motorPower = meccanum(gamepad1);
            writeMotorValues(motorPower);

            if (lifterTouch.isPressed())
                encoderAdjustment = 0 - motorsAuxTeleOpEncoders.GetCurrentPos();
            adjustedEncoderPosition = motorsAuxTeleOpEncoders.GetCurrentPos() - encoderAdjustment;
            double lifterPower = Math.abs(gamepad2.right_trigger) - Math.abs(gamepad2.left_trigger);
            if (gamepad2.right_trigger > 0) lifterMode = Modes.Manual;
            else if (gamepad2.left_trigger > 0) lifterMode = Modes.Manual;
            else if (gamepad2.y) lifterMode = Modes.Automatic;
            else lifterMode = Modes.Inactive;
            switch (lifterMode) {
                case Manual:
                    setLifterPower(Range.clip(lifterPower, -1, 1));
                    break;
                case Automatic:
                    if (setLifterPowerBasedOnEncoders(motorsAux.stackerVertical1.getCurrentPosition())) {
                        lifterMode = Modes.Manual;
                    }
                    break;
                case Inactive:
                    setLifterPower(0);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + lifterMode);
            }
        }
    }

    private enum Modes {
        Automatic,
        Inactive,
        Manual,
    }

    /*
     * TODO
     *  i cant think straight enough to work on this rn
     *  but when i get home i'm gonna fix all this
     *  i still gotta add most of gamepad 2 controls before itll be 100%0%00% okay
     */
}
