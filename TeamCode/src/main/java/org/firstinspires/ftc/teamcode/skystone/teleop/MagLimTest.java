package org.firstinspires.ftc.teamcode.skystone.teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.TouchSensor;

@Disabled
@TeleOp(name="MagLim Sensor Test", group="default")
public class MagLimTest extends LinearOpMode
{
    public TouchSensor liftertouch;
    public TouchSensor extenderlimit;
    public void runOpMode ()
    {
        waitForStart();
        liftertouch = hardwareMap.touchSensor.get("liftertouch");
        extenderlimit = hardwareMap.touchSensor.get("extenderlimit");
        while (opModeIsActive())
        {
            telemetry.addData("Lifter", liftertouch.isPressed());
            telemetry.addData("Extender", extenderlimit.isPressed());
            telemetry.update();
        }
    }
}
