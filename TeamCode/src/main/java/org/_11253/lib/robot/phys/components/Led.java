package org._11253.lib.robot.phys.components;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.LED;

/**
 * A class for controlling Light Emitting Diodes (LEDs).
 * 
 * @author Colin Robertson
 */
public class Led extends Component {
    private DcMotor ledComponent;

    public Led(String name) {
        super(LED.class, name);
        ledComponent = (DcMotor) component;
    }

    public void setColor() {

    }
}
