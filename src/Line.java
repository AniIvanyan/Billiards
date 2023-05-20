public class Line {

    private final Point start;
    private final Point end;

    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    public double getSlope() {
        if (start.getX() == end.getX()) {
            return 0.0;
        }

        return (end.getY() - start.getY()) / (end.getX() - start.getX());
    }

    public double getYIntercept() {
        return start.getY() - getSlope() * start.getX();
    }

    public Line lineTo(Point that) {
        return new Line(this.start, that);
    }

}


