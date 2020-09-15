package org.firstinspires.ftc.teamcode.skystone.old;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
@Deprecated
public class ServosOLD {
    public CRServo intakeLeft;
    public CRServo intakeRight;
    public CRServo stackerTop;
    public CRServo stackerBottom;
    public CRServo blockGrabber;
    public CRServo autonRed;
    public CRServo autonBlue;
    public void init(HardwareMap hwMap) {
        intakeLeft = hwMap.get(CRServo.class, "intakeLeft");
        intakeRight = hwMap.get(CRServo.class, "intakeRight");
        stackerTop = hwMap.get(CRServo.class, "stackerTop");
        stackerBottom = hwMap.get(CRServo.class, "stackerBottom");
        blockGrabber = hwMap.get(CRServo.class, "blockGrabber");
        autonRed = hwMap.get(CRServo.class, "autonRed");
        autonBlue = hwMap.get(CRServo.class, "autonBlue");
    }
}
