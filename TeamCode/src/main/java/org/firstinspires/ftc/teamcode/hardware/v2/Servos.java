package org.firstinspires.ftc.teamcode.hardware.v2;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * See "PHONE_CONFIG.md" and "ROBOT_CONFIG.md" for help with configuring the robot.
 * Servo Interface
 * This file contains all the interfacing between
 * the user's code and the hardware's servos.
 */
public class Servos {
    public CRServo extenderServo;
    private CRServo intakeServoRight;
    private CRServo intakeServoLeft;
    public Servo gripperServo;
    public Servo foundationServo1;
    public Servo intakeDropper;
    public CRServo blockWheel;
    public Servo armRControl;
    public Servo capstoneRelease;
    public RevBlinkinLedDriver leds;
    public Servo blockGrabberR;
    public Servo alignmentArm;

    /**
     * Init all of the servos that get used
     * @param hwMap - the hardware map that gets used
     */
    public void init (HardwareMap hwMap)
    {
        extenderServo = hwMap.get(CRServo.class, "extenderServo");
        intakeServoRight = hwMap.get(CRServo.class, "intakeServoRight");
        intakeServoLeft = hwMap.get(CRServo.class, "intakeServoLeft");
        gripperServo = hwMap.get(Servo.class, "gripperServo");
        foundationServo1 = hwMap.get(Servo.class, "foundationServo1");
        intakeDropper = hwMap.get(Servo.class, "intakeDropper");
        blockWheel = hwMap.get(CRServo.class, "blockWheel");
        armRControl = hwMap.get(Servo.class, "blockArmR");
        capstoneRelease = hwMap.get(Servo.class, "capstoneRelease");
        blockGrabberR = hwMap.get(Servo.class, "blockGrabberR");
        leds = hwMap.get(RevBlinkinLedDriver.class, "leds");
        alignmentArm = hwMap.get(Servo.class, "blockGrabberL");
    }

    public void teleOpInit(HardwareMap hwMap) {
        init(hwMap);
        gripperServo.setPosition(0);
        intakeDropper.setPosition(0.3);
    }

    /**
     * TeleOp class, contains all the functions / utils used in teleop
     */
    public class TeleOp
    {
        /**
         * This function sets the direction of the referenced CR servo.
         * @param reference - The servo that's being referenced.
         * @param direction - The direction that said servo should now be going in.
         */
        public void SetCRServoDirection (CRServo reference, DcMotorSimple.Direction direction)
        {
            reference.setDirection(direction);
        }

        /**
         * ### OVERLOAD METHOD
         * Stops the CR servo if no power is set.
         * This function sets the power of the referenced CR servo.
         * @param reference - The servo that's being referenced.
         *
         */
        public void SetCRServoPower(CRServo reference) {
            SetCRServoPower(reference, 0.0);
        }

        /**
         * This function sets the power of the referenced CR servo.
         * @param reference - The servo that's being referenced.
         * @param power - The power that said servo should now be moving with.
         */
        public void SetCRServoPower (CRServo reference, double power)
        {
            reference.setPower(power);
        }

        /**
         * This function sets the new position of a servo.
         * @param reference - The servo that's being referenced.
         * @param position - The position the servo should start moving to.
         */
        public void SetServoPosition (Servo reference, double position)
        {
            reference.setPosition(position);
        }

        /**
         * This function controls both of the intake servos.
         * @param power - the new power the servos should be moving with. Should only be -1, 0, or 1
         * @see this.SetCRServoDirection();
         * @see this.SetCRServoPower();
         */
        public void SetIntakeCRServosDirection (double power)
        {
            CRServo reference1 = intakeServoRight,
                    reference2 = intakeServoLeft;
            if (power == 0.0) // Don't move in either direction
            {
                SetCRServoPower(reference1, 0.0);
                SetCRServoPower(reference2, 0.0);
            }
            else if (power > 0.0) // Go forward
            {
                SetCRServoDirection(reference1, DcMotorSimple.Direction.FORWARD);
                SetCRServoDirection(reference2, DcMotorSimple.Direction.FORWARD);
                SetCRServoPower(reference1, 1.0);
                SetCRServoPower(reference2, -1.0);
            }
            else if (power < 0.0) // Go backward
            {
                SetCRServoDirection(reference1, DcMotorSimple.Direction.REVERSE);
                SetCRServoDirection(reference2, DcMotorSimple.Direction.REVERSE);
                SetCRServoPower(reference1, 1.0);
                SetCRServoPower(reference2, -1.0);
            }
        }
    }

    /**
     * Auton class, contains all the functions / utils used in auton
     */
    public class Auton
    {

    }
}
