package util.test;

import javafx.application.Application;
import javafx.plus.util.Classie;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import mockit.Deencapsulation;
import mockit.Expectations;
import mockit.Mock;
import mockit.MockUp;
import mockit.integration.junit4.JMockit;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static mockit.Deencapsulation.invoke;
import static org.hamcrest.Matchers.*;

/**
 * Created by theBeacon
 */
@RunWith(JMockit.class)
public class ClassieTest {

    @BeforeClass
    public static void before() {

        new MockUp<Application>() {
            @Mock
            public  String getUserAgentStylesheet() {
                return "";
            }
        };
    }

    @Test
    public void testHas() {

        Node node = new Button();
        node.getStyleClass().addAll("test","magic","unicorn");


        //positive testing

        Assert.assertThat(Classie.has(node,"test"),is(true));

        Assert.assertThat(Classie.has(node,"test","magic"),is(true));
        Assert.assertThat(Classie.has(node,"magic","test"),is(true));

        Assert.assertThat(Classie.has(node,"test","unicorn"),is(true));

        Assert.assertThat(Classie.has(node,"magic","unicorn"),is(true));
        Assert.assertThat(Classie.has(node,"magic","unicorn","test"),is(true));

        //negative testing
        Assert.assertThat(Classie.has(node,"tail"),is(false));
        Assert.assertThat(Classie.has(node,"crocs","magic"),is(false));
        Assert.assertThat(Classie.has(node,"maigc","crocs"),is(false));


        //?
        Assert.assertThat(Classie.has(node),is(true));
    }

    @Test
    public void testAdd() {
        Node node = new Button();

        Classie.add(node,"mickey");
        Assert.assertThat(node.getStyleClass(),hasItems("mickey"));

        Classie.add(node,"minnie","pluto");
        Assert.assertThat(node.getStyleClass(),hasItems("pluto","minnie"));
        Assert.assertThat(node.getStyleClass(),hasItems("mickey","pluto","minnie"));
    }

    @Test
    public void testRemove(){
        Node node = new Button();
        node.getStyleClass().addAll("iron-man","captain-america","thor","spider-man");

        Classie.remove(node,"spider-man");
        Assert.assertThat(node.getStyleClass().contains("spider-man"),is(false));
        Assert.assertThat(node.getStyleClass(),hasItems("iron-man","captain-america","thor"));


        Classie.remove(node,"thor","captain-america");
        Assert.assertThat(node.getStyleClass().contains("thor"),is(false));
        Assert.assertThat(node.getStyleClass().contains("caption-america"),is(false));
        Assert.assertThat(node.getStyleClass().contains("iron-man"),is(true));

    }


    @Test
    public void testToggle() {

        Node node = new Button();
        node.getStyleClass().addAll("iron-man","captain-america","thor","spider-man");


        Classie.toggle(node,"bruce");
        Assert.assertThat(node.getStyleClass().contains("bruce"),is(true));

        Classie.toggle(node,"bruce");
        Assert.assertThat(node.getStyleClass().contains("bruce"),is(false));
    }


    @Test
    public void testRemoveByPattern() {
        Node node = new Button();
        node.getStyleClass().addAll("iron-man","super-man","spider-man","he-man","hulk","thor");

        //remove all men
        Classie.removeByPattern(node,".*-man");
        Assert.assertThat(node.getStyleClass(),hasItems("hulk","thor"));
        Assert.assertThat(node.getStyleClass(),not(contains("iron-man","spider-man","super-man","he-man")));
    }

}
