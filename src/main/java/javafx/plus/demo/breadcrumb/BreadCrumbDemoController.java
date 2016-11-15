package javafx.plus.demo.breadcrumb;


import javafx.fxml.FXML;
import javafx.plus.component.breadcrumb.BreadCrumb;
import javafx.scene.control.Button;

/**
 * @author theBeacon
 */
public class BreadCrumbDemoController {

    @FXML
    private BreadCrumb breadCrumb;


    @FXML
    public void initialize() {
        breadCrumb.addButton(new Button("A"),new Button("B"),new Button("C"));
    }


}
