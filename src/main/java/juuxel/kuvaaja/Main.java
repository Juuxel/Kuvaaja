package juuxel.kuvaaja;

import juuxel.kuvaaja.curve.NumberCurve;
import juuxel.kuvaaja.curve.NumberLine;
import juuxel.kuvaaja.curve.NumberSemicircle;
import juuxel.kuvaaja.inputset.FixedCountGraphInputSet;
import juuxel.kuvaaja.inputset.FixedIntervalAndInverseGraphInputSet;
import juuxel.kuvaaja.inputset.FixedIntervalGraphInputSet;
import juuxel.kuvaaja.inputset.GraphInputSet;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ItemListener;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Vector;
import java.util.function.Consumer;

public final class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::setupAndShow);
    }

    private static Vector<ComboEntry<NumberCurve>> getAllCurves() {
        return new ComboListBuilder<NumberCurve>()
            .add("Number line", new NumberLine())
            .add("Top semicircle", new NumberSemicircle(NumberSemicircle.Orientation.TOP_HALF))
            .add("Bottom semicircle", new NumberSemicircle(NumberSemicircle.Orientation.BOTTOM_HALF))
            .build();
    }

    @SuppressWarnings("unchecked")
    private static <E> void addItemListener(@SuppressWarnings("unused") JComboBox<ComboEntry<E>> comboBox, Consumer<E> consumer) {
        ItemListener listener = e -> consumer.accept((E) ((ComboEntry<?>) e.getItem()).value());
        comboBox.addItemListener(listener);
    }

    private static void setupAndShow() {
        JGrapher grapher = new JGrapher();
        grapher.setFunction(x -> x);
        grapher.getGrapher().setInputSet(new FixedCountGraphInputSet(30));

        JLabel functionLabel = new JLabel("Function");
        JComboBox<ComboEntry<GraphableFunction>> functionBox = new JComboBox<>(
            new ComboListBuilder<GraphableFunction>()
                .add("y = x", x -> x)
                .add("y = -x", x -> -x)
                .add("y = |x|", Math::abs)
                .add("y = 1 / x", GraphableFunction.of(x -> 1 / x, GraphableFunction.NON_ZERO))
                .add("y = 1 / -x", GraphableFunction.of(x -> 1 / -x, GraphableFunction.NON_ZERO))
                .add("y = ln(x)", GraphableFunction.of(Math::log, GraphableFunction.POSITIVE))
                .add("y = ln(-x)", GraphableFunction.of(x -> Math.log(-x), GraphableFunction.NEGATIVE))
                .add("y = sin(x)", Math::sin)
                .add("y = cos(x)", Math::cos)
                .build()
        );
        addItemListener(functionBox, grapher::setFunction);
        JLabel inputSetLabel = new JLabel("Inputs");
        JComboBox<ComboEntry<GraphInputSet>> inputSetBox = new JComboBox<>(
            new ComboListBuilder<GraphInputSet>()
                .add("Fixed count", grapher.getGrapher().getInputSet())
                .add("Fixed interval", new FixedIntervalGraphInputSet())
                .add("Fixed interval with inverses", new FixedIntervalAndInverseGraphInputSet())
                .build()
        );
        addItemListener(inputSetBox, grapher.getGrapher()::setInputSet);
        JLabel inputOffsetLabel = new JLabel("Input offset");
        JSlider inputOffsetSlider = new JSlider(-100, 100, 0);
        inputOffsetSlider.setPaintTicks(true);
        inputOffsetSlider.setSnapToTicks(true);
        inputOffsetSlider.setPaintLabels(true);
        inputOffsetSlider.setMajorTickSpacing(20);
        Dictionary<Integer, JLabel> labels = new Hashtable<>();
        labels.put(-100, new JLabel("-5,0"));
        labels.put(0, new JLabel("0,0"));
        labels.put(100, new JLabel("5,0"));
        inputOffsetSlider.setLabelTable(labels);
        inputOffsetSlider.addChangeListener(e -> {
            JSlider slider = (JSlider) e.getSource();
            int raw = slider.getValue();
            double mapped = Mth.map(raw, slider.getMinimum(), slider.getMaximum(), -5, 5);
            grapher.setInputOffset(mapped);
        });
        JLabel inputCurveLabel = new JLabel("Input curve");
        JLabel outputCurveLabel = new JLabel("Output curve");
        JComboBox<ComboEntry<NumberCurve>> inputCurveBox = new JComboBox<>(getAllCurves());
        addItemListener(inputCurveBox, grapher.getGrapher()::setInputCurve);
        inputCurveBox.addItemListener(e -> grapher.getGrapher().setInputCurve((NumberCurve) ((ComboEntry<?>) e.getItem()).value()));
        JComboBox<ComboEntry<NumberCurve>> outputCurveBox = new JComboBox<>(getAllCurves());
        addItemListener(outputCurveBox, grapher.getGrapher()::setOutputCurve);

        JPanel controlPane = new JPanel();
        GroupLayout groupLayout = new GroupLayout(controlPane);
        controlPane.setLayout(groupLayout);
        groupLayout.setAutoCreateGaps(true);
        groupLayout.setAutoCreateContainerGaps(true);
        groupLayout.setHorizontalGroup(
            groupLayout.createSequentialGroup()
                .addGroup(groupLayout.createParallelGroup()
                    .addComponent(functionLabel)
                    .addComponent(inputSetLabel)
                    .addComponent(inputOffsetLabel)
                    .addComponent(inputCurveLabel)
                    .addComponent(outputCurveLabel))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(groupLayout.createParallelGroup()
                    .addComponent(functionBox)
                    .addComponent(inputSetBox)
                    .addComponent(inputOffsetSlider)
                    .addComponent(inputCurveBox)
                    .addComponent(outputCurveBox))
        );
        groupLayout.setVerticalGroup(
            groupLayout.createSequentialGroup()
                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(functionLabel)
                    .addComponent(functionBox))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(inputSetLabel)
                    .addComponent(inputSetBox))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(inputOffsetLabel)
                    .addComponent(inputOffsetSlider))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(inputCurveLabel)
                    .addComponent(inputCurveBox))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(outputCurveLabel)
                    .addComponent(outputCurveBox))
        );

        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.add(grapher, BorderLayout.CENTER);
        contentPane.add(controlPane, BorderLayout.EAST);

        JFrame frame = new JFrame("Kuvaaja");
        frame.setSize(640, 480);
        frame.setContentPane(contentPane);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private static final class JGrapher extends JComponent {
        private final Grapher grapher = new Grapher();
        private GraphableFunction function;
        private double inputOffset;

        {
            grapher.addPropertyChangeListener(e -> {
                revalidate();
                repaint();
            });
        }

        JGrapher() {
        }

        JGrapher(GraphableFunction function) {
            this.function = function;
        }

        public Grapher getGrapher() {
            return grapher;
        }

        public GraphableFunction getFunction() {
            return function;
        }

        public void setFunction(GraphableFunction function) {
            GraphableFunction old = this.function;
            this.function = function;

            firePropertyChange("function", old, function);
            revalidate();
            repaint();
        }

        public double getInputOffset() {
            return inputOffset;
        }

        public void setInputOffset(double inputOffset) {
            double old = this.inputOffset;
            this.inputOffset = inputOffset;
            firePropertyChange("inputOffset", old, inputOffset);
            revalidate();
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            if (function != null) {
                Insets insets = getInsets();
                Graphics h = g.create();
                h.translate(insets.left, insets.top);
                Dimension size = getSize(null);
                size.width -= insets.left + insets.right;
                size.height -= insets.top + insets.bottom;
                GraphableFunction transformedFunction = GraphableFunction.of(
                    x -> function.applyAsDouble(x + inputOffset),
                    x -> function.acceptsInput(x + inputOffset)
                );
                grapher.graph(g, size, transformedFunction);
                h.dispose();
            }
        }
    }
}
