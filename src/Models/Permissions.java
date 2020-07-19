package Models;

public class Permissions {
    
    private boolean editAccess;
    
    public boolean getEditAccess()
    {
        return editAccess;
    }
    
    public void setEditAccess(boolean editAccess)
    {
        this.editAccess = editAccess;
    }
    
    private boolean newAccess;
    
    public boolean getNewAccess()
    {
        return newAccess;
    }
    
    public void setNewAccess(boolean newAccess)
    {
        this.newAccess = newAccess;
    }
    
    private boolean viewAccess;
    
    public boolean getViewAccess()
    {
        return viewAccess;
    }
    
    public void setViewAccess(boolean viewAccess)
    {
        this.viewAccess = viewAccess;
    }
}
