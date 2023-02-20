package juuxel.kuvaaja.inputset;

import juuxel.kuvaaja.Grapher;

import java.util.stream.DoubleStream;

public class FixedIntervalAndInverseGraphInputSet implements GraphInputSet {
    private double interval = 0.15;

    public FixedIntervalAndInverseGraphInputSet() {
    }

    public FixedIntervalAndInverseGraphInputSet(double interval) {
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
            if (Math.abs(x) < 1 - 1e-6) continue;
            builder.add(x);
            if (Math.abs(x) > 1e-6) builder.add(1 / x);
        }

        return builder.build().toArray();
    }
}
