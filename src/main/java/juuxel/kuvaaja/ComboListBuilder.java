package juuxel.kuvaaja;

import java.util.Vector;

final class ComboListBuilder<E> {
    private final Vector<ComboEntry<E>> vector = new Vector<>();

    public ComboListBuilder<E> add(String label, E value) {
        vector.add(new ComboEntry<>(value, label));
        return this;
    }

    public Vector<ComboEntry<E>> build() {
        return vector;
    }
}
