package org.firstinspires.ftc.teamcode.auton.LibertyScienceCenter;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.hardware.v2.Motors_Drive;


@Autonomous(name = "Red_Far_Delay", group = "Auto")
public class Red_Far_Delay extends LinearOpMode
{
    Motors_Drive Motors_Drive_Reference = new Motors_Drive();
    @Override
    public void runOpMode() throws InterruptedException
    {
        waitForStart();
        Motors_Drive_Reference.init(hardwareMap);
        Motors_Drive.Common Motors_Drive_Common = new Motors_Drive().new Common();
        Motors_Drive.Auton Motors_Drive_Auton = new Motors_Drive().new Auton();
        if (opModeIsActive())
        {
            Thread.sleep(27000);
            Motors_Drive_Common.MeccanumDirection("LEFT", 0.5, 1);
            Thread.sleep(1200);
            Motors_Drive_Common.MeccanumDirection("BACKWARD", 0.5, 1);
            Thread.sleep(1200);
            Motors_Drive_Common.Brake();
            Thread.sleep(300000);
        }
    }
}
