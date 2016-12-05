package javafx.plus.component.inputgroup;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.plus.util.ObservableListUtils;
import javafx.plus.util.StyleClassTogglerOnValue;
import javafx.scene.Node;
import javafx.scene.control.SkinBase;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.util.List;
import java.util.ListIterator;

public class InputGroupSkin extends SkinBase<InputGroup> {

    @FXML
    private HBox leftAddOnsPlaceholder;

    @FXML
    private HBox rightAddOnsPlaceholder;

    @FXML
    private HBox textFieldPlaceholder;

    private  InputGroup inputGroup;


    public InputGroupSkin(InputGroup inputGroup) {

        super(inputGroup);
        this.inputGroup = inputGroup;

        FXMLLoader fxml = new FXMLLoader();
        fxml.setLocation(InputGroup.class.getClassLoader().getResource("component/inputgroup/fxml/input-group.fxml"));
        fxml.setController(this);

        try{
            super.getChildren().add(fxml.load());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        this.addToLeft(inputGroup.getLeftAddOns());

        inputGroup.getLeftAddOns().addListener(
                new ListChangeListener<Node>() {
                    @Override
                    public void onChanged(Change<? extends Node> c) {
                        while(c.next()) {
                            if(c.wasAdded()) {
                                addToLeft(c.getAddedSubList());
                            }

                            if(c.wasRemoved()) {
                                leftAddOnsPlaceholder.getChildren().removeAll(c.getRemoved());
                            }

                        }
                    }
                }
        );

        this.addToRight(inputGroup.getRightAddOns());
        ObservableListUtils.bind(rightAddOnsPlaceholder.getChildren(),inputGroup.getRightAddOns());

        textFieldPlaceholder.getChildren().add(inputGroup.getTextField());

    }


    private void addToRight(List<? extends Node> rightAddOns) {
        if(null!=rightAddOns && !rightAddOns.isEmpty()) {
            rightAddOnsPlaceholder.getChildren().addAll(rightAddOns);
        }
    }

    private void addToLeft(List<? extends Node> leftAddOns) {

        //add in reverse way
        if(null!=leftAddOns && !leftAddOns.isEmpty()) {
            ListIterator<Node> iterator = inputGroup.getLeftAddOns().listIterator(leftAddOns.size());
            while (iterator.hasPrevious()) {
                leftAddOnsPlaceholder.getChildren().add(iterator.previous());
            }
        }
    }

}
