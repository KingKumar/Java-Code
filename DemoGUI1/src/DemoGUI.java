import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Demo GUI to illustrate several features of Swing.
 * 
 * @author Bruce W. Weide
 * 
 */
public final class DemoGUI extends JFrame implements ActionListener {

    /**
     * Main program that sets up main application window and starts user
     * interaction.
     * 
     * @param args
     *            command-line argument; not used
     */
    public static void main(String[] args) {
        /*
         * Create an instance of this class, which is a JFrame in which the
         * constructor sets up everything that will appear in the main
         * application window to begin with, and also starts up the interaction
         * process by registering a callback method with each GUI widget that
         * the user can manipulate. The actual callback method, actionPerformed
         * (declared in ActionListener and implemented in this class), is called
         * by a widget whenever the user manipulates it, e.g., presses a button.
         * (The reference returned by the constructor may be ignored, as the
         * main program is done with its job once the constructor returns!)
         */
        new DemoGUI();
    }

    // -----------------------------------------------------------------------

    /**
     * Model variables.
     * 
     * So-called "model variables", in the "Model-View-Controller (MVC)"
     * terminology often encountered when dealing with GUIs, are arguably
     * related to (but should not be confused with) "mathematical models" as
     * used in design-by-contract.
     */
    private String input, output;

    /**
     * GUI widgets that need to be in scope in actionPerformed method, and
     * related constants. (Each should have its own Javadoc comment, but these
     * are elided here to keep the code shorter.)
     */
    private static final int LINES_IN_TEXT_AREAS = 5;
    private static final int LINE_LENGTHS_IN_TEXT_AREAS = 20;
    private static final int ROWS_IN_BUTTON_PANEL_GRID = 1;
    private static final int COLUMNS_IN_BUTTON_PANEL_GRID = 2;
    private static final int ROWS_IN_THIS_GRID = 3;
    private static final int COLUMNS_IN_THIS_GRID = 1;
    private final JTextArea inputText;
    private final JTextArea outputText;
    private final JButton resetButton;
    private final JButton copyButton;

    /**
     * Default constructor.
     */
    public DemoGUI() {
        /*
         * Call the JFrame (superclass) constructor with a String parameter to
         * name the window in its title bar
         */
        super("Simple GUI Demo");

        // Set up the model variables ----------------------------------------

        /*
         * Initialize model variable values; both start as empty strings
         */
        this.input = "";
        this.output = "";

        // Set up the GUI widgets --------------------------------------------

        /*
         * Create widgets
         */
        this.inputText = new JTextArea("", LINES_IN_TEXT_AREAS,
                LINE_LENGTHS_IN_TEXT_AREAS);
        this.outputText = new JTextArea("", LINES_IN_TEXT_AREAS,
                LINE_LENGTHS_IN_TEXT_AREAS);
        this.resetButton = new JButton("Reset");
        this.copyButton = new JButton("Copy Input");
        /*
         * Text areas should wrap lines, and outputText should be read-only
         */
        this.inputText.setEditable(true);
        this.inputText.setLineWrap(true);
        this.inputText.setWrapStyleWord(true);
        this.outputText.setEditable(false);
        this.outputText.setLineWrap(true);
        this.outputText.setWrapStyleWord(true);
        /*
         * Create scroll panes for the text areas in case text is long enough to
         * require scrolling in one or both dimensions
         */
        JScrollPane inputTextScrollPane = new JScrollPane(this.inputText);
        JScrollPane outputTextScrollPane = new JScrollPane(this.outputText);
        /*
         * Create a button panel organized using grid layout
         */
        JPanel buttonPanel = new JPanel(new GridLayout(
                ROWS_IN_BUTTON_PANEL_GRID, COLUMNS_IN_BUTTON_PANEL_GRID));
        /*
         * Add the buttons to the button panel, from left to right and top to
         * bottom
         */
        buttonPanel.add(this.resetButton);
        buttonPanel.add(this.copyButton);
        /*
         * Organize main window using grid layout
         */
        this.setLayout(new GridLayout(ROWS_IN_THIS_GRID, COLUMNS_IN_THIS_GRID));
        /*
         * Add scroll panes and button panel to main window, from left to right
         * and top to bottom
         */
        this.add(inputTextScrollPane);
        this.add(buttonPanel);
        this.add(outputTextScrollPane);

        // Set up the observers ----------------------------------------------

        /*
         * Register this object as the observer for all GUI events
         */
        this.resetButton.addActionListener(this);
        this.copyButton.addActionListener(this);

        // Set up the main application window --------------------------------

        /*
         * Make sure the main window is appropriately sized for the widgets in
         * it, that it exits this program when closed, and that it becomes
         * visible to the user now
         */
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    // -----------------------------------------------------------------------

    @Override
    public void actionPerformed(ActionEvent event) {
        /*
         * Set cursor to indicate computation on-going; this matters only if
         * processing the event might take a noticeable amount of time as seen
         * by the user
         */
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        /*
         * Determine which event has occurred that we are being notified of by
         * this callback; in this case, the source of the event (i.e, the widget
         * calling actionPerformed) is all we need because only buttons are
         * involved here, so the event must be a button press
         */
        Object source = event.getSource();
        if (source == this.resetButton) {
            /*
             * Reset both model variables to empty strings
             */
            this.input = "";
            this.output = "";
        } else if (source == this.copyButton) {
            /*
             * Do whatever is to be done to the input model variable, to get the
             * intended output model variable; here, do nothing but copy the
             * input as a demo
             */
            this.input = this.inputText.getText();
            this.output = this.input;
        }
        /*
         * Display the model variable values in the GUI widgets
         */
        this.inputText.setText(this.input);
        this.outputText.setText(this.output);
        /*
         * Set the cursor back to normal (because we changed it at the beginning
         * of the method body)
         */
        this.setCursor(Cursor.getDefaultCursor());
    }

}
