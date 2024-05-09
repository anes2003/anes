package dev.anes.service.ui;

import java.io.IOException;

import dev.anes.App;
import dev.anes.core.model.Debtor;
import dev.anes.layout.Modal.Loan.AddLoanController;
import dev.anes.layout.Modal.receipt.PaymentController;
import dev.anes.layout.TrashButusefull.DeleteController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public class Modall {
    private static FXMLLoader _load(App app, String uri) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(App.class.getResource("layout/" + uri + ".fxml"));
        Node pane = loader.load();

        StackPane modal = new StackPane(pane);
        modal.setPadding(new Insets(0, 0, 50, 0));

        app.getstackPaneRoot().getChildren().add(modal);
        modal.setStyle("-fx-background-color: ffffff2d;");

        StackPane.setAlignment(pane, Pos.TOP_CENTER);
        StackPane.setMargin(pane, new Insets(100, 0, 0, 0));

        modal.setOnMouseClicked(ev -> close(app));
        pane.setOnMouseClicked(ev -> ev.consume());

        return loader;
    }

    public static void close(App app) {
        // executes command in another thread separate from the main
        // javafx thread
        Platform.runLater(() -> {
            int lastElementIndex = app.getstackPaneRoot().getChildren().size() - 1;
            app.getstackPaneRoot().getChildren().remove(lastElementIndex);
        });
    }
    public static void load_newloan(App app,Debtor debtor)throws IOException{
        FXMLLoader loader =_load(app ,"Modal/loan/AddLoan");

        AddLoanController controller=loader.getController();
        controller.load(app,debtor);
    }
    public static void load_payment(App app,Debtor debtor)throws IOException{
        FXMLLoader loader =_load(app ,"Modal/receipt/PAYMENT");

        PaymentController controller=loader.getController();
        controller.load(app,debtor);
    }
    public static void load_delete(App app,Debtor debtor)throws IOException{
        FXMLLoader loader =_load(app ,"Deptor/Delete");

        DeleteController controller=loader.getController();
        controller.load(app,debtor);
    }


 
}
