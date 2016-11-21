package javafx.plus.component.breadcrumb;


import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * @author theBeacon
 */
public class BreadCrumb extends HBox {

    public static final String BREADCRUMB_STYLE_CLASS = "breadcrumb";
    public static final String SEPARATOR_STYLE_CLASS = "separator";

    public static final LabelSeparatorFactory LABEL_SEPARATOR_FACTORY = new LabelSeparatorFactory() {
        private String separator = "/";
        @Override
        public String getText() {
            return separator;
        }

        @Override
        public void setText(String text) {
            this.separator = text;
        }
    };


    public static final ImageSeparatorFactory IMAGE_SEPARATOR_FACTORY = new ImageSeparatorFactory() {

        private Image image;
        @Override
        public Image getImage() {
            return image;
        }

        @Override
        public void setImage(Image image) {
            this.image = image;
        }
    };


    private ObjectProperty<SeparatorFactory> separatorFactory;

    private SimpleObjectProperty<Button> currentButton;


    public BreadCrumb() {
        this.separatorFactory = new SimpleObjectProperty<>();
        this.currentButton = new SimpleObjectProperty<>(null);

        this.getStyleClass().add(BREADCRUMB_STYLE_CLASS);
        this.getStylesheets().add("component/breadcrumb/css/breadcrumb.css");

        this.prefHeight(50);
        this.prefWidth(500);

        this.setWidth(500);
        this.setHeight(50);

        this.setSeparator(new Image(this.getClass().getClassLoader().getResourceAsStream("component/breadcrumb/image/arrow.png")));


        this.currentButton.addListener((observable, oldValue, newValue) -> {

            //enable earliar button
            if(null != oldValue) {
                oldValue.setDisable(false);
            }

            //disable current button
            if(null != newValue){
                newValue.setDisable(true);
            }
        });


    }


    public void addButton(final Button button) {
        if(null == button) {
            return;
        }


        this.currentButton.set(button);

        button.addEventHandler(ActionEvent.ACTION, event -> {
            int i = super.getChildren().indexOf(button);
            if(null != separatorFactory) {
                super.getChildren().remove(i + 2, super.getChildren().size());
            } else {
                super.getChildren().remove(i + 1, super.getChildren().size());
            }
            this.currentButton.set(button);
        });


        if(null != separatorFactory) {
            super.getChildren().addAll(button,this.separatorFactory.get().produce());
        }else {
            super.getChildren().addAll(button);
        }

    }


    public void addButton(Button ... buttons) {
        for(Button b : buttons) {
            this.addButton(b);
        }
    }

    public void setButtons(Button ... buttons) {
        for(Button b : buttons) {
            this.addButton(b);
        }
    }

    public void setSeparatorFactory(SeparatorFactory separatorFactory) {
        this.separatorFactory.set(() -> {
            Node node = separatorFactory.produce();
            node.getStyleClass().add(SEPARATOR_STYLE_CLASS);
            return node;
        });
    }



    public void setSeparator(Image image) {
        separatorFactory.set(IMAGE_SEPARATOR_FACTORY);
        IMAGE_SEPARATOR_FACTORY.setImage(image);
    }

    public void setSeparator(String text) {
        separatorFactory.set(LABEL_SEPARATOR_FACTORY);
        LABEL_SEPARATOR_FACTORY.setText(text);
    }

    @FunctionalInterface
    public static interface SeparatorFactory {
        Node produce();
    }

    public static interface ImageSeparatorFactory extends SeparatorFactory{
        default Node produce() {
            Node node = new ImageView(this.getImage());
            node.getStyleClass().add(SEPARATOR_STYLE_CLASS);
            return node;
        }
        Image getImage();
        void setImage(Image image);
    }


    public static interface LabelSeparatorFactory extends SeparatorFactory {

        default Node produce() {
            Node node = new Label(this.getText());
            node.getStyleClass().add(SEPARATOR_STYLE_CLASS);
            return node;
        }

        String getText();
        void setText(String text);

    }

}
