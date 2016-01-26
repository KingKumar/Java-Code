import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.xmltree.XMLTree;
import components.xmltree.XMLTree1;

/**
 * Program to convert an XML RSS (version 2.0) feed from a given URL into the
 * corresponding HTML output file.
 * 
 * @author Ronit Kumar
 * 
 */
public final class Reader {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private Reader() {
    }

    /**
     * Outputs the opening tags in the generated HTML file.
     * 
     * @param channel
     *            the channel element XMLTree
     * @param out
     *            the output stream
     * @updates {@code out.content}
     * @requires <pre>
     * {@code [the root of channel is a <channel> tag] and out.is_open}
     * </pre>
     * @ensures <pre>
     * {@code out.content = #out.content * [the HTML opening tags]}
     * </pre>
     */
    private static void outputHeader(XMLTree channel, SimpleWriter out) {
        assert channel != null : "Violation of: channel is not null";
        assert out != null : "Violation of: out is not null";
        assert channel.isTag() && channel.label().equals("channel") : ""
                + "Violation of: the label root of channel is a <channel> tag";
        assert out.isOpen() : "Violation of: out.is_open";

        // Prints to the file the opening tags
        out.println("<?xml version='1.0' encoding='ISO-8859-1' ?>");
        out.println("<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Strict//EN'"
                + " 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd'>");
        out.println("<html xmlns='http://www.w3.org/1999/xhtml'>");
        out.println("<head>");
        out.println("<meta http-equiv='Content-Type'"
                + " content='text/html; charset=ISO-8859-1' />");

        // Prints the <channel>title as the page title
        XMLTree title = channel.child(getChildElement(channel, "title"));
        String header = title.child(0).label();
        out.println("<title>" + header + "</title>");

        // Prints out more opening tags
        out.println("</head>");
        out.println("<body>");

        // Gets the content in the link tag
        XMLTree link = channel.child(getChildElement(channel, "link"));
        String headerLink = link.child(0).label();
        out.println("<h1><a href = " + headerLink + ">" + header + "</a></h1>");
    }

    /**
     * Outputs the closing tags in the generated HTML file.
     * 
     * @param out
     *            the output stream
     * @updates {@code out.contents}
     * @requires <pre>
     * {@code out.is_open}
     * </pre>
     * @ensures <pre>
     * {@code out.content = #out.content * [the HTML closing tags]}
     * </pre>
     */
    private static void outputFooter(SimpleWriter out) {
        assert out != null : "Violation of: out is not null";
        assert out.isOpen() : "Violation of: out.is_open";

        out.println("</body>");
        out.println("</html>");
    }

    /**
     * Finds the first occurrence of the given tag among the children of the
     * given {@code XMLTree} and return its index; returns -1 if not found.
     * 
     * @param xml
     *            the {@code XMLTree} to search
     * @param tag
     *            the tag to look for
     * @return the index of the first child of type tag of the {@code XMLTree}
     *         or -1 if not found
     * @requires <pre>
     * {@code [the label of the root of xml is a tag]}
     * </pre>
     * @ensures <pre>
     * {@code getChildElement =
     *  [the index of the first child of type tag of the {@code XMLTree} or
     *   -1 if not found]}
     * </pre>
     */
    private static int getChildElement(XMLTree xml, String tag) {
        assert xml != null : "Violation of: xml is not null";
        assert tag != null : "Violation of: tag is not null";
        assert xml.isTag() : "Violation of: the label root of xml is a tag";

        int i = 0, pos = 0;
        String name = xml.child(i).label();

        // Finds the child element that the tag is
        while (!name.equals(tag) && i < xml.numberOfChildren()) {
            name = xml.child(i).label();
            if (name.equals(tag)) {
                pos = i;
            }
            i++;
        }

        // Returns the appropriate child element
        if (!name.equals(tag)) {
            return -1;
        } else {
            return pos;
        }
    }

    /**
     * Processes one news item and outputs one table row. The row contains three
     * elements: the publication date, the source, and the title (or
     * description) of the item.
     * 
     * @param item
     *            the news item
     * @param out
     *            the output stream
     * @updates {@code out.content}
     * @requires <pre>
     * {@code [the label of the root of item is an <item> tag] and out.is_open}
     * </pre>
     * @ensures <pre>
     * {@code out.content = #out.content *
     *   [an HTML table row with publication date, source, and title of news item]}
     * </pre>
     */
    private static void processItem(XMLTree item, SimpleWriter out) {
        assert item != null : "Violation of: item is not null";
        assert out != null : "Violation of: out is not null";
        assert item.isTag() && item.label().equals("item") : ""
                + "Violation of: the label root of item is an <item> tag";
        assert out.isOpen() : "Violation of: out.is_open";

        // Starts the table row
        out.println("<tr>");

        // Prints the publication date if present
        int n = getChildElement(item, "pubDate");
        if (n == -1) {
            out.println("<td>No date available</td>");
        } else {
            String pubDateContent = item.child(n).child(0).label();
            out.println("<td>" + pubDateContent + "</td>");
        }

        // Prints the source if present
        n = getChildElement(item, "source");
        if (n == -1) {
            out.println("<td>No source available</td>");
        } else {
            String url = item.child(n).attributeValue("url");
            String sourceContent = item.child(n).child(0).label();
            out.println("<td><a href = " + url + ">" + sourceContent
                    + "</a></td>");
        }

        // Prints the title if present
        n = getChildElement(item, "title");
        if (n == -1) {
            n = getChildElement(item, "description");
            String descriptionContent = item.child(n).child(0).label();
            n = getChildElement(item, "link");
            if (n == -1) {
                out.println("<td>" + descriptionContent + "</td>");
            } else {
                String url = item.child(n).child(0).label();
                out.println("<td><a href = " + url + ">" + descriptionContent
                        + "</a></td>");
            }
        } else {
            String titleContent = item.child(n).child(0).label();
            n = getChildElement(item, "link");
            if (n == -1) {
                out.println("<td>" + titleContent + "</td>");
            } else {
                String url = item.child(n).child(0).label();
                out.println("<td><a href = " + url + ">" + titleContent
                        + "</a></td>");
            }
        }

        // Ends the table row
        out.println("</tr>");
    }

    /**
     * Main method.
     * 
     * @param args
     *            the command line arguments; unused here
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        String value = null;

        out.println("Enter the URL of an RSS 2.0 feed:");
        XMLTree t = new XMLTree1(in.nextLine());

        String root = t.label();
        boolean b = t.hasAttribute("version");
        if (b) {
            value = t.attributeValue("version");
        }

        // Makes sure that the tested XMLTree object is an RSS 2.0 feed
        while (root != "rss" && !b && value != "2.0") {
            out.println("Not a proper RSS 2.0 feed.");

            out.println("Enter another URL of an RSS 2.0 feed:");
            XMLTree tree = new XMLTree1(in.nextLine());

            root = tree.label();
            b = tree.hasAttribute("version");
            if (b) {
                value = t.attributeValue("version");
            }
        }

        // Gets the name of an output file
        out.println("Enter name of output file:");
        SimpleWriter outfile = new SimpleWriter1L(in.nextLine());

        // Prints out the header to the file and opening lines
        XMLTree channel = t.child(0);
        outputHeader(channel, outfile);

        // Outputs a paragraph in the file
        XMLTree description = channel.child(getChildElement(channel,
                "description"));
        String descriptionContent = description.child(0).label();
        outfile.println("<p>" + descriptionContent + "</p>");

        // Sets up the table and gives the column headers
        outfile.println("<p>Table of latest news and headlines.</p>");
        outfile.println("<table>");
        outfile.println("<tr>");
        outfile.println("<td><strong>Date</strong></td>");
        outfile.println("<td><strong>Source</strong></td>");
        outfile.println("<td><strong>News</strong></td>");
        outfile.println("</tr>");

        // Outputs the table to the file
        int n = getChildElement(channel, "item");
        if (n != -1) {
            processItem(channel.child(n), outfile);
            n++;
            while (n < channel.numberOfChildren()) {
                if (channel.child(n).label().equals("item")) {
                    processItem(channel.child(n), outfile);
                }
                n++;
            }
        }

        // Ends the table tag
        outfile.println("</table>");

        // Outputs closing tags for html document
        outputFooter(outfile);

        in.close();
        out.close();
        outfile.close();
    }
}