import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.xmltree.XMLTree;
import components.xmltree.XMLTree1;

/**
 * Program to evaluate XMLTree expressions of {@code int}.
 * 
 * @author Ronit Kumar
 * 
 */
public final class XMLTreeIntExpressionEvaluator {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private XMLTreeIntExpressionEvaluator() {
    }

    /**
     * Evaluate the given expression.
     * 
     * @param exp
     *            the {@code XMLTree} representing the expression
     * @return the value of the expression
     * @requires <pre>
     * {@code [exp is a well-formed XML expression}
     * </pre>
     * @ensures <pre>
     * {@code evaluate = [the value of the expression]}
     * </pre>
     */
    private static int evaluate(XMLTree exp) {
        assert exp != null : "Violation of: exp is not null";
        int value = 0;
        int childone = 0;
        int childtwo = 0;
        while (exp.numberOfChildren() != 0) {
            if (exp.label() == "plus") {
                if (exp.child(0).hasAttribute("number") == true) {
                    String child1 = exp.child(0).attributeValue("number");
                    childone = Integer.parseInt(child1);
                } else {
                    evaluate(exp.child(0));
                }
                if (exp.child(1).hasAttribute("number") == true) {
                    String child2 = exp.child(0).attributeValue("number");
                    childtwo = Integer.parseInt(child2);
                } else {
                    evaluate(exp.child(1));
                }
                value = childone + childtwo;
            } else if (exp.label() == "minus") {
                if (exp.child(0).hasAttribute("number") == true) {
                    String child1 = exp.child(0).attributeValue("number");
                    childone = Integer.parseInt(child1);
                } else {
                    evaluate(exp.child(0));
                }
                if (exp.child(1).hasAttribute("number") == true) {
                    String child2 = exp.child(0).attributeValue("number");
                    childtwo = Integer.parseInt(child2);
                } else {
                    evaluate(exp.child(1));
                }
                value = childone + childtwo;
            } else if (exp.label() == "times") {
                if (exp.child(0).hasAttribute("number") == true) {
                    String child1 = exp.child(0).attributeValue("number");
                    childone = Integer.parseInt(child1);
                } else {
                    evaluate(exp.child(0));
                }
                if (exp.child(1).hasAttribute("number") == true) {
                    String child2 = exp.child(0).attributeValue("number");
                    childtwo = Integer.parseInt(child2);
                } else {
                    evaluate(exp.child(1));
                }
                value = childone + childtwo;
            } else if (exp.label() == "divide") {
                if (exp.child(0).hasAttribute("number") == true) {
                    String child1 = exp.child(0).attributeValue("number");
                    childone = Integer.parseInt(child1);
                } else {
                    evaluate(exp.child(0));
                }
                if (exp.child(1).hasAttribute("number") == true) {
                    String child2 = exp.child(0).attributeValue("number");
                    childtwo = Integer.parseInt(child2);
                } else {
                    evaluate(exp.child(1));
                }
                value = childone / childtwo;
            }
        }
        return value;
    }

    /**
     * Main method.
     * 
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        out.print("Enter the name of an expression XML file: ");
        String file = in.nextLine();
        while (!file.equals("")) {
            XMLTree exp = new XMLTree1(file);
            out.println(evaluate(exp.child(0)));
            out.print("Enter the name of an expression XML file: ");
            file = in.nextLine();
        }

        in.close();
        out.close();
    }

}