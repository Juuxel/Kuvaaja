package juuxel.kuvaaja.curve;

import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.Point2D;

public class NumberSemicircle implements NumberCurve {
    private Orientation orientation = Orientation.TOP_HALF;

    public NumberSemicircle() {
    }

    public NumberSemicircle(Orientation orientation) {
        this.orientation = orientation;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    @Override
    public double getHeight(double width) {
        return 0.5 * width;
    }

    @Override
    public void transform(Point2D point, double width) {
        if (Math.abs(point.getY()) > 1e-6) {
            throw new IllegalArgumentException("Nonzero y coordinate for point (%f, %f)".formatted(point.getX(), point.getY()));
        }

        double radius = width * 0.5;
        double angle = Math.PI * point.getX() / width;
        double x = radius * (1 - Math.cos(angle));
        double sin = Math.sin(angle);
        if (orientation == Orientation.TOP_HALF) sin = 1 - sin;
        double y = radius * sin;
        point.setLocation(x, y);
    }

    @Override
    public void paint(Graphics2D g, double width) {
        double y;
        double angle;

        if (orientation == Orientation.TOP_HALF) {
            y = 0;
            angle = 180;
        } else {
            y = -getHeight(width);
            angle = -180;
        }

        g.draw(new Arc2D.Double(0, y, width, width, 0, angle, Arc2D.OPEN));
    }

    public enum Orientation {
        TOP_HALF,
        BOTTOM_HALF
    }
}
