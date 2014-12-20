package mapzoomsample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;

/**
 * JavaFx with Google map simple sample.
 * 
 * @author tomo
 */
public class MapZoomSample extends Application{
    private Slider slider;
    
    @Override
    public void start(Stage stage) throws Exception {
        VBox root = new VBox(10);
        // Zoom Label
        HBox hbox1 = new HBox(10);
        hbox1.setPadding(new Insets(15,12,0,12));
        Label spinnerLabel = new Label("Zoom Label:");
        spinnerLabel.setFont(new Font("Arial", 20));
        TextField zoomText = new TextField(); // TODO spinner
        Button button = new Button("set zoom");
        hbox1.getChildren().addAll(spinnerLabel, zoomText, button);
        // map
        HBox hbox2 = new HBox(10);
        hbox2.setPadding(new Insets(15,12,15,12));
        WebView webView = new WebView();
        WebEngine engine = webView.getEngine();
        String html = getClass().getResource("sample.html").toExternalForm();
        engine.load(html);
        hbox2.getChildren().addAll(webView);
        // zoom slider
        HBox hbox3 = new HBox(10);
        hbox3.setPadding(new Insets(0,12,15,12));
        Label sliderLabel = new Label("Zoom Slider:");
        sliderLabel.setFont(new Font("Arial", 20));
        initSlider();
        hbox3.getChildren().addAll(sliderLabel,slider);
        // set zoom action
        button.setOnAction(e -> {
            String zoom = zoomText.getText();
            // invoke JavaScript function from JavaFX
            engine.executeScript("changeZoom(" + zoom + ")"); 
        });
        // JavaScript can call Java method using the name app
        JSObject win = (JSObject) engine.executeScript("window");
        win.setMember("app", new JavaApp());

        root.getChildren().addAll(hbox1, hbox2, hbox3);
        Scene scene = new Scene(root, 500, 470);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }    

    private void initSlider() {
        slider = new Slider(0, 21, 21);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(3);
        slider.setBlockIncrement(3);
        slider.setValue(12);
    }
    
public class JavaApp{
    // set the slider
    public void setZoom(int zoom){
        slider.setValue(zoom);
    }
}
    
}
