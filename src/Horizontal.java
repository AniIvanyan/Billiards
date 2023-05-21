import java.util.*;
import java.util.List;

public class Horizontal {

    private static final double DELTA = 3; // degree
    private static final int N = 10;

    public static void main(String[] args) {
        Point initialPoint = generatePointInsideUnitCircle();
        Point initialMomentum = generatePointInsideUnitCircle();

        System.out.println(initialPoint.getX() + ", " + initialPoint.getY());
        System.out.println(initialMomentum.getX() + ", " + initialMomentum.getY());

        List<Line> lines = new ArrayList<>();
        Point point = initialPoint;
        Point momentum = initialMomentum;
        for (int i = 0; i < N; i++) {
            Point nextPoint = point.add(point, momentum);
            Point intersection = findIntersection(point, nextPoint);
            lines.add(new Line(point, intersection));

            point = intersection;
            momentum = calcNewMomentum(point, momentum);
        }

        momentum.setX(-momentum.getX());

        List<Line> reverseLines = new ArrayList<>();
        boolean deviationFound = false;
        for (int i = 0; i < N; i++) {
            Point nextPoint = point.add(point, momentum);
            Point intersection = findIntersection(point, nextPoint);
            Line line = new Line(point, intersection);
            reverseLines.add(line);
            if (Math.atan2(line.getSlope(), lines.get(i).getSlope()) > DELTA && !deviationFound) {
                System.out.println("After " + (i + 1) + " reflections the deviation is more than " + DELTA);
                deviationFound = true;
            }
            point = intersection;
            momentum = calcNewMomentum(point, momentum);
        }

        if (!deviationFound) {
            System.out.println("There was no deviation more than " + DELTA);
        }
        lines.forEach(System.out::println);
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
        double x1 = (-b + Math.sqrt(b * b + 1)) / 2;
        double y1 = k * x1 + b;
        double x2 = (-b - Math.sqrt(b * b + 1)) / 2;
        double y2 = k * x2 + b;
        return new Point(y1, y2);
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
}
