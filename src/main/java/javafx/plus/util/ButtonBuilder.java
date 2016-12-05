package javafx.plus.util;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

/**
 * ButtonBuilder aids to build button faster, also enhance readability to the code.
 *
 * Use it when a button needs to be configured with multiple options.
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

    public ButtonBuilder visible(boolean value) {
        button.setVisible(value);
        return this;
    }


    public ButtonBuilder cancel(boolean value) {
        button.setCancelButton(value);
        return this;
    }

    public ButtonBuilder defualt(boolean value) {
        button.setDefaultButton(value);
        return this;
    }


    public Button build() {
        return button;
    }
}
