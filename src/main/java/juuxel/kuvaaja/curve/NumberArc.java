package juuxel.kuvaaja.curve;

import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.Point2D;

public class NumberArc implements NumberCurve {
    private double angle = 180;
    private Orientation orientation = Orientation.TOP_HALF;

    public NumberArc() {
    }

    public NumberArc(Orientation orientation) {
        this.orientation = orientation;
    }

    private double angleRadians() {
        return Math.toRadians(angle);
    }

    // The angle (pi - angle) / 2.
    private double auxAngleRadians() {
        return 0.5 * (Math.PI - angleRadians());
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    @Override
    public double getHeight(double width) {
        return 0.5 * width * (1 - Math.sin(auxAngleRadians()));
    }

    @Override
    public void transform(Point2D point, double width) {
        if (Math.abs(point.getY()) > 1e-6) {
            throw new IllegalArgumentException("Nonzero y coordinate for point (%f, %f)".formatted(point.getX(), point.getY()));
        }

        double arcAngle = angleRadians();
        double angleStart = 0.5 * (Math.PI - arcAngle);
        double radius = width / Math.sin(arcAngle) * Math.sin(angleStart);
        double angle = (arcAngle + angleStart) * point.getX() / width;
        double x = radius * (1 - Math.cos(angle) + Math.cos(arcAngle));
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
            angle = this.angle;
        } else {
            y = -getHeight(width);
            angle = -this.angle;
        }

        Graphics2D h = (Graphics2D) g.create();
        boolean isSemicircle = Math.abs(this.angle - 180) < 1e-6;
        double beta = auxAngleRadians();
        double sinBeta = Math.sin(beta);
        double diameter = isSemicircle ? width : 2 * width * sinBeta / Math.sin(angleRadians());
        double x = (Math.cos(beta) - 1) * width;
        if (!isSemicircle) {
            double deltaY = 0.5 * diameter * (1 - sinBeta);
            h.translate(0, -deltaY);
        }
        h.rotate(-beta, 0.5 * diameter, 0.5 * diameter);
        h.draw(new Arc2D.Double(x, y, diameter, diameter, 0, angle, Arc2D.OPEN));
        h.dispose();
    }

    public enum Orientation {
        TOP_HALF,
        BOTTOM_HALF
    }
}
