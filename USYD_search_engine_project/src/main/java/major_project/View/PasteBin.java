package major_project.View;

import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import major_project.Presenter.Presenter;

public class PasteBin {
    BorderPane root = new BorderPane();
    boolean finished = false;
    Stage stage = new Stage();
    String linkLong;
    String linkShort;
    Presenter presenter;

    public PasteBin(Presenter presenter) {

        this.presenter = presenter;
        buildSplash();
    }

    public void buildSplash() {

        VBox shelf = new VBox();


        Button shortPasteBtn = new Button("Create short pastebin link");
        Button longPasteBtn = new Button("Create long pastebin link");
        Label shortTitle = new Label("Pastebin short link:");
        TextField shortLink = new TextField();

        Label longTitle = new Label("Pastebin long link:");
        TextField longLink = new TextField(linkLong);
        Button postBtn = new Button("Post to Reddit");
        TextField redditLink = new TextField();
        postBtn.setOnAction((event) -> {
            presenter.makePost();
            postBtn.setDisable(true);
            redditLink.setText("https://www.reddit.com/user/muscleman1997/posts/");

        });
        

        shortPasteBtn.setOnAction((event) -> {
            shortLink.setText(presenter.pasteShort());
            shortPasteBtn.setDisable(true);
        });

        longPasteBtn.setOnAction((event) -> {
            longLink.setText(presenter.pasteLong());
            longPasteBtn.setDisable(true);
        });
        

        shelf.getChildren().addAll(shortPasteBtn, shortTitle, shortLink, longPasteBtn,longTitle, longLink, postBtn,redditLink);

        root.setCenter(shelf);
        

        

        stage.setTitle("Pastebin Links");
        stage.setScene(new Scene(root, 500, 500));
        stage.show();

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