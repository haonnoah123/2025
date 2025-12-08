public class PointPair {
    Point p1;
    Point p2;
    double distance;

    public PointPair(Point p1, Point p2, double distance) {
        this.p1 = p1;
        this.p2 = p2;
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "Points: (" + p1.x + "," + p1.y + "," + p1.z + ") and (" + p2.x + "," + p2.y + "," + p2.z
                + "), Distance: " + distance;
    }
}