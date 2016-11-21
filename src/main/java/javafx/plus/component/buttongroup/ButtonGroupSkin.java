package javafx.plus.component.buttongroup;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.plus.util.Classie;
import javafx.scene.Node;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.List;

/**
 * @author theBeacon
 */
public class ButtonGroupSkin extends SkinBase<ButtonGroup> {

    private static final String HORIZONTAL_FXML = "component/buttongroup/fxml/horizontal-button-group.fxml";
    private static final String VERTICAL_FXML = "component/buttongroup/fxml/vertical-button-group.fxml";

    private static final String FIRST_STYLE_CLASS = "first";
    private static final String LAST_STYLE_CLASS = "last";

    private ButtonGroup buttonGroup;
    private Pane horizontalPane;
    private Pane verticalPane;
    private Pane currentPane;


    private ObjectProperty<ButtonBase> first;

    private ObjectProperty<ButtonBase> last;

    public ButtonGroupSkin(ButtonGroup buttonGroup) {

        super(buttonGroup);
        this.buttonGroup = buttonGroup;

        loadStructure();


        if(buttonGroup.getOrientation() == ButtonGroup.Orientation.HORIZONTAL) {
            currentPane = horizontalPane;
        } else {
            currentPane = verticalPane;
        }



        // for adding class to first and last button
        this.first = new SimpleObjectProperty<>();
        this.last = new SimpleObjectProperty<>();

        first.addListener((o,oldValue,newValue)->{
            if(null != newValue) {
                Classie.add(newValue,FIRST_STYLE_CLASS);
            }
            if(null != oldValue) {
                Classie.remove(oldValue,FIRST_STYLE_CLASS);
            }
        });

        last.addListener((o,oldValue,newValue)->{
            if(null != newValue) {
                Classie.add(newValue,LAST_STYLE_CLASS);
            }
            if(null != oldValue) {
                Classie.remove(oldValue,LAST_STYLE_CLASS);
            }
        });


        if(null!= buttonGroup.getButtons() && !buttonGroup.getButtons().isEmpty()) {
            first.setValue(buttonGroup.getButtons().get(0));
            last.setValue((buttonGroup.getButtons().get(buttonGroup.getButtons().size() - 1)));

            buttonGroup.getButtons().addListener(new ListChangeListener<ButtonBase>() {
                @Override
                public void onChanged(Change<? extends ButtonBase> c) {
                    if(!buttonGroup.getButtons().isEmpty()) {
                        first.setValue(buttonGroup.getButtons().get(0));
                        last.setValue((buttonGroup.getButtons().get(buttonGroup.getButtons().size() - 1)));
                    }

                    while (c.next()) {
                        if(c.wasAdded()) {
                            currentPane.getChildren().addAll(c.getAddedSubList());
                        }
                        if(c.wasRemoved()) {
                            currentPane.getChildren().removeAll(c.getRemoved());
                        }
                        currentPane.requestLayout();
                    }

                }


            });
        }


        buttonGroup.orientationProperty().addListener((observable, oldValue, newValue) -> {
            changeLayout(newValue);
        });

        currentPane.getChildren().addAll(buttonGroup.getButtons());
        super.getChildren().add(currentPane);

    }



    private void loadStructure() {

        FXMLLoader horizontalLoader = new FXMLLoader();
        horizontalLoader.setLocation(ButtonGroupSkin.class.getClassLoader().getResource(HORIZONTAL_FXML));

        FXMLLoader verticalLoader = new FXMLLoader();
        verticalLoader.setLocation(ButtonGroupSkin.class.getClassLoader().getResource(VERTICAL_FXML));

        try {
            horizontalPane = horizontalLoader.load();
            verticalPane = verticalLoader.load();

        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }



        // for button to span full width of vertical
        verticalPane.getChildren().addListener(new ListChangeListener<Node>() {
            @Override
            public void onChanged(Change<? extends Node> c) {
                while (c.next()) {
                    if (c.wasAdded()) {
                        List<Node> nodes = verticalPane.getChildren().subList(c.getFrom(), c.getTo());
                        for (Node node : nodes) {
                            if (node instanceof ButtonBase) {
                                ButtonBase bb = (ButtonBase) node;
                                bb.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                            }
                        }
                    }
                }
            }
        });


    }

    public void changeLayout(ButtonGroup.Orientation orientation) {

            Pane targetPane = orientation == ButtonGroup.Orientation.VERTICAL ? verticalPane : horizontalPane;
            currentPane.getChildren().clear();
            targetPane.getChildren().clear();
            targetPane.getChildren().addAll(buttonGroup.getButtons());

            super.getChildren().clear();
            super.getChildren().addAll(targetPane);
            currentPane = targetPane;

    }


}
