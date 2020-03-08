package org.firstinspires.ftc.teamcode.hardware.v2.Sensors;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

public class Touch
{
    private String HwName;
    public static TouchSensor touch;
    public Touch (String NewHwName)
    {
        HwName = NewHwName;
    }
    public void init (HardwareMap hwMap)
    {
        touch = hwMap.get(TouchSensor.class, HwName);
    }
    public boolean IsSensorActive ()
    {
        return touch.isPressed();
    }
}
