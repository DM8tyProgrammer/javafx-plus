package javafx.plus.component.buttongroup;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.plus.util.Classie;
import javafx.plus.util.ObservableListUtils;
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

        currentPane = this.loadContainer(buttonGroup.getOrientation());

        // for adding class to first and last button
        this.first = new SimpleObjectProperty<>();
        this.last = new SimpleObjectProperty<>();
        this.first.addListener(new SidesValueChangeListener(FIRST_STYLE_CLASS));
        this.last.addListener(new SidesValueChangeListener(LAST_STYLE_CLASS));
        this.setSidesButton();
        ObservableListUtils.bind(currentPane.getChildren(),buttonGroup.getButtons());

        buttonGroup.getButtons().addListener(new ListChangeListener<ButtonBase>() {
            @Override
            public void onChanged(Change<? extends ButtonBase> c) {
                setSidesButton();
                currentPane.requestLayout();
            }
        });

        buttonGroup.orientationProperty().addListener((observable, oldValue, newValue) -> {
            changeLayout(newValue);

        });

        currentPane.getChildren().addAll(buttonGroup.getButtons());
        super.getChildren().add(currentPane);

    }



    private Pane loadContainer(ButtonGroup.Orientation orientation) {

        try {
            ClassLoader classLoader = ButtonGroupSkin.class.getClassLoader();
            horizontalPane = FXMLLoader.load(classLoader.getResource(HORIZONTAL_FXML));
            verticalPane = FXMLLoader.load(classLoader.getResource(VERTICAL_FXML));

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


        return orientation == ButtonGroup.Orientation.HORIZONTAL? horizontalPane : verticalPane;

    }

    private void setSidesButton() {
        if(null!= buttonGroup.getButtons() && !buttonGroup.getButtons().isEmpty()) {
            first.setValue(buttonGroup.getButtons().get(0));
            last.setValue((buttonGroup.getButtons().get(buttonGroup.getButtons().size() - 1)));
        }
    }

    public void changeLayout(ButtonGroup.Orientation orientation) {

            Pane targetPane = orientation == ButtonGroup.Orientation.VERTICAL ? verticalPane : horizontalPane;
            currentPane.getChildren().clear();
            targetPane.getChildren().clear();
            targetPane.getChildren().addAll(buttonGroup.getButtons());

            super.getChildren().clear();
            super.getChildren().addAll(targetPane);
            currentPane = targetPane;

            ObservableListUtils.bind(currentPane.getChildren(),buttonGroup.getButtons());
    }



    private static class SidesValueChangeListener implements ChangeListener<ButtonBase> {

        private String styleClass;

        SidesValueChangeListener(String styleClass) {
         this.styleClass = styleClass;
        }

        @Override
        public void changed(ObservableValue<? extends ButtonBase> observable, ButtonBase oldValue, ButtonBase newValue) {
            if(null != newValue) {
                Classie.add(newValue,this.styleClass);
            }
            if(null != oldValue) {
                Classie.remove(oldValue,this.styleClass);
            }
        }
    }
}
