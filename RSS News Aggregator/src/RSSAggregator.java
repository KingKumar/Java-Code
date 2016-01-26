import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.xmltree.XMLTree;
import components.xmltree.XMLTree1;

/**
 * Program to convert an XML of RSS (version 2.0) feeds from a given file into
 * the corresponding HTML output files.
 * 
 * @author Ronit Kumar
 * 
 */
public final class RSSAggregator {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private RSSAggregator() {
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
    private static void outputIndex(XMLTree channel, SimpleWriter out) {
        assert channel != null : "Violation of: channel is not null";
        assert out != null : "Violation of: out is not null";
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
        String header = channel.attributeValue("title");
        out.println("<title>" + header + "</title>");

        // Prints out more opening tags
        out.println("</head>");
        out.println("<body>");

        out.println("<h2>" + header + "</h2>");
        out.println("<ul>");
        int k = 0;
        while (k < channel.numberOfChildren()) {
            String file = channel.child(k).attributeValue("file");
            String name = channel.child(k).attributeValue("name");
            out.println("<li><a href=\"" + file + "\">" + name + "</a></li>");
            k++;
        }

        out.println("</ul>");

        out.println("</body>");
        out.println("</html>");
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

        int k = 0, j = 0;
        String name = xml.child(k).label();

        // Finds the child element that the tag is
        while (!name.equals(tag) && k < xml.numberOfChildren()) {
            name = xml.child(k).label();
            if (name.equals(tag)) {
                j = k;
            }
            k++;
        }

        // Returns the appropriate child element
        if (!name.equals(tag)) {
            return -1;
        } else {
            return j;
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
        int k = getChildElement(item, "pubDate");
        if (k == -1) {
            out.println("<td>No date available</td>");
        } else {
            String pubDateContent = item.child(k).child(0).label();
            out.println("<td>" + pubDateContent + "</td>");
        }

        // Prints the source if present
        k = getChildElement(item, "source");
        if (k == -1) {
            out.println("<td>No source available</td>");
        } else {
            String url = item.child(k).attributeValue("url");
            String sourceContent = item.child(k).child(0).label();
            out.println("<td><a href = " + url + ">" + sourceContent
                    + "</a></td>");
        }

        // Prints the title if present
        k = getChildElement(item, "title");
        if (k == -1) {
            k = getChildElement(item, "description");
            String descriptionContent = item.child(k).child(0).label();
            k = getChildElement(item, "link");
            if (k == -1) {
                out.println("<td>" + descriptionContent + "</td>");
            } else {
                String url = item.child(k).child(0).label();
                out.println("<td><a href = " + url + ">" + descriptionContent
                        + "</a></td>");
            }
        } else {
            String titleContent = item.child(k).child(0).label();
            k = getChildElement(item, "link");
            if (k == -1) {
                out.println("<td>" + titleContent + "</td>");
            } else {
                String url = item.child(k).child(0).label();
                out.println("<td><a href = " + url + ">" + titleContent
                        + "</a></td>");
            }
        }

        // Ends the table row
        out.println("</tr>");
    }

    /**
     * Processes one XML RSS (version 2.0) feed from a given URL converting it
     * into the corresponding HTML output file.
     * 
     * @param url
     *            the URL of the RSS feed
     * @param file
     *            the name of the HTML output file
     * @param out
     *            the output stream to report progress or errors
     * @updates {@code out.content}
     * @requires <pre>
     * {@code out.is_open}
     * </pre>
     * @ensures <pre>
     * {@code [reads RSS feed from url, saves HTML document with table of news items
     *   to file, appends to out.content any needed messages]}
     * </pre>
     */
    private static void processFeed(String feed, String file, SimpleWriter out) {
        XMLTree t = new XMLTree1(feed);

        String versionvalue = null;
        String rootlabel = t.label();
        boolean b = t.hasAttribute("version");
        if (b) {
            versionvalue = t.attributeValue("version");
        }

        // Makes sure that the tested XMLTree object is an RSS 2.0 feed
        while (rootlabel != "rss" && versionvalue != "2.0") {
            out.println("Not a proper RSS 2.0 feed.");

            out.println("Enter another URL of an RSS 2.0 feed:");
            XMLTree tree = new XMLTree1(feed);

            rootlabel = tree.label();
            b = tree.hasAttribute("version");
            if (b) {
                versionvalue = t.attributeValue("version");
            }
        }

        // Gets the name of an output file
        SimpleWriter outfile = new SimpleWriter1L(file);

        // Prints out the header to the file and opening lines
        XMLTree channel = t.child(0);
        outputHeader(channel, outfile);

        // Outputs a paragraph in the file
        XMLTree description = channel.child(getChildElement(channel,
                "description"));
        if (getChildElement(channel, "description") == -1) {
            String descriptionContent = description.child(0).label();
            outfile.println("<p>" + descriptionContent + "</p>");
        }
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

        outfile.close();
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

        // Asks for XML file containing list of feeds
        out.println("Enter feed list: ");
        XMLTree source = new XMLTree1(in.nextLine());
        source.display();

        // Gets the name of an output file
        out.println("Enter name of output file:");
        String file = in.nextLine();
        SimpleWriter index = new SimpleWriter1L(file);
        outputIndex(source, index);

        int k = 0;

        while (k < source.numberOfChildren()) {
            String outfile = "out" + (k + 1);
            String feed = source.child(k).attributeValue("url");
            processFeed(feed, outfile, out);
            k++;
        }

        in.close();
        out.close();
        index.close();
    }
}