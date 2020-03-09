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
    Color verifyColor = new Color("verifyColor");
    public Distance distanceSensorLeft = new Distance("distanceSensorLeft");
    public Touch extenderlimit = new Touch("extenderlimit");
    public Touch lifterTouchSensor = new Touch("liftertouch");
    public Distance distanceSensorRight = new Distance("distanceSensorRight");
    public Distance alignDistance = new Distance("alignDistance");
    // public Distance distanceFrontLeft = new Distance("distanceFrontLeft");
    /* TODO add sensors
     * - Add 2 front facing distance sensors for alignment
     * - Add 2 front facing color sensors for alignment
     */
    public void init (HardwareMap hwMap)
    {
//        Gyro.init(hwMap);
//        distanceSensorLeft.init(hwMap);
//        armRight.init(hwMap);
        Limit.init(hwMap);
        // redSideAuto.init(hwMap);
        // blueSideAuto.init(hwMap);
        verifyColor.init(hwMap);
        inside1.init(hwMap);
        inside2.init(hwMap);
        extenderlimit.init(hwMap);
        lifterTouchSensor.init(hwMap);
        distanceSensorLeft.init(hwMap);
        distanceSensorRight.init(hwMap);
        alignDistance.init(hwMap);
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
    public Map<String, Integer> getVerifySensorVals () { return makeColorSensorMap(verifyColor); }
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

    public enum Differential
    {
        Right,
        Left,
    }

    public Differential getDifferential ()
    {
        double right = distanceSensorRight.getDistanceCm();
        double left = distanceSensorLeft.getDistanceCm();
        if (right > left)
        {
            return Differential.Right;
        }
        else
        {
            return Differential.Left;
        }
    }
}
