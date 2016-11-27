package javafx.plus.component.inputgroup;


import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.plus.component.buttongroup.ButtonGroup;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

/**
 * @author theBeacon
 */
public class InputGroup extends Control {

    private ObjectProperty<ObservableList<Node>> leftAddOns;
    private ObjectProperty<ObservableList<Node>> rightAddOns;
    private TextField textField;


    public InputGroup() {
        this(FXCollections.observableArrayList(),FXCollections.observableArrayList());
    }

    public InputGroup(ObservableList<Node> leftAddOns, ObservableList<Node> rightAddOns) {
        this.leftAddOns = new SimpleObjectProperty<>(leftAddOns);
        this.rightAddOns = new SimpleObjectProperty<>(rightAddOns);
        textField = new TextField();
    }


    public void addLeftAddOn(Node node) {
        if(null == leftAddOns.get()){
            leftAddOns.set(FXCollections.observableArrayList());
        }

        sanitize(node);
        leftAddOns.get().add(node);
    }
    public void addRightAddOn(Node node) {
        if(null == rightAddOns.get()) {
         rightAddOns.set(FXCollections.observableArrayList());
        }
        sanitize(node);
        rightAddOns.get().add(node);
    }

    public ObservableList<Node> getLeftAddOns() {
        return leftAddOns.get();
    }

    public ObservableList<Node> getRightAddOns() {
        return rightAddOns.get();
    }

    public TextField getTextField() {
        return textField;
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new InputGroupSkin(this);
    }

    protected  void sanitize(Node node) {
        if(node instanceof ImageView) {
            ImageView iv = (ImageView) node;
            iv.setPreserveRatio(true);
            iv.setFitHeight(26);
        } else if (node instanceof ButtonGroup) {
            ButtonGroup bg = (ButtonGroup) node;
            bg.setOrientation(ButtonGroup.Orientation.HORIZONTAL);
        }

    }

}
