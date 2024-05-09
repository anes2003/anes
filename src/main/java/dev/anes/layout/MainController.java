package dev.anes.layout;

import java.io.IOException;

import dev.anes.App;
import dev.anes.service.ui.Content;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class MainController {
    @FXML
    private BorderPane root;
    @FXML
    private ImageView imageView;

    private Image image;
    private App app;

    public void load(App app) {
        this.app = app;
        _load_fields();
        _load_bindings();
        _load_listeners();
    }

    private void _load_fields() {
        app.setApplicationRoot(root);

        
        String FAVICON = App.class.getResource("assets/img/FAVICON.png").toExternalForm();
        image = new Image(FAVICON);
        imageView.setImage(image);
        try {
            Content.load_debtor(app);
        } catch (IOException e) {

            e.printStackTrace();
        }

    }

    private void _load_bindings() {
    }

    private void _load_listeners() {

    }

   

    public String toString(int Integer) {
        return String.valueOf(Integer);
    }

    public int toInteger(String str) {
        return Integer.valueOf(str);
    }
}
