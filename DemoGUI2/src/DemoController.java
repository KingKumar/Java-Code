/**
 * Controller class.
 * 
 * The "controller class" should implement an interface that is used as the
 * declared type where needed, but no interface is provided in this very
 * simplified demo. Instead, typical informal Javadoc comments are included with
 * each method here.
 * 
 * @author Bruce W. Weide
 * 
 */
public final class DemoController {

    /**
     * Model object.
     */
    private final DemoModel model;

    /**
     * View object.
     */
    private final DemoView view;

    /**
     * Updates this.view to display this.model.
     */
    private void updateViewToMatchModel() {
        /*
         * Get model info
         */
        String input = this.model.input();
        String output = this.model.output();
        /*
         * Update view to reflect changes in model
         */
        this.view.updateInputDisplay(input);
        this.view.updateOutputDisplay(output);
    }

    /**
     * Constructor; connects {@code this} to the model and view it coordinates.
     * 
     * @param model
     *            model to connect to
     * @param view
     *            view to connect to
     */
    public DemoController(DemoModel model, DemoView view) {
        this.model = model;
        this.view = view;
    }

    /**
     * Processes reset event.
     */
    public void processResetEvent() {
        /*
         * Update model in response to this event
         */
        this.model.setInput("");
        this.model.setOutput("");
        /*
         * Update view to reflect changes in model
         */
        this.updateViewToMatchModel();
    }

    /**
     * Processes copy event.
     * 
     * @param input
     *            value of input text (provided by view)
     */
    public void processCopyEvent(String input) {
        /*
         * Update model in response to this event
         */
        this.model.setInput(input);
        this.model.setOutput(input);
        /*
         * Update view to reflect changes in model
         */
        this.updateViewToMatchModel();
    }

}
