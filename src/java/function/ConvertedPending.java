package function;

public class ConvertedPending {
    private String infTitle;
    private String Name;
    private String pendCat;
    private String postDate;
    private String infoId;
    private String userId;
    private String reason;

    public ConvertedPending(String infTitle, String Name, String pendCat, String postDate, String infoId, String userId, String reason) {
        this.infTitle = infTitle;
        this.Name = Name;
        this.pendCat = pendCat;
        this.postDate = postDate;
        this.infoId = infoId;
        this.userId = userId;
        this.reason = reason;
        
    }

    public String getInfTitle() {
        return infTitle;
    }

    public void setInfTitle(String infTitle) {
        this.infTitle = infTitle;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getPendCat() {
        return pendCat;
    }

    public void setPendCat(String pendCat) {
        this.pendCat = pendCat;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getInfoId() {
        return infoId;
    }

    public void setInfoId(String infoId) {
        this.infoId = infoId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
    
}
