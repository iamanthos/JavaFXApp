package Models;

public class Stock {
    
    private String stockId;
    
    public String getStockId(){
        return stockId;
    }
    
    public void setStockId(String stockId){
        this.stockId = stockId;
    }
    
    private String stock_Desc;
    
    public String getStock_Desc(){
        return stock_Desc;
    }
    
    public void setStock_Desc(String stock_Desc){
           this.stock_Desc = stock_Desc;
    }
    
    private String product_Id;
    
    public String getProduct_Id(){
        return product_Id;
    }
    
    public void setProduct_Id(String product_Id){
           this.product_Id = product_Id;
    }
    
    private String Steps;
    
    public String getSteps(){
        return Steps;
    }
    
    public void setSteps(String Steps){
        this.Steps = Steps;
    }

    private String status;
    
    public String getStatus(){
        return status;
    }
    
    public void setStatus(String status){
        this.status = status;
    }
    
    private String actions;
    
    public String getActions(){
        return actions;
    }
    
    public void setActions(String actions){
        this.actions = actions;
    }
    
    
    private User User;
    
    public User getUser(){
        return User;
    }
    
    public void setUser(User User){
        this.User = User;
    }
    
    private String traceability;
    
    public String getTraceability(){
        return traceability;
    }
    
    public void setTraceability(String traceability){
        this.traceability = traceability;
    }
    
    private String enrollDate;
    
    public String getEnrollDate(){
        return enrollDate;
    }
    
    public void setEnrollDate(String enrollDate){
        this.enrollDate = enrollDate;
    }
    
    private String enrolledUser;
    
    public String getEnrolledUser(){
        return enrolledUser;
    }
    
    public void setEnrolledUser(String enrolledUser){
        this.enrolledUser = enrolledUser;
    }
    
        private String assignedBy;
    
    public String getAssignedBy(){
        return assignedBy;
    }
    
    public void setAssignedBy(String assignedBy){
        this.assignedBy = assignedBy;
    }
    
    private String assignedTo;
    
    public String getAssignedTo(){
        return assignedTo;
    }
    
    public void setAssignedTo(String assignedTo){
        this.assignedTo = assignedTo;
    }
    
    private String updatedDate;
    
    public String getUpdatedDate(){
        return updatedDate;
    }
    
    public void setUpdatedDate(String updatedDate){
        this.updatedDate = updatedDate;
    }
}
