package database;

import Models.Overview;
import Models.Product;
import Models.Stock;
import Models.Traceability;
import Models.User;
import Util.Util;
import dashboard.DashboardController;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Database {
    
    private final String serverName; 
    private final String databaseName;
    private final int portno;
    
    public Database() {
        this.serverName =  "ANTHOS-PC\\SQLINSTANCE";
        this.databaseName = "JavaDB";
        this.portno = 41506;
    }
    
    public Connection Connect() throws ClassNotFoundException, SQLException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionString = String.format("jdbc:sqlserver://%s:%d;databaseName=%s;user=%s;password=%s", serverName, portno, databaseName, "sa", "Password123");
        Connection con = DriverManager.getConnection(connectionString, "sa", "Password123");
        System.out.println("connected");
        return con;
    }

    public String InsertUser(String userName, String role) throws ClassNotFoundException, SQLException
    {
        try{
            String sqlQuery = String.format("INSERT INTO [JavaDB].[dbo].[Users] (UserName, Roles) VALUES (%s, %s)", userName, role);
            Connection con = Connect();
            Statement statement = con.createStatement();
            statement.executeUpdate(sqlQuery);
            CloseConnection(con);
            return "Insert Successfully";
        }
        catch(SQLException e)
        {
            return "Unsuccessful Insert. Error Occured " + e;
        }
    }
    
    public String CloseConnection(Connection con){
        try{
            con.close();
            return "Connection Closed";
        }
        catch(SQLException e){
            return "Connection Close Error. " + e;
        }
    }
    
    public boolean CheckUser(String user) throws ClassNotFoundException, SQLException
    {
        String sqlQuery = String.format("SELECT 1 as Result FROM [JavaDB].[dbo].[Users] where [UserName] = '%s'", user);
        Connection con = Connect();
        Statement statement = con.createStatement();
        try
        {
            ResultSet rs = statement.executeQuery(sqlQuery);
            if(rs!= null)
            {
                rs.next();
                int result = rs.getInt(1);
                con.close();
                System.out.print("result: " + result);
                if(result != 1)
                {
                    return false;
                }
                else
                {
                    return true;
                }
            }
            else
            {
                return false;
            }
            
        }
        catch(SQLException e)
        {
            CloseConnection(con);
            System.out.print(e);
            
        }
        
        return false;
    }
    
    public boolean ValidateKey(String key)
    {
        try
        {
            String sqlQuery = String.format("SELECT 1 as Result FROM [JavaDB].[dbo].[Users] where [Key] = '%s'", key);
            Connection con = Connect();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sqlQuery);
            
            rs.next();
            int result = rs.getInt(1);
            con.close();
            System.out.print("result: " + result);
            if(result != 1)
            {
                return false;
            }
            else
            {
                return true;
            }
        }
        catch(ClassNotFoundException | SQLException e)
        {
            System.out.print(e);
        }
        return false;
    }
    
    public HashMap<String, String> SelectUser(String role)
    {
        String sqlQuery = "";
        if(role.equals("Admin"))
        {
            sqlQuery = "SELECT [UserName], [Roles] FROM [JavaDB].[dbo].[Users]";
        }
        else if(role.equals("Read") || role.equals("Write"))
        {
            sqlQuery = String.format("SELECT [UserName], [Roles] FROM [JavaDB].[dbo].[Users] WHERE [Roles] = '%s'", role);
        }
        try{
        HashMap<String, String> userRoles = new HashMap<String, String>();
        
            Connection con = Connect();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sqlQuery);
            
            while(rs.next())
            {
                userRoles.put(rs.getString("UserName"), rs.getString("Roles"));
            }
            con.close();
            return userRoles;
        }
        catch(ClassNotFoundException | SQLException e)
        {
            System.out.print(e);
            return null;
        }
    }
    
    public HashMap<String, String> SelectUser()
    {
        try{
        HashMap<String, String> userRoles = new HashMap<String, String>();
//        List<String> users = new ArrayList<String>();
        
        String sqlQuery = "SELECT [UserName], [Roles] FROM [JavaDB].[dbo].[Users]";
        Connection con = Connect();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sqlQuery);
            
            while(rs.next())
            {
                userRoles.put(rs.getString("UserName"), rs.getString("Roles"));
            }
            con.close();
            return userRoles;
        }
        catch(ClassNotFoundException | SQLException e)
        {
            System.out.print(e);
            return null;
        }
    }
    
    public ObservableList GetAllProducts(String filter)
    {
        ObservableList data;
        Collection<Product> products = new ArrayList();
        try{
            String sqlQuery = "SELECT [Id], [ProductId],[Product_Name],[Overview_Id],[Product_Desc],[Status],[Actions],[Product_Priority],[Traceability], [Date_Created], [Created_User] FROM [JavaDB].[dbo].[Product]";
            if(filter != "" && filter != null)
            {
               sqlQuery = String.format(sqlQuery + " WHERE ProductId = '%s'", filter);
            }
            Connection con = Connect();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sqlQuery);
            
            while(rs.next())
            {
                Product prod = new Product();
                prod.setProductId(rs.getString("ProductId")); 
                prod.setProductName(rs.getString("Product_Name"));
                prod.setOverviewId(rs.getString("Overview_Id")); 
                prod.setProductDesc(rs.getString("Product_Desc"));
                prod.setProductPriority(rs.getString("Product_Priority"));
                prod.setStatus(rs.getString("Status"));
                prod.setActions(rs.getString("Actions"));
                prod.setTraceability(rs.getString("Traceability"));
                prod.setEnrollDate(rs.getString("Date_Created"));
                prod.setEnrolledUser(rs.getString("Created_User"));
                
                products.add(prod);
            }
            con.close();
            data = FXCollections.observableArrayList(products);
            return data;
        }
        catch(ClassNotFoundException | SQLException e)
        {
            System.out.print(e);
            return null;
        }
    }
    
    public ObservableList GetAllUsers()
    {
        ObservableList data;
        Collection<String> users = new ArrayList();
        try{
            String sqlQuery = "SELECT [UserName] FROM [JavaDB].[dbo].[Users]";
            Connection con = Connect();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sqlQuery);
            
            while(rs.next())
            {
                String user = rs.getString("UserName");
                
                users.add(user);
            }
            con.close();
            data = FXCollections.observableArrayList(users);
            return data;
        }
        catch(ClassNotFoundException | SQLException e)
        {
            System.out.print(e);
            return null;
        }
    }
    
    public String GetUserRole(String user)
    {
        String role = "";
        try{
            String sqlQuery = "SELECT [Roles] FROM [JavaDB].[dbo].[Users] WHERE UserName = '" + user + "'";
            Connection con = Connect();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sqlQuery);
            
            while(rs.next())
            {
                role = rs.getString("Roles");
            }
            con.close();
            return role;
        }
        catch(ClassNotFoundException | SQLException e)
        {
            System.out.print(e);
            return null;
        }
    }
    
    public ObservableList GetAllOverviews(String filter)
    {
        ObservableList data;
        Collection<Overview> overviewCollection = new ArrayList();
        try{
            String sqlQuery = "SELECT [OverviewId],[Overview_Desc],[StockId],[Status],[Action],[Traceability], [Date_Created], [Created_User] FROM [JavaDB].[dbo].[Overview]";
            if(filter != "" && filter != null)
            {
               sqlQuery = String.format(sqlQuery + " WHERE OverviewId = '%s'", filter);
            }
            Connection con = Connect();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sqlQuery);
            
            while(rs.next())
            {
                Overview overview = new Overview();
                overview.setOverviewId(rs.getString("OverviewId")); 
                overview.setOverview_Desc(rs.getString("Overview_Desc"));
                overview.setStockId(rs.getString("StockId")); 
                overview.setStatus(rs.getString("Status"));
                overview.setAction(rs.getString("Action"));
                overview.setTraceability(rs.getString("Traceability"));
                overview.setEnrollDate(rs.getString("Date_Created"));
                overview.setEnrolledUser(rs.getString("Created_User"));
                
                overviewCollection.add(overview);
            }
            con.close();
            data = FXCollections.observableArrayList(overviewCollection);
            return data;
        }
        catch(ClassNotFoundException | SQLException e)
        {
            System.out.print(e);
            return null;
        }
    }
   
    public ObservableList GetAllStocks(String filter)
    {
        ObservableList data;
        Collection<Stock> stocks = new ArrayList();
        try{
            String sqlQuery = "SELECT  [StockId], [Stock_Desc], [Product_Id], [Steps], [Status], [Action], [Traceability], [Date_Created], [Created_User] FROM [JavaDB].[dbo].[Stock]";
            if(filter != "" && filter != null)
            {
               sqlQuery = String.format(sqlQuery + " WHERE StockId = '%s'", filter);
            }
            Connection con = Connect();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sqlQuery);
            
            while(rs.next())
            {
                Stock stock = new Stock();
                stock.setStockId(rs.getString("StockId"));
                stock.setStock_Desc(rs.getString("Stock_Desc"));
                stock.setProduct_Id(rs.getString("Product_Id"));
                stock.setSteps(rs.getString("Steps"));
                stock.setStatus(rs.getString("Status"));
                stock.setActions(rs.getString("Action"));
                stock.setTraceability(rs.getString("Traceability"));
                stock.setEnrollDate(rs.getString("Date_Created"));
                stock.setEnrolledUser(rs.getString("Created_User"));
                
                stocks.add(stock);
            }
            con.close();
            data = FXCollections.observableArrayList(stocks);
            return data;
        }
        catch(ClassNotFoundException | SQLException e)
        {
            System.out.print(e);
            return null;
        }
    }
    
    public ObservableList GetAllTraceability(String filter)
    {
        ObservableList data;
        Collection<Traceability> traceRelation = new ArrayList();
        try{
            String sqlQuery = "SELECT o.OverviewId, p.ProductId, s.StockId FROM Overview o\n" +
            "JOIN Product p ON o.OverviewId = p.Overview_Id\n" +
            "JOIN Stock s ON s.StockId = o.StockId";
            if(filter != "" && filter != null)
            {
               sqlQuery = String.format(sqlQuery + " WHERE p.ProductId = '%s'", filter);
            }
            Connection con = Connect();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sqlQuery);
            
            while(rs.next())
            {
                Traceability traceability = new Traceability();
                traceability.setOverviewId(rs.getString("OverviewId"));
                traceability.setProductId(rs.getString("ProductId"));
                traceability.setStockId(rs.getString("StockId"));
                traceRelation.add(traceability);
            }
            con.close();
            data = FXCollections.observableArrayList(traceRelation);
            return data;
        }
        catch(ClassNotFoundException | SQLException e)
        {
            System.out.print(e);
            return null;
        }
    }
   
    public String saveNewProduct(Product product, String user) throws ClassNotFoundException
    {
            LocalDateTime currentDateTime = LocalDateTime.now();
            try{
                String sqlQuery = String.format("INSERT INTO [JavaDB].[dbo].[Product] ([ProductId], [Product_Name], [Overview_Id], [Product_Desc], [Status], [Actions], [Product_Priority], [Traceability], [Date_Created], [Created_User]) VALUES ('%s','%s','%s','%s','%s','%s','%s','%s', '%s', '%s')", product.getProductId(), product.getProductName(), product.getOverviewId(), product.getProductDesc(), product.getStatus(), product.getActions(), product.getProductPriority(), product.getTraceability(), currentDateTime.toString(), user);
                Connection con = Connect();
                Statement statement = con.createStatement();
                statement.executeUpdate(sqlQuery);
                CloseConnection(con);
                return "Insert Successfully";
            }
            catch(SQLException e)
            {
                return "Unsuccessful Insert. Error Occured " + e;
            }
        }
        
    public String saveNewOverview(Overview overview, String user) throws ClassNotFoundException
    {
            try{
                LocalDateTime currentDateTime = LocalDateTime.now();
                String sqlQuery = String.format("INSERT INTO [JavaDB].[dbo].[Overview] ([OverviewId],[Overview_Desc],[StockId],[Status],[Action],[Traceability], [Date_Created], [Created_User]) VALUES ('%s','%s','%s','%s','%s','%s','%s','%s')", overview.getOverviewId(), overview.getOverview_Desc(), overview.getStockId(), overview.getStatus(), overview.getAction(), overview.getTraceability(), currentDateTime.toString(), user);
                Connection con = Connect();
                Statement statement = con.createStatement();
                statement.executeUpdate(sqlQuery);
                CloseConnection(con);
                return "Insert Successfully";
            }
            catch(SQLException e)
            {
                return "Unsuccessful Insert. Error Occured " + e;
            }
        }
        
    public String saveNewStock(Stock stock, String user) throws ClassNotFoundException
    {
            try{
                LocalDateTime currentDateTime = LocalDateTime.now();
                String sqlQuery = String.format("INSERT INTO [JavaDB].[dbo].[Stock] ([StockId], [Stock_Desc], [Product_Id], [Steps], [Status], [Action], [Traceability], [User], [Date_Created], [Created_User]) VALUES ('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s')",stock.getStockId(), stock.getStock_Desc(), stock.getProduct_Id(),stock.getSteps(), stock.getStatus(), stock.getActions(), stock.getTraceability(), user, currentDateTime.toString(), user);
                Connection con = Connect();
                Statement statement = con.createStatement();
                statement.executeUpdate(sqlQuery);
                CloseConnection(con);
                return "Insert Successfully";
            }
            catch(SQLException e)
            {
                return "Unsuccessful Insert. Error Occured " + e;
            }
        }
        
    public Product GetProductById(String prodId)
    {
        try{
            String sqlQuery = String.format("SELECT [ProductId],[Product_Name],[Overview_Id],[Product_Desc],[Status],[Actions],[Product_Priority],[Traceability] FROM [JavaDB].[dbo].[Product] WHERE [ProductId] = '%s'", prodId) ;
            Connection con = Connect();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sqlQuery);
            
            Product prod = new Product();
            while(rs.next())
            {
                prod.setProductId(rs.getString("ProductId")); 
                prod.setProductName(rs.getString("Product_Name"));
                prod.setOverviewId(rs.getString("Overview_Id")); 
                prod.setProductDesc(rs.getString("Product_Desc"));
                prod.setProductPriority(rs.getString("Product_Priority"));
                prod.setStatus(rs.getString("Status"));
                prod.setActions(rs.getString("Actions"));
                prod.setTraceability(rs.getString("Traceability")); 
            }
            con.close();
            return prod;
        }
        catch(ClassNotFoundException | SQLException e)
        {
            System.out.print(e);
            return null;
        }
    }
     
    public Overview GetOverviewById(String overviewId)
    {
        try{
            String sqlQuery = String.format("SELECT [OverviewId],[Overview_Desc],[StockId],[Status],[Action],[Traceability] FROM [JavaDB].[dbo].[Overview] WHERE [OverviewId] = '%s'", overviewId) ;
            Connection con = Connect();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sqlQuery);
            
            Overview overview = new Overview();
            while(rs.next())
            {
               
                overview.setOverviewId(rs.getString("OverviewId")); 
                overview.setOverview_Desc(rs.getString("Overview_Desc"));
                overview.setStockId(rs.getString("StockId")); 
                overview.setStatus(rs.getString("Status"));
                overview.setAction(rs.getString("Action"));
                overview.setTraceability(rs.getString("Traceability"));
            }
            con.close();
            return overview;
        }
        catch(ClassNotFoundException | SQLException e)
        {
            System.out.print(e);
            return null;
        }
    }
    
     public Stock GetStockById(String stockId)
    {
        try{
            String sqlQuery = String.format("SELECT  [StockId], [Stock_Desc], [Product_Id], [Steps], [Status], [Action], [Traceability] FROM [JavaDB].[dbo].[Stock] WHERE [StockId] = '%s'", stockId) ;
            Connection con = Connect();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sqlQuery);
            
            Stock stock = new Stock();
            while(rs.next())
            {
                stock.setStockId(rs.getString("StockId"));
                stock.setStock_Desc(rs.getString("Stock_Desc"));
                stock.setProduct_Id(rs.getString("Product_Id"));
                stock.setSteps(rs.getString("Steps"));
                stock.setStatus(rs.getString("Status"));
                stock.setActions(rs.getString("Action"));
                stock.setTraceability(rs.getString("Traceability"));
            }
            con.close();
            return stock;
        }
        catch(ClassNotFoundException | SQLException e)
        {
            System.out.print(e);
            return null;
        }
    }
     
public void UpdateProduct(Product prod)
    {
        try{
            LocalDateTime currentDateTime = LocalDateTime.now();
            String sqlQuery = String.format("UPDATE [JavaDB].[dbo].[Product] SET [Product_Name] = '%s',[Overview_Id] = '%s',[Product_Desc] = '%s',[Status] = '%s',[Actions] = '%s',[Product_Priority] = '%s',[Traceability] = '%s', [Date_Updated] = '%s' WHERE [ProductId] = '%s'", prod.getProductName(), prod.getOverviewId(), prod.getProductDesc(),prod.getStatus(), prod.getActions(), prod.getProductPriority(), prod.getTraceability(), currentDateTime.toString(), prod.getProductId());
            Connection con = Connect();
            Statement statement = con.createStatement();
            statement.executeUpdate(sqlQuery);
            
            con.close();
            System.out.println("Updated Successfully");
        }
        catch(ClassNotFoundException | SQLException e)
        {
            System.out.println("Unable to Update");
            System.out.print(e);
            
        }
    }
    
    public void UpdateProductAction(Product prod)
    {
        try{
            LocalDateTime currentDateTime = LocalDateTime.now();
            String sqlQuery = String.format("UPDATE [JavaDB].[dbo].[Product] SET [Status] = '%s', [Date_Updated] = '%s', [Assigned_By] = '%s', [Assigned_To] = '%s' WHERE [ProductId] = '%s'", prod.getStatus(), currentDateTime.toString(), prod.getAssignedBy(), prod.getAssignedTo(), prod.getProductId());
            Connection con = Connect();
            Statement statement = con.createStatement();
            statement.executeUpdate(sqlQuery);
            
            con.close();
            System.out.println("Updated Successfully");
        }
        catch(ClassNotFoundException | SQLException e)
        {
            System.out.println("Unable to Update");
            System.out.print(e);
            
        }
    }
    
    public void UpdateOverviewAction(Overview over)
    {
        try{
            LocalDateTime currentDateTime = LocalDateTime.now();
            String sqlQuery = String.format("UPDATE [JavaDB].[dbo].[Overview] SET [Status] = '%s', [Date_Updated] = '%s', [Assigned_By] = '%s', [Assigned_To] = '%s' WHERE [OverviewId] = '%s'", over.getStatus(), currentDateTime.toString(), over.getAssignedBy(), over.getAssignedTo(), over.getOverviewId());
            Connection con = Connect();
            Statement statement = con.createStatement();
            statement.executeUpdate(sqlQuery);
            
            con.close();
            System.out.println("Updated Successfully");
        }
        catch(ClassNotFoundException | SQLException e)
        {
            System.out.println("Unable to Update");
            System.out.print(e);
            
        }
    }
    

    
    public void UpdateOverview(Overview overview)
    {
        try{
            LocalDateTime currentDateTime = LocalDateTime.now();
            String sqlQuery = String.format("UPDATE [dbo].[Overview] SET [Overview_Desc] = '%s' ,[StockId] = '%s' ,[Status] = '%s' ,[Action] = '%s' ,[Traceability] = '%s', [Date_Updated] = '%s' WHERE [OverviewId] = '%s'", overview.getOverview_Desc(), overview.getStockId(), overview.getStatus(),overview.getAction(), overview.getTraceability(), currentDateTime.toString(), overview.getOverviewId());
            Connection con = Connect();
            Statement statement = con.createStatement();
            statement.executeUpdate(sqlQuery);
            
            con.close();
            System.out.println("Updated Successfully");
        }
        catch(ClassNotFoundException | SQLException e)
        {
            System.out.println("Unable to Update");
            System.out.print(e);
        }
    }
    
    public void UpdateStockAction(Stock stock)
    {
        try{
            LocalDateTime currentDateTime = LocalDateTime.now();
            String sqlQuery = String.format("UPDATE [JavaDB].[dbo].[Stock] SET [Status] = '%s', [Date_Updated] = '%s', [Assigned_By] = '%s', [Assigned_To] = '%s' WHERE [StockId] = '%s'", stock.getStatus(), currentDateTime.toString(), stock.getAssignedBy(), stock.getAssignedTo(), stock.getStockId());
            Connection con = Connect();
            Statement statement = con.createStatement();
            statement.executeUpdate(sqlQuery);
            
            con.close();
            System.out.println("Updated Successfully");
        }
        catch(ClassNotFoundException | SQLException e)
        {
            System.out.println("Unable to Update");
            System.out.print(e);
            
        }
    }
    
    public void UpdateStock(Stock stock)
    {
        try{
            LocalDateTime currentDateTime = LocalDateTime.now();
            String sqlQuery = String.format("UPDATE [dbo].[Stock] SET [Stock_Desc] = '%s' ,[Product_Id] = '%s' ,[Steps] = '%s', [Status] = '%s', [Action] = '%s', [Traceability] = '%s', [Date_Updated] = '%s' WHERE [StockId] = '%s'", stock.getStock_Desc(), stock.getProduct_Id(), stock.getSteps(), stock.getStatus(),stock.getActions(), stock.getTraceability(), currentDateTime.toString(), stock.getStockId());
            Connection con = Connect();
            Statement statement = con.createStatement();
            statement.executeUpdate(sqlQuery);
            
            con.close();
            System.out.println("Updated Successfully");
        }
        catch(ClassNotFoundException | SQLException e)
        {
            System.out.println("Unable to Update");
            System.out.print(e);
        }
    }
    
    public void insertUser (String user, String role, String key)
    {
        try{
            String sqlQuery = String.format("INSERT INTO [dbo].[Users] ([UserName],[Roles],[Key]) VALUES ('%s', '%s', '%s')", user, role, key);
            Connection con = Connect();
            Statement statement = con.createStatement();
            statement.executeQuery(sqlQuery);
            
            con.close();
            System.out.println("Updated Successfully");
        }
        catch(ClassNotFoundException | SQLException e)
        {
            System.out.println("Unable to Update");
            System.out.print(e);
        }
    }
    
    public void updateUser (String user, String key)
    {
        try{
            String sqlQuery = String.format("UPDATE [JavaDB].[dbo].[Users] SET [Key] = '%s' WHERE [UserName] = '%s'", key, user);
            Connection con = Connect();
            Statement statement = con.createStatement();
            statement.executeUpdate(sqlQuery);
            CloseConnection(con);
             System.out.println("Updated Successfully");
        }
        catch(ClassNotFoundException | SQLException e)
        {
            System.out.println("Unable to Update");
            System.out.print(e);
        }
    }
    
    public void DeleteProduct(String productId)
    {
        try{
            String sqlQuery = String.format("DELETE FROM [JavaDB].[dbo].[Product] WHERE [ProductId] = '%s'", productId);
            Connection con = Connect();
            Statement statement = con.createStatement();
            statement.executeUpdate(sqlQuery);
            
            con.close();
            System.out.println("Deleted Successfully");
        }
        catch(ClassNotFoundException | SQLException e)
        {
            System.out.println("Unable to Delete");
            System.out.print(e);
        }
    }
    
    public void DeleteOverview(String overviewId)
    {
        try{
            String sqlQuery = String.format("DELETE FROM [JavaDB].[dbo].[Overview] WHERE [OverviewId] = '%s'", overviewId);
            Connection con = Connect();
            Statement statement = con.createStatement();
            statement.executeUpdate(sqlQuery);
            
            con.close();
            System.out.println("Deleted Successfully");
        }
        catch(ClassNotFoundException | SQLException e)
        {
            System.out.println("Unable to Delete");
            System.out.print(e);
        }
    }
        
    public void DeleteStock(String stockId)
    {
        try{
            String sqlQuery = String.format("DELETE FROM [JavaDB].[dbo].[Stock] WHERE [StockId] = '%s'", stockId);
            Connection con = Connect();
            Statement statement = con.createStatement();
            statement.executeUpdate(sqlQuery);
            
            con.close();
            System.out.println("Deleted Successfully");
        }
        catch(ClassNotFoundException | SQLException e)
        {
            System.out.println("Unable to Delete");
            System.out.print(e);
        }
    }
    
    public void DeleteUser(String username)
    {
        try{
            String sqlQuery = String.format("DELETE FROM [JavaDB].[dbo].[Users] WHERE [UserName] = '%s'", username);
            Connection con = Connect();
            Statement statement = con.createStatement();
            statement.executeUpdate(sqlQuery);
            
            con.close();
            System.out.println("Deleted Successfully");
        }
        catch(ClassNotFoundException | SQLException e)
        {
            System.out.println("Unable to Delete");
            System.out.print(e);
        }
    }
    
    public ObservableList GetNotifProducts(String status, String updatedDate, String currentUser)
    {
        ObservableList data;
        Collection<Product> products = new ArrayList();
        try{
            String role = GetUserRole(currentUser);
            String sqlQuery = "";
            if(role.equals("Admin"))
            {
                sqlQuery = "SELECT [ProductId], [Status], [Assigned_By], [Assigned_To], [Date_Updated] FROM [JavaDB].[dbo].[Product]";
            }
            else
            {
                sqlQuery = "SELECT [ProductId], [Status], [Assigned_By], [Assigned_To], [Date_Updated] FROM [JavaDB].[dbo].[Product]";
                if(!"".equals(status) && status != null && "".equals(updatedDate))
                {
                   sqlQuery = String.format(sqlQuery + " WHERE Status = '%s' AND [Assigned_To] = '%s'", status, currentUser);
                }
                if("".equals(status) && status != null && !"".equals(updatedDate))
                {
                   sqlQuery = String.format(sqlQuery + " WHERE Date_Updated like '%s'  AND [Assigned_To] = '%s'", updatedDate+'%', currentUser);
                }
                if(!"".equals(status) && status != null && !"".equals(updatedDate))
                {
                   sqlQuery = String.format(sqlQuery + " WHERE Status = '%s' AND Date_Updated like '%s' AND [Assigned_To] = '%s'", status, updatedDate+'%', currentUser);
                }
                else
                {
                    sqlQuery = String.format(sqlQuery + " WHERE [Assigned_To] = '%s'", currentUser);
                }
            }
            Connection con = Connect();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sqlQuery);
            
            while(rs.next())
            {
                Product prod = new Product();
                prod.setProductId(rs.getString("ProductId")); 
                prod.setStatus(rs.getString("Status"));
                prod.setAssignedBy(rs.getString("Assigned_By"));
                prod.setAssignedTo(rs.getString("Assigned_To"));
                prod.setUpdatedDate(rs.getString("Date_Updated"));
                products.add(prod);
            }
            con.close();
            data = FXCollections.observableArrayList(products);
            return data;
        }
        catch(ClassNotFoundException | SQLException e)
        {
            System.out.print(e);
            return null;
        }
    }
    
    public ObservableList GetNotifOverviews(String status, String updatedDate, String currentUser)
    {
        ObservableList data;
        Collection<Overview> overviewCollection = new ArrayList();
        try{
            String role = GetUserRole(currentUser);
            String sqlQuery = "";
            if(role.equals("Admin"))
            {
                sqlQuery = "SELECT [OverviewId], [Status], [Assigned_By], [Assigned_To], [Date_Updated] FROM [JavaDB].[dbo].[Overview]";
            }
            else
            {
                if(!"".equals(status) && status != null && "".equals(updatedDate))
                {
                   sqlQuery = String.format(sqlQuery + " WHERE Status = '%s' AND [Assigned_To] = '%s'", status, currentUser);
                }
                if("".equals(status) && status != null && !"".equals(updatedDate))
                {
                   sqlQuery = String.format(sqlQuery + " WHERE Date_Updated like '%s'  AND [Assigned_To] = '%s'", updatedDate+'%', currentUser);
                }
                if(!"".equals(status) && status != null && !"".equals(updatedDate))
                {
                   sqlQuery = String.format(sqlQuery + " WHERE Status = '%s' AND Date_Updated like '%s' AND [Assigned_To] = '%s'", status, updatedDate+'%', currentUser);
                }
                else
                {
                    sqlQuery = String.format(sqlQuery + " WHERE [Assigned_To] = '%s'", currentUser);
                }
            }
            
            Connection con = Connect();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sqlQuery);
            
            while(rs.next())
            {
                Overview overview = new Overview();
                overview.setOverviewId(rs.getString("OverviewId")); 
                overview.setStatus(rs.getString("Status"));
                overview.setAssignedBy(rs.getString("Assigned_By"));
                overview.setAssignedTo(rs.getString("Assigned_To"));
                overview.setUpdatedDate(rs.getString("Date_Updated"));
                
                overviewCollection.add(overview);
            }
            con.close();
            data = FXCollections.observableArrayList(overviewCollection);
            return data;
        }
        catch(ClassNotFoundException | SQLException e)
        {
            System.out.print(e);
            return null;
        }
    }
   
    public ObservableList GetNotifStock(String status, String updatedDate, String currentUser)
    {
        ObservableList data;
        Collection<Stock> stocks = new ArrayList();
        try{
            String role = GetUserRole(currentUser);
            String sqlQuery = "";
            if(role.equals("Admin"))
            {
                sqlQuery = "SELECT  [StockId], [Status], [Assigned_By], [Assigned_To], [Date_Updated] FROM [JavaDB].[dbo].[Stock]";
            }
            else
            {
                if(!"".equals(status) && status != null && "".equals(updatedDate))
                {
                   sqlQuery = String.format(sqlQuery + " WHERE Status = '%s' AND [Assigned_To] = '%s'", status, currentUser);
                }
                if("".equals(status) && status != null && !"".equals(updatedDate))
                {
                   sqlQuery = String.format(sqlQuery + " WHERE Date_Updated like '%s'  AND [Assigned_To] = '%s'", updatedDate+'%', currentUser);
                }
                if(!"".equals(status) && status != null && !"".equals(updatedDate))
                {
                   sqlQuery = String.format(sqlQuery + " WHERE Status = '%s' AND Date_Updated like '%s' AND [Assigned_To] = '%s'", status, updatedDate+'%', currentUser);
                }
                else
                {
                    sqlQuery = String.format(sqlQuery + " WHERE [Assigned_To] = '%s'", currentUser);
                }
            }
            Connection con = Connect();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sqlQuery);
            
            while(rs.next())
            {
                Stock stock = new Stock();
                stock.setStockId(rs.getString("StockId"));
                stock.setStatus(rs.getString("Status"));
                stock.setAssignedBy(rs.getString("Assigned_By"));
                stock.setAssignedTo(rs.getString("Assigned_To"));
                stock.setUpdatedDate(rs.getString("Date_Updated"));
                
                stocks.add(stock);
            }
            con.close();
            data = FXCollections.observableArrayList(stocks);
            return data;
        }
        catch(ClassNotFoundException | SQLException e)
        {
            System.out.print(e);
            return null;
        }
    }
}
