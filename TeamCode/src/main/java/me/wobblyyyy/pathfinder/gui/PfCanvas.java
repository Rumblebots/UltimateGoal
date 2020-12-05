//package me.wobblyyyy.pathfinder.gui;
//
//import me.wobblyyyy.pathfinder.fieldMapping.components.Coordinate;
//
//import java.util.ArrayList;
//
//public class PfCanvas extends JFrame {
//    public Canvas canvas;
//
//    public PfCanvas(ArrayList<Coordinate<Double>> points) {
//        super("PfCanvas");
//
//        Canvas c = new Canvas() {
//            public void paint(Graphics g) {
//                g.setColor(Color.BLACK);
//                for (Coordinate<Double> point : points) {
//                    int x = (int) Math.round(point.getX() * 10.0 * 2.0 / 10.0);
//                    int y = (int) Math.round(point.getY() * 10.0 * 2.0 / 10.0);
//                    g.drawLine(x, y, x, y);
//                }
////                g.drawString("ayeee what's good", 100, 100);
//            }
//        };
//
//        add(c);
//        setSize(288, 288);
//        show();
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    }
//}
