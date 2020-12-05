package org._11253.lib.robot.phys.components;

import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.hardware.ServoImplEx;
import org._11253.lib.Global;
import org._11253.lib.utils.Timed;
import org._11253.lib.utils.async.event.StringEvents;

/**
 * A simple class for controlling LEDs.
 *
 * @author Colin Robertson
 */
public class Led {
    /**
     * A list of colors that can be used for LED control.
     */
    public enum colors {
        SOLID_HOT_PINK(1785),
        SOLID_DARK_RED(1795),
        SOLID_RED(1805),
        SOLID_RED_ORANGE(1815),
        SOLID_ORANGE(1825),
        SOLID_GOLD(1835),
        SOLID_YELLOW(1845),
        SOLID_LAWN_GREEN(1855),
        SOLID_LIME(1865),
        SOLID_DARK_GREEN(1875),
        SOLID_GREEN(1885),
        SOLID_BLUE_GREEN(1895),
        SOLID_AQUA(1905),
        SOLID_SKY_BLUE(1915),
        SOLID_DARK_BLUE(1925),
        SOLID_BLUE(1935),
        SOLID_BLUE_VIOLET(1945),
        SOLID_VIOLET(1955),
        SOLID_WHITE(1965),
        SOLID_GRAY(1975),
        SOLID_DARK_GRAY(1985),
        SOLID_BLACK(1995);

        public final double width;

        colors(double width) {
            this.width = width;
        }
    }

    /**
     * Servo implementation, used for PWM control.
     */
    private final ServoImplEx servo;

    /**
     * The lowest possible US rate in microseconds.
     *
     * <p>
     * This corresponds with the LED colors that are available in the
     * "colors" enum of this class.
     * </p>
     */
    private final static double usPulseLower = 1785;

    /**
     * The highest possible US rate in microseconds.
     *
     * <p>
     * This corresponds with the LED colors that are available in the
     * "colors" enum of this class.
     * </p>
     */
    private final static double usPulseUpper = 1995;

    private final static String stringName = "LED control";

    /**
     * The range for pulse width.
     */
    private PwmControl.PwmRange range = new PwmControl.PwmRange(
            usPulseLower,
            usPulseUpper
    );

    /**
     * Create a new LED element.
     *
     * @param name the hardware name of the LED.
     */
    public Led(String name) {
        servo = Global.getHwMap().get(ServoImplEx.class, name);
        servo.setPwmEnable();
    }

    /**
     * Set a color based on the pulse width. You can (and should)
     * use the colors enum for this... ex:
     * <pre>
     * <code>
     * led.setColor(Led.colors.SOLID_WHITE);
     * </code>
     * </pre>
     *
     * @param pulseWidth the pulse width - corresponds to the color
     *                   of the LED. White, for example, is 1965.
     */
    public void setColor(double pulseWidth) {
        setColorInternal(pulseWidth);
        servo.setPwmRange(range);
        stopCycleColors();
    }

    /**
     * Used internally to set color to LEDs.
     *
     * @param pulseWidth pulse width.
     */
    private void setColorInternal(double pulseWidth) {
        range = new PwmControl.PwmRange(
                usPulseLower,
                usPulseUpper,
                pulseWidth
        );
    }

    /**
     * Get the current microsecond pulse width of the LED.
     *
     * @return current color (as a double).
     */
    public double getColor() {
        return range.usFrame;
    }

    /**
     * Automatically cycle all of the colors.
     *
     * <p>
     * This is cancelled if setColor is called or if stopCycleColors
     * is called.
     * </p>
     *
     * @param changeTime how long (in ms) it takes for there to be a change
     *                   of 10 microseconds of pulse width.
     * @param duration   how long (in ms) the automatic cycling should last.
     *                   Set to 0 to disable - the color cycling will occur
     *                   indefinitely.
     */
    public void cycleColors(final int changeTime,
                            int duration) {
        StringEvents.schedule(
                stringName,
                changeTime,
                0,
                new Timed() {
                    @Override
                    public Runnable during() {
                        return new Runnable() {
                            @Override
                            public void run() {
                                setColorInternal(getColor() + 10.0);
                            }
                        };
                    }
                },
                true
        );
        StringEvents.schedule(
                stringName,
                duration,
                0,
                new Timed() {
                    @Override
                    public Runnable open() {
                        return new Runnable() {
                            @Override
                            public void run() {
                                stopCycleColors();
                            }
                        };
                    }
                },
                false
        );
    }

    /**
     * Stop cycling the colors.
     */
    public void stopCycleColors() {
        StringEvents.clear(stringName);
    }
}
