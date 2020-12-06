package org.firstinspires.ftc.teamcode.testCode.recording;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import me.wobblyyyy.intra.ftc2.utils.Timed;
import me.wobblyyyy.intra.ftc2.utils.async.event.StringEvents;
import me.wobblyyyy.pathfinder.Pathfinder;
import me.wobblyyyy.pathfinder.localizer.PfMotorPower;
import me.wobblyyyy.pathfinder_ftc.RecorderAndroid;

@TeleOp(name = "Playback", group = "Test")
public class PlaybackOpMode extends OpMode {
    public RecorderAndroid recorder = new RecorderAndroid();

    boolean hasExec = false;

    DcMotor frontRight;
    DcMotor frontLeft;
    DcMotor backRight;
    DcMotor backLeft;

    @Override
    public void init() {
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
    }

    @Override
    public void loop() {
        if (!hasExec) {
            recorder.loadRecording("", "recording.json");
            recorder.executeRecording();
            hasExec = true;
        }

        PfMotorPower mp = Pathfinder.pfMotorPower;

        frontRight.setPower(mp.getFr());
        backRight.setPower(mp.getBr());
        frontLeft.setPower(mp.getFl());
        backLeft.setPower(mp.getBl());
    }
}
