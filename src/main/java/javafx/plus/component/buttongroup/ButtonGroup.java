package javafx.plus.component.buttongroup;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.plus.util.Classie;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;


/**
 * @author theBeacon
 */
public class ButtonGroup extends Control {

    public enum Orientation  {HORIZONTAL, VERTICAL};

    private ObservableList<ButtonBase> buttons;

    private ObjectProperty<Orientation> orientation;
    public ButtonGroup() {
        this(FXCollections.observableArrayList());
    }

    public ButtonGroup(ObservableList<ButtonBase> buttons) {
        this.buttons = buttons;
        orientation = new SimpleObjectProperty<>(Orientation.HORIZONTAL);
    }

    public void addButton(ButtonBase button) {
        buttons.add(button);

    }


    public void addButtons(ButtonBase ... buttons) {
        for(ButtonBase button : buttons){
            this.addButton(button);
        }
    }

    public ObservableList<ButtonBase> getButtons() {
        return this.buttons;
    }

    public Orientation getOrientation() {
        return orientation.get();
    }
    public ObjectProperty<Orientation> orientationProperty(){
        return  orientation;
    };


    public void setOrientation(Orientation orientation) {
        this.orientation.setValue(orientation);
    }


    @Override
    protected Skin<?> createDefaultSkin() {
        return new ButtonGroupSkin(this);
    }
}
