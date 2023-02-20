package juuxel.kuvaaja.inputset;

import juuxel.kuvaaja.Grapher;

import java.util.stream.DoubleStream;

public class FixedIntervalGraphInputSet implements GraphInputSet {
    private double interval = 0.15;

    public FixedIntervalGraphInputSet() {
    }

    public FixedIntervalGraphInputSet(double interval) {
        this.interval = interval;
    }

    public double getInterval() {
        return interval;
    }

    public void setInterval(double interval) {
        this.interval = interval;
    }

    @Override
    public double[] getInputs(Grapher grapher) {
        DoubleStream.Builder builder = DoubleStream.builder();

        for (double x = grapher.getRangeStart(); x <= grapher.getRangeEnd(); x += interval) {
            builder.add(x);
        }

        return builder.build().toArray();
    }
}
