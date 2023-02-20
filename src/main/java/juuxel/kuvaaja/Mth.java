package juuxel.kuvaaja;

final class Mth {
    static double map(double x, double startA, double endA, double startB, double endB) {
        return (x - startA) / (endA - startA) * (endB - startB) + startB;
    }
}
