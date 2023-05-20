import java.lang.Math;
import java.util.Random;

public class Point {

    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getLength() {
        return Math.sqrt(x * x + y * y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public Point normalize() {
        return new Point(x / getLength(), y / getLength());
    }

    public static Point add(Point a, Point b) {
        return new Point(a.x + b.x, a.y + b.y);
    }

    public static Point subtract(Point a, Point b) {
        return new Point(a.x - b.x, a.y - b.y);
    }

    public static Point divide(Point a, double c) {
        return new Point(a.x / c, a.y / c);
    }

    public static Point multiply(Point a, int c) {
        return new Point(a.x * c, a.y * c);
    }

    public static Point point(Number a, Number b) {
        return new Point(a.doubleValue(), b.doubleValue());
    }

    public static Point generatePointInsideUnitCircle() {
        Random random = new Random();
        double r = random.nextDouble();
        double t = random.nextDouble() * 2 * Math.PI;

        return point(r * Math.cos(t), r * Math.sin(t));
    }

    public static Point generateMomentumWithLength1() {
        Random random = new Random();
        double t = 2 * Math.PI * random.nextDouble();
        return point(Math.cos(t), Math.sin(t));
    }
}