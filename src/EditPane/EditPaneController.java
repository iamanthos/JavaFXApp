package EditPane;

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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class EditPaneController implements Initializable, ControlledScreen {

    ScreenController myController;
    
    @FXML
    public TreeView<String> treeview;
    public Label lblWelcome;
    private String currentScene;
    private Database database = new Database();
    private String currentUser;
    private String uniqueId;
    private User user;
    private ObservableList<String> statusList = FXCollections.observableArrayList("Not started", "In Progress", "Blocked", "De-scoped", "Completed");
    
    @FXML Label lbl1;
    @FXML Label lbl2;
    @FXML Label lbl3;
    @FXML Label lbl4;
    @FXML Label lbl5;
    @FXML Label lbl6;
    @FXML Label lbl7;
    @FXML Label lbl8;
    
    @FXML TextField txtbox1;
    @FXML ComboBox<String> txtbox2;
    @FXML ComboBox<String> txtbox3;
    @FXML TextField txtbox4;
    @FXML TextField txtbox5;
    @FXML TextField txtbox6;
    @FXML TextField txtbox7;
    @FXML TextField txtbox8;
    
    @FXML Label status;
    @FXML Button save;
    @FXML TreeView treeview1;
    
    private Product productAction = new Product();
    private Overview overviewAction = new Overview();
    private Stock stockAction = new Stock();

    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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

    @Override
    public void setInitialData(User user, String scene, Object obj) {
        this.user = user;
        this.currentUser = user.getUser();
        lblWelcome.setText("Welcome " + this.currentUser);
        
        Util util = new Util();
        TreeItem<String> root = util.setTreeView(user);
        treeview1.setRoot(root);
          
        this.currentScene = scene;
        setLabel(scene);
          
//        save.setDisable(true);
          
        if(scene.equals("Product"))
        {
            this.productAction = (Product) obj;
        }
        if(scene.equals("Overview"))
        {
            this.overviewAction = (Overview) obj;
        }
        if(scene.equals("Stock"))
        {
            this.stockAction = (Stock) obj;
        }
        setTextBoxValue();
    }
    
    public void setLabel(String scene)
    {
        if(scene.equals("Product"))
        {
            lbl1.setText("Product Id");
            lbl2.setText("Status");
            lbl3.setText("Assigned To");
//            lbl2.setText("Product Name");
//            lbl3.setText("Overview Id");
//            lbl4.setText("Product Description");
//            lbl5.setText("Status");
//            lbl6.setText("Actions");
//            lbl7.setVisible(true);
//            txtbox7.setVisible(true);
//            lbl7.setText("Product Priority");
//            lbl8.setVisible(true);
//            txtbox8.setVisible(true);
//            lbl8.setText("Traceability");
        }
         if(scene.equals("Overview"))
        {
            lbl1.setText("Overview Id");
            lbl2.setText("Status");
            lbl3.setText("Assigned To");
//            lbl1.setText("Overview Id");
//            lbl2.setText("Overview Description");
//            lbl3.setText("Stock Id");
//            lbl4.setText("Status");
//            lbl5.setText("Actions");
//            lbl6.setText("Traceability");
        }
         
        if(scene.equals("Stock"))
        {
            lbl1.setText("StockId");
            lbl2.setText("Status");
            lbl3.setText("Assigned To");
//            lbl2.setText("Stock_Description");
//            lbl3.setText("Product_Id");
//            lbl4.setText("Steps");
//            lbl5.setText("Status");
//            lbl6.setText("Actions");
//            lbl7.setVisible(true);
//            txtbox7.setVisible(true);
//            lbl7.setText("Traceability");
        }
    }
    
    public void setTextBoxValue()
    {
        if(this.currentScene.equals("Product"))
        {
            status.setText("");
            txtbox1.setText(this.productAction.getProductId());
            txtbox1.setEditable(false);
            
//            Product prod = database.GetProductById(this.uniqueId);
//            if(prod.getProductId() != null)
//            {
//                txtbox2.setText(prod.getProductName());
//                txtbox3.setText(prod.getAssignedTo());
//                txtbox4.setText(prod.getProductDesc());
//                txtbox5.setText(prod.getStatus());
//                txtbox6.setText(prod.getActions());
//                txtbox7.setText(prod.getProductPriority());
//                txtbox8.setText(prod.getTraceability());
//                save.setDisable(false);
//            }
//            else
//            {
//                status.setText("Id not available!");
//            }
        }
        
        if(this.currentScene.equals("Overview"))
        {
            status.setText("");
            txtbox1.setText(this.overviewAction.getOverviewId());
            txtbox1.setEditable(false);
//            status.setText("");
//            Overview overview = database.GetOverviewById(this.uniqueId);
//            if(overview.getOverviewId() != null)
//            {
//            txtbox2.setText(overview.getOverview_Desc());
//            txtbox3.setText(overview.getStockId());
//            txtbox4.setText(overview.getStatus());
//            txtbox5.setText(overview.getAction());
//            txtbox6.setText(overview.getTraceability());
//            save.setDisable(false);
//            }
//            else
//            {
//                //add label not exist
//                status.setText("Id not available!");
//            }
        }
        
        if(this.currentScene.equals("Stock"))
        {
            status.setText("");
            txtbox1.setText(this.stockAction.getStockId());
            txtbox1.setEditable(false);
//            status.setText("");
//            Stock stock = database.GetStockById(this.uniqueId);
//            if(stock.getStockId() != null)
//            {
//                txtbox2.setText(stock.getStock_Desc());
//                txtbox3.setText(stock.getStatus());
//                txtbox4.setText(stock.getProduct_Id());
//                txtbox5.setText(stock.getSteps());
//                txtbox6.setText(stock.getActions());
//                txtbox7.setText(stock.getTraceability());
//                save.setDisable(false);
//            }
//            else
//            {
//                status.setText("Id not available!");
//            }
        }
        txtbox2.setItems(statusList);
        txtbox2.setPromptText("Status");
        ObservableList<String> userList = database.GetAllUsers();
        txtbox3.setItems(userList);
        txtbox3.setPromptText("User");
    }
    
//    public ObservableList fetchEditData()
//    {
//        ObservableList list = null;
//        if(this.currentScene.equals("Product"))
//        {
//            list = database.GetAllProducts(null);
//        }
//        if(this.currentScene.equals("Overview"))
//        {
//            list = database.GetAllOverviews(null);
//        }
//        return list;
//    }
    
    ///take value from table
//    @FXML
//    public void getIdOnClick (ActionEvent event)
//    {
//        String id = txtbox1.getText();
//        this.uniqueId = id;
////        setTextBoxValue();
//    }
    
    public void submit(ActionEvent event) throws ClassNotFoundException
    {
        Object obj = getTextBoxValue();
        if(this.currentScene.equals("Product"))
        {
            Product p = (Product) obj;
            this.productAction.setAssignedTo(p.getAssignedTo());
            this.productAction.setStatus(p.getStatus());
            this.productAction.setAssignedBy(this.currentUser);
            database.UpdateProductAction(this.productAction);
            status.setText("Changes Saved!");
            status.setVisible(true);
        }
        if(this.currentScene.equals("Overview"))
        {
            Overview o = (Overview) obj;
            this.overviewAction.setAssignedTo(o.getAssignedTo());
            this.overviewAction.setStatus(o.getStatus());
            this.overviewAction.setAssignedBy(this.currentUser);
            database.UpdateOverviewAction(this.overviewAction);
            status.setText("Changes Saved!");
            status.setVisible(true);
        }
        if(this.currentScene.equals("Stock"))
        {
            Stock s = (Stock) obj;
            this.stockAction.setAssignedTo(s.getAssignedTo());
            this.stockAction.setStatus(s.getStatus());
            this.stockAction.setAssignedBy(this.currentUser);
            database.UpdateStockAction(this.stockAction);
            status.setText("Changes Saved!");
            status.setVisible(true);
        }
        resetTextBoxValue();
        
    }
    
    public Object getTextBoxValue()
    {
        if(this.currentScene.equals("Product"))
        {
            Product prod = new Product();
            prod.setProductId(txtbox1.getText());
            prod.setStatus(txtbox2.valueProperty().getValue());
            prod.setAssignedTo(txtbox3.valueProperty().getValue());
//            prod.setProductName(txtbox2.getText());
//            prod.setOverviewId(txtbox3.getText());
//            prod.setProductDesc(txtbox4.getText());
//            prod.setStatus(txtbox5.getText());
//            prod.setActions(txtbox6.getText());
//            prod.setProductPriority(txtbox7.getText());
//            prod.setTraceability(txtbox8.getText());

            return prod;
        }
        
        if(this.currentScene.equals("Overview"))
        {
            Overview overview = new Overview();
            overview.setOverviewId(txtbox1.getText());
            overview.setStatus(txtbox2.valueProperty().getValue());
            overview.setAssignedTo(txtbox3.valueProperty().getValue());
//            overview.setOverview_Desc(txtbox2.getText());
//            overview.setStockId(txtbox3.getText());
//            overview.setStatus(txtbox4.getText());
//            overview.setAction(txtbox5.getText());
//            overview.setTraceability(txtbox6.getText());
            

            return overview;
        }
        if(this.currentScene.equals("Stock"))
        {
            Stock stock = new Stock();
            stock.setStockId(txtbox1.getText());
            stock.setStatus(txtbox2.valueProperty().getValue());
            stock.setAssignedTo(txtbox3.valueProperty().getValue());
//            stock.setStock_Desc(txtbox2.getText());
//            stock.setProduct_Id(txtbox3.getText());
//            stock.setSteps(txtbox4.getText());
//            stock.setStatus(txtbox5.getText());
//            stock.setActions(txtbox6.getText());
//            stock.setTraceability(txtbox7.getText());
//            stock.setUser(this.user);
            return stock;
        }
        else{
            return null;
        }
    }
    
    public void resetTextBoxValue()
    {
        txtbox1.setText("");
        txtbox2.setItems(statusList);
        txtbox2.setPromptText("Status");
        txtbox3.setPromptText("Users");
//        txtbox4.setText("");
//        txtbox5.setText("");
//        txtbox6.setText("");
//        txtbox7.setText("");
//        txtbox8.setText("");
    }
    
    public void goToDashboard(ActionEvent action) {
        myController.loadScreen(ScreenNavigator.dashboardScreenId, ScreenNavigator.dashboardScreeFile, this.user, null, null);
        myController.setScreen(ScreenNavigator.dashboardScreenId);
    }
}
