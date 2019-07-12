package unibo.algat.util;

import javafx.geometry.Point2D;

public class Point2D360 extends Point2D {
    /**
     * Creates a new instance of {@code Point2D}.
     *
     * @param x the x coordinate of the point
     * @param y the y coordinate of the point
     */
    public Point2D360(double x, double y) {
        super(x, y);
    }

    @Override
    public double angle(double x2, double y2) {
        final double x1 = getX();
        final double y1 = getY();

        final double dot = dotProduct(x2,y2);       // dot product between [x1, y1] and [x2, y2]
        final double det = x1*y2 - y1*x2;       // determinant
        return Math.toDegrees(Math.atan2(dot,det));
    }

}
