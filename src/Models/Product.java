package Models;

import javafx.scene.control.Hyperlink;

public class Product {
   private String productId;
    
    public String getProductId(){
        return productId;
    }
    
    public void setProductId(String productId){
        this.productId = productId;
    }
    
    private String productName;
    
    public String getProductName(){
        return productName;
    }
    
    public void setProductName(String productName){
        this.productName = productName;
    }
    
    private String overviewId;
    
    public String getOverviewId(){
        return overviewId;
    }
    
    public void setOverviewId(String overviewId){
        this.overviewId = overviewId;
    }
    
        private String productDesc;
    
    public String getProductDesc(){
        return productDesc;
    }
    
    public void setProductDesc(String productDesc){
        this.productDesc = productDesc;
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
    
        private Hyperlink action;
    
    public Hyperlink getAction(){
        return action;
    }
    
    public void setAction(Hyperlink action){
        this.action = action;
    }
    
    
    private String productPriority;
    
    public String getProductPriority(){
        return productPriority;
    }
    
    public void setProductPriority(String productPriority){
        this.productPriority = productPriority;
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
