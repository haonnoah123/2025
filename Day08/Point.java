public class Point {
    int x;
    int y;
    int z;

    public Point(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Point(int[] coords) {
        this(coords[0], coords[1], coords[2]);
    }

    public double distance(Point p2) {
        return Math.sqrt(Math.pow(this.x - p2.x, 2) + Math.pow(this.y - p2.y, 2) + Math.pow(this.z - p2.z, 2));
    }

}
