package dashboard;

import Models.Overview;
import Models.Permissions;
import Models.Product;
import Models.Stock;
import Models.User;
import ScreenManager.ControlledScreen;
import ScreenManager.ScreenController;
import ScreenManager.ScreenNavigator;
import Util.Util;
import database.Database;
import javafx.scene.image.Image;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;

public class DashboardController implements Initializable, ControlledScreen{


    
    private String currentUser;
    private String currentUserRole;
    private User user;
    private String status = "";
    private String dateUpdate = "";
    private ArrayList<String> availableUsers = new ArrayList<String>();
    private ObservableList<String> statusList = FXCollections.observableArrayList("Not started", "In Progress", "Blocked", "De-scoped", "Completed");
    
    private Database database = new Database();
    private Permissions permission = new Permissions();
    
    ScreenController myController;
    
    private String adminRole = "Admin";
    
    @FXML TableColumn prodId, prodStatus, prodAssignee, prodAssignedTo, prodDate, overId, overStatus, overAssignee, overAssignedTo, overDate, stockId, stockStatus, stockAssignee, stockAssignedTo, stockDate;
    
    @FXML TableView prodNotif, overNotif, stockNotif;
    
    @FXML
    public Label lblWelcome;
    
    @FXML
    public TreeView<String> treeview;
    
    @FXML TextField txtUsername;
    @FXML TextField txtRole;
    @FXML TextField txtKey;
    
    @FXML ComboBox cmboStatus;
    @FXML ComboBox comboUser;
    @FXML TextField txtUpdDate;
    
    
    @FXML Label lblUserError;
    @FXML Label lblAccess;
    
    
    @FXML Button btnAddUser;
    @FXML Button btnInsert;
    @FXML Button delUser;
    
    TreeItem<String> root;
    
    Image icon = new Image(getClass().getResourceAsStream("/Images/File.png"), 20, 20, false, false);
    
    @FXML
    @Override
     public void initialize(URL url, ResourceBundle rb) {
    }    

    @Override
    public void setScreenParent(ScreenController screenParent) {
         myController = screenParent;
    }

    @Override
    public void setInitData(User user) {
        this.currentUser = user.getUser();
        this.currentUserRole = user.getRole();
        this.user = user;
        lblWelcome.setText("Welcome " + this.currentUser);
    }
    
    @FXML
    @Override
    public void setTreeView(User user) {
        String role = user.getRole();
        if(role != null && !role.equals(adminRole))
            {
                availableUsers.remove(this.currentUser);
                root.getChildren().remove("TestUser2");
                treeview.setRoot(root);
            }
    }
    
    public void setViewNotif()
    {
        cmboStatus.setItems(statusList);
        this.status = (String) cmboStatus.valueProperty().getValue();
        this.dateUpdate = txtUpdDate.getText();
        viewProductNotif();
        viewOverviewNotif();
        viewStockNotif();

    }
   
    private void setProductNotifColumnName()
    {
        prodId.setText("ProductId");
        prodStatus.setText("Status");
        prodAssignee.setText("AssignedBy");
        prodAssignedTo.setText("AssignedTo");
        prodDate.setText("DateUpdated");
    }
    
    private void setOverviewNotifColumnName()
    {
        overId.setText("OverviewId");
        overStatus.setText("Status");
        overAssignee.setText("AssignedBy");
        overAssignedTo.setText("AssignedTo");
        overDate.setText("DateUpdated");
    }
    
    private void setStockNotifColumnName()
    {
        stockId.setText("StockId");
        stockStatus.setText("Status");
        stockAssignee.setText("AssignedBy");
        stockAssignedTo.setText("AssignedTo");
        stockDate.setText("DateUpdated");
    }
    
    private void setProductNotifRowValues ()
    {
        prodId.setCellValueFactory(new PropertyValueFactory("productId"));
        prodStatus.setCellValueFactory(new PropertyValueFactory("status"));
        prodAssignee.setCellValueFactory(new PropertyValueFactory("assignedBy"));
        prodAssignedTo.setCellValueFactory(new PropertyValueFactory("assignedTo"));
        prodDate.setCellValueFactory(new PropertyValueFactory("updatedDate"));
    }
    
    private void setOverviewNotifRowValues ()
    {
        overId.setCellValueFactory(new PropertyValueFactory("overviewId"));
        overStatus.setCellValueFactory(new PropertyValueFactory("status"));
        overAssignee.setCellValueFactory(new PropertyValueFactory("assignedBy"));
        overAssignedTo.setCellValueFactory(new PropertyValueFactory("assignedTo"));
        overDate.setCellValueFactory(new PropertyValueFactory("updatedDate"));
    }
    
    private void setStockNotifRowValues ()
    {
        stockId.setCellValueFactory(new PropertyValueFactory("stockId"));
        stockStatus.setCellValueFactory(new PropertyValueFactory("status"));
        stockAssignee.setCellValueFactory(new PropertyValueFactory("assignedBy"));
        stockAssignedTo.setCellValueFactory(new PropertyValueFactory("assignedTo"));
        stockDate.setCellValueFactory(new PropertyValueFactory("updatedDate"));
    }
    
    @FXML
    public void viewProductNotif()
    {
        ObservableList<Product> products = database.GetNotifProducts(this.status, this.dateUpdate, this.currentUser);
        setProductNotifColumnName();
        prodNotif.setItems(products);
        setProductNotifRowValues();
        prodNotif.getColumns().setAll(prodId, prodStatus, prodAssignee, prodAssignedTo, prodDate);
    }
    
    @FXML
    public void viewOverviewNotif()
    {
        ObservableList<Overview> overviews = database.GetNotifOverviews(this.status, this.dateUpdate, this.currentUser);
        setOverviewNotifColumnName();
        overNotif.setItems(overviews);
        setOverviewNotifRowValues();
        overNotif.getColumns().setAll(overId, overStatus, overAssignee, overAssignedTo, overDate);
    }
    
    @FXML
    public void viewStockNotif()
    {
        ObservableList<Stock> stock = database.GetNotifStock(this.status, this.dateUpdate, this.currentUser);
        setStockNotifColumnName();
        stockNotif.setItems(stock);
        setStockNotifRowValues();
        stockNotif.getColumns().setAll(stockId, stockStatus, stockAssignee, stockAssignedTo, stockDate);
    }
    
    
    @FXML
    public void newPoduct(ActionEvent event)
    {
        String refScreen = "Product";
        gotoNew(refScreen);
    }
    
    @FXML
    public void editProduct(ActionEvent event)
    {
        String refScreen = "Product";
        gotoEdit(refScreen);
    }
    
    @FXML
    public void viewProduct(ActionEvent event)
    {
        String refScreen = "Product";
        gotoView(refScreen);
    }
    
    @FXML
    public void newOverview(ActionEvent event)
    {
        String refScreen = "Overview";
        gotoNew(refScreen);
    }
    
    @FXML
    public void editOverview(ActionEvent event)
    {
        String refScreen = "Overview";
        gotoEdit(refScreen);
    }
    
    @FXML
    public void viewOverview(ActionEvent event)
    {
        String refScreen = "Overview";
        gotoView(refScreen);
    }
    
    @FXML
    public void newStock(ActionEvent event)
    {
        
        String refScreen = "Stock";
        gotoNew(refScreen);
    }
    
    @FXML
    public void editStock(ActionEvent event)
    {
        
        String refScreen = "Stock";
        gotoEdit(refScreen);
    }
    
    @FXML
    public void viewStock(ActionEvent event)
    {
        
        String refScreen = "Stock";
        gotoView(refScreen);
    }
    
    @FXML
    public void Traceability (ActionEvent event)
    {
        
        String refScreen = "Traceability";
        gotoView(refScreen);
    }
    
    private void gotoNew(String screen)
    {
        lblAccess.setVisible(false);
        if(this.permission.getNewAccess())
        {
            myController.loadScreen(ScreenNavigator.newPaneScreenId, ScreenNavigator.newPaneScreeFile, this.user, screen, treeview);
            myController.setScreen(ScreenNavigator.newPaneScreenId);
        }
        else
        {
            lblAccess.setVisible(true);
            lblAccess.setText("Access Denied");
        }
    }
    
    private void gotoView(String screen)
    {
        lblAccess.setVisible(false);
        if(this.permission.getViewAccess())
        {
            myController.loadScreen(ScreenNavigator.viewPaneScreenId, ScreenNavigator.viewPaneScreeFile, this.user, screen, treeview);
            myController.setScreen(ScreenNavigator.viewPaneScreenId);
        }
        else
        {
            lblAccess.setVisible(true);
            lblAccess.setText("Access Denied");
        }
       
    }
    
    private void gotoEdit(String screen)
    {
        lblAccess.setVisible(false);
        if(this.permission.getEditAccess())
        {
            myController.loadScreen(ScreenNavigator.editPaneScreenId, ScreenNavigator.editPaneScreeFile, this.user, screen, treeview);
            myController.setScreen(ScreenNavigator.editPaneScreenId);
        }
        else
        {
            lblAccess.setVisible(true);
            lblAccess.setText("Access Denied");
        }
    }

    @Override
    public void setInitialData(User user, String screen, Object obj) {
        this.currentUser = user.getUser();
        this.currentUserRole = user.getRole();
        this.user = user;
        lblWelcome.setText("Welcome " + this.currentUser);
        
          Util util = new Util();
          TreeItem<String> root = util.setTreeView(user);
          treeview.setRoot(root);
          
          this.permission = util.IsUserPermitted(user);
          
        if(!this.permission.getEditAccess() && !this.permission.getNewAccess() && !this.permission.getNewAccess())
        {
            btnAddUser.setVisible(false);
            comboUser.setVisible(false);
            delUser.setVisible(false);
        }
          
          ObservableList<String> userList = database.GetAllUsers();
          
          comboUser.setItems(userList);
          
          setViewNotif();
    }
    
    public void addUser(ActionEvent event)
    {
        lblUserError.setVisible(false);
        btnAddUser.setVisible(false);
        btnInsert.setVisible(true);
        txtUsername.setVisible(true);
        txtRole.setVisible(true);
        txtKey.setVisible(true);
        txtUsername.setText("");
        txtRole.setText("");
        txtKey.setText("");
    }
    
    public void insertUser(ActionEvent event)
    {
        String username = "";
        String role = "";
        String key = "";
        username = txtUsername.getText();
        role = txtRole.getText();
        key = txtKey.getText();
        if(username != null && role != null && key != null)
        {
            if(!username.equals("") && !role.equals("") && !key.equals(""))  
            {
                database.insertUser(username, role, key);
                txtUsername.setVisible(false);
                txtRole.setVisible(false);
                txtKey.setVisible(false);
                btnInsert.setVisible(false);
                btnAddUser.setVisible(true);
                
            }
            else
            {
                System.out.println("User Insert Error");
                lblUserError.setVisible(true);
                txtUsername.setVisible(false);
                txtRole.setVisible(false);
                txtKey.setVisible(false);
                btnInsert.setVisible(false);
                btnAddUser.setVisible(true);
            }
        }
        else
        {
            System.out.println("User Insert Error");
            lblUserError.setVisible(true);
            txtUsername.setVisible(false);
            txtRole.setVisible(false);
            txtKey.setVisible(false);
            btnInsert.setVisible(false);
            btnAddUser.setVisible(true);
        }
    }
    
    public void logout(ActionEvent event)
    {
        myController.loadScreen(ScreenNavigator.loginScreenId, ScreenNavigator.loginScreenFile, null, null, null);
        myController.setScreen(ScreenNavigator.loginScreenId);
    }
    
    public void Filter(ActionEvent event)
    {
        this.status = (String) cmboStatus.valueProperty().getValue();
        this.dateUpdate = txtUpdDate.getText();
        viewProductNotif();
        viewOverviewNotif();
        viewStockNotif();
    }
    
    public void RemoveUser(ActionEvent event)
    {
        String userName = (String) comboUser.valueProperty().getValue();
        if(userName != "" && userName != null)
        {
            database.DeleteUser(userName);
            ObservableList<String> userList = database.GetAllUsers();
            comboUser.setItems(userList);
        }
        else
        {
            System.out.print("Invalid UserName");
        }
    }
}
