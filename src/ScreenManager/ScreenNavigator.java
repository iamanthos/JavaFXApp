package ScreenManager;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollBar;
import javafx.stage.Stage;

public class ScreenNavigator extends Application {
    
    public static String loginScreenId = "loginScreen";
    public static String loginScreenFile = "/login/Login.fxml";
    public static String dashboardScreenId = "dashboardScreen";
    public static String dashboardScreeFile = "/dashboard/Dashboard.fxml";
    public static String viewPaneScreenId = "viewPaneScreen";
    public static String viewPaneScreeFile = "/ViewPane/ViewPane.fxml";
    public static String newPaneScreenId = "newPaneScreen";
    public static String newPaneScreeFile = "/NewPane/newPane.fxml";
    public static String editPaneScreenId = "editPaneScreen";
    public static String editPaneScreeFile = "/EditPane/EditPane.fxml";
    public static String forgotPasswordId = "forgotPasswordScreen";
    public static String forgotPasswordFile = "/ForgotPassword/forgotPassword.fxml";
    @Override
    public void start(Stage primaryStage) throws Exception {
        ScreenController mainContainer = new ScreenController();
        mainContainer.loadScreen(ScreenNavigator.loginScreenId, ScreenNavigator.loginScreenFile);
        mainContainer.setScreen(ScreenNavigator.loginScreenId);
        
        //For scroll bar uncomment below
//        ScrollBar s = new ScrollBar();
//        s.setPrefWidth(1366);
//        s.setTranslateY(700);
//        s.valueProperty().addListener(new ChangeListener<Number>(){
//            public void changed(ObservableValue<? extends Number> cv,
//                    Number old_val, Number new_val){
//                mainContainer.setLayoutX(-new_val.doubleValue());
//            }
//        });
        
        Group root = new Group();
        //For scroll bar comment below
        root.getChildren().addAll(mainContainer);  
        //For scroll bar uncomment below
//        root.getChildren().addAll(mainContainer, s);
        Scene scene = new Scene(root, 1366, 768);
        
        
        primaryStage.setMaximized(true);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
     public static void main(String[] args) {
        launch(args);
    }
    
}
