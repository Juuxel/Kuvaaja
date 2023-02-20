package juuxel.kuvaaja;

public record ComboEntry<A>(A value, String label) {
    @Override
    public String toString() {
        return label;
    }
}
