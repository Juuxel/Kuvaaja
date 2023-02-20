package juuxel.kuvaaja;

import juuxel.kuvaaja.curve.NumberCurve;
import juuxel.kuvaaja.curve.NumberLine;
import juuxel.kuvaaja.inputset.FixedCountGraphInputSet;
import juuxel.kuvaaja.inputset.GraphInputSet;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Dimension2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public final class Grapher {
    private final PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private double rangeStart = -5;
    private double rangeEnd = 5;
    private GraphInputSet inputSet = new FixedCountGraphInputSet();
    private Color inputColor = Color.BLACK;
    private Color outputColor = new Color(0x5555FF);
    private Color graphColor = Color.RED;
    private Stroke numberLineStroke = new BasicStroke(1.5f);
    private Stroke graphStroke = new BasicStroke(1f);
    private float axisSeparation = 2;
    private NumberCurve inputCurve = new NumberLine();
    private NumberCurve outputCurve = new NumberLine();

    public void graph(Graphics graphics, Dimension2D size, GraphableFunction function) {
        double scale = size.getWidth() / 12;
        Graphics2D g = (Graphics2D) graphics.create();
        g.translate(6 * scale, scale);
        g.setStroke(numberLineStroke);

        // Paint input curve
        g.setPaint(inputColor);
        double curveX = -5 * scale;
        double curveWidth = 10 * scale;
        paintNumberCurve(g, curveX, 0, curveWidth, inputCurve, 5, "x");

        // Paint output curve
        g.setPaint(outputColor);
        double inputHeight = inputCurve.getHeight(curveWidth);
        double outputCurveY = inputHeight + axisSeparation * scale;
        paintNumberCurve(g, curveX, outputCurveY, curveWidth, outputCurve, 5, "y");

        g.setPaint(graphColor);
        g.setStroke(graphStroke);

        for (double input : inputSet.getInputs(this)) {
            if (!function.acceptsInput(input)) continue;

            try {
                double output = function.applyAsDouble(input);
                Point2D inputPoint = new Point2D.Double(Mth.map(input, rangeStart, rangeEnd, 0, curveWidth), 0);
                inputCurve.transform(inputPoint, curveWidth);
                inputPoint.setLocation(inputPoint.getX() + curveX, inputPoint.getY());
                Point2D outputPoint = new Point2D.Double(Mth.map(output, rangeStart, rangeEnd, 0, curveWidth), 0);
                outputCurve.transform(outputPoint, curveWidth);
                outputPoint.setLocation(outputPoint.getX() + curveX, outputPoint.getY() + outputCurveY);

                g.draw(new Line2D.Double(inputPoint, outputPoint));
            } catch (ArithmeticException e) {
                // ignored
            }
        }

        g.dispose();
    }

    private static void paintNumberCurve(Graphics2D g, double x, double y, double width, NumberCurve curve, double labelSeparation, String label) {
        Graphics2D h = (Graphics2D) g.create();
        h.translate(x, y);
        curve.paint(h, width);
        h.dispose();

        double height = curve.getHeight(width);
        g.drawString(label, (float) (x + width + labelSeparation), (float) (y + 0.5 * height));
    }

    public double getRangeStart() {
        return rangeStart;
    }

    public void setRangeStart(double rangeStart) {
        double old = this.rangeStart;
        this.rangeStart = rangeStart;
        changeSupport.firePropertyChange("rangeStart", old, rangeStart);
    }

    public double getRangeEnd() {
        return rangeEnd;
    }

    public void setRangeEnd(double rangeEnd) {
        double old = this.rangeEnd;
        this.rangeEnd = rangeEnd;
        changeSupport.firePropertyChange("rangeEnd", old, rangeEnd);
    }

    public GraphInputSet getInputSet() {
        return inputSet;
    }

    public void setInputSet(GraphInputSet inputSet) {
        GraphInputSet old = this.inputSet;
        this.inputSet = inputSet;
        changeSupport.firePropertyChange("inputSet", old, inputSet);
    }

    public Color getInputColor() {
        return inputColor;
    }

    public void setInputColor(Color inputColor) {
        Color old = this.inputColor;
        this.inputColor = inputColor;
        changeSupport.firePropertyChange("inputColor", old, inputColor);
    }

    public Color getOutputColor() {
        return outputColor;
    }

    public void setOutputColor(Color outputColor) {
        Color old = this.outputColor;
        this.outputColor = outputColor;
        changeSupport.firePropertyChange("outputColor", old, outputColor);
    }

    public Color getGraphColor() {
        return graphColor;
    }

    public void setGraphColor(Color graphColor) {
        Color old = this.graphColor;
        this.graphColor = graphColor;
        changeSupport.firePropertyChange("graphColor", old, graphColor);
    }

    public Stroke getNumberLineStroke() {
        return numberLineStroke;
    }

    public void setNumberLineStroke(Stroke numberLineStroke) {
        Stroke old = this.numberLineStroke;
        this.numberLineStroke = numberLineStroke;
        changeSupport.firePropertyChange("numberLineStroke", old, numberLineStroke);
    }

    public Stroke getGraphStroke() {
        return graphStroke;
    }

    public void setGraphStroke(Stroke graphStroke) {
        Stroke old = this.graphStroke;
        this.graphStroke = graphStroke;
        changeSupport.firePropertyChange("graphStroke", old, graphStroke);
    }

    public float getAxisSeparation() {
        return axisSeparation;
    }

    public void setAxisSeparation(float axisSeparation) {
        float old = this.axisSeparation;
        this.axisSeparation = axisSeparation;
        changeSupport.firePropertyChange("axisSeparation", old, axisSeparation);
    }

    public NumberCurve getInputCurve() {
        return inputCurve;
    }

    public void setInputCurve(NumberCurve inputCurve) {
        NumberCurve old = this.inputCurve;
        this.inputCurve = inputCurve;
        changeSupport.firePropertyChange("inputCurve", old, inputCurve);
    }

    public NumberCurve getOutputCurve() {
        return outputCurve;
    }

    public void setOutputCurve(NumberCurve outputCurve) {
        NumberCurve old = this.outputCurve;
        this.outputCurve = outputCurve;
        changeSupport.firePropertyChange("outputCurve", old, outputCurve);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void addPropertyChangeListener(String property, PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(property, listener);
    }
}
