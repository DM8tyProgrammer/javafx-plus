package javafx.plus.demo.breadcrumb;


import javafx.fxml.FXML;
import javafx.plus.component.breadcrumb.BreadCrumb;
import javafx.scene.control.Button;

/**
 * Created by Puneet on 31/10/16.
 */
public class BreadCrumbDemoController {

    @FXML
    private BreadCrumb breadCrumb;


    @FXML
    public void initialize() {
        breadCrumb.addButton(new Button("A"),new Button("B"),new Button("C"));
    }


}
