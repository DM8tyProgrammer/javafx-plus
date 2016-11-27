package javafx.plus.util;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;

public class StyleClassTogglerOnValue implements ChangeListener<Node> {

        private String styleClass;

        public StyleClassTogglerOnValue(String styleClass) {
         this.styleClass = styleClass;
        }

        @Override
        public void changed(ObservableValue<? extends Node> observable, Node oldValue, Node newValue) {
            if(null != newValue) {
                Classie.add(newValue,this.styleClass);
            }
            if(null != oldValue) {
                Classie.remove(oldValue,this.styleClass);
            }
        }
    }