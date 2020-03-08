package org.firstinspires.ftc.teamcode.hardware.v2;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

/**
 * See "PHONE_CONFIG.md" and "ROBOT_CONFIG.md" for help with configuring the robot.
 * This file contains all of the auxiliary motors - aka motors that aren't on the drive train.
 */
public class Motors_Aux
{
    public DcMotor stackerVertical1;
    private DcMotor stackerVertical2;
    private DcMotor intakeRight;
    private DcMotor intakeLeft;
    public void init (HardwareMap hwMap)
    {
        stackerVertical1 = hwMap.get(DcMotor.class, "stackerVertical1");
        stackerVertical2 = hwMap.get(DcMotor.class, "stackerVertical2");
        intakeRight = hwMap.get(DcMotor.class, "intakeRight");
        intakeLeft = hwMap.get(DcMotor.class, "intakeLeft");
    }

    /**
     * TeleOp Class.
     * All utilities from the Motors_Aux interface that are used in TeleOp.
     */
    public class TeleOp
    {
        /**
         * This function changes the power of the intake motor.
         * @param direction - power, inaptly named although it's supposed to represent power.
         */
        public void SetIntakeDirection (double direction)
        {
            intakeRight.setPower(direction);
            intakeLeft.setPower(-direction);
        }

        /**
         * This function changes the power of the stacker motors.
         * @param direction - power, inaptly named although it's supposed to represent power.
         */
        public void SetStackerDirection (double direction)
        {
            stackerVertical1.setPower(direction);
            stackerVertical2.setPower(-direction);
        }
    }

    /**
     * Auton Class.
     * All utilities from the Motors_Aux interface that are used in Auton.
     */
    public class TeleOpEncoders
    {
        public void SetMode (DcMotor.RunMode RunMode)
        {
            stackerVertical1.setMode(RunMode);
        }
        void SetPower (double Power)
        {
            stackerVertical1.setPower(Power);
            stackerVertical2.setPower(-Power);
        }
        public void ResetEncoders ()
        {
            SetMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            SetMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
        public void SetTargetPos (int TargetPos)
        {
            stackerVertical1.setTargetPosition(TargetPos);
        }
        public int GetCurrentPos ()
        {
            return stackerVertical1.getCurrentPosition();
        }
        public int GetTargetPos ()
        {
            return stackerVertical1.getTargetPosition();
        }
        boolean IsMotorAtTargetPos ()
        {
            if (GetTargetPos() == GetCurrentPos())
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        int GetDistanceFromTargetPos ()
        {
            return stackerVertical1.getTargetPosition() - stackerVertical1.getCurrentPosition();
        }
        public void AdjustMotorPower (int cpos)
        {
            if (IsMotorAtTargetPos())
            {
                SetPower(0.0);
            }
            else
            {
                int dif = stackerVertical1.getTargetPosition() - cpos;
                double speed = Range.clip(dif * 0.01, -0.9, 0.9);
                /* TODO
                 * Instead of setting to a constant power, design some formula to, as it gets closer to the target position,
                 * slow down. This would ensure that it's significantly less likely to miss the position or keep twitching.
                 */
                if (Math.abs(speed) > 0.8)
                {
                    SetPower(-speed);
                }
                else
                {
                    SetPower(0.0);
                }
            }
        }
    }
//    private class ForEachMotor
//        {
//            void setMode (DcMotor.RunMode RunMode)
//            {
//                FrontRight.setMode(RunMode);
//                FrontLeft.setMode(RunMode);
//                BackRight.setMode(RunMode);
//                BackLeft.setMode(RunMode);
//            }
//            void setPower (double power)
//            {
//                FrontRight.setPower(power);
//                FrontLeft.setPower(power);
//                BackRight.setPower(power);
//                BackLeft.setPower(power);
//            }
//            void setTargetPosition (int position) {
//                setTargetPosition(position, position, position, position);
//            }
//            void setTargetPosition (int positionFrontLeft, int positionFrontRight, int positionBackLeft, int positionBackRight)
//            {
//                FrontRight.setTargetPosition(positionFrontRight);
//                FrontLeft.setTargetPosition(positionFrontLeft);
//                BackRight.setTargetPosition(positionBackRight);
//                BackLeft.setTargetPosition(positionBackLeft);
//            }
//        }
//        private ForEachMotor ForEachMotor = new ForEachMotor(); // Define ForEachMotor for easy reference
//
//        public void setTargetDirection (String Direction, int target_pos)
//        {
//            int newLeftTarget = getFrontLeftPos() + (int) (-target_pos * COUNTS_PER_INCH);
//            int newRightTarget = getFrontRightPos() + (int) (-target_pos * COUNTS_PER_INCH);
//            int newBackRightTarget = getBackRightPos() + (int) (-target_pos * COUNTS_PER_INCH);
//            int newBackLeftTarget = getBackLeftPos() + (int) (-target_pos * COUNTS_PER_INCH);
//
//            Log.i("POS", String.valueOf(newLeftTarget));
//            Log.i("POS", String.valueOf(newRightTarget));
//            Log.i("POS", String.valueOf(newBackRightTarget));
//            Log.i("POS", String.valueOf(newBackLeftTarget));
//            switch (Direction)
//            {
//                case "FORWARD":
//                    FrontRight.setTargetPosition(newRightTarget);
//                    FrontLeft.setTargetPosition(-newLeftTarget);
//                    BackRight.setTargetPosition(newBackRightTarget);
//                    BackLeft.setTargetPosition(-newBackLeftTarget);
//                    break;
//                case "LEFT":
//                    FrontRight.setTargetPosition(-newRightTarget);
//                    FrontLeft.setTargetPosition(-newLeftTarget);
//                    BackRight.setTargetPosition(newBackRightTarget);
//                    BackLeft.setTargetPosition(newBackLeftTarget);
//                    break;
//                case "BACKWARD":
//                    FrontRight.setTargetPosition(-newRightTarget);
//                    FrontLeft.setTargetPosition(newLeftTarget);
//                    BackRight.setTargetPosition(-newBackRightTarget);
//                    BackLeft.setTargetPosition(newBackLeftTarget);
//                    break;
//                case "RIGHT":
//                    FrontRight.setTargetPosition(newRightTarget);
//                    FrontLeft.setTargetPosition(newLeftTarget);
//                    BackRight.setTargetPosition(-newBackRightTarget);
//                    BackLeft.setTargetPosition(-newBackLeftTarget);
//                    break;
//                case "TURN_R":
//                    FrontRight.setTargetPosition(-newRightTarget);
//                    FrontLeft.setTargetPosition(-newLeftTarget);
//                    BackRight.setTargetPosition(-newBackRightTarget);
//                    BackLeft.setTargetPosition(-newBackLeftTarget);
//                    break;
//                case "TURN_L":
//                    FrontRight.setTargetPosition(newRightTarget);
//                    FrontLeft.setTargetPosition(newLeftTarget);
//                    BackRight.setTargetPosition(newBackRightTarget);
//                    BackLeft.setTargetPosition(newBackLeftTarget);
//                    break;
//            }
//        }
//        /**
//         * Turn on and off the encoders.
//         * It's basically a dishwasher for your encoders.
//         */
//        public void ResetEncoders ()
//        {
//            ForEachMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//            ForEachMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        }
//
//        /**
//         * Encoder drive method (gets inches and converts them to motor targets, setting the position of each motor)
//         * @param leftInches
//         * @param rightInches
//         */
//
//        public void autoDriveEncoders(double leftInches, double rightInches, double divisor) {
//            // Determine new target position, and pass to motor controller
//            leftInches = leftInches/divisor;
//            rightInches = rightInches/divisor;
//            int newLeftTarget = FrontLeft.getCurrentPosition() + (int) (-leftInches * COUNTS_PER_INCH);
//            int newRightTarget = FrontRight.getCurrentPosition() + (int) (-rightInches * COUNTS_PER_INCH);
//
//            if (divisor < 1.0) {
//                ForEachMotor.setTargetPosition(-newLeftTarget, -newRightTarget, newLeftTarget, newRightTarget);
//            } else {
//                ForEachMotor.setTargetPosition(-newLeftTarget, newRightTarget, -newLeftTarget, newRightTarget);
//            }
//            RunToPos(true);
//        }
//
//        /**
//         * Method to check if the front left motor is busy (for auto movements)
//         * @return true or false
//         */
//
//        public boolean frontLeftBusy() {
//            return FrontLeft.isBusy();
//        }
//
//        /**
//         * Method to check if the front right motor is busy (for auto movements)
//         * @return true or false
//         */
//
//        public boolean frontRightBusy() {
//            return FrontRight.isBusy();
//        }
//
//        /**
//         * Method to check if the back left motor is busy (for auto movements)
//         * @return true or false
//         */
//
//        public boolean backLeftBusy() {
//            return BackLeft.isBusy();
//        }
//
//        /**
//         * Method to check if the back right motor is busy (for auto movements)
//         * @return true or false
//         */
//
//        public boolean backRightBusy() {
//            return BackRight.isBusy();
//        }
//
//        /**
//         * Method to get the position of the front right motor
//         * @return the motor's position as an int
//         */
//
//        public int getFrontRightPos() {
//            return FrontRight.getCurrentPosition();
//        }
//
//        /**
//         * Method to get the position of the front right motor
//         * @return the motor's position as an int
//         */
//
//        public int getFrontLeftPos() {
//            return FrontLeft.getCurrentPosition();
//        }
//
//        /**
//         * Method to get the position of the back left motor
//         * @return the motor's position as an int
//         */
//
//        public int getBackLeftPos() {
//            return BackLeft.getCurrentPosition();
//        }
//
//        /**
//         * Method to get the position of the back right motor
//         * @return the motor's position as an int
//         */
//
//        public int getBackRightPos() {
//            return BackRight.getCurrentPosition();
//        }
//
//        /**
//         * ### OVERLOAD METHOD
//         * Set the next target position.
//         * @param position - determines what the next position will be
//         *
//         */
//        public void SetPos (int position)
//        {
//            SetPos(position, false);
//        }
//
//        /**
//         * Set the next target position.
//         * @param position - determines what the next position will be
//         * @param RunToPos - optional flag to run immediately after setting
//         */
//        public void SetPos (int position, boolean RunToPos)
//        {
//            ForEachMotor.setTargetPosition(position);
//            if (RunToPos) RunToPos(true);
//        }
//
//        /**
//         * ### OVERLOAD METHOD
//         * Toggle for running to position.
//         */
//        public void RunToPos ()
//        {
//            RunToPos(true);
//        }
//
//        /**
//         * Toggle for running to position.
//         * @param Mode - TRUE = on, FALSE = off.
//         */
//        public void RunToPos (boolean Mode)
//        {
//            if (Mode) ForEachMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            else ForEachMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        }
//
//        /**
//         * Set motors to go forward! Woww!!!
//         */
//        public void autonMove (double power)
//        {
//            ForEachMotor.setPower(Math.abs(power));
//        }
//
//        /**
//         * Enable strafing for motors.
//         * Two options, RIGHT or LEFT.
//         * yeah that's about it
//         * @param Direction - the direction to strafe in
//         */
//        public void Strafe (String Direction)
//        {
//            switch (Direction)
//            {
//                case "RIGHT":
//                    break;
//                case "LEFT":
//                default:
//                    throw new IllegalArgumentException("has to be either RIGHT or LEFT");
//            }
//        }
//
//        /**
//         * Set all the motors to have no power. Turn them off, really.
//         */
//        public void Stop ()
//        {
//            ForEachMotor.setPower(0.0);
//        }
//    }
}