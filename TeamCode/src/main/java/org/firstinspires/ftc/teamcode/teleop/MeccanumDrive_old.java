package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.hardware.MotorsAux;
import org.firstinspires.ftc.teamcode.hardware.MotorsDrive;
import org.firstinspires.ftc.teamcode.hardware.Servos;

@Deprecated
@Disabled
@TeleOp(name = "Meccanum Main Drive (old)", group = "TeleOp")
public class MeccanumDrive_old extends OpMode {
    //Declare all needed motor and servo classes and class variables
    private MotorsDrive motors = new MotorsDrive();
    private Servos servos = new Servos();
    private MotorsAux motorsAux = new MotorsAux();
    private int motorSpeedDiv = 2;
    boolean inverted = false;

    //Initialize all motors and servos
    @Override
    public void init() {
        motors.init(hardwareMap);
        servos.init(hardwareMap);
        motorsAux.init(hardwareMap);
    }

    //Main loop method (where everything happens)
    @Override
    public void loop() {
        //Set the intake servos to 0 (drops intake)
        servos.intakeLeft.setPosition(0.0);
        servos.intakeRight.setPosition(0.0);

        //Change the speed reduction/gain upon a button press
        if (gamepad1.right_trigger > 0.1) {
            motorSpeedDiv = 1;
        } else if (gamepad1.right_bumper) {
            motorSpeedDiv = 4;
        } else {
            motorSpeedDiv = 2;
        }

        //Inverter
        if (gamepad1.left_trigger > 0.1) {
            inverted = true;
        } else {
            inverted = false;
        }

        //Horizontal intake controls
        if (gamepad2.right_bumper) {
            servos.stackerHorizontal.setPower(1.0);
        } else if (gamepad2.left_bumper) {
            servos.stackerHorizontal.setPower(-1.0);
        } else {
            servos.stackerHorizontal.setPower(0.0);
        }

        //New Stacker up/down controls
        if (gamepad2.right_trigger > 0) {
            motorsAux.stakcerVertical.setPower(gamepad2.right_trigger);
        } else if (gamepad2.left_trigger > 0) {
            motorsAux.stakcerVertical.setPower(-gamepad2.left_trigger);
        } else {
            motorsAux.stakcerVertical.setPower(0.0);
        }

        //New Intake (Both CR and Motor)
        if (gamepad2.a) {
            //Set both (CRServo and motor) inwards on a to pull in a block and flick it up to be grabbed
            motorsAux.intakeRight.setDirection(DcMotorSimple.Direction.FORWARD);
            motorsAux.intakeRight.setPower(1.0);
            motorsAux.intakeLeft.setDirection(DcMotorSimple.Direction.REVERSE);
            motorsAux.intakeLeft.setPower(1.0);
        } else if (gamepad2.b) {
            //Set both (CRServo and motors) to go outwards in case we need to remove a block
            motorsAux.intakeRight.setDirection(DcMotorSimple.Direction.REVERSE);
            motorsAux.intakeRight.setPower(1.0);
            motorsAux.intakeLeft.setDirection(DcMotorSimple.Direction.FORWARD);
            motorsAux.intakeLeft.setPower(1.0);
        } else {
            //Nothing pressed, make sure it's all stopped.
            motorsAux.intakeRight.setPower(0.0);
            motorsAux.intakeLeft.setPower(0.0);
        }

        //needed joystick values for the drive
        float gamepad1LeftY = -gamepad1.left_stick_y;
        float gamepad1LeftX = gamepad1.left_stick_x;
        float gamepad1RightX = gamepad1.right_stick_x;

        // holonomic formulas

        float FrontLeft = gamepad1LeftY + gamepad1LeftX + gamepad1RightX;
        float FrontRight = -gamepad1LeftY + gamepad1LeftX + gamepad1RightX;
        float BackRight = -gamepad1LeftY - gamepad1LeftX + gamepad1RightX;
        float BackLeft = gamepad1LeftY - gamepad1LeftX + gamepad1RightX;

        // clip the right/left values so that the values never exceed +/- 1
        FrontRight = Range.clip(FrontRight, -1, 1);
        FrontLeft = Range.clip(FrontLeft, -1, 1);
        BackLeft = Range.clip(BackLeft, -1, 1);
        BackRight = Range.clip(BackRight, -1, 1);

        // write the values to the motors, and base it off of whether the motors are set to run inverted or not
        if (inverted) {
            motors.motorFrontRight.setPower(-FrontRight/motorSpeedDiv);
            motors.motorFrontLeft.setPower(-FrontLeft/motorSpeedDiv);
            motors.motorBackLeft.setPower(-BackLeft/motorSpeedDiv);
            motors.motorBackRight.setPower(-BackRight/motorSpeedDiv);
        } else {
            motors.motorFrontRight.setPower(FrontRight/motorSpeedDiv);
            motors.motorFrontLeft.setPower(FrontLeft/motorSpeedDiv);
            motors.motorBackLeft.setPower(BackLeft/motorSpeedDiv);
            motors.motorBackRight.setPower(BackRight/motorSpeedDiv);
        }
        telemetry.addData("Speed %: ", Math.round(100.0/motorSpeedDiv) + "%");
        telemetry.update();
    }
}
