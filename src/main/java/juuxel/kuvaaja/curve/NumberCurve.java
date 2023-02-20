package juuxel.kuvaaja.curve;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;

public interface NumberCurve {
    double getHeight(double width);

    /**
     * Transforms a point on the number line to a point on this curve.
     *
     * <p>The curve runs from 0 to {@code width} on the x-axis, and from 0 to the {@linkplain #getHeight(double) height}
     * on the y-axis.
     *
     * <p>The y-coordinate of the point before transformation must be zero with a maximum error of {@code 1e-6}.
     *
     * @param point the point to be transformed
     * @param width the width of the drawn curve
     */
    void transform(Point2D point, double width);

    /**
     * Paints this number curve at (0, 0).
     *
     * <p>Callers should set a desired default stroke and paint for the {@link Graphics2D} parameter,
     * but implementations are free to replace it with their own.
     *
     * @param g     the graphics
     * @param width the width of the drawn curve
     */
    void paint(Graphics2D g, double width);
}
