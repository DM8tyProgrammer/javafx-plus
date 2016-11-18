package javafx.plus.demo.buttongroup;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.plus.component.buttongroup.ButtonGroup;
import javafx.plus.demo.breadcrumb.BreadCrumbDemo;
import javafx.plus.util.ButtonBuilder;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * @author theBeacon
 */
public class ButtonGroupDemo extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {


        ButtonGroup bg = new ButtonGroup();

        bg.addButtons(new Button("AAAAA"),new Button("BBB"),new Button("C"));


        AnchorPane pane = new AnchorPane();
        AnchorPane.setTopAnchor(bg,0.0);
        AnchorPane.setLeftAnchor(bg,0.0);


        Button changeLayoutButton = ButtonBuilder.newBuilder().label("Change Layout").on(event -> {
            if(bg.getOrientation() == ButtonGroup.Orientation.HORIZONTAL) {
                bg.setOrientation(ButtonGroup.Orientation.VERTICAL);
            } else {
                bg.setOrientation(ButtonGroup.Orientation.HORIZONTAL);
            }
        }).build();

        AnchorPane.setLeftAnchor(changeLayoutButton,250.0);
        AnchorPane.setTopAnchor(changeLayoutButton,250.0);

        pane.setPadding(new Insets(10));


        pane.getChildren().addAll(bg,changeLayoutButton);
        Scene scene = new Scene(pane);

        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(ButtonGroupDemo.class);
    }

}
