/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package function;

import entity.Category;
import entity.UserData;

public class ConvertedInfo {
    private String infoId;
    private String title;
    private String content;
    private String term;
    private String createDate;
    private String priority;
    private String infotype;
    private Category CategoryId;
    private UserData userId;
    
    /* コンストラクタ */
    public ConvertedInfo(String infoId, String title, String content, String term, String createDate, String priority, String infotype, Category CategoryId, UserData userId) {
        this.infoId = infoId;
        this.title = title;
        this.content = content;
        this.term = term;
        this.createDate = createDate;
        this.priority = priority;
        this.infotype = infotype;
        this.CategoryId = CategoryId;
        this.userId = userId;
    }
    
    /* セッターゲッター */

    public String getInfoId() {
        return infoId;
    }

    public void setInfoId(String infoId) {
        this.infoId = infoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getInfotype() {
        return infotype;
    }

    public void setInfotype(String infotype) {
        this.infotype = infotype;
    }

    public Category getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(Category CategoryId) {
        this.CategoryId = CategoryId;
    }

    public UserData getUserId() {
        return userId;
    }

    public void setUserId(UserData userId) {
        this.userId = userId;
    }
    
    
    
}
