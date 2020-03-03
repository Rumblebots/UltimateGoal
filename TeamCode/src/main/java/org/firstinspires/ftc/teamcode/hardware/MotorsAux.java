package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class MotorsAux {
    //Declare all motors publicly accessible
    public DcMotor intakeLeft;
    public DcMotor intakeRight;
    public DcMotor stakcerVertical;
    //Init method to get their hardware values with the given string (put that string in your configuration)
    public void init(HardwareMap hwMap) {
        intakeLeft = hwMap.get(DcMotor.class, "intakeLeft");
        intakeRight = hwMap.get(DcMotor.class, "intakeRight");
        stakcerVertical = hwMap.get(DcMotor.class, "stackerVertical");
    }
}