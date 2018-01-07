package function;

public class ConvertedPending {
    private String infTitle;
    private String Name;
    private String pendCat;
    private String postDate;
    private String infoId;

    public ConvertedPending(String infTitle, String Name, String pendCat, String postDate, String infoId) {
        this.infTitle = infTitle;
        this.Name = Name;
        this.pendCat = pendCat;
        this.postDate = postDate;
        this.infoId = infoId;
        
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
    
}
