package dev.anes.layout;

import dev.anes.App;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;

public class RootController {
            @FXML
    private StackPane root;

    private App app;

    public void load(App app) {
        this.app = app;
        _load_fields();
        _load_bindings();
        _load_listeners();
    }

    private void _load_fields() {
        app.setstackPaneRoot(root);

        
    }

    private void _load_bindings() {
    }

    private void _load_listeners() {
    }
}
