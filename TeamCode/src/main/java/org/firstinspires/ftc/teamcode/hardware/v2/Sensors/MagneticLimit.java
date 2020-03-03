package org.firstinspires.ftc.teamcode.hardware.v2.Sensors;

import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class MagneticLimit
{
    private String HwName;
    public MagneticLimit (String HardwareName)
    {
        HwName = HardwareName;
    }
    private DigitalChannel Limit;
    public void init (HardwareMap hwMap)
    {
        Limit = hwMap.get(DigitalChannel.class, HwName);
        Limit.setMode(DigitalChannel.Mode.INPUT);
    }
    public boolean GetState ()
    {
        return Limit.getState();
    }
}
