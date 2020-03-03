package org.firstinspires.ftc.teamcode.teleop;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.v2.Motors_Drive;
import org.firstinspires.ftc.teamcode.hardware.v2.Sensors.Sensors;

@TeleOp(name = "test", group = "default")
public class MeccanumTest extends OpMode
{
    private Motors_Drive MotorsDriveReference = new Motors_Drive();
    private Motors_Drive.TeleOp TeleOpDriveMotors = new Motors_Drive().new TeleOp();
    private Motors_Drive.Common CommonDriveMotors = new Motors_Drive().new Common();
    private org.firstinspires.ftc.teamcode.hardware.v2.Sensors.Sensors Sensors = new Sensors();
    public void init ()
    {
        MotorsDriveReference.init(hardwareMap);
        Sensors.init(hardwareMap);
    }
//    public void addTelem (org.firstinspires.ftc.teamcode.hardware.v2.Sensors.Sensors.Turn Turn)
//    {
//        telemetry.addData("Turn degrees", Turn.Target());
//        telemetry.addData("Current rotatiion", Turn.CurrentZ());
//        telemetry.addData("Turn complete?", Turn.Finished());
//        telemetry.addData("Power to set", Turn.Power());
//        telemetry.addData("Target", Turn.Target);
//    }
    public void loop ()
    {
//        Sensors.Turn Turn0 = new Sensors().new Turn(0);
//        Sensors.Turn Turn90 = new Sensors().new Turn(90);
//        Sensors.Turn Turn180 = new Sensors().new Turn(180);
//        Sensors.Turn TurnN180 = new Sensors().new Turn(-180);
//        Sensors.Turn TurnN90 = new Sensors().new Turn(-90);
//
//        addTelem(Turn0);
//        addTelem(Turn90);
//        addTelem(Turn180);
//        addTelem(TurnN180);
//        addTelem(TurnN90);

        telemetry.addData("Limit switch status", Sensors.IsLimitSwitchActive());
    }
}
