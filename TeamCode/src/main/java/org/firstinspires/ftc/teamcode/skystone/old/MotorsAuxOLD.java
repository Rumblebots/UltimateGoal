package org.firstinspires.ftc.teamcode.skystone.old;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
@Deprecated
public class MotorsAuxOLD {
    public DcMotor intakeLeft;
    public DcMotor intakeRight;
    public DcMotor stackerTop;
    public DcMotor stackerBotttom;
    public void init(HardwareMap hwMap) {
        intakeLeft = hwMap.get(DcMotor.class, "intakeLeft");
        intakeRight = hwMap.get(DcMotor.class, "intakeRight");
        stackerTop = hwMap.get(DcMotor.class, "stackerTop");
        stackerBotttom = hwMap.get(DcMotor.class, "stackerBottom");
    }
}
