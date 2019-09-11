package com.jkong.xml.xpath;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author JKong
 * @version v1.0
 * @description xml xpath utils
 * @date 2019/8/8 14:14.
 */
public class XmlXpathUtils {

    private static final XPathFactory X_PATH_FACTORY = XPathFactory.newInstance();

    /**
     * Return a new instance of Document.
     *
     * @param xmlStr xml file string
     * @return Return a new instance of Document.
     * @throws ParserConfigurationException if there was a problem processing the specified xml.
     * @throws IOException                  if there was a problem read input stream.
     * @throws SAXException                 his class can contain basic error or warning information from
     *                                      either the XML parser or the application
     */
    public static Document document(String xmlStr)
            throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
        return documentBuilder.parse(new ByteArrayInputStream(xmlStr.getBytes()));
    }

    /**
     * Return a new instance of Document.
     *
     * @param inputStream xml file input stream
     * @return Return a new instance of Document.
     * @throws ParserConfigurationException if there was a problem processing the specified xml.
     * @throws IOException                  if there was a problem read input stream.
     * @throws SAXException                 his class can contain basic error or warning information from
     *                                      either the XML parser or the application
     */
    public static Document document(InputStream inputStream)
            throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
        return documentBuilder.parse(inputStream);
    }

    /**
     * Returns a new instance of XPath, which is not thread safe and not reentrant.
     */
    public static XPath xpath() {
        return X_PATH_FACTORY.newXPath();
    }

    /**
     * Evaluates the specified XPath expression and returns the results as a
     * Double.
     *
     * @param expression The XPath expression to evaluate.
     * @param node       The node to run the expression on.
     * @return The Double result.
     * @throws XPathExpressionException If there was a problem processing the specified XPath
     *                                  expression.
     */
    public static Double asDouble(String expression, Node node)
            throws XPathExpressionException {
        return asDouble(expression, node, xpath());
    }

    /**
     * Same as {@link #asDouble(String, Node)} but allows an xpath to be passed
     * in explicitly for reuse.
     */
    public static Double asDouble(String expression, Node node, XPath xpath)
            throws XPathExpressionException {
        String doubleString = evaluateAsString(expression, node, xpath);
        return (isEmptyString(doubleString)) ? null : Double.parseDouble(doubleString);
    }

    /**
     * Evaluates the specified XPath expression and returns the result as a
     * string.
     *
     * @param expression The XPath expression to evaluate.
     * @param node       The node to run the expression on.
     * @return The string result.
     * @throws XPathExpressionException If there was a problem processing the specified XPath
     *                                  expression.
     */
    public static String asString(String expression, Node node)
            throws XPathExpressionException {
        return evaluateAsString(expression, node, xpath());
    }

    /**
     * Same as {@link #asString(String, Node)} but allows an xpath to be passed
     * in explicitly for reuse.
     */
    public static String asString(String expression, Node node, XPath xpath)
            throws XPathExpressionException {
        return evaluateAsString(expression, node, xpath);
    }

    /**
     * Evaluates the specified XPath expression and returns the result as an
     * Integer.
     *
     * @param expression The XPath expression to evaluate.
     * @param node       The node to run the expression on.
     * @return The Integer result.
     * @throws XPathExpressionException If there was a problem processing the specified XPath
     *                                  expression.
     */
    public static Integer asInteger(String expression, Node node)
            throws XPathExpressionException {
        return asInteger(expression, node, xpath());
    }

    /**
     * Same as {@link #asInteger(String, Node)} but allows an xpath to be passed
     * in explicitly for reuse.
     */
    public static Integer asInteger(String expression, Node node, XPath xpath)
            throws XPathExpressionException {
        String intString = evaluateAsString(expression, node, xpath);
        return (isEmptyString(intString)) ? null : Integer.parseInt(intString);
    }

    /**
     * Evaluates the specified XPath expression and returns the result as a
     * Boolean.
     *
     * @param expression The XPath expression to evaluate.
     * @param node       The node to run the expression on.
     * @return The Boolean result.
     * @throws XPathExpressionException If there was a problem processing the specified XPath
     *                                  expression.
     */
    public static Boolean asBoolean(String expression, Node node)
            throws XPathExpressionException {
        return asBoolean(expression, node, xpath());
    }

    /**
     * Same as {@link #asBoolean(String, Node)} but allows an xpath to be passed
     * in explicitly for reuse.
     */
    public static Boolean asBoolean(String expression, Node node, XPath xpath)
            throws XPathExpressionException {
        String booleanString = evaluateAsString(expression, node, xpath);
        return (isEmptyString(booleanString)) ? null : Boolean.parseBoolean(booleanString);
    }

    /**
     * Evaluates the specified XPath expression and returns the result as a
     * Float.
     *
     * @param expression The XPath expression to evaluate.
     * @param node       The node to run the expression on.
     * @return The Float result.
     * @throws XPathExpressionException If there was a problem processing the specified XPath
     *                                  expression.
     */
    public static Float asFloat(String expression, Node node)
            throws XPathExpressionException {
        return asFloat(expression, node, xpath());
    }

    /**
     * Same as {@link #asFloat(String, Node)} but allows an xpath to be passed
     * in explicitly for reuse.
     */
    public static Float asFloat(String expression, Node node, XPath xpath)
            throws XPathExpressionException {
        String floatString = evaluateAsString(expression, node, xpath);
        return (isEmptyString(floatString)) ? null : Float.valueOf(floatString);
    }

    /**
     * Evaluates the specified XPath expression and returns the result as a
     * Long.
     *
     * @param expression The XPath expression to evaluate.
     * @param node       The node to run the expression on.
     * @return The Long result.
     * @throws XPathExpressionException If there was a problem processing the specified XPath
     *                                  expression.
     */
    public static Long asLong(String expression, Node node)
            throws XPathExpressionException {
        return asLong(expression, node, xpath());
    }

    /**
     * Same as {@link #asLong(String, Node)} but allows an xpath to be passed
     * in explicitly for reuse.
     */
    public static Long asLong(String expression, Node node, XPath xpath)
            throws XPathExpressionException {
        String longString = evaluateAsString(expression, node, xpath);
        return (isEmptyString(longString)) ? null : Long.parseLong(longString);
    }

    /**
     * Evaluates the specified XPath expression and returns the result as a
     * Byte.
     *
     * @param expression The XPath expression to evaluate.
     * @param node       The node to run the expression on.
     * @return The Byte result.
     * @throws XPathExpressionException If there was a problem processing the specified XPath
     *                                  expression.
     */
    public static Byte asByte(String expression, Node node)
            throws XPathExpressionException {
        return asByte(expression, node, xpath());
    }

    /**
     * Same as {@link #asByte(String, Node)} but allows an xpath to be passed
     * in explicitly for reuse.
     */
    public static Byte asByte(String expression, Node node, XPath xpath)
            throws XPathExpressionException {
        String byteString = evaluateAsString(expression, node, xpath);
        return (isEmptyString(byteString)) ? null : Byte.valueOf(byteString);
    }


    /**
     * Returns true if the specified node is null or has no children.
     *
     * @param node The node to test.
     * @return True if the specified node is null or has no children.
     */
    public static boolean isEmpty(Node node) {
        return (node == null);
    }

    /**
     * Evaluates the specified XPath expression and returns the result as a
     * Node.
     *
     * @param nodeName The XPath expression to evaluate.
     * @param node     The node to run the expression on.
     * @return The Node result.
     * @throws XPathExpressionException If there was a problem processing the specified XPath
     *                                  expression.
     */
    public static Node asNode(String nodeName, Node node)
            throws XPathExpressionException {
        return asNode(nodeName, node, xpath());
    }

    /**
     * Same as {@link #asNode(String, Node)} but allows an xpath to be
     * passed in explicitly for reuse.
     */
    public static Node asNode(String nodeName, Node node, XPath xpath)
            throws XPathExpressionException {
        if (node == null) {
            return null;
        }
        return (Node) xpath.evaluate(nodeName, node, XPathConstants.NODE);
    }

    /**
     * Returns the length of the specified node list.
     *
     * @param list The node list to measure.
     * @return The length of the specified node list.
     */
    public static int nodeLength(NodeList list) {
        return list == null ? 0 : list.getLength();
    }

    /**
     * Evaluates the specified expression on the specified node and returns the
     * result as a String.
     *
     * @param expression The Xpath expression to evaluate.
     * @param node       The node on which to evaluate the expression.
     * @return The result of evaluating the specified expression, or null if the
     * evaluation didn't return any result.
     * @throws XPathExpressionException If there are any problems evaluating the Xpath expression.
     */
    private static String evaluateAsString(String expression, Node node,
                                           XPath xpath) throws XPathExpressionException {
        if (isEmpty(node)) {
            return null;
        }

        if (!expression.equals(".")) {
            /*
             * If the expression being evaluated doesn't select a node, we want
             * to return null to distinguish between cases where a node isn't
             * present (which should be represented as null) and when a node is
             * present, but empty (which should be represented as the empty
             * string).
             *
             * We skip this test if the expression is "." since we've already
             * checked that the node exists.
             */
            if (asNode(expression, node, xpath) == null) {
                return null;
            }
        }

        String s = xpath.evaluate(expression, node);
        return s.trim();
    }

    /**
     * Returns true if the specified string is null or empty.
     *
     * @param s The string to test.
     * @return True if the specified string is null or empty.
     */
    private static boolean isEmptyString(String s) {
        return s == null || s.trim().length() == 0;
    }

}

