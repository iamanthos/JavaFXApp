package ViewPane;

import Models.Overview;
import Models.Permissions;
import Models.Product;
import Models.Stock;
import Models.Traceability;
import Models.User;
import ScreenManager.ControlledScreen;
import ScreenManager.ScreenController;
import ScreenManager.ScreenNavigator;
import Util.HyperlinkCell;
import Util.ImportData;
import Util.Util;
import database.Database;
import javafx.scene.control.Label;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
//import javafx.Scene.Control.TableView;
import javafx.scene.control.TableColumn;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
 
public class ViewPaneController implements Initializable, ControlledScreen {

    ScreenController myController;
    Database database = new Database();
    
    String prodId = "ProductId";
    String prodName = "Product_Name";
    String overviewId = "Overview_Id";
    String prodDesc = "Product_Desc";
    String status = "Status";
    String actions = "Actions";
    String prodPriority = "Product_Priority";
    String traceability = "Traceability";
    String enrollDate = "Enrollment_Date";
    String enrolledUser = "Enrollment_User";
    
    private String currentScene;
    private String currentUser;
    private String productScene = "Product";
    private String overviewScene = "Overview";
    private String stockScene = "Stock";
    private String filter = "";
    private String viewPane = "";
    private ObservableList<String> statusList = FXCollections.observableArrayList("Not started", "In Progress", "Blocked", "De-scoped", "Completed");
    private ObservableList<String> priorityList = FXCollections.observableArrayList("High", "Medium", "Low");
    private Permissions permission = new Permissions();
    private User user;
    
    @FXML TableColumn c1, c2, c3, c4, c5, c6, c7, c8, c9, c10;
    
    @FXML TableView table;
    
    @FXML Button edit;
    @FXML Button remove;
    @FXML Button btnSave;
    @FXML Button btnImport;
    @FXML Button btnEntryAdd;
    @FXML Button btnEntryDel;
    
    @FXML Label lblError; //Create it
    public Label lblWelcome;
    @FXML Label lblFilter;    
    
    @FXML TextField txtFilter;
    
    @FXML TreeView treeview1;
    
    @FXML TextField txtbox1;
    @FXML TextField txtbox2;
    @FXML TextField txtbox3;
    @FXML TextField txtbox4;
    @FXML ComboBox<String> txtbox5;
    @FXML TextField txtbox6;
    @FXML TextField txtbox7;
    @FXML ComboBox<String> txtbox8;
    
    public Hyperlink action1 = new Hyperlink("Action");
    
    @FXML AnchorPane formAnchor;
    private boolean formVisible = false;
          
    @FXML
    public void MouseClick(ActionEvent event)
    {
    }
    
    public void setGraphic()
    {
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    private void setProductColumnName()
    {
        c1.setText(prodId);
        c2.setText(prodName);
        c3.setText(overviewId);
        c4.setText(prodDesc);
        c5.setText(status);
        c6.setText(actions);
        c7.setText(prodPriority);
        c8.setText(traceability);
        c9.setText(enrollDate);
        c10.setText(enrolledUser);
    }
    
    private void setProductRowValues ()
    {
        c1.setCellValueFactory(new PropertyValueFactory("productId"));
        c2.setCellValueFactory(new PropertyValueFactory("productName"));
        c3.setCellValueFactory(new PropertyValueFactory("overviewId"));
        c4.setCellValueFactory(new PropertyValueFactory("productDesc"));
        c5.setCellValueFactory(new PropertyValueFactory("status"));
        c6.setCellValueFactory(new PropertyValueFactory("actions"));
        c7.setCellValueFactory(new PropertyValueFactory("productPriority"));
        c8.setCellValueFactory(new PropertyValueFactory("traceability"));
        c9.setCellValueFactory(new PropertyValueFactory("enrollDate"));
        c10.setCellValueFactory(new PropertyValueFactory("enrolledUser"));
        
    }
    
    @FXML
    private void setOverviewColumnName()
    {
        c1.setText("OverviewId");
        c2.setText("Overview_Desc");
        c3.setText("StockId");
        c4.setText("Action");
        c5.setText("Status");
        c6.setText("Traceability");
        c7.setText("Enrollment_Date");
        c8.setText("Enrollment_User");
    }
    
    @FXML
    private void setOverviewRowValues ()
    {
        c1.setCellValueFactory(new PropertyValueFactory("overviewId"));
        c2.setCellValueFactory(new PropertyValueFactory("overview_Desc"));
        c3.setCellValueFactory(new PropertyValueFactory("stockId"));
        c4.setCellValueFactory(new PropertyValueFactory("action"));
        c5.setCellValueFactory(new PropertyValueFactory("status"));
        c6.setCellValueFactory(new PropertyValueFactory("traceability"));
        c7.setCellValueFactory(new PropertyValueFactory("enrollDate"));
        c8.setCellValueFactory(new PropertyValueFactory("enrolledUser"));
    }
    
    @FXML
    private void setStockColumnName()
    {
        c1.setText("StockId");
        c2.setText("Stock_Desc");
        c3.setText("Product_Id");
        c4.setText("Steps");
        c5.setText("Status");
        c6.setText("Action");
        c7.setText("Traceability");
        c8.setText("Enrollment_Date");
        c9.setText("Enrollment_User");
    }
    
    @FXML
    private void setStockRowValues ()
    {
        c1.setCellValueFactory(new PropertyValueFactory("stockId"));
        c2.setCellValueFactory(new PropertyValueFactory("stock_Desc"));
        c3.setCellValueFactory(new PropertyValueFactory("product_Id"));
        c4.setCellValueFactory(new PropertyValueFactory("Steps"));
        c5.setCellValueFactory(new PropertyValueFactory("status"));
        c6.setCellValueFactory(new PropertyValueFactory("actions"));
        c7.setCellValueFactory(new PropertyValueFactory("traceability"));
        c8.setCellValueFactory(new PropertyValueFactory("enrollDate"));
        c9.setCellValueFactory(new PropertyValueFactory("enrolledUser"));
    }
    
    @FXML
    private void setTraceabilityColumnName()
    {
        c1.setText("Overview_Id");
        c2.setText("Product_Id");
        c3.setText("Stock_Id");
    }
    
    @FXML
    private void setTraceabilityRowValues ()
    {
        c1.setCellValueFactory(new PropertyValueFactory("overviewId"));
        c2.setCellValueFactory(new PropertyValueFactory("productId"));
        c3.setCellValueFactory(new PropertyValueFactory("stockId"));
    }
    
    
    @Override
    public void setScreenParent(ScreenController screenParent) {
        myController = screenParent;
    }

    @Override
    public void setInitData(User user) {
        
    }

    @Override
    public void setTreeView(User user) {
       
    }
    
    @FXML
    public void viewProducts()
    {
        ObservableList<Product> products = database.GetAllProducts(this.filter);
        setProductColumnName();
        table.setItems(products);
        setProductRowValues();
        table.getColumns().setAll(c1, c2, c3, c4, c5, c6, c7, c8, c9, c10);
        setProductColumnsEditable();
        setNewProductTextBox();
    }
    
    public void save(Product prod)
    {
        database.UpdateProduct(prod);
    }
    
    public void save(Overview overview)
    {
        database.UpdateOverview(overview);
    }
    
    public void save(Stock stock)
    {
        database.UpdateStock(stock);
    }
    
    private void setProductColumnsEditable(){
//        c1.setCellFactory(TextFieldTableCell.forTableColumn());
//        c1.setOnEditCommit(
//        new EventHandler<CellEditEvent<Product, String>>() {
//        @Override
//        public void handle(CellEditEvent<Product, String> t) {
//                ((Product) t.getTableView().getItems().get(
//                    t.getTablePosition().getRow())
//                    ).setProductId(t.getNewValue() != null ? t.getNewValue() : t.getOldValue());
//                    save(t.getRowValue());
//                }
//            }
//        );
        
        c2.setCellFactory(TextFieldTableCell.forTableColumn());
        c2.setOnEditCommit(
            new EventHandler<CellEditEvent<Product, String>>() {
            @Override
            public void handle(CellEditEvent<Product, String> t) {
                    ((Product) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setProductName(!"".equals(t.getNewValue()) ? t.getNewValue() : t.getOldValue());
                        save(t.getRowValue());
                    }
                }
            );
        
        c3.setCellFactory(TextFieldTableCell.forTableColumn());
        c3.setOnEditCommit(
        new EventHandler<CellEditEvent<Product, String>>() {
        @Override
        public void handle(CellEditEvent<Product, String> t) {
                ((Product) t.getTableView().getItems().get(
                    t.getTablePosition().getRow())
                    ).setOverviewId(!"".equals(t.getNewValue()) ? t.getNewValue() : t.getOldValue());
                    save(t.getRowValue());
                }
            }
        );
        
        c4.setCellFactory(TextFieldTableCell.forTableColumn());
        c4.setOnEditCommit(
        new EventHandler<CellEditEvent<Product, String>>() {
        @Override
        public void handle(CellEditEvent<Product, String> t) {
                ((Product) t.getTableView().getItems().get(
                    t.getTablePosition().getRow())
                    ).setProductDesc(!"".equals(t.getNewValue()) ? t.getNewValue() : t.getOldValue());
                    save(t.getRowValue());
                }
            }
        );
        
        c5.setCellFactory(ComboBoxTableCell.forTableColumn(this.statusList));
        c5.setOnEditCommit(
        new EventHandler<CellEditEvent<Product, String>>() {
        @Override
        public void handle(CellEditEvent<Product, String> t) {
                ((Product) t.getTableView().getItems().get(
                    t.getTablePosition().getRow())
                    ).setStatus(t.getNewValue());
                    save(t.getRowValue());  
                }
            }
        );
        
        if(this.permission.getEditAccess())
        {
            c6.setCellFactory(tc -> {
               TableCell<Product, String> cell = new TableCell<Product, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                    }
                };
                cell.setOnMouseClicked(e -> {
                Product prod = (Product) cell.getTableRow().getTableView().getItems().get(cell.getIndex());
                myController.loadScreen(ScreenNavigator.editPaneScreenId, ScreenNavigator.editPaneScreeFile, this.user, this.currentScene, prod);
                myController.setScreen(ScreenNavigator.editPaneScreenId);
                }); 
                return cell ; 
            });
        }

        c7.setCellFactory(ComboBoxTableCell.forTableColumn(this.priorityList));
        c7.setOnEditCommit(
        new EventHandler<CellEditEvent<Product, String>>() {
        @Override
        public void handle(CellEditEvent<Product, String> t) {
                ((Product) t.getTableView().getItems().get(
                    t.getTablePosition().getRow())
                    ).setProductPriority(t.getNewValue());
                    save(t.getRowValue());
                }
            }
        );
        
//        c8.setCellFactory(TextFieldTableCell.forTableColumn());
//        c8.setOnEditCommit(
//        new EventHandler<CellEditEvent<Product, String>>() {
//        @Override
//        public void handle(CellEditEvent<Product, String> t) {
//                ((Product) t.getTableView().getItems().get(
//                    t.getTablePosition().getRow())
//                    ).setTraceability(t.getNewValue() != null ? t.getNewValue() : t.getOldValue());
//                    save(t.getRowValue());
//                }
//            }
//        );
//        
//        c9.setCellFactory(TextFieldTableCell.forTableColumn());
//        c9.setOnEditCommit(
//        new EventHandler<CellEditEvent<Product, String>>() {
//        @Override
//        public void handle(CellEditEvent<Product, String> t) {
//                ((Product) t.getTableView().getItems().get(
//                    t.getTablePosition().getRow())
//                    ).setEnrollDate(t.getNewValue() != null ? t.getNewValue() : t.getOldValue());
//                    save(t.getRowValue());
//                }
//            }
//        );
//        
//        c10.setCellFactory(TextFieldTableCell.forTableColumn());
//        c10.setOnEditCommit(
//        new EventHandler<CellEditEvent<Product, String>>() {
//        @Override
//        public void handle(CellEditEvent<Product, String> t) {
//                ((Product) t.getTableView().getItems().get(
//                    t.getTablePosition().getRow())
//                    ).setEnrolledUser(t.getNewValue() != null ? t.getNewValue() : t.getOldValue());
//                    save(t.getRowValue());                
//                }
//            }
//        );
    }
    
    private void setOverviewColumnsEditable(){
        
        c2.setCellFactory(TextFieldTableCell.forTableColumn());
        c2.setOnEditCommit(
            new EventHandler<CellEditEvent<Overview, String>>() {
            @Override
            public void handle(CellEditEvent<Overview, String> t) {
                    ((Overview) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setOverview_Desc(!"".equals(t.getNewValue()) ? t.getNewValue() : t.getOldValue());
                        save(t.getRowValue());
                    }
                }
            );
        
        c3.setCellFactory(TextFieldTableCell.forTableColumn());
        c3.setOnEditCommit(
            new EventHandler<CellEditEvent<Overview, String>>() {
            @Override
            public void handle(CellEditEvent<Overview, String> t) {
                    ((Overview) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setStockId(!"".equals(t.getNewValue()) ? t.getNewValue() : t.getOldValue());
                        save(t.getRowValue());
                    }
                }
            );
        
//        c4.setCellFactory(TextFieldTableCell.forTableColumn());
//        c4.setOnEditCommit(
//            new EventHandler<CellEditEvent<Overview, String>>() {
//            @Override
//            public void handle(CellEditEvent<Overview, String> t) {
//                    ((Overview) t.getTableView().getItems().get(
//                        t.getTablePosition().getRow())
//                        ).setAction(!"".equals(t.getNewValue()) ? t.getNewValue() : t.getOldValue());
//                        save(t.getRowValue());
//                    }
//                }
//            );
        
        if(this.permission.getEditAccess())
        {
            c4.setCellFactory(tc -> {
           TableCell<Overview, String> cell = new TableCell<Overview, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                }
            };
            cell.setOnMouseClicked(e -> {
            Overview ovr = (Overview) cell.getTableRow().getTableView().getItems().get(cell.getIndex());
            myController.loadScreen(ScreenNavigator.editPaneScreenId, ScreenNavigator.editPaneScreeFile, this.user, this.currentScene, ovr);
            myController.setScreen(ScreenNavigator.editPaneScreenId);
            }); 
            return cell ; 
            });
        }
        
        c5.setCellFactory(ComboBoxTableCell.forTableColumn(this.statusList));
        c5.setOnEditCommit(
            new EventHandler<CellEditEvent<Overview, String>>() {
            @Override
            public void handle(CellEditEvent<Overview, String> t) {
                    ((Overview) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setStatus(t.getNewValue());
                        save(t.getRowValue());
                    }
                }
            );
    }
    
    private void setStockColumnsEditable(){
        
        c2.setCellFactory(TextFieldTableCell.forTableColumn());
        c2.setOnEditCommit(
            new EventHandler<CellEditEvent<Stock, String>>() {
            @Override
            public void handle(CellEditEvent<Stock, String> t) {
                    ((Stock) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setStock_Desc(!"".equals(t.getNewValue()) ? t.getNewValue() : t.getOldValue());
                        save(t.getRowValue());
                    }
                }
            );
        
        c3.setCellFactory(TextFieldTableCell.forTableColumn());
        c3.setOnEditCommit(
            new EventHandler<CellEditEvent<Stock, String>>() {
            @Override
            public void handle(CellEditEvent<Stock, String> t) {
                    ((Stock) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setProduct_Id(!"".equals(t.getNewValue()) ? t.getNewValue() : t.getOldValue());
                        save(t.getRowValue());
                    }
                }
            );
        
        c4.setCellFactory(TextFieldTableCell.forTableColumn());
        c4.setOnEditCommit(
            new EventHandler<CellEditEvent<Stock, String>>() {
            @Override
            public void handle(CellEditEvent<Stock, String> t) {
                    ((Stock) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setSteps(!"".equals(t.getNewValue()) ? t.getNewValue() : t.getOldValue());
                        save(t.getRowValue());
                    }
                }
            );
        
        c5.setCellFactory(ComboBoxTableCell.forTableColumn(this.statusList));
        c5.setOnEditCommit(
            new EventHandler<CellEditEvent<Stock, String>>() {
            @Override
            public void handle(CellEditEvent<Stock, String> t) {
                    ((Stock) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setStatus(t.getNewValue());
                        save(t.getRowValue());
                    }
                }
            );
        
//        c6.setCellFactory(TextFieldTableCell.forTableColumn());
//        c6.setOnEditCommit(
//            new EventHandler<CellEditEvent<Stock, String>>() {
//            @Override
//            public void handle(CellEditEvent<Stock, String> t) {
//                    ((Stock) t.getTableView().getItems().get(
//                        t.getTablePosition().getRow())
//                        ).setActions(t.getNewValue());
//                        save(t.getRowValue());
//                    }
//                }
//            );
        
        if(this.permission.getEditAccess())
        {
           c6.setCellFactory(tc -> {
           TableCell<Stock, String> cell = new TableCell<Stock, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                }
            };
            cell.setOnMouseClicked(e -> {
            Stock stock = (Stock) cell.getTableRow().getTableView().getItems().get(cell.getIndex());
            myController.loadScreen(ScreenNavigator.editPaneScreenId, ScreenNavigator.editPaneScreeFile, this.user, this.currentScene, stock);
            myController.setScreen(ScreenNavigator.editPaneScreenId);
            }); 
            return cell ; 
            });
        }
    }
    
    
    
    
    @FXML
    public void viewOverviews()
    {
        ObservableList<Overview> overviews = database.GetAllOverviews(this.filter);
        setOverviewColumnName();
        table.setItems(overviews);
        setOverviewRowValues();
        table.getColumns().setAll(c1, c2, c3, c4, c5, c6, c7, c8);
        setOverviewColumnsEditable();
        setNewOverviewTextBox();
    }
    
    @FXML
    public void viewStocks()
    {
        ObservableList<Stock> stock = database.GetAllStocks(this.filter);
        setStockColumnName();
        table.setItems(stock);
        setStockRowValues();
        table.getColumns().setAll(c1, c2, c3, c4, c5, c6, c7, c8, c9);
        setStockColumnsEditable();
        setNewStockTextBox();
    }
    
    @FXML
    public void viewTraceability()
    {
        ObservableList<Traceability> traceability = database.GetAllTraceability(this.filter);
        setTraceabilityColumnName();
        table.setItems(traceability);
        setTraceabilityRowValues();
        table.getColumns().setAll(c1, c2, c3);
    }

    
    @Override
    public void setInitialData(User user, String scene, Object obj) {
        this.currentUser = user.getUser();
        
        Util util = new Util();
        TreeItem<String> root = util.setTreeView(user);
        treeview1.setRoot(root);
          
        this.currentScene = scene;
          
        
        this.user = user;
        lblWelcome.setText("Welcome " + user.getUser());
        this.viewPane = scene;
        
        
        this.permission = util.IsUserPermitted(user);
        
        if(this.permission.getEditAccess())
        {
            table.setEditable(true);
            btnImport.setVisible(true);
            btnEntryAdd.setVisible(true);
            btnEntryDel.setVisible(true);
        }
        if(this.permission.getNewAccess())
        {
            btnImport.setVisible(true);
            btnEntryAdd.setVisible(true);
            btnEntryDel.setVisible(true);
        }
        if(!this.permission.getNewAccess())
        {
            btnImport.setVisible(false);
            btnEntryAdd.setVisible(false);
            btnEntryDel.setVisible(false);
        }
        formAnchor.setVisible(false);
        
        if(scene.equals(productScene))
        {
            viewProducts();
        }
        
        if(scene.equals(overviewScene))
        {
            viewOverviews();
        }
        
        if(scene.equals(stockScene))
        {
            viewStocks();
        }
        
        if(scene.equals("Traceability"))
        {
            btnImport.setVisible(false);
            btnImport.setDisable(true);
            btnEntryAdd.setVisible(false);
            btnEntryAdd.setDisable(true);
            btnEntryDel.setVisible(false);
            btnEntryDel.setDisable(true);
            btnSave.setVisible(false);
            viewTraceability();
        }
    }

    public void goToDashboard(ActionEvent action) {
        myController.loadScreen(ScreenNavigator.dashboardScreenId, ScreenNavigator.dashboardScreeFile, this.user, null, null);
        myController.setScreen(ScreenNavigator.dashboardScreenId);
    }
        
    public void filter (ActionEvent event)
    {
        this.filter = txtFilter.getText();
        table.getItems().clear();
        if(this.viewPane.equals(productScene))
        {
            viewProducts();
        }
        if(this.viewPane.equals(overviewScene))
        {
            viewOverviews();
        }
        if(this.viewPane.equals(stockScene))
        {
            viewStocks();
        }
    }
    
    public void setNewProductTextBox ()
    {
        txtbox1.setVisible(true);
        txtbox1.setPromptText("Product Id"); 
        txtbox2.setVisible(true);
        txtbox2.setPromptText("Product Name"); 
        txtbox3.setVisible(true);
        txtbox3.setPromptText("Overview Id");
        txtbox4.setVisible(true);
        txtbox4.setPromptText("Product Description");
        txtbox5.setVisible(true);
        txtbox5.setPromptText("Status");
        txtbox5.setItems(statusList);
        txtbox6.setVisible(true);
        txtbox6.setPromptText("Action");
        txtbox7.setVisible(true);
        txtbox7.setPromptText("Traceability");
        txtbox8.setVisible(true);
        txtbox8.setPromptText("Product Priority");
        txtbox8.setItems(priorityList);
    }
    
    public void setNewOverviewTextBox ()
    {
        txtbox1.setVisible(true);
        txtbox1.setPromptText("Overview Id"); 
        txtbox2.setVisible(true);
        txtbox2.setPromptText("Overview Description"); 
        txtbox3.setVisible(true);
        txtbox3.setPromptText("Stock Id");
        txtbox4.setVisible(true);
        txtbox4.setPromptText("Actions");
        txtbox5.setVisible(true);
        txtbox5.setPromptText("Status");
        txtbox5.setItems(statusList);
        txtbox6.setVisible(true);
        txtbox6.setPromptText("Traceability");
    }
    
    public void setNewStockTextBox ()
    {
        txtbox1.setVisible(true);
        txtbox1.setPromptText("Stock Id"); 
        txtbox2.setVisible(true);
        txtbox2.setPromptText("Stock Description"); 
        txtbox3.setVisible(true);
        txtbox3.setPromptText("Product Id");
        txtbox4.setVisible(true);
        txtbox4.setPromptText("Steps");
        txtbox5.setVisible(true);
        txtbox5.setPromptText("Status");
        txtbox5.setItems(statusList);
        txtbox6.setVisible(true);
        txtbox6.setPromptText("Traceability");
        txtbox7.setVisible(true);
        txtbox7.setPromptText("Actions");
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
                    ObservableList<Product> products = database.GetAllProducts(this.filter);
                    table.setItems(products);
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
                    ObservableList<Overview> overviews = database.GetAllOverviews(this.filter);
                    table.setItems(overviews);
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
                    ObservableList<Stock> stocks = database.GetAllStocks(this.filter);
                    table.setItems(stocks);
                }
                else
                {
                    lblError.setVisible(true);
                }
            }
            resetTextBoxValue();
            
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
    
    public void Delete(ActionEvent event) throws ClassNotFoundException
    {
        String id = "";
        if(this.currentScene.equals("Product"))
        {
            Product prod = (Product) table.getSelectionModel().getSelectedItem();
            id = prod.getProductId();
            if(!id.equals(""))
            {
                database.DeleteProduct(id);
                ObservableList<Product> products = database.GetAllProducts(this.filter);
                table.setItems(products);
            }
        }
        if(this.currentScene.equals("Overview"))
        {
            Overview ovrvw = (Overview) table.getSelectionModel().getSelectedItem();
            id = ovrvw.getOverviewId();
            if(!id.equals(""))
            {
                database.DeleteOverview(id);
                ObservableList<Overview> overviews = database.GetAllOverviews(this.filter);
                table.setItems(overviews);
            }
        }
        if(this.currentScene.equals("Stock"))
        {
            Stock stock = (Stock) table.getSelectionModel().getSelectedItem();
            id = stock.getStockId();
            if(!id.equals(""))
            {
                database.DeleteStock(id);
                ObservableList<Stock> stocks = database.GetAllStocks(this.filter);
                table.setItems(stocks);
            }
        }
    }
    
    public void ShowForm(ActionEvent event) throws ClassNotFoundException
    {
        this.formVisible = !this.formVisible;
        formAnchor.setVisible(this.formVisible);
    }
    
    public void Import(ActionEvent event) throws ClassNotFoundException, FileNotFoundException, IOException
    {
        ImportData importData = new ImportData();
        importData.Import(this.currentScene);
        
        if(this.currentScene.equals("Product"))
        {
            ObservableList<Product> products = database.GetAllProducts(this.filter);
            table.setItems(products);
        }
        if(this.currentScene.equals("Overview"))
        {
            ObservableList<Overview> overviews = database.GetAllOverviews(this.filter);
            table.setItems(overviews);
        }
        if(this.currentScene.equals("Stock"))
        {
            ObservableList<Stock> stocks = database.GetAllStocks(this.filter);
            table.setItems(stocks);
        }
     }
}
