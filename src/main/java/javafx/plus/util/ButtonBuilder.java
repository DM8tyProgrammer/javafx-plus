package javafx.plus.util;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

/**
 * @author theBeacon
 */
public class ButtonBuilder {

    private Button button;
    private ButtonBuilder() {
        button = new Button();
    }

    public static ButtonBuilder newBuilder() {
        return new ButtonBuilder();

    }

    public ButtonBuilder on(EventHandler<ActionEvent> handler) {
        button.addEventHandler(ActionEvent.ACTION,handler);
        return this;
    }

    public ButtonBuilder label(String label) {
        button.setText(label);
        return this;
    }

    public Button build() {
        return button;
    }
}
