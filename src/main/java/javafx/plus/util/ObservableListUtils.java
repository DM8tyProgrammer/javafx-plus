package javafx.plus.util;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.util.function.Consumer;

/**
 * @author theBeacon
 */
public class ObservableListUtils {

    private ObservableListUtils () {

    }


    public static <E> void bind(ObservableList<E> target,ObservableList<? extends E> source ){
        source.addListener(new ListChangeListener<E>() {
            @Override
            public void onChanged(Change<? extends E> c) {
                while (c.next()) {
                    if(c.wasRemoved()) {
                        target.removeAll(c.getRemoved());
                    }

                    if(c.wasAdded()) {
                        target.addAll(c.getAddedSubList());
                    }
                }
            }
        });
    }
}
