import java.util.*;

import static java.lang.Math.*;

public class Billiard {
    private static final int NUMBREFLECT = 10;

    public static void main(String[] args) {
        Point point = Point.generatePointInsideUnitCircle();
        Point momentum = Point.generateMomentumWithLength1();
        System.out.println(calculate(point, momentum));
    }
    public static List<Line> calculate(Point initialPoint, Point initialMomentum) {
        Point point = initialPoint;
        Point momentum = initialMomentum;
        double L = 2.0;
        List<Line> lines = new ArrayList<>();

        for (int i = 0; i < NUMBREFLECT; i++) {
            Line line = new Line(point, Point.add(point, momentum));
            Point intersectionWithLine = intersectionWithLine(line, momentum);
            if (abs(intersectionWithLine.getX()) < L / 2) {
                lines.add(new Line(point, intersectionWithLine));
                point = intersectionWithLine;
                momentum = new Point(momentum.getX(), -momentum.getY());
            } else {
                PointC intersectionAndXc;
                if (intersectionWithLine.getX() >= L / 2) {
                    intersectionAndXc = intersectionWithRightSemiCircle(line, L);
                } else {
                    intersectionAndXc = intersectionWithLeftSemiCircle(line, L);
                }
                Point intersection = intersectionAndXc.point;
                double xc = intersectionAndXc.c;

                lines.add(new Line(point, intersection));
                point = intersection;
                momentum = generateNewMomentum(point, momentum, xc);
            }
        }
        return lines;
    }

    private static Point generateNewMomentum(Point point, Point momentum, double xc) {
        double x = point.getX();
        double y = point.getY();
        double px = momentum.getX();
        double py = momentum.getY();
        double a = pow(y, 2) - pow(x - xc, 2);
        double b = -2 * (x - xc) * y;
        return new Point(a * px + b * py, b * px - a * py);
    }

    private static Point intersectionWithLine(Line line, Point momentum) {
        int y = momentum.getY() > 0 ? 1 : -1;
        if (momentum.getX() == 0.0) {
            return new Point(line.getStart().getX(), y);
        }
        double x = (y - line.getYIntercept()) / line.getSlope();
        return new Point(x, y);
    }

    private static PointC intersectionWithRightSemiCircle(Line line, double L) {
        double k = line.getSlope();
        double b = line.getYIntercept();
        double c = pow((2 * k * b - L), 2) - 4 * (1 + pow(k, 2)) * (-1 + pow(b, 2) + pow(L / 2, 2));
        double x1 = (-(2 * k * b - L) + sqrt(c)) / (2 * (1 + pow(k, 2)));
        double x2 = (-(2 * k * b - L) - sqrt(c)) / (2 * (1 + pow(k, 2)));
        double y1 = k * x1 + b;
        double y2 = k * x2 + b;

        List<Point> intersections = List.of(new Point(x1, y1), new Point(x2, y2));
        return intersections.stream().max(Comparator.comparingDouble(Point::getX)).map(p -> new PointC(p, L / 2))
                .orElseThrow();
    }

    private static PointC intersectionWithLeftSemiCircle(Line line, double l) {
        double k = line.getSlope();
        double b = line.getYIntercept();
        double _a = 1 + pow(k, 2);
        double _b = 2 * k * b + l;
        double _c = -1 + pow(b, 2) + pow(l / 2, 2);
        double d = pow(_b, 2) - 4 * _a * _c;
        double x1 = (-_b + sqrt(d)) / (2 * _a);
        double x2 = (-_b - sqrt(d)) / (2 * _a);
        double y1 = k * x1 + b;
        double y2 = k * x2 + b;

        List<Point> intersections = List.of(new Point(x1, y1), new Point(x2, y2));
        return intersections.stream().min(Comparator.comparingDouble(Point::getX)).map(p -> new PointC(p, -l / 2))
                .orElseThrow();
    }

    private static class PointC {
        Point point;
        double c;

        PointC(Point point, double c) {
            this.point = point;
            this.c = c;
        }
    }
}
