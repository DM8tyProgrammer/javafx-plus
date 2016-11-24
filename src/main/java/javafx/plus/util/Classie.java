package javafx.plus.util;

import javafx.scene.Node;

import java.util.Arrays;
import java.util.Iterator;
import java.util.regex.Pattern;

/**
 * Inspired By Classie.js
 * Classie is utility made for add,remove,checking styleclass to a node
 *
 * @author theBeacon
 */
public class Classie {

    private Classie() {
    }

    /**
     * Checks the presence of StyleClass(es) in node
     * @param node to be inspected
     * @param styleClasses
     * @return true if all match
     */
    public static boolean has(Node node, String ... styleClasses) {
        return node.getStyleClass().containsAll(Arrays.asList(styleClasses));
    }


    /**
     * Adds StyleClass(es) to a node
     * @param node to which StyleClass(es) to be added
     * @param styleClasses
     */
    public static void add(Node node, String ... styleClasses) {
        node.getStyleClass().addAll(styleClasses);
    }

    /**
     * Removes StyleClass(es) from a node
     * @param node from which StyleClass(es) to be removed
     * @param styleClasses
     */
    public static void remove(Node node, String ... styleClasses) {
        node.getStyleClass().removeAll(styleClasses);
    }

    /**
     * Toggles(if there remove it,else add it) the style class from/to a node
     *
     * @param node
     * @param styleClass
     */
    public static void toggle(Node node, String styleClass) {
        if(Classie.has(node,styleClass)) {
            Classie.remove(node,styleClass);
        } else {
            Classie.add(node,styleClass);
        }
    }

    /**
     *
     * Removes all the classes which match with regex from a node
     * @param node
     * @param regex
     */
    public static void removeByPattern(Node node, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Iterator<String> iterable = node.getStyleClass().iterator();
        for(;iterable.hasNext();) {

            if(pattern.matcher(iterable.next()).find()){
                iterable.remove();
            }
        }
    }

}
