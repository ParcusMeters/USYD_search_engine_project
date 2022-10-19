package major_project.View;




import javafx.scene.Scene;

import javafx.scene.control.ProgressIndicator;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.layout.BorderPane;

import javafx.stage.Stage;


public class SplashWindow {
    BorderPane root = new BorderPane();
    boolean finished = false;
    Stage stage = new Stage();

    public SplashWindow(){
        buildSplash();
    }

    public void buildSplash(){

    
        Image splash = new Image("logo.png");
        ImageView iView = new ImageView(splash);
        root.setCenter(iView);

        ProgressIndicator loading = new ProgressIndicator();

        root.setTop(loading);
        stage.setTitle("Splash");
        stage.setScene(new Scene(root, 500, 500));
        stage.show();
    
       
       
        
        
    }

    public void closeSplash(){
        stage.close();
    }

    
    /** 
     * @return BorderPane
     */
    public BorderPane getRoot(){
        return this.root;
    }

}
