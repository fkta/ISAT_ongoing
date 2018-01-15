package function;

import entity.UserData;

public class ConvertBulletinBoard {
    private String threadId;
    private String title;
    private String postDate;
    private UserData userId;
    private String detail;

    public String getThreadId() {
        return threadId;
    }

    public void setThreadId(String threadId) {
        this.threadId = threadId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }


    public UserData getUserId() {
        return userId;
    }

    public void setUserId(UserData userId) {
        this.userId = userId;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
    
    
}
