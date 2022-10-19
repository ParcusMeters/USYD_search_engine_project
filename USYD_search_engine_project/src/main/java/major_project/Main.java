package major_project;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.stage.Stage;
import javafx.util.Duration;
import major_project.Model.Engine;
import major_project.Model.HTTP.Input.InputOffline;
import major_project.Model.HTTP.Input.InputOnline;
import major_project.Model.HTTP.Input.InputProtocol;
import major_project.Model.HTTP.Output.OutputOffline;
import major_project.Model.HTTP.Output.OutputOnline;
import major_project.Model.HTTP.Output.OutputProtocol;
import major_project.Model.HTTP.Output.Reddit.RedditOutput;
import major_project.Model.SQL.MainSQL;
import major_project.Presenter.Presenter;
import major_project.View.GameWindow;
import major_project.View.SplashWindow;

public class Main extends Application {

    
    /** 
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) {

        InputProtocol input = new InputOnline();
        OutputProtocol output = new OutputOnline();
        HostServices hs = getHostServices();

        MainSQL.createDB();
        MainSQL.setupDB();

        System.out.println(this.getParameters().getRaw());
        System.out.println(this.getParameters().getRaw().size());

        if (this.getParameters().getRaw().get(0).equals("offline")) {
            input = new InputOffline();
            if (this.getParameters().getRaw().get(1).equals("offline")) {
                output = new OutputOffline();
            } else {
                output = new OutputOnline();
            }

        }

        if (this.getParameters().getRaw().get(0).equals("online")) {
            output = new OutputOnline();
            if (this.getParameters().getRaw().get(1).equals("offline")) {
                output = new OutputOffline();
            } else {
                output = new OutputOnline();
            }
        }

        Engine model = new Engine(input, output);

        SplashWindow splash = new SplashWindow();

        PauseTransition delay = new PauseTransition(Duration.seconds(15));
        delay.setOnFinished(event -> {
            splash.closeSplash();
            GameWindow view = new GameWindow(model, hs);
            Presenter presenter = new Presenter(model, view);
            view.addPresenter(presenter);
            primaryStage.setScene(view.getScene());
            primaryStage.setTitle("Guardian API");
            primaryStage.show();

            System.out.println("End of splash");
        });
        delay.play();

    }

    
    /** 
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
