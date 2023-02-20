package juuxel.kuvaaja;

import java.util.function.DoublePredicate;
import java.util.function.DoubleUnaryOperator;

@FunctionalInterface
public interface GraphableFunction extends DoubleUnaryOperator {
    DoublePredicate NON_ZERO = x -> Math.abs(x) > 1e-6;
    DoublePredicate POSITIVE = x -> x > 0;
    DoublePredicate NEGATIVE = x -> x < 0;

    default boolean acceptsInput(double x) {
        return true;
    }

    static GraphableFunction of(DoubleUnaryOperator op, DoublePredicate condition) {
        return new GraphableFunction() {
            @Override
            public double applyAsDouble(double operand) {
                return op.applyAsDouble(operand);
            }

            @Override
            public boolean acceptsInput(double x) {
                return condition.test(x);
            }
        };
    }
}
