package ForgotPassword;

import Models.User;
import ScreenManager.ControlledScreen;
import ScreenManager.ScreenController;
import ScreenManager.ScreenNavigator;
import database.Database;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;

public class ForgotPasswordController implements Initializable, ControlledScreen {
    
    @FXML
    public TextField txtLogin;
    
    @FXML
    public TextField txtNewPswd;
    
    @FXML
    public TextField txtConfirmPswd;
    
    @FXML
    public Label lblStatus;
    
    ScreenController myController;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @Override
    public void setScreenParent(ScreenController screenParent){
        myController = screenParent;
    }

    @Override
    public void setInitData(User user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setTreeView(User user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setInitialData(User user, String scene, Object obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    

    
    public void ConfirmPasssword(ActionEvent event) throws ClassNotFoundException, SQLException
    {
        String loginId = txtLogin.getText();
        String newPswd = txtNewPswd.getText();
        String confirmPswd = txtConfirmPswd.getText();
        
        if(!loginId.equals("") && !newPswd.equals("") && !confirmPswd.equals(""))
        {
            Database database = new Database();
            boolean isUser = database.CheckUser(loginId);

            if(isUser)
            {
                if(newPswd.equals(confirmPswd))
                {
                    database.updateUser(loginId, newPswd);
                    lblStatus.setText("Password Changed");
                }
                else
                {
                    lblStatus.setText("Password doesn't match!");
                }
            }
            else
            {
                 lblStatus.setText("Non Existing User!");
            }
        }
        else
        {
            lblStatus.setText("Empty Value!");
        }
    }
    
    public void GoToLogin(ActionEvent event) {
        String screenRef = "Login";
        myController.loadScreen(ScreenNavigator.loginScreenId, ScreenNavigator.loginScreenFile);
        myController.setScreen(ScreenNavigator.loginScreenId);
    }
    
}
