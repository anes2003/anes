package dev.anes.service.ui;

import java.io.IOException;

import dev.anes.App;
import dev.anes.core.model.Debtor;
import dev.anes.layout.MainController;
import dev.anes.layout.RootController;
import dev.anes.layout.Center.Deptor.DeptorController;
import dev.anes.layout.Center.Deptor.HistoryController;
import dev.anes.layout.Center.Deptor.InfoController;
import dev.anes.layout.Center.Deptor.LoanController;
import dev.anes.layout.Center.Deptor.LoanInfoHistoryController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Content {
    public static final int Max_RESOLUTION_Y = 708;
    public static final int Min_RESOLUTION_X = 1280;
    // Favicon
    private static final String FAVICON = App.class.getResource("assets/img/FAVICON.png").toExternalForm();
    // Css Stylesheets->
    public static String COLOR = "migraine.css";

    public static String CSS_THEME = App.class.getResource("assets/css/" + COLOR).toExternalForm();

    // Masser.css
    public static FXMLLoader load(String uri) {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(App.class.getResource("layout/" + uri + ".fxml"));
        return loader;
    }

    // APPLICATION STAGE->
    public static void intialize_application(App app) throws IOException {
        Stage applicationStage = app.getApplicationStage();
        applicationStage.setTitle("Lending Management App");
        applicationStage.setMinHeight(Max_RESOLUTION_Y);// 708
        applicationStage.setMinWidth(Min_RESOLUTION_X);// 1280
        // applicationStage.setResizable(false);
      
        // Img-->
        applicationStage.getIcons()
                .add(new Image(FAVICON));

    }

    // CUSTOM LOADERS -->
 

    public static void load_Loan(App app) throws IOException {
        FXMLLoader loader = load("MAIN");
        Parent container = loader.load();
        app.getstackPaneRoot().getChildren().add(container);
       
        MainController controller = loader.getController();
        controller.load(app);
    }
    public static void load_root(App app) throws IOException {
        intialize_application(app);
        FXMLLoader loader = load("ROOT");
        Parent container = loader.load();
        Scene scene = new Scene(container);
        scene.getStylesheets().addAll(CSS_THEME);
        app.getApplicationStage().setScene(scene);
        app.getApplicationStage().show();
        RootController controller = loader.getController();

        controller.load(app);
    }
  

   

    public static void load_debtor(App app) throws IOException {
        FXMLLoader loader = load("Center/Deptor/Deptor");
        Parent container = loader.load();
        
        app.getApplicationRoot().setCenter(container);

        DeptorController controller = loader.getController();

        controller.load(app);
    }
    public static void load_loaninfoloanhistory(App app,Debtor debtor) throws IOException{
        FXMLLoader loader = load("Center/Deptor/LOANINFOLOANHISTORY");

        Parent container = loader.load();
        
        app.getApplicationRoot().setCenter(container);

        LoanInfoHistoryController controller = loader.getController();

        controller.load(app,debtor);
    }
    public static void load_loan(App app,Debtor debtor) throws IOException{
        FXMLLoader loader = load("Center/Deptor/LOAN");

        Parent container = loader.load();
       
        app.getloaninfohistoryRoot().getChildren().setAll(container);

        LoanController controller = loader.getController();

        controller.load(app,debtor);
    }
    public static void load_info(App app,Debtor debtor) throws IOException{
        FXMLLoader loader = load("Center/Deptor/INFO");

        Parent container = loader.load();
      
        app.getloaninfohistoryRoot().getChildren().setAll(container);

        InfoController controller = loader.getController();

        controller.load(app,debtor);
    }
    public static void load_history(App app,Debtor debtor) throws IOException{
        FXMLLoader loader = load("Center/Deptor/HISTORY");

        Parent container = loader.load();
       
        app.getloaninfohistoryRoot().getChildren().setAll(container);

        HistoryController controller = loader.getController();

        controller.load(app,debtor);
    }
    


}
