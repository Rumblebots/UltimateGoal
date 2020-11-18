//package org.firstinspires.ftc.teamcode.testCode;
//
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import me.wobblyyyy.pathfinder.fieldMapping.Map;
//import me.wobblyyyy.pathfinder.fieldMapping.MapApi;
//import me.wobblyyyy.pathfinder.fieldMapping.maps.ultimategoal.UltimateGoalMap;
//import org._11253.lib.drives.ShifterMeccanum;
//import org._11253.lib.odometry.threeWheelOdometry.ThreeWheel;
//import org._11253.lib.odometry.threeWheelOdometry.ThreeWheels;
//import org._11253.lib.utils.telem.Telemetry;
//import org.firstinspires.ftc.teamcode.ultimategoal.shared.OdometryWheelPositions;
//
//@TeleOp(name = "Mapped Odometry Test", group = "default")
//public class MappedOdometryTest extends ShifterMeccanum {
//    private ThreeWheels threeWheels;
//    private ThreeWheel threeWheel;
//    private Map map;
//    private MapApi mapApi;
//
//    public MappedOdometryTest() {
//        onStart.add(
//                new Runnable() {
//                    @Override
//                    public void run() {
//                        threeWheels = new ThreeWheels(OdometryWheelPositions.positions);
//                        threeWheel = new ThreeWheel(threeWheels);
//                        map = new UltimateGoalMap();
//                        mapApi = new MapApi(map);
////                        mapApi.scheduleAsync(threeWheel, 125);
//                    }
//                }
//        );
//
//        onStartRun.add(
//                new Runnable() {
//                    @Override
//                    public void run() {
//                        Telemetry.addData(
//                                "mapped_odo_test",
//                                "Positions",
//                                ": ",
//                                mapApi.getPositionsString()
//                        );
//                    }
//                }
//        );
//    }
//}
