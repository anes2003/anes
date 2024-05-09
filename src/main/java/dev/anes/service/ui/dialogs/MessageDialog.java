package dev.anes.service.ui.dialogs;


import dev.anes.App;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class MessageDialog extends StackPane {
    public static final int SUCCESS = 0x1110;
    public static final int DANGER = 0x1111;
    public static final int WARNING = 0x1112;
    public static final int INFO = 0x1113;
    private StackPane container;
    private Label messageLabel;
    private App app;
    private StringProperty message;
    private int theme;

    public  MessageDialog(String message, int theme, App app) {
        this.app = app;
        this.message = new SimpleStringProperty(message);
        this.theme = theme;
        _load_fields();
        _load_components();
        _load_bindings();
        _load_listeners();
    }

    private void _load_fields() {
        container = new StackPane();
        messageLabel = new Label();
    }

    private void _load_components() {
        String alertStyle = "";

        setMaxHeight(Region.USE_PREF_SIZE);

        StackPane.setMargin(this, new Insets(40, 10, 0, 10));
        StackPane.setAlignment(this, Pos.TOP_CENTER);
        container.setPadding(new Insets(10));

        switch (theme) {
            case SUCCESS:
                alertStyle = "alert-success";
                break;
            case DANGER:
                alertStyle = "alert-danger";
                break;
            case WARNING:
                alertStyle = "alert-warning";
                break;
            case INFO:
                alertStyle = "alert-info";
                break;
        }

        container.getStyleClass().addAll("alert", alertStyle);
        container.getChildren().addAll(messageLabel);
        StackPane.setAlignment(messageLabel, Pos.CENTER_RIGHT);
        getChildren().add(container);
    }

    private void _load_bindings() {
        messageLabel.textProperty().bind(message);
    }

    private void _load_listeners() {
        setOnMousePressed(ev -> {
            Platform.runLater(() -> {
                app.getApplicationRoot().getChildren().remove(this);
            });
        });
        // TIMELINE>ADDING COUNTDOWN FUNCTIONALITY IN JAVAFX
        // ?ADDS A TIMELINE TO THE DIALOG FOR AUTO-CLOSE AFTER A SPECIGIC AMOUNT OF
        // ?time
        //   KeyFrame startFrame = new KeyFrame(Duration.seconds(10),
        //         new KeyValue(opacityProperty(), 0));
        KeyFrame endFrame = new KeyFrame(Duration.seconds(15),
                new KeyValue(opacityProperty(), 0));
        KeyFrame autoCloseFrame = new KeyFrame(Duration.seconds(10), actionEvent -> {
            app.getApplicationRoot().getChildren().remove(this);
        });

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().addAll(endFrame,autoCloseFrame);
        timeline.play();
    }

    public void load() {
        app.getApplicationRoot().getChildren().add(this);
        KeyFrame startFrame = new KeyFrame(Duration.millis(0),
                new KeyValue(opacityProperty(), 0));
        KeyFrame endFrame = new KeyFrame(Duration.millis(100),
                new KeyValue(opacityProperty(), 0.9));
        Timeline loadInTimeline = new Timeline();
        loadInTimeline.getKeyFrames().addAll(startFrame, endFrame);
        loadInTimeline.play();
        
    }

}
