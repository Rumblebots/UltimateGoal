package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.v2.Sensors.Sensors;

@TeleOp(name="Distance Sensor Test", group="default")
public class DistanceSensorTest extends LinearOpMode
{
    public void runOpMode ()
    {
        /*
         * Pre - init functionality
         */
        waitForStart();
        /*
         * Post - init functionality
         * Yay sensors!!
         */
        telemetry.addLine("waiting for start");
        Sensors sensors = new Sensors();
        sensors.init(hardwareMap);
        while (opModeIsActive())
        {
            telemetry.addData("Right Distance Sensor", sensors.distanceSensorRight.getDistanceCm());
            telemetry.addData("Left Distance Sensor", sensors.distanceSensorLeft.getDistanceCm());
            telemetry.update();
        }
    }
}
