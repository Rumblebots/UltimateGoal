package org._11253.lib.playback;

import org._11253.lib.controllers.Controller;

public class PlaybackControllerManager {
    public static ControllerState getControllerState(Controller c) {
        return new ControllerState(
                c.getA(),
                c.getB(),
                c.getX(),
                c.getY(),
                c.getDpadUp(),
                c.getDpadDown(),
                c.getDpadLeft(),
                c.getDpadRight(),
                c.getStart(),
                c.getSelect(),
                c.getRightBumper(),
                c.getLeftBumper(),
                c.getRightTrigger(),
                c.getLeftTrigger(),
                c.getRightX(),
                c.getLeftX(),
                c.getRightY(),
                c.getLeftY()
        );
    }

    public static void setControllerState(Controller c,
                                          ControllerState s) {
        c.setA(s.a);
        c.setB(s.b);
        c.setX(s.x);
        c.setY(s.y);
        c.setDpadUp(s.dpu);
        c.setDpadDown(s.dpd);
        c.setDpadLeft(s.dpl);
        c.setDpadRight(s.dpr);
        c.setStart(s.str);
        c.setSelect(s.sel);
        c.setRightBumper(s.rb);
        c.setLeftBumper(s.lb);
        c.setRightTrigger(s.rt);
        c.setLeftTrigger(s.lt);
        c.setRightStickX(s.rsx);
        c.setLeftStickX(s.lsx);
        c.setRightStickY(s.rsy);
        c.setLeftStickY(s.lsy);
    }
}
