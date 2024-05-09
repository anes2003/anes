package dev.anes.service.ui;

import java.io.IOException;


import dev.anes.App;
import dev.anes.core.model.Debtor;
import dev.anes.layout.Modal.ownerAccount.login.LoginController;
import dev.anes.layout.Modal.ownerAccount.register.RegisterController;
import dev.anes.layout.TrashButusefull.AddDebtorController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;

import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public class Modal {
    private static FXMLLoader _load(App app, String uri) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(App.class.getResource("layout/" + uri + ".fxml"));
        Node pane = loader.load();

        StackPane modal = new StackPane(pane);
      
      
        app.getstackPaneRoot().getChildren().add(modal);
      

      

       

        return loader;
    }

    public static void close(App app){
        close(app, 0);
    }

    public static void close(App app, int remove) {
        // executes command in another thread separate from the main
        // javafx thread
        Platform.runLater(() -> {
            int lastElementIndex = app.getstackPaneRoot().getChildren().size() - (remove+1);
            app.getstackPaneRoot().getChildren().remove(lastElementIndex);
        });
    }

    public static void load_Login(App app) throws IOException {
        FXMLLoader loader = _load(app, "Modal/ownerAccount/Login/login");

        LoginController controller = loader.getController();
        controller.load(app);
    }
    public static void load_Register(App app) throws IOException {
        FXMLLoader loader = _load(app, "Modal/ownerAccount/register/register");

        RegisterController controller = loader.getController();
        controller.load(app);
  
    }

   
    public static void load_Add(App app,Debtor debtor)throws IOException{
        FXMLLoader loader =_load(app ,"Deptor/ADDDebtor");

        AddDebtorController controller=loader.getController();
        controller.load(app,debtor);
    }
   
    
 
  

  
   

    
}
