package org._11253.lib.playback;

public class ControllerState {
    public boolean a = false,
            b = false,
            x = false,
            y = false,
            dpu = false,
            dpd = false,
            dpl = false,
            dpr = false,
            str = false,
            sel = false,
            rb = false,
            lb = false;

    public double rt = 0.0,
            lt = 0.0,
            rsx = 0.0,
            lsx = 0.0,
            rsy = 0.0,
            lsy = 0.0;

    public ControllerState(boolean a,
                           boolean b,
                           boolean x,
                           boolean y,
                           boolean dpu,
                           boolean dpd,
                           boolean dpl,
                           boolean dpr,
                           boolean str,
                           boolean sel,
                           boolean rb,
                           boolean lb,
                           double rt,
                           double lt,
                           double rsx,
                           double lsx,
                           double rsy,
                           double lsy) {
        this.a = a;
        this.b = b;
        this.x = x;
        this.y = y;
        this.dpu = dpu;
        this.dpd = dpd;
        this.dpl = dpl;
        this.dpr = dpr;
        this.str = str;
        this.sel = sel;
        this.rb = rb;
        this.lb = lb;
        this.rt = rt;
        this.lt = lt;
        this.rsx = rsx;
        this.lsx = lsx;
        this.rsy = rsy;
        this.lsy = lsy;
    }

    public ControllerState() {

    }
}
