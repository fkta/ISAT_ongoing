package bean;

import ejb.CategoryFacade;
import ejb.InfoFacade;
import ejb.PendingFacade;
import ejb.UserDataFacade;
import entity.Category;
import entity.Info;
import entity.Pending;
import entity.PendingPK;
import entity.UserData;
import function.ConvertedPending;
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
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
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
    private String delReason;
    //メッセージ
    protected FacesContext context = FacesContext.getCurrentInstance();
    
    @EJB
    InfoFacade inf;
    @EJB
    CategoryFacade cf;
    @EJB
    PendingFacade pf;
    @EJB
    UserDataFacade udf;
    @Inject
    InfoDataManager idm;
    @Inject
    UserDataManager udm;
    
    @PostConstruct
    public void init(){
        List<Category> catList;
        catList = getAllCat();
        category = new ArrayList<SelectItem>();
        for(Category ct: catList){
            SelectItem item = new SelectItem(ct.getCategoryId().toString(),ct.getCategoryName());
            category.add(item);
        }
        
        /*term = new ArrayList<SelectItem>();
        SelectItemGroup g1 = new SelectItemGroup("週単位");
        g1.setSelectItems(new SelectItem[]{new SelectItem("1w","1週間"),new SelectItem("2w","2週間"),new SelectItem("3w","3週間")});
        term.add(g1);
        SelectItemGroup g2 = new SelectItemGroup("月単位");
        g2.setSelectItems(new SelectItem[]{
            new SelectItem("1m","1ヶ月"),new SelectItem("2m","2ヶ月"),new SelectItem("3m","3ヶ月"),new SelectItem("4m","4ヶ月"),
            new SelectItem("5m","5ヶ月"),new SelectItem("6m","6ヶ月"),new SelectItem("7m","7ヶ月"),new SelectItem("8m","8ヶ月"),
            new SelectItem("9m","9ヶ月"),new SelectItem("10m","10ヶ月"),new SelectItem("11m","11ヶ月")
        });
        term.add(g2);*/
    }
    
    /* 変数の初期化 */
    public void clear(){
        this.title = null;
        this.content = null;
        this.title = null;
        this.selectedTerm = null;
        this.endDate = null;
        this.priority = null;
        this.catItem = null;
        this.catItem = null;
        this.content = null;
    }
    
    /* ページ遷移処理 */
    public String transToDetail(){
        getDetail(paramGetInfoId());
        // ユーザ種別で遷移先を振り分ける
        if(udm.getUser().getUsertype().equals("admin")||udm.getUser().getUsertype().equals("teacher")){
            System.out.println("info_detail usertype : "+udm.getUser().getUsertype());
            return "infodetail_upper.xhtml?faces-redirect=true";
        }else if(udm.getUser().getUsertype().equals("student")){
            System.out.println("info_detail usertype : "+udm.getUser().getUsertype());
            return "infodetail.xhtml?faces-redirect=true";
        }else{
            System.out.println("ユーザ種別が不正です。");
            udm.setErrorMessage("セッションがタイムアウトしました　再度ログインしてください。");
            return "/login/login.xhtml?faces-redirect=true";
        }
        
    }
    
    public String transToList(){
        if(udm.getUser().getUsertype().equals("admin")||udm.getUser().getUsertype().equals("teacher")){
            System.out.println("インフォ一覧 userId : "+udm.getUser().getUsertype());
            return "infolist_upper.xhtml?faces-redirect=true";
        }else if(udm.getUser().getUsertype().equals("student")){
            System.out.println("インフォ一覧 userId : "+udm.getUser().getUsertype());
            return "info_list.xhtml?faces-redirect=true";
        }else{
            System.out.println("ユーザ種別が不正です。");
            udm.setErrorMessage("セッションがタイムアウトしました　再度ログインしてください。");
            return "/login/login.xhtml?faces-redirect=true";
        }
    }
    
    public String transToPendList(){
        switch (udm.getUser().getUsertype()) {
            case "admin":
                return "infopendinglist.xhtml?faces-redirect=true";
                
            case "teacher":
                return "infopendinglist.xhtml?faces-redirect=true";
                
            case "student":
                System.out.println("Error ユーザ種別が学生");
                return "info_list.xhtml?faces-redirect=true";
            default:
                System.out.println("ユーザ種別が不正です。");
                udm.setErrorMessage("セッションがタイムアウトしました　再度ログインしてください。");
                return "/login/login.xhtml?faces-redirect=true";
        }
        
    }
    
    public String transToPendingDetail(){
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
        String pendUserId = params.get("userId");
        System.out.println("申請者id : "+pendUserId);
        
        String infoId = paramGetInfoId();
        idm.setPendPK(new PendingPK(infoId,pendUserId));
        idm.setPend(pf.findPKData(idm.getPendPK()).get(0));
        
        switch(udm.getUser().getUsertype()){
            case "admin":
                getDetail(infoId);
               return "infopendingdetail_upper.xhtml?faces-redirect=true";
                
            case "teacher":
                getDetail(infoId);
                return "infopendingdetail.xhtml?faces-redirect=true";
                
            default:
                System.out.println("ユーザ種別が不正です。");
                udm.setErrorMessage("セッションがタイムアウトしました　再度ログインしてください。");
                return "/login/login.xhtml?faces-redirect=true";
        }
    }
    
    /* 新規作成 */
    public void addInfo() throws ParseException{
        
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
        ud.setUserId(udm.getUser().getUserId());
        
        /* インスタンスの生成 */
        info.setInfoId("inf"+sdf.format(new Date()));
        info.setTitle(title);
        info.setContent(content);
        info.setCategoryId(cat);
        
        // Infotype Edit
        /*if(ud.getUserId().startsWith("ad")){
            //管理者ならinfotypeをdisplayにする
            info.setInfotype("display");
        }else if(ud.getUserId().startsWith("tc")){
            //教師なら申請中にする
            info.setInfotype("create_pending");
        }*/
        
        info.setInfotype("display");
        
        info.setTerm(selectedTerm);
        info.setCreateDate(nowTime);
        info.setPriority(priority);
        info.setUserId(udm.getUser());
        System.out.println("作成者 : " +info.getUserId().getName());
        inf.create(info);
        
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO ,"Success", "作成しました") );
        
        /*if(ud.getUserId().startsWith("tc")){
            //申請情報の作成
            PendingPK pndPK = new PendingPK(info.getInfoId(),udm.getUser().getUserId());
            Pending pnd = new Pending();
            pnd.setPendingPK(pndPK);
            pnd.setPendingCategory("create");
            pnd.setPostDate(nowTime);
            pnd.setReason("新規作成の申請です。");
            pf.create(pnd);
            System.out.println("create pending succesful");
        }*/
    }
    
    /* 更新 */
    public String pendUpdate(){
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
        String param = params.get("decision");
        System.out.println("param : "+param);
        List<Pending> pending = pf.findPKData(idm.getPendPK());
        if(pending.size() > 0 && pending.get(0).getPendingCategory().startsWith("create")){
            switch (param) {
            case "permit":
                Info info = idm.getDetailData();
                info.setInfotype("display");
                inf.edit(info);
                System.out.println("インフォ種別情報変更処理完了");
                //関連する申請データを削除する処理
               for(Pending d : pending){
                    pf.remove(d);
                }
                break;
                
            case "rejection":
                for(Pending d : pending){
                    pf.remove(d);
                }
                break;
                
            default:
                System.out.println("値が不正です。");
                break;
            }
            
        }else if(pending.size() > 0 && pending.get(0).getPendingCategory().startsWith("delete")){
            switch(param){
                case "permit":
                    inf.remove(idm.getDetailData());
                    for(Pending d : pending){
                    pf.remove(d);
                    }
                    break;
                    
                case "rejection":
                    for(Pending d : pending){
                    pf.remove(d);
                    }
                    break;
                    
                default:
                    System.out.println("値が不正です。");
                    break;
            }
            
        }else{
            
            System.out.println("権限が不正な値です。update"+idm.getConvData().getInfotype());
            System.out.println("userType : "+udm.getUser().getUsertype());
            udm.setErrorMessage("セッションがタイムアウトしました　再度ログインしてください。");
            return "/login/login.xhtml?faces-redirect=true";
        }
        
        
        return "infopendinglist.xhtml?faces-redirect=true";
    }
    
    public String pendUpdate_lower(){
        List<Pending> pending = pf.findPKData(idm.getPendPK());
        for(Pending d : pending){
            if(d.getPendingCategory().equals("create")){
                inf.remove(idm.getDetailData());
            }else if(d.getPendingCategory().equals("delete")){
                pf.remove(d);
            }else{
                System.out.println("権限が不正な値です。update"+idm.getConvData().getInfotype());
                System.out.println("userType : "+udm.getUser().getUsertype());
                udm.setErrorMessage("セッションがタイムアウトしました　再度ログインしてください。");
                return "/login/login.xhtml?faces-redirect=true";
            }
        }
        return "infopendinglist.xhtml?faces-redirect=true";
    }
    
    /* 削除 */
    public String deleteInfo(){
        // 権限の確認
        if(udm.getUser().getUsertype().equals("admin")||udm.getUser().getUsertype().equals("teacher")){
            // 削除する
            inf.remove(idm.getDetailData());
            idm.clear();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO ,"Success", "削除しました") );
            return "infolist_upper.xhtml?faces-redirect=true";
        }else{
            System.out.println("権限が不正な値です。");
            System.out.println("userType : "+udm.getUser().getUsertype());
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR ,"Error", "セッションがタイムアウトしました　再度ログインしてください。") );
            udm.setErrorMessage("セッションがタイムアウトしました　再度ログインしてください。");
            return "/login/login.xhtml?faces-redirect=true";
        }
    }
    
    public void deleteInfo(Info info){
        inf.remove(info);
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO ,"Success", "削除しました") );
    }
    
    /* 削除申請 */
    public String deleteRequest() throws ParseException{
        Info target = idm.getDetailData();
        // 申請種別を削除申請中に変更
        /*target.setInfotype("delete_pending");
        inf.edit(target);*/
        PendingPK pndPK = new PendingPK(idm.getDetailData().getInfoId(),udm.getUser().getUserId());
        Pending pnd = new Pending();
        pnd.setPendingPK(pndPK);
        pnd.setPendingCategory("delete");
        
        // 現在日付取得処理
        String timeManager;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date nowTime = new Date();
        timeManager = sdf.format(nowTime);
        nowTime = sdf.parse(timeManager);
        
        pnd.setPostDate(nowTime);
        pnd.setReason(delReason);
        pf.create(pnd);
        System.out.println("削除申請を作成しました。");
        System.out.println("infid : "+pnd.getPendingPK().getInfoId()+" pending user : "+pnd.getPendingPK().getUserId());
        return "infolist_upper.xhtml?faces-redirect=true";
    }
    
    /* 詳細表示 */
    
    
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
    public String paramGetInfoId(){
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
        String param = params.get("detailId");
        System.out.println("param : "+param);
        return param;
    }
    
    public List<Info> getAllInfo() throws ParseException{
//        List<ConvertedInfo> convInfoList = new ArrayList<ConvertedInfo>();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
//        for(Info data : inf.findAllInfo()){
//            ConvertedInfo cInfo = new ConvertedInfo(
//                    data.getInfoId(),data.getTitle(),data.getContent(),
//                    sdf.format(data.getTerm()),sdf.format(data.getCreateDate()),
//                    data.getPriority(),data.getInfotype(),data.getCategoryId(),
//                    data.getUserId());
//            
//            convInfoList.add(cInfo);
//        }
        return inf.findAllInfo();
    }
    
    public void getDetail(String param){
        /*FacesContext fc = FacesContext.getCurrentInstance();
        Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
        String param = params.get("detailId");
        System.out.println("param : "+param);*/
        
        idm.setDetailData(inf.findDetail(param));
    }
    
    public List<Category> getAllCat(){
        return cf.findAll();
    }
    
    public List<Pending> getAllPend(){
        return pf.findAllPending();
    }
    
    public List<ConvertedPending> getPendingData(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        switch (udm.getUser().getUsertype()) {
            case "admin":
                System.out.println("申請データ取得");
                //申請情報をすべて取得する
                List<Pending> pnd = getAllPend();
                List<ConvertedPending> cPnd = new ArrayList<ConvertedPending>();
                //申請情報に基づき、インフォメーションの情報を取得する
                for(Pending d : pnd){
                    Info info = inf.findDetail(d.getPendingPK().getInfoId());
                    ConvertedPending cp = new ConvertedPending(info.getTitle(),
                            udf.findUserId(d.getPendingPK().getUserId()).getName(),d.getPendingCategory(),
                            sdf.format(d.getPostDate()),d.getPendingPK().getInfoId(),d.getPendingPK().getUserId(),
                            d.getReason());
                    cPnd.add(cp);
                }
                idm.setConvPendData(cPnd);
                return cPnd;
                
            case "teacher":
                System.out.println("申請リストへ遷移");
                List<Pending> pnd2 = pf.findData(udm.getUser().getUserId());
                List<ConvertedPending> cPnd2 = new ArrayList<ConvertedPending>();
                //申請情報に基づき、インフォメーションの情報を取得する
                for(Pending d : pnd2){
                    Info info = inf.findDetail(d.getPendingPK().getInfoId());
                    ConvertedPending cp = new ConvertedPending(info.getTitle(),
                            udf.findUserId(d.getPendingPK().getUserId()).getName(),d.getPendingCategory(),
                            sdf.format(d.getPostDate()),d.getPendingPK().getInfoId(),d.getPendingPK().getUserId(),
                            d.getReason());
                    cPnd2.add(cp);
                }
                idm.setConvPendData(cPnd2);
                return cPnd2;
            default:
                System.out.println("ユーザ種別が不正です。");
                udm.setErrorMessage("セッションがタイムアウトしました　再度ログインしてください。");
                return null;
        }
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

    public String getDelReason() {
        return delReason;
    }

    public void setDelReason(String delReason) {
        this.delReason = delReason;
    }

    
}
