package org.firstinspires.ftc.teamcode.testCode.recording;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import me.wobblyyyy.intra.ftc2.utils.Timed;
import me.wobblyyyy.intra.ftc2.utils.async.event.StringEvents;
import me.wobblyyyy.pathfinder.localizer.PfMotorPower;
import me.wobblyyyy.pathfinder_ftc.RecorderAndroid;
import org._11253.lib.utils.gen.Toggle;

@TeleOp(name = "Record", group = "Test")
public class RecordOpMode extends OpMode {
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
            recorder.startRecording(
                    10000,
                    0,
                    50
            );
            StringEvents.schedule(
                    "ajdlfkajsdlfk",
                    10000,
                    0,
                    new Timed() {
                        @Override
                        public Runnable close() {
                            return new Runnable() {
                                @Override
                                public void run() {
                                    recorder.saveRecording("", "recording.json");
                                }
                            };
                        }
                    },
                    false
            );
            hasExec = true;
        }

        frontRight.setPower(gamepad1.left_stick_y + gamepad1.right_stick_x - gamepad1.left_stick_x);
        backRight.setPower(gamepad1.left_stick_y + gamepad1.right_stick_x + gamepad1.left_stick_x);
        frontLeft.setPower(-gamepad1.left_stick_y + gamepad1.right_stick_x + gamepad1.left_stick_x);
        backLeft.setPower(-gamepad1.left_stick_y + gamepad1.right_stick_x - gamepad1.left_stick_x);

        recorder.update(new PfMotorPower(
                frontRight.getPower(),
                frontLeft.getPower(),
                backRight.getPower(),
                backLeft.getPower()
        ));
    }
}
