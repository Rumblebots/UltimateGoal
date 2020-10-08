package org.firstinspires.ftc.teamcode.ultimategoal.shared.subystems;

public class Storage {
    private static int count = 0;

    public static void increment() {
        count++;
    }

    public static void decrement() {
        count--;
    }

    public static int getCount() {
        return count;
    }

    public static boolean canIntake() {
        return count + 1 > 3;
    }
}
