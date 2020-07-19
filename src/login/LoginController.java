package login;

import Models.User;
import ScreenManager.ControlledScreen;
import ScreenManager.ScreenController;
import ScreenManager.ScreenNavigator;
import dashboard.DashboardController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import database.Database;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;

public class LoginController implements Initializable, ControlledScreen  {

    @FXML
    public TextField txtLogin;
    
    @FXML
    public TextField txtPassword;
    
    @FXML
    public TextField txtUser;
    
    @FXML
    public Label lblStatus;
    
    @FXML
    public Label lblSign;
    
    @FXML
    public Label lblForgot;
    
    @FXML
    public Button btnLogin;
    
    private Database database = new Database();
    
    public User user = new User();

    ScreenController myController;

    public void setScreenParent(ScreenController screenParent){
        myController = screenParent;
    }
    
    @FXML
    private void goToDashboard(){
//       myController.loadScreen(ScreenNavigator.dashboardScreenId, ScreenNavigator.dashboardScreeFile, user);
        String screenRef = "Dashboard";
        myController.loadScreen(ScreenNavigator.dashboardScreenId, ScreenNavigator.dashboardScreeFile, user, screenRef, null);
        myController.setScreen(ScreenNavigator.dashboardScreenId);
    }
    
    public void Login(ActionEvent event) throws ClassNotFoundException, SQLException
    {
        if(txtLogin.getText() != null && !txtLogin.getText().equals(""))
        {
            boolean isUser = database.CheckUser(txtLogin.getText());
            setRole(txtLogin.getText());
            
            if(isUser)
            {
                user.setUser(txtLogin.getText());
                if(CheckPassword(txtPassword.getText()))
                {
                    goToDashboard();
                }
                else
                {
                    lblStatus.setText("Login Failed");
                }
                
            }
            
            else
            {
                lblStatus.setText("Login Failed");
            }
        }
        else
            {
                lblStatus.setText("Login Failed");
            }
    }
    
    private boolean CheckPassword(String password)
    {
        boolean isAuthenticated = database.ValidateKey(password);
        return isAuthenticated;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @Override
    public void setInitData(User user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void setRole(String userName)
    {
        HashMap<String, String> userRoles = database.SelectUser();
        
        userRoles.entrySet().forEach((mapElement) -> {
            String role = (String) mapElement.getValue();
            if (userName.equals(mapElement.getKey())) {
                user.setRole(role);
            }
        });
    }

    @Override
    public void setTreeView(User user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setInitialData(User user, String screen, Object obj) {
        txtUser.setVisible(false);
        txtLogin.setText("");
        txtPassword.setText("");
    }

    public void ForgotPassword(MouseEvent event) {
        String screenRef = "ForgotPassword";
        myController.loadScreen(ScreenNavigator.forgotPasswordId, ScreenNavigator.forgotPasswordFile);
        myController.setScreen(ScreenNavigator.forgotPasswordId);
    }
    
}
