package juuxel.kuvaaja.inputset;

import juuxel.kuvaaja.Grapher;

public class FixedCountGraphInputSet implements GraphInputSet {
    private int count = 20;

    public FixedCountGraphInputSet() {
    }

    public FixedCountGraphInputSet(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public double[] getInputs(Grapher grapher) {
        double[] result = new double[count];
        double unitLength = (grapher.getRangeEnd() - grapher.getRangeStart()) / (count - 1);
        for (int i = 0; i < count; i++) {
            result[i] = grapher.getRangeStart() + i * unitLength;
        }
        return result;
    }
}
