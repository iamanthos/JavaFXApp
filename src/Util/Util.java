package Util;

import Models.Permissions;
import Models.User;
import ScreenManager.ScreenNavigator;
import database.Database;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Util {
    
    private String userName;
    
    
    public TreeItem<String> setTreeView(User user)
    {
        this.userName = user.getUser();
        Database database = new Database();
        
        Image icon = new Image(getClass().getResourceAsStream("/Images/File.png"), 20, 20, false, false);
        ArrayList<String> availableUsers = new ArrayList<String>();
        HashMap<String, String> userRoles = database.SelectUser(user.getRole());
        TreeItem<String> root = new TreeItem<>("Available Users", new ImageView(icon));
        
        Object permission = IsUserPermitted(user);
        userRoles.entrySet().stream().map((mapElement) -> 
                (String) mapElement.getKey()).map((userInfo) -> 
                    new TreeItem<>(userInfo , new ImageView(icon))).forEachOrdered((node) -> {
                        root.getChildren().add(node);
        });
        userRoles.forEach((k,v) -> availableUsers.add(k));

        return root;
    }
    
    public Permissions IsUserPermitted(User user)
    {
        Permissions permission = new Permissions();
        
        if(user.getRole().equals("Admin"))
        {
            permission.setViewAccess(true);
            permission.setEditAccess(true);
            permission.setNewAccess(true);
        }
        else if(user.getRole().equals("Read"))
        {
            permission.setViewAccess(true);
            permission.setEditAccess(false);
            permission.setNewAccess(false);
        }
        
        else if(user.getRole().equals("Write"))
        {
            permission.setViewAccess(true);
            permission.setEditAccess(false);
            permission.setNewAccess(true);
        }
        
        return permission;
    }
    
    public String getCurrentUser()
    {
        return this.userName;
    }
    
}
