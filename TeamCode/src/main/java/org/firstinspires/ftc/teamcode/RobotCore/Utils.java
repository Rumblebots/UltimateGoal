package org.firstinspires.ftc.teamcode.RobotCore;

public class Utils {
    public class Comparisons
    {
        private double Tolerance;
        public Comparisons (double tolerance)
        {
            Tolerance = tolerance;
        }
        public boolean Compare (double NumberToCompare, double ComparisonReference)
        {
            if (NumberToCompare <= ComparisonReference + Tolerance && NumberToCompare >= ComparisonReference - Tolerance)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
    }
    public enum Shift
    {
        Up,
        Down,
    }
    public class Shifter
    {
        private boolean CanCycle;
        private boolean CanShift;
        private int Min;
        private int Max;
        public int CurrentGear;
        public Shifter(int min, int max)
        {
            this(min, max, true);
        }
        public Shifter (int min, int max, boolean cycle)
        {
            this.Min = min;
            this.Max = max;
            this.CanCycle = cycle;
            this.CurrentGear = min;
        }
        private void ShiftUp ()
        {
            if (CanShift && CurrentGear + 1 <= Max)
            {
                CurrentGear++;
            }
            else if (CanShift && CanCycle)
            {
                CurrentGear = Min;
            }
            CanShift = false;
        }
        private void ShiftDown ()
        {
            if (CanShift && CurrentGear - 1 >= Min)
            {
                CurrentGear--;
            }
            else if (CanShift && CanCycle)
            {
                CurrentGear = Max;
            }
            CanShift = false;
        }
        public void Released ()
        {
            CanShift = true;
        }
        public void Shift (Shift ShiftMode)
        {
            switch (ShiftMode)
            {
                case Up:
                    ShiftUp();
                    break;
                case Down:
                    ShiftDown();
                    break;
                default: throw new IllegalArgumentException("yeah lmao that doesn't work out.");
            }
        }
    }
    public class Toggle
    {
        private boolean Toggle;
        private boolean ToggleCanHappen = true;
        public Toggle (boolean Default)
        {
            this.Toggle = Default;
        }
        public boolean Toggle ()
        {
            return Toggle_Internal();
        }
        public boolean Toggle_Internal ()
        {
            return this.Toggle;
        }
        public void OverrideSetToggle (String String)
        {
            switch (String)
            {
                case "ON":
                    Toggle = true;
                    break;
                case "OFF":
                    Toggle = false;
                    break;
                default:
                    throw new IllegalArgumentException("ON / OFF are the only valid inputs");
            }
        }
        public void UpdateToggle(String String)
        {
            switch (String)
            {
                case "ON":
                    HasBeenPressed();
                    break;
                case "OFF":
                    HasBeenReleased();
                    break;
                default:
                    throw new IllegalArgumentException("ON / OFF are the only valid inputs");
            }
        }
        private void HasBeenPressed ()
        {
            if (this.ToggleCanHappen)
            {
                this.Toggle = !this.Toggle;
            }
            this.ToggleCanHappen = false;
        }
        private void HasBeenReleased ()
        {
            this.ToggleCanHappen = true;
        }
    }
    public class range
    {
        public float clip (float input)
        {
            if (input > 1.0) return 1;
            else if (input < -1.0) return -1;
            else return input;
        }
    }
}
