package bean;

import ejb.CategoryFacade;
import ejb.InfoFacade;
import entity.Category;
import entity.Info;
import entity.UserData;
import function.ConvertedInfo;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import javax.inject.Inject;

@Named(value = "infomationBean")
@RequestScoped
public class InfomationBean{
    private String title;
    private String content;
    private List<SelectItem> category;
    private String priority;
    private String catItem;
    private List<SelectItem> term;
    private Date selectedTerm;
    private Boolean endDate;
    
    @EJB
    InfoFacade inf;
    @EJB
    CategoryFacade cf;
    @Inject
    InfoDataManager idm;
    
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
    
    public String transToDetail(){
        getDetail();
        return "infodetail.xhtml?faces-redirect=true";
    }
    
    public String addInfo() throws ParseException{
        
        /*System.out.println("1 content : "+content);
        System.out.println("1 catItem : "+catItem);
        System.out.println("1 priority : "+priority);*/
        
        // カレンダーの初期化
        //Calendar cal = termDecision();
        
        // 日付書式の変換
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String timeManager;
        System.out.println("selectedTerm : "+sdf.format(selectedTerm));
        
        //日付加算後の日時を代入
//        Date date = cal.getTime();
        if(endDate != false){
            //終日
            String convert = sdf.format(selectedTerm);
            convert = convert.substring(0, 11);
            selectedTerm = sdf.parse(convert.concat("23:59:59"));
            System.out.println("終日term : "+convert.concat("23:59:59"));
        }
        
        //変換処理開始
        /*timeManager = sdf.format(date);
        date = sdf.parse(timeManager);*/
        
        //現在時刻の取得 書式変換
        Date nowTime = new Date();
        timeManager = sdf.format(nowTime);
        nowTime = sdf.parse(timeManager);
        
        //主キー作成用に書式を変更
        sdf.applyPattern("yyyyMMddHHmmss");
        
        
        // カテゴリの設定
        Category cat = new Category();
        cat.setCategoryId(Integer.parseInt(catItem));
        
        // 作成者の設定
        Info info = new Info();
        UserData ud = new UserData();
        ud.setUserId("ad99999999");
        
        /* インスタンスの生成 */
        info.setInfoId("inf"+sdf.format(new Date()));
        info.setTitle(title);
        info.setContent(content);
        info.setCategoryId(cat);
        if(ud.getUserId().startsWith("ad")){
            //管理者ならinfotypeをdisplayにする
            info.setInfotype("display");
        }else if(ud.getUserId().startsWith("tc")){
            //教師なら申請中にする
            info.setInfotype("pending");
        }
        info.setTerm(selectedTerm);
        info.setCreateDate(nowTime);
        info.setPriority(priority);
        info.setUserId(ud);
        inf.create(info);
        return null;
    }
    
    public String deleteInfo(){
        inf.remove(idm.getDetailData());
        idm.clear();
        return "info_list.xhtml?faces-redirect=true";
    }
    
    /*public Calendar termDecision(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        System.out.println("2 selectedTerm : "+selectedTerm);
        System.out.println("2 catItem : "+catItem);
        System.out.println("2 priority : "+priority);
        if(selectedTerm.endsWith("w")){
            int time = Integer.parseInt(selectedTerm.substring(0, 1));
            cal.add(Calendar.WEEK_OF_YEAR, time);
            System.out.println("time : "+time);
        }else if(selectedTerm.endsWith("m")){
            int time = Integer.parseInt(selectedTerm.substring(0, 1));
            cal.add(Calendar.MONTH, time);
            System.out.println(time);
        }else{
            System.out.println("期間取得エラー");
        }
        
        
        return cal;
        
    }*/
    
    /* データの取得 */
    public List<ConvertedInfo> getAllInfo() throws ParseException{
        List<ConvertedInfo> convInfoList = new ArrayList<ConvertedInfo>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        for(Info data : inf.findAllInfo()){
            ConvertedInfo cInfo = new ConvertedInfo(
                    data.getInfoId(),data.getTitle(),data.getContent(),
                    sdf.format(data.getTerm()),sdf.format(data.getCreateDate()),
                    data.getPriority(),data.getInfotype(),data.getCategoryId(),
                    data.getUserId());
            
            convInfoList.add(cInfo);
        }
        return convInfoList;
    }
    
    public void getDetail(){
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
        String param = params.get("detailId");
        System.out.println("param : "+param);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Info dd = inf.findDetail(param);
        ConvertedInfo convInfo = new ConvertedInfo(
                dd.getInfoId(),dd.getTitle(),dd.getContent(),sdf.format(dd.getTerm()),
                sdf.format(dd.getCreateDate()),dd.getPriority(),dd.getInfotype(),
                dd.getCategoryId(),dd.getUserId());
        
        idm.setDetailData(inf.findDetail(param));
        idm.setConvData(convInfo);
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
        content = content.replaceAll("\n","<br/>");
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

    public Date getSelectedTerm() {
        return selectedTerm;
    }

    public void setSelectedTerm(Date selectedTerm) {
        this.selectedTerm = selectedTerm;
    }

    public Boolean getEndDate() {
        return endDate;
    }

    public void setEndDate(Boolean endDate) {
        this.endDate = endDate;
    }

}
