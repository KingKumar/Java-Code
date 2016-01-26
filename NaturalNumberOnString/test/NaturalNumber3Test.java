import components.naturalnumber.NaturalNumber;

/**
 * Customized JUnit test fixture for {@code NaturalNumber3}.
 */
public class NaturalNumber3Test extends NaturalNumberTest {

    @Override
    protected final NaturalNumber constructor() {
        return new NaturalNumber3();
    }

    @Override
    protected final NaturalNumber constructor(int i) {
        return new NaturalNumber3(i);
    }

    @Override
    protected final NaturalNumber constructor(String s) {
        return new NaturalNumber3(s);
    }

    @Override
    protected final NaturalNumber constructor(NaturalNumber n) {
        return new NaturalNumber3(n);
    }

}
