package NewPane;

import Models.Overview;
import Models.Product;
import Models.Stock;
import Models.User;
import ScreenManager.ControlledScreen;
import ScreenManager.ScreenController;
import ScreenManager.ScreenNavigator;
import Util.Util;
import database.Database;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class NewPaneController implements Initializable, ControlledScreen {

    ScreenController myController;
    
    @FXML
    public TreeView<String> treeview;
    public Label lblWelcome;
    private String currentScene;
    private Database database = new Database();
    private String currentUser;
    private User user;
    private ObservableList<String> statusList = FXCollections.observableArrayList("Not started", "Pending", "In Progress", "Blocked", "De-scoped", "Completed");
    private ObservableList<String> priorityList = FXCollections.observableArrayList("High", "Medium", "Low");
    
    @FXML Label lblError;
    @FXML Label lbl1;
    @FXML Label lbl2;
    @FXML Label lbl3;
    @FXML Label lbl4;
    @FXML Label lbl5;
    @FXML Label lbl6;
    @FXML Label lbl7;
    @FXML Label lbl8;
    
    @FXML TextField txtbox1;
    @FXML TextField txtbox2;
    @FXML TextField txtbox3;
    @FXML TextField txtbox4;
    @FXML ComboBox<String> txtbox5;
    @FXML TextField txtbox6;
    @FXML TextField txtbox7;
    @FXML ComboBox<String> txtbox8;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }    
    
    @Override
    public void setInitialData(User user, String scene, Object obj) {
        
          this.currentUser = user.getUser();
          this.user = user;
          lblWelcome.setText("Welcome " + this.currentUser);
        
          Util util = new Util();
          TreeItem<String> root = util.setTreeView(user);
          treeview.setRoot(root);
          
          this.currentScene = scene;
          setLabel(scene);
    }

    @Override
    public void setScreenParent(ScreenController screenParent) {
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
    
    public void submit(ActionEvent event) throws ClassNotFoundException
    {
            lblError.setVisible(!lblError.isVisible());
            Object obj = getTextBoxValue();
            if(this.currentScene.equals("Product"))
            {
                Product product = (Product) obj;
                if(!product.getProductId().equals(""))
                {
                    database.saveNewProduct(product, this.currentUser);
                    lblError.setText("Changes Saved!");
                    lblError.setVisible(true);
                }
                else
                {
                    lblError.setVisible(true);
                }
            }
            if(this.currentScene.equals("Overview"))
            {
                Overview overview = (Overview) obj;
                if(!overview.getOverviewId().equals(""))
                {
                    database.saveNewOverview(overview, this.currentUser);
                    lblError.setText("Changes Saved!");
                    lblError.setVisible(true);
                }
                else
                {
                    lblError.setVisible(true);
                }
            }
            if(this.currentScene.equals("Stock"))
            {
                Stock stock = (Stock) obj;
                if(!stock.getStockId().equals(""))
                {
                    database.saveNewStock(stock, this.currentUser);
                    lblError.setText("Changes Saved!");
                    lblError.setVisible(true);
                }
                else
                {
                    lblError.setVisible(true);
                }
            }
            resetTextBoxValue();
            
        }
    
    public void resetTextBoxValue()
    {
        txtbox1.setText("");
        txtbox2.setText("");
        txtbox3.setText("");
        txtbox4.setText("");
        txtbox5.valueProperty().set("Select Status");
        txtbox6.setText("");
        txtbox7.setText("");
        txtbox8.valueProperty().set("Select Priority");;
    }
    
    public void setLabel(String scene)
    {
        if(scene.equals("Product"))
        {
            lbl1.setText("Product Id");
            lbl2.setText("Product Name");
            lbl3.setText("Overview Id");
            lbl4.setText("Product Description");
            lbl5.setText("Status");
            lbl6.setText("Actions");
            lbl7.setVisible(true);
            txtbox7.setVisible(true);
            lbl7.setText("Traceability");
            lbl8.setVisible(true);
            txtbox8.setVisible(true);
            lbl8.setText("Product Priority");
            txtbox8.setItems(priorityList);
        }
        if(scene.equals("Overview"))
        {
            lbl1.setText("Overview Id");
            lbl2.setText("Overview Description");
            lbl3.setText("Stock Id");
            lbl4.setText("Actions");
            lbl5.setText("Status");
            lbl6.setText("Traceability");
        }
        if(scene.equals("Stock"))
        {
            lbl1.setText("StockId");
            lbl2.setText("Stock_Description");
            lbl3.setText("Product_Id");
            lbl4.setText("Steps");
            lbl5.setText("Status");
            lbl6.setText("Actions");
            lbl7.setVisible(true);
            txtbox7.setVisible(true);
            lbl7.setText("Traceability");
        }
        txtbox5.setItems(statusList);
    }
    
    public Object getTextBoxValue()
    {
        if(this.currentScene.equals("Product"))
        {
            Product prod = new Product();
            prod.setProductId(txtbox1.getText());
            prod.setProductName(txtbox2.getText());
            prod.setOverviewId(txtbox3.getText());
            prod.setProductDesc(txtbox4.getText());
            if(!"Select Status".equals(txtbox5.valueProperty().getValue()))
            {
                prod.setStatus(txtbox5.valueProperty().getValue());
            }
            prod.setActions(txtbox6.getText());
            prod.setTraceability(txtbox7.getText());
            if(!"Select Priority".equals(txtbox8.valueProperty().getValue()))
            {
                prod.setProductPriority(txtbox8.valueProperty().getValue());
            }

            return prod;
        }
        
        if(this.currentScene.equals("Overview"))
        {
            Overview overview = new Overview();
            overview.setOverviewId(txtbox1.getText());
            overview.setOverview_Desc(txtbox2.getText());
            overview.setStockId(txtbox3.getText());
            overview.setAction(txtbox4.getText());
             if(!"Select Status".equals(txtbox5.valueProperty().getValue()))
            {
                overview.setStatus(txtbox5.valueProperty().getValue());
            }
            overview.setTraceability(txtbox6.getText());

            return overview;
        }
        
        if(this.currentScene.equals("Stock"))
        {
            Stock stock = new Stock();
            stock.setStockId(txtbox1.getText());
            stock.setStock_Desc(txtbox2.getText());
            stock.setProduct_Id(txtbox3.getText());
            stock.setSteps(txtbox4.getText());
             if(!"Select Status".equals(txtbox5.valueProperty().getValue()))
            {
                stock.setStatus(txtbox5.valueProperty().getValue());
            }
            stock.setActions(txtbox6.getText());
            stock.setTraceability(txtbox7.getText());
            stock.setUser(this.user);

            return stock;
        }
        
        else{
            return null;
        }
    }
    
    public void goToDashboard(ActionEvent action) {
        myController.loadScreen(ScreenNavigator.dashboardScreenId, ScreenNavigator.dashboardScreeFile, this.user, null, null);
        myController.setScreen(ScreenNavigator.dashboardScreenId);
    }
}
