/****
 * Made by Tejas Mehta
 * Made on Thursday, October 08, 2020
 * File Name: Intake
 * Package: org._11253.lib.robot.phys.assm
 */
package org.firstinspires.ftc.teamcode.ultimategoal.shared.subystems;

import org._11253.lib.Global;
import org._11253.lib.robot.phys.components.CRServo;
import org._11253.lib.robot.phys.components.Motor;

/**
 * Simple class for communicating with a two-motor intake
 * Motors should be faced in opposite directions, hence the setting of a * Motors should be faced in opposite directions, hence the setting of a revered & forward durction on each revered & forward durction on each
 *
 * @author Tejas Mehta
 */
public class Intake {
    public static Motor intakeMotor;
    public static CRServo intakeServo;

    public String intakeMotorName = "intake";
    public String intakeServoName = "intakeServo";

    public void init() {
        if (Global.getHwMap() == null) {
            throw new NullPointerException("Global hardware map has to be initialized before initializing the intake.");
        }

        intakeMotor = new Motor(intakeMotorName);
        intakeServo = new CRServo(intakeServoName);
    }

    public void setPower(double motorPower) {
        intakeMotor.setPower(motorPower);
        intakeServo.setPower(-motorPower);
    }
}
