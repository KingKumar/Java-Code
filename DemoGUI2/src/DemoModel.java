/**
 * Model class.
 * 
 * The "model class" should implement an interface that is used as the declared
 * type where needed, but no interface is provided in this very simplified demo.
 * Instead, typical informal Javadoc comments are included with each method
 * here.
 * 
 * @author Bruce W. Weide
 * 
 */
public final class DemoModel {

    /**
     * Model variables.
     * 
     * So-called "model variables", in the Model-View-Controller (MVC)
     * terminology, are arguably related to (but should not be confused with)
     * "mathematical models" as used in design-by-contract.
     * 
     * In this simple situation, the methods dealing with the model variables
     * are "setter/getter" methods. Conventionally, "getter" methods are named
     * starting with "get", but this unfortunate naming style is avoided here in
     * favor of the more parsimonious style in which function methods do not
     * have imperative names (e.g., "getInput") but rather the names descriptive
     * of the values they return (e.g., "input").
     */
    private String input, output;

    /**
     * Default constructor.
     */
    public DemoModel() {
        /*
         * Initialize model; both variables start as empty strings
         */
        this.input = "";
        this.output = "";
    }

    /**
     * Sets input model to String provided as argument.
     * 
     * @param input
     *            new value of input model
     */
    public void setInput(String input) {
        this.input = input;
    }

    /**
     * Reports input model.
     * 
     * @return input model
     */
    public String input() {
        return this.input;
    }

    /**
     * Sets output model to String provided as argument.
     * 
     * @param output
     *            new value of output model
     */
    public void setOutput(String output) {
        this.output = output;
    }

    /**
     * Reports output model.
     * 
     * @return output model
     */
    public String output() {
        return this.output;
    }

}
