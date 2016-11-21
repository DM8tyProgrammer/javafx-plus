package javafx.plus.util;

import javafx.scene.Node;

import java.util.Arrays;
import java.util.Iterator;
import java.util.regex.Matcher;
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
     *
     * @param node
     * @param styleClasses
     * @return true if all match
     */
    public static boolean has(Node node, String ... styleClasses) {
        return node.getStyleClass().containsAll(Arrays.asList(styleClasses));
    }


    /**
     * Add styleClasses
     * @param node
     * @param styleClasses
     */
    public static void add(Node node, String ... styleClasses) {
        node.getStyleClass().addAll(styleClasses);
    }

    public static void remove(Node node, String ... styleClasses) {
        node.getStyleClass().removeAll(styleClasses);
    }

    /**
     * Toggles(if there remove it, else add it) the style class.
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
