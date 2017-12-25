package bean;

import ejb.CategoryFacade;
import ejb.InfoFacade;
import entity.Category;
import entity.Info;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.model.SelectItem;

@Named(value = "infomationBean")
@RequestScoped
public class InfomationBean{
    private String title;
    private String content;
    private List<SelectItem> category;
    private String priority;
    private String catItem;
    
    @EJB
    InfoFacade inf;
    @EJB
    CategoryFacade cf;
    
    @PostConstruct
    public void init(){
        System.out.println("InfomationBean init() start");
        List<Category> catList;
        catList = getAllCat();
        category = new ArrayList<SelectItem>();
        for(Category ct: catList){
            SelectItem item = new SelectItem(ct.getCategoryId().toString(),ct.getCategoryName());
            category.add(item);
        }
        System.out.println("InfomationBean init() end");
    }
    
    public void clear(){
        this.title = null;
        this.content = null;
    }
    
    public String addInfo(){
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/mm/dd hh:mm:ss");
        Info info = new Info();
        info.setInfoId("inf"+"20171222162812");
        info.setTitle(title);
        info.setContent(content);
        info.setInfotype("display");
        info.setTerm(d);
        inf.create(info);
        return null;
    }
    
    /* データの取得 */
    public List<Info> getAllInfo(){
        return inf.findAll();
    }
    
    public List<Category> getAllCat(){
        return cf.findAll();
    }
    
    /*セッターゲッター*/

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

    public List<SelectItem> getCategory() {
        return category;
    }

    public void setCategory(List<SelectItem> category) {
        this.category = category;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getCatItem() {
        return catItem;
    }

    public void setCatItem(String catItem) {
        this.catItem = catItem;
    }
    
}
