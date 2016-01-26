package components.list;

/**
 * Layered implementations of secondary methods for {@code List}.
 * 
 * @param <T>
 *            type of List entries
 */
public abstract class ListSecondary<T> extends ListCommon<T> {

    // CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public/* final except for lab */void retreat() {
        assert this.leftLength() > 0 : "Violation of: this.left /= <>";

        /*
         * 2221/2231 assignment code deleted.
         */

    }

    //CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public/* final except for lab */void swapRights(List<T> list) {
        assert list != null : "Violation of: list is not null";

        /*
         * 2221/2231 assignment code deleted.
         */

    }

}
