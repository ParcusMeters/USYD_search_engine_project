package major_project.View;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class About {
    BorderPane root = new BorderPane();
    boolean finished = false;
    Stage stage = new Stage();
    String linkLong;
    String linkShort;

    public About() {
        buildSplash();
    }

    public void buildSplash() {

        VBox vBox = new VBox();
        Label label = new Label("deez");

        vBox.getChildren().add(label);

        vBox.getChildren().add(new TextField("Guardian API  \nBy Marcus Peters"));

        root.setCenter(vBox);

        stage.setTitle("About");
        stage.setScene(new Scene(root, 500, 500));
        stage.showAndWait();

    }

    public void closeSplash() {
        stage.close();
    }

    
    /** 
     * @return BorderPane
     */
    public BorderPane getRoot() {
        return this.root;
    }
}
