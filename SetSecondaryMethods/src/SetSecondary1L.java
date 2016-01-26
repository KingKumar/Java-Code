import components.set.Set;
import components.set.Set1L;

/**
 * Layered implementations of secondary methods {@code add} and {@code remove}
 * for {@code Set}.
 * 
 * @param <T>
 *            type of {@code Set} elements
 */
public final class SetSecondary1L<T> extends Set1L<T> {

    /**
     * Default constructor.
     */
    public SetSecondary1L() {
        super();
    }

    @Override
    public void remove(Set<T> s) {
        assert s != null : "Violation of: s is not null";
        assert s != this : "Violation of: s is not this";

        Set<T> temp = s.newInstance();
        temp.transferFrom(s);
        while (temp.size() > 0) {
            T x = temp.removeAny();
            if (this.contains(x)) {
                this.remove(x);
            }
            s.add(x);
        }
    }

    @Override
    public void add(Set<T> s) {
        assert s != null : "Violation of: s is not null";
        assert s != this : "Violation of: s is not this";

        // TODO - fill in body

    }

}