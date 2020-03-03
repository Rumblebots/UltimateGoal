package org.firstinspires.ftc.teamcode.hardware.v2;

import android.util.Log;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.RobotCore.Utils;

/**
 * See "PHONE_CONFIG.md" and "ROBOT_CONFIG.md" for help with configuring the robot.
 * Motors_Drive class
 * This class contains all of the motors used on the robot's drive train.
 */
public class Motors_Drive
{
    private Utils.range range = new Utils().new range();
    public static DcMotor FrontRight;
    public static DcMotor FrontLeft;
    public static DcMotor BackRight;
    public static DcMotor BackLeft;

    /**
     * Init all of the drive motors.
     * @param hwMap - the hardware map that gets used
     */
    public void init (HardwareMap hwMap)
    {
        FrontRight = hwMap.get(DcMotor.class, "FrontRight");
        FrontLeft = hwMap.get(DcMotor.class, "FrontLeft");
        BackRight = hwMap.get(DcMotor.class, "BackRight");
        BackLeft = hwMap.get(DcMotor.class, "BackLeft");
    }

    /**
     * This class provides common utilities that
     * have to do with drive motors.
     * Used in Sensors.java
     */
    public class Common
    {
        public double[] GetCurrentMotorValues ()
        {
            double[] f = new double[4];
            f[0] = FrontRight.getPower();
            f[1] = FrontLeft.getPower();
            f[2] = BackRight.getPower();
            f[3] = BackLeft.getPower();
            return f;
        }
        public void SetAllMotorValues (double Input)
        {
            FrontRight.setPower(Input);
            FrontLeft.setPower(Input);
            BackRight.setPower(Input);
            BackLeft.setPower(Input);
        }
        public void Brake ()
        {
            FrontRight.setPower(-FrontRight.getPower());
            FrontLeft.setPower(-FrontLeft.getPower());
            BackRight.setPower(-BackRight.getPower());
            BackLeft.setPower(-BackLeft.getPower());
            SetAllMotorValues(0);
        }

        public void MeccanumDirection (String Direction, double Power)
        {
            MeccanumDirection(Direction, Power, 1);
        }

        public void MeccanumDirection (String Direction, double Power, int Time)
        {
            switch (Direction)
            {
                case "FORWARD":
                    FrontRight.setPower(-Power);
                    FrontLeft.setPower(Power);
                    BackRight.setPower(-Power);
                    BackLeft.setPower(Power);
                    break;
                case "LEFT":
                    FrontRight.setPower(-Power);
                    FrontLeft.setPower(-Power);
                    BackRight.setPower(Power);
                    BackLeft.setPower(Power);
                    break;
                case "BACKWARD":
                    MeccanumDirection("FORWARD", -Power, Time);
                    break;
                case "RIGHT":
                    FrontRight.setPower(Power);
                    FrontLeft.setPower(Power);
                    BackRight.setPower(-Power);
                    BackLeft.setPower(-Power);
                    break;
            }
        }
    }

    /**
     * Class TeleOp.
     * All utilities from the Motors_Drive interface that are used in TeleOp.
     * Note that a lot of the functions in TeleOp use arrays.
     * These arrays allow for better code organization and condensation
     */
    public class TeleOp
    {
        /**
         * This function gets the current power values that the motors have.
         * It's useful for motor smoothing.
         * @return d - an array containing current motor values.
         */
        double[] ReturnCurrentMotorValues ()
        {
            double[] d = new double[4];
            {
                d[0] = FrontRight.getPower();
                d[1] = FrontLeft.getPower();
                d[2] = BackRight.getPower();
                d[3] = BackLeft.getPower();
            }
            return d;
        }

        /**
         * This function uses the current gamepad values, and uses some
         * magical calculations to return the new / next motor values.
         * @param gamepad1LeftY - Left Y value
         * @param gamepad1LeftX - Left X value
         * @param gamepad1RightX - Right X value
         * @param div - Motor Speed Div
         * @return d - an array containing the next motor values.
         */
        double[] ReturnNextMotorValues (float gamepad1LeftY, float gamepad1LeftX, float gamepad1RightX, float div)
        {
            double[] d = new double[4];
            {
                d[0] = -gamepad1LeftY + gamepad1LeftX + gamepad1RightX;
                d[1] = gamepad1LeftY + gamepad1LeftX + gamepad1RightX;
                d[2] = -gamepad1LeftY - gamepad1LeftX + gamepad1RightX;
                d[3] = gamepad1LeftY - gamepad1LeftX + gamepad1RightX;
            }
            {
                d[0] = (range.clip((float) d[0]) / div);
                d[1] = (range.clip((float) d[1]) / div);
                d[2] = (range.clip((float) d[2]) / div);
                d[3] = (range.clip((float) d[3]) / div);
            }
            return d;
        }

        /**
         * This function just averages the CURRENT and NEXT motor values.
         * @param Current - Current motor values
         * @param Next - Next motor values
         * @return d - Averaged motor values
         */
        double[] ReturnSmoothedMotorValues (double[] Current, double[] Next, double Curve)
        {
            if (Curve == 0) return Next; // If curve is zero, don't do any smoothing.
            double[] d = new double[4];
            {
                d[0] = Math.round((100000d)*((Current[0] * Curve) + Next[0]) / (1 + Curve))/(100000d);
                d[1] = Math.round((100000d)*((Current[1] * Curve) + Next[1]) / (1 + Curve))/(100000d);
                d[2] = Math.round((100000d)*((Current[2] * Curve) + Next[2]) / (1 + Curve))/(100000d);
                d[3] = Math.round((100000d)*((Current[3] * Curve) + Next[3]) / (1 + Curve))/(100000d);
            }
            return d;
        }

        /**
         * ### OVERLOAD METHOD
         * This is the overload method for UpdateMotors, in case the user forgets to add a curve value.
         * @param gp1ly - Abbreviation, means gamepad1LeftY
         * @param gp1lx - Abbreviation, means gamepad1LeftX
         * @param gp1rx - Abbreviation, means gamepad1RightX
         * @param div   - Motor speed div / speed limiter / whatever you wanna call it
         * @return d - the newly updated motor values.
         */
        public double[] UpdateMotors(float gp1ly, float gp1lx, float gp1rx, float div)
        {
            return UpdateMotors(gp1ly, gp1lx, gp1rx, div, 1);
        }

        /**
         * This function is what updates the motor values on the robot.
         * First, initialize a new double array (d) and set its value equal to...
         * The curved average between the current motor value and the next motor value.
         * Curving can be disabled by simply setting it to 0.
         * @param gp1ly - Abbreviation, means gamepad1LeftY
         * @param gp1lx - Abbreviation, means gamepad1LeftX
         * @param gp1rx - Abbreviation, means gamepad1RightX
         * @param div   - Motor speed div / speed limiter / whatever you wanna call it
         * @param Curve - how aggressively the smoothing should be curved in favor of smoothness
         * @return d - the newly updated motor values.
         */
        public double[] UpdateMotors(float gp1ly, float gp1lx, float gp1rx, float div, double Curve)
        {
            double[] d = ReturnSmoothedMotorValues(ReturnCurrentMotorValues(), ReturnNextMotorValues(gp1ly, gp1lx, gp1rx, div), Curve);
            {
                FrontRight.setPower(d[0]);
                FrontLeft.setPower(d[1]);
                BackRight.setPower(d[2]);
                BackLeft.setPower(d[3]);
            }
            return d;
        }
    }

    /**
     * Class Auton.
     * All utilities from the Motors_Drive interface that are used in Auton.
     */
    public class Auton
    {
        static final double COUNTS_PER_MOTOR_REV = 537.6;    // eg: TETRIX Motor Encoder
        static final double DRIVE_GEAR_REDUCTION = 1.0;     // This is < 1.0 if geared UP
        static final double WHEEL_DIAMETER_INCHES = 6;     // For figuring circumference
        static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
                (WHEEL_DIAMETER_INCHES * 3.1415);
        /**
         * Utility class, it's designed to simplify doing one action to every motor.
         * Everything in here is named as it would be if you were to use Qualcomm's default
         * functions, so this shouldn't need too much explanation.
         */
        private class ForEachMotor
        {
            void setMode (DcMotor.RunMode RunMode)
            {
                FrontRight.setMode(RunMode);
                FrontLeft.setMode(RunMode);
                BackRight.setMode(RunMode);
                BackLeft.setMode(RunMode);
            }
            void setPower (double power)
            {
                FrontRight.setPower(power);
                FrontLeft.setPower(power);
                BackRight.setPower(power);
                BackLeft.setPower(power);
            }
            void setTargetPosition (int position) {
                setTargetPosition(position, position, position, position);
            }
            void setTargetPosition (int positionFrontLeft, int positionFrontRight, int positionBackLeft, int positionBackRight)
            {
                FrontRight.setTargetPosition(positionFrontRight);
                FrontLeft.setTargetPosition(positionFrontLeft);
                BackRight.setTargetPosition(positionBackRight);
                BackLeft.setTargetPosition(positionBackLeft);
            }
        }
        private ForEachMotor ForEachMotor = new ForEachMotor(); // Define ForEachMotor for easy reference

        public void setTargetDirection (String Direction, int target_pos)
        {
            ResetEncoders();
            int newLeftTarget = getFrontLeftPos() + (int) (-target_pos * COUNTS_PER_INCH);
            int newRightTarget = getFrontRightPos() + (int) (-target_pos * COUNTS_PER_INCH);
            int newBackRightTarget = getBackRightPos() + (int) (-target_pos * COUNTS_PER_INCH);
            int newBackLeftTarget = getBackLeftPos() + (int) (-target_pos * COUNTS_PER_INCH);

            Log.i("POS", String.valueOf(newLeftTarget));
            Log.i("POS", String.valueOf(newRightTarget));
            Log.i("POS", String.valueOf(newBackRightTarget));
            Log.i("POS", String.valueOf(newBackLeftTarget));
            switch (Direction)
            {
                case "FORWARD":
                    FrontRight.setTargetPosition(newRightTarget);
                    FrontLeft.setTargetPosition(-newLeftTarget);
                    BackRight.setTargetPosition(newBackRightTarget);
                    BackLeft.setTargetPosition(-newBackLeftTarget);
                    break;
                case "LEFT":
                    FrontRight.setTargetPosition(-newRightTarget);
                    FrontLeft.setTargetPosition(-newLeftTarget);
                    BackRight.setTargetPosition(newBackRightTarget);
                    BackLeft.setTargetPosition(newBackLeftTarget);
                    break;
                case "BACKWARD":
                    FrontRight.setTargetPosition(-newRightTarget);
                    FrontLeft.setTargetPosition(newLeftTarget);
                    BackRight.setTargetPosition(-newBackRightTarget);
                    BackLeft.setTargetPosition(newBackLeftTarget);
                    break;
                case "RIGHT":
                    FrontRight.setTargetPosition(newRightTarget);
                    FrontLeft.setTargetPosition(newLeftTarget);
                    BackRight.setTargetPosition(-newBackRightTarget);
                    BackLeft.setTargetPosition(-newBackLeftTarget);
                    break;
                case "TURN_R":
                    FrontRight.setTargetPosition(-newRightTarget);
                    FrontLeft.setTargetPosition(-newLeftTarget);
                    BackRight.setTargetPosition(-newBackRightTarget);
                    BackLeft.setTargetPosition(-newBackLeftTarget);
                    break;
                case "TURN_L":
                    FrontRight.setTargetPosition(newRightTarget);
                    FrontLeft.setTargetPosition(newLeftTarget);
                    BackRight.setTargetPosition(newBackRightTarget);
                    BackLeft.setTargetPosition(newBackLeftTarget);
                    break;
            }
        }
        /**
         * Turn on and off the encoders.
         * It's basically a dishwasher for your encoders.
         */
        public void ResetEncoders ()
        {
            ForEachMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            ForEachMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

        /**
         * Encoder drive method (gets inches and converts them to motor targets, setting the position of each motor)
         * @param leftInches
         * @param rightInches
         */

        public void autoDriveEncoders(double leftInches, double rightInches, double divisor) {
            // Determine new target position, and pass to motor controller
            leftInches = leftInches/divisor;
            rightInches = rightInches/divisor;
            int newLeftTarget = FrontLeft.getCurrentPosition() + (int) (-leftInches * COUNTS_PER_INCH);
            int newRightTarget = FrontRight.getCurrentPosition() + (int) (-rightInches * COUNTS_PER_INCH);

            if (divisor < 1.0) {
                ForEachMotor.setTargetPosition(-newLeftTarget, -newRightTarget, newLeftTarget, newRightTarget);
            } else {
                ForEachMotor.setTargetPosition(-newLeftTarget, newRightTarget, -newLeftTarget, newRightTarget);
            }
            RunToPos(true);
        }

        /**
         * Method to check if the front left motor is busy (for auto movements)
         * @return true or false
         */

        public boolean frontLeftBusy() {
            return FrontLeft.isBusy();
        }

        /**
         * Method to check if the front right motor is busy (for auto movements)
         * @return true or false
         */

        public boolean frontRightBusy() {
            return FrontRight.isBusy();
        }

        /**
         * Method to check if the back left motor is busy (for auto movements)
         * @return true or false
         */

        public boolean backLeftBusy() {
            return BackLeft.isBusy();
        }

        /**
         * Method to check if the back right motor is busy (for auto movements)
         * @return true or false
         */

        public boolean backRightBusy() {
            return BackRight.isBusy();
        }

        /**
         * Method to get the position of the front right motor
         * @return the motor's position as an int
         */

        public int getFrontRightPos() {
            return FrontRight.getCurrentPosition();
        }

        /**
         * Method to get the position of the front right motor
         * @return the motor's position as an int
         */

        public int getFrontLeftPos() {
            return FrontLeft.getCurrentPosition();
        }

        /**
         * Method to get the position of the back left motor
         * @return the motor's position as an int
         */

        public int getBackLeftPos() {
            return BackLeft.getCurrentPosition();
        }

        /**
         * Method to get the position of the back right motor
         * @return the motor's position as an int
         */

        public int getBackRightPos() {
            return BackRight.getCurrentPosition();
        }

        /**
         * ### OVERLOAD METHOD
         * Set the next target position.
         * @param position - determines what the next position will be
         *
         */
        public void SetPos (int position)
        {
            SetPos(position, false);
        }

        /**
         * Set the next target position.
         * @param position - determines what the next position will be
         * @param RunToPos - optional flag to run immediately after setting
         */
        public void SetPos (int position, boolean RunToPos)
        {
            ForEachMotor.setTargetPosition(position);
            if (RunToPos) RunToPos(true);
        }

        /**
         * ### OVERLOAD METHOD
         * Toggle for running to position.
         */
        public void RunToPos ()
        {
            RunToPos(true);
        }

        /**
         * Toggle for running to position.
         * @param Mode - TRUE = on, FALSE = off.
         */
        public void RunToPos (boolean Mode)
        {
            if (Mode) ForEachMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            else ForEachMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }

        /**
         * Set motors to go forward! Woww!!!
         */
        public void autonMove (double power)
        {
            ForEachMotor.setPower(Math.abs(power));
        }

        /**
         * Enable strafing for motors.
         * Two options, RIGHT or LEFT.
         * yeah that's about it
         * @param Direction - the direction to strafe in
         */
        public void Strafe (String Direction)
        {
            switch (Direction)
            {
                case "RIGHT":
                    break;
                case "LEFT":
                default:
                    throw new IllegalArgumentException("has to be either RIGHT or LEFT");
            }
        }

        /**
         * Set all the motors to have no power. Turn them off, really.
         */
        public void Stop ()
        {
            ForEachMotor.setPower(0.0);
        }
    }
}
