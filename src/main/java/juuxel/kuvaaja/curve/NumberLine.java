package juuxel.kuvaaja.curve;

import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class NumberLine implements NumberCurve {
    @Override
    public double getHeight(double width) {
        return 0;
    }

    @Override
    public void transform(Point2D point, double width) {

    }

    @Override
    public void paint(Graphics2D g, double width) {
        g.draw(new Line2D.Double(0, 0, width, 0));
    }
}
