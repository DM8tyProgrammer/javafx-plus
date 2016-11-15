package javafx.plus.demo.breadcrumb;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author theBeacon
 */
public class BreadCrumbDemo extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setMaximized(true);

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(BreadCrumbDemo.class.getClassLoader().getResource("demo/breadcrumb/breadcrumb-demo.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(BreadCrumbDemo.class);
    }
}
