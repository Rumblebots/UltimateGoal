/****
 * Made by Tejas Mehta
 * Made on Thursday, October 08, 2020
 * File Name: Intake
 * Package: org._11253.lib.robot.phys.assm
 */
package org.firstinspires.ftc.teamcode.ultimategoal.shared.subystems;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import org._11253.lib.Global;
import org._11253.lib.robot.phys.components.Motor;

/***
 * @author Tejas Mehta
 * Simple class for communicating with a two-motor intake
 * Motors should be faced in opposite directions, hence the setting of a * Motors should be faced in opposite directions, hence the setting of a revered & forward durction on each revered & forward durction on each
 */

public class Intake {
    public static Motor leftIntakeMotor;
    public static Motor rightIntakeMotor;

    public String leftMotorName = "leftIntakeMotor";
    public String rightMotorName = "rightIntakeMotor";

    public boolean isRound = false;

    public void init() {
        if (Global.getHwMap() == null) {
            throw new NullPointerException("Global hardware map has to be initialized before initializing the intake.");
        }

        leftIntakeMotor = new Motor(leftMotorName);
        rightIntakeMotor = new Motor(rightMotorName);

        leftIntakeMotor.isRound = isRound;
        rightIntakeMotor.isRound = isRound;

        rightIntakeMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rightIntakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void setPower(double motorPower) {
        leftIntakeMotor.setPower(motorPower);
        rightIntakeMotor.setPower(motorPower);
    }
}
