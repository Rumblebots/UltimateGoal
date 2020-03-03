package org.firstinspires.ftc.teamcode.hardware.v2.Sensors;

import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.HashMap;
import java.util.Map;

public class Sensors
{
//    Gyroscope Gyro = new Gyroscope("imu");
    MagneticLimit Limit = new MagneticLimit("magneticLimit");
//    Color redSideAuto  = new Color("redSideAuto");
//    Color blueSideAuto  = new Color("blueSideAuto");
    Color inside1 = new Color("inside1");
    Color inside2 = new Color("inside2");
    public Distance armLeft = new Distance("armLeft");
    public Distance armRight = new Distance("armRight");
    public Touch lifterTouchSensor = new Touch("liftertouch");
    public Touch extenderlimit = new Touch("extenderlimit");
    /* TODO add sensors
     * - Add 2 front facing distance sensors for alignment
     * - Add 2 front facing color sensors for alignment
     */
    public void init (HardwareMap hwMap)
    {
//        Gyro.init(hwMap);
        armLeft.init(hwMap);
        armRight.init(hwMap);
        Limit.init(hwMap);
        // redSideAuto.init(hwMap);
        // blueSideAuto.init(hwMap);
        inside1.init(hwMap);
        inside2.init(hwMap);
        lifterTouchSensor.init(hwMap);
        extenderlimit.init(hwMap);
    }
    public double Power (int Direction, double Power)
    {
        if (Direction * Power > 1)  return 1;
        else if (Direction * Power < -1) return -1;
        else return Direction * Power;
    }
//    public class Turn
//    {
//        public double Target;
//        private double Current = Gyro.GetCurrentFacingZ();
//        public Turn (double NewTarget)
//        {
//            Target = NewTarget;
//        }
//        public boolean Finished ()
//        {
//            Utils.Comparisons Comparator = new Utils().new Comparisons(5);
//            if (Comparator.Compare(Current, Target))
//            {
//                return true;
//            }
//            else
//            {
//                return false;
//            }
//        }
//        public double CurrentZ ()
//        {
//            return Gyro.GetCurrentFacingZ();
//        }
//        public double Target ()
//        {
//            return Target;
//        }
//        public double Power ()
//        {
//            if (Target > Current && Current >= 0)
//            {
//                return 1;
//            }
//            else if (Target < Current && Current <= 0)
//            {
//                return -1;
//            }
//            else if (Target < Current && Target > 0)
//            {
//                return -1;
//            }
//            else if (Target > Current & Target < 0)
//            {
//                return 1;
//            }
//            else
//            {
//                return 0;
//            }
//        }
//    }

    public boolean IsLimitSwitchActive ()
    {
        return !Limit.GetState();
    }
//    public boolean IsGyroCalibrated ()
//    {
//        return Gyro.IsGyroCalibrated();
//    }

    public Map<String, Integer> getInside1SensorVals () { return makeColorSensorMap(inside1); }
    public Map<String, Integer> getInside2SensorVals () { return makeColorSensorMap(inside2); }
    public boolean getInside1State()
    {
        Map t = getInside1SensorVals();
        if ((int)t.get("argb") > 500000)
        {
            return true;
        }
        else return false;
    }
    public boolean getInside2State()
    {
        Map t = getInside2SensorVals();
        if ((int)t.get("argb") > 500000)
        {
            return true;
        }
        else return false;
    }

    /**
     * Method to return a map with all the information needed from a color sensor (map as it will allow me to get the value from the given key)
     * @param colorSensor
     * @return - map with all values needed from a color sensor to measure changes in color
     */
    public Map<String, Integer> makeColorSensorMap(final Color colorSensor) {
        return new HashMap<String, Integer>() {{
            put("red", colorSensor.getRed());
            put("blue", colorSensor.getBlue());
            put("green", colorSensor.getGreen());
            put("argb", colorSensor.getARGB());
            put("alpha", colorSensor.getAlpha());
        }};
    }
}
