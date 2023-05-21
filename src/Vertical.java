import java.util.*;

public class Vertical {

    private static final double DELTA = 2;
    private static final int N = 10;
    private static final Point g = new Point(0, -10);

    public static void main(String[] args) {
        Point initialPoint = generatePointInsideUnitCircle();
        Point initialMomentum = Point.multiply(generatePointInsideUnitCircle(),10);

        calc(initialPoint, initialMomentum);
    }

    private static Point generatePointInsideUnitCircle() {
        return new Point((int) (Math.random() * 2 - 1), (int) (Math.random() * 2 - 1));
    }

    private static Point findIntersection(Point start, Point end) {
        double startX = start.getX();
        double startY = start.getY();
        double endX = end.getX();
        double endY = end.getY();
        double k = (endY - startY) / (endX - startX);
        double b = startY - k * startX;
        double _a = 1 + k * k;
        double _b = 2 * k * b;
        double _c = -1 + b * b;

        double d = _b * _b - 4 * _a * _c;

        double ix1 = (-_b + Math.sqrt(d)) / (2 * _a);
        double ix2 = (-_b - Math.sqrt(d)) / (2 * _a);

        double iy1 = k * ix1 + b;
        double iy2 = k * ix2 + b;

        return new Point(ix1, iy1);
    }

    private static Point calcNewMomentum(Point point, Point momentum) {
        double x = point.getX();
        double y = point.getY();
        double px = momentum.getX();
        double py = momentum.getY();

        double a = y * y - x * x;
        double b = -2 * x * y;

        return new Point((a * px + b * py), (b * px - a * py));
    }

    private static void calc(Point initialPoint, Point initialMomentum) {
        List<Line> lines = new ArrayList<>();
        Point point = initialPoint;
        Point momentum = initialMomentum;
        for (int i = 0; i < N; i++) {
            Point nextPoint = Point.add(Point.add(point, momentum),g);
            Point intersection = findIntersection(point, nextPoint);
            lines.add(new Line(point, intersection));

            if (i - 1 != N) {
                point = intersection;
                momentum = calcNewMomentum(point, momentum);
            }
        }

        momentum.setX(-momentum.getX());

        List<Line> reverseLines = new ArrayList<>();
        boolean deviationFound = false;
        for (int i = 0; i < N; i++) {
            Point nextPoint = Point.add(Point.add(point, momentum),g);;
            Point intersection = findIntersection(point, nextPoint);
            Line line = new Line(point, intersection);
            reverseLines.add(line);
            if (Math.atan2(line.getSlope(), lines.get(lines.size() - i - 1).getSlope()) > DELTA && !deviationFound) {
                System.out.println("After " + (i + 1) + " reflections the deviation is more than " + DELTA);
                deviationFound = true;
            }
            point = intersection;
            momentum = calcNewMomentum(point, momentum);

        }

        if (!deviationFound) {
            System.out.println("There was no deviation more than " + DELTA);
        }

        // Print the lines to the console.
        for (Line line : lines) {
            System.out.println(line);
        }
    }
}
