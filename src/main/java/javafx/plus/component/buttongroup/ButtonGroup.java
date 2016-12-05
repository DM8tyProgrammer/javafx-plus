package javafx.plus.component.buttongroup;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;


/**
 * @author theBeacon
 */
public class ButtonGroup extends Control {

    private static final String CONTROL_STYLE_CLASS = "button-group";
    public enum Orientation  {HORIZONTAL, VERTICAL};


    private ListProperty<ButtonBase> buttons;
    private ObjectProperty<Orientation> orientation;

    public ButtonGroup() {
        this(FXCollections.observableArrayList());
    }

    public ButtonGroup(ObservableList<ButtonBase> buttons) {
        this.buttons = new SimpleListProperty<>(buttons);
        orientation = new SimpleObjectProperty<>(Orientation.HORIZONTAL);
        this.getStyleClass().add(CONTROL_STYLE_CLASS);
    }

    public void addButton(ButtonBase button) {
        if(null == buttons.get()) {
            buttons.setValue(FXCollections.observableArrayList());
        }

        button.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        buttons.get().add(button);
    }

    public void addButtons(ButtonBase ... buttons) {
        for(ButtonBase button : buttons){
            this.addButton(button);
        }
    }

    public ObservableList<ButtonBase> getButtons() {
        return this.buttons.get();
    }


    //Orientation
    public ObjectProperty<Orientation> orientationProperty(){
        return  orientation;
    }
    public Orientation getOrientation() {
        return orientation.get();
    }
    public void setOrientation(Orientation orientation) {
        this.orientation.setValue(orientation);
    }


    @Override
    protected Skin<?> createDefaultSkin() {
        return new ButtonGroupSkin(this);
    }
}
