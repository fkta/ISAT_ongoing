package bean;

import ejb.CategoryFacade;
import ejb.InfoFacade;
import entity.Category;
import entity.Info;
import entity.UserData;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

@Named(value = "infomationBean")
@RequestScoped
public class InfomationBean{
    private String title;
    private String content;
    private List<SelectItem> category;
    private String priority;
    private String catItem;
    private List<SelectItem> term;
    private String selectedTerm;
    
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
        
        term = new ArrayList<SelectItem>();
        SelectItemGroup g1 = new SelectItemGroup("週単位");
        g1.setSelectItems(new SelectItem[]{new SelectItem("1w","1週間"),new SelectItem("2w","2週間"),new SelectItem("3w","3週間")});
        term.add(g1);
        SelectItemGroup g2 = new SelectItemGroup("月単位");
        g2.setSelectItems(new SelectItem[]{
            new SelectItem("1m","1ヶ月"),new SelectItem("2m","2ヶ月"),new SelectItem("3m","3ヶ月"),new SelectItem("4m","4ヶ月"),
            new SelectItem("5m","5ヶ月"),new SelectItem("6m","6ヶ月"),new SelectItem("7m","7ヶ月"),new SelectItem("8m","8ヶ月"),
            new SelectItem("9m","9ヶ月"),new SelectItem("10m","10ヶ月"),new SelectItem("11m","11ヶ月")
        });
        term.add(g2);
        System.out.println("InfomationBean init() end");
    }
    
    public void clear(){
        this.title = null;
        this.content = null;
    }
    
    public String addInfo() throws ParseException{
        System.out.println("1 selectedTerm : "+selectedTerm);
        System.out.println("1 content : "+content);
        System.out.println("1 catItem : "+catItem);
        System.out.println("1 priority : "+priority);
        
        /* カレンダーの初期化 */
        Calendar cal = termDecision();
        
        /* 日付書式の変換 */
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/mm/dd hh:mm:ss");
        String timeManager;
        
        //日付加算後の日時を代入
        Date date = cal.getTime();
        
        //変換処理開始
        timeManager = sdf.format(date);
        date = sdf.parse(timeManager);
        
        System.out.println("書式変更後のdate → "+date);
        
        //現在時刻の取得 書式変換
        Date nowTime = new Date();
        timeManager = sdf.format(nowTime);
        nowTime = sdf.parse(timeManager);
        
        
        /* カテゴリの設定 */
        Category cat = new Category();
        cat.setCategoryId(Integer.parseInt(catItem));
        
        /* 作成者の設定 */
        Info info = new Info();
        UserData ud = new UserData();
        ud.setUserId("ad99999999");
        
        /* インスタンスの生成 */
        info.setInfoId("inf"+"20171222162812");
        info.setTitle(title);
        info.setContent(content);
        info.setCategoryId(cat);
        info.setInfotype("display");
        info.setTerm(date);
        info.setCreateDate(nowTime);
        info.setPriority(priority);
        info.setUserId(ud);
        inf.create(info);
        return null;
    }
    
    public Calendar termDecision(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        System.out.println("2 selectedTerm : "+selectedTerm);
        System.out.println("2 catItem : "+catItem);
        System.out.println("2 priority : "+priority);
        if(selectedTerm.endsWith("w")){
            int time = Integer.parseInt(selectedTerm.substring(0, 1));
            cal.add(Calendar.WEEK_OF_YEAR, time);
        }else if(selectedTerm.endsWith("m")){
            int time = Integer.parseInt(selectedTerm.substring(0, 1));
            cal.add(Calendar.MONTH, time);
        }else{
            System.out.println("期間取得エラー");
        }
        
        return cal;
        
    }
    
    /* データの取得 */
    public List<Info> getAllInfo() throws ParseException{;
        return inf.findAllInfo();
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

    public List<SelectItem> getTerm() {
        return term;
    }

    public void setTerm(List<SelectItem> term) {
        this.term = term;
    }

    public String getSelectedTerm() {
        return selectedTerm;
    }

    public void setSelectedTerm(String selectedTerm) {
        this.selectedTerm = selectedTerm;
    }
    
    
}
