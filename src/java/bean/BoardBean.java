package bean;

import ejb.BulletinBoardFacade;
import ejb.ResponseFacade;
import entity.BulletinBoard;
import entity.Response;
import entity.ResponsePK;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

@Named(value = "boardBean")
@RequestScoped
public class BoardBean {
    
    private String title;
    private String detail;
    private String comment;
    // レスポンスの削除確認画面で使用する
    private Response delData;
    // メッセージ
    protected FacesContext context = FacesContext.getCurrentInstance();
    
    @EJB
    BulletinBoardFacade bbf;
    @EJB
    ResponseFacade rf;
    @Inject
    UserDataManager udm;
    @Inject
    BoardDataManager bdm;
    
    /* 初期化処理 */
    public void clear(){
        this.title = null;
        this.comment = null;
        this.detail = null;
    }
    
    /* 削除メッセージ用のデータを作成する */
    public void addDelMessage(Response response){
        this.delData = response;
        System.out.println("delData : "+delData.getComment());
    }
    
    /* スレッド作成 */
    public void addThread() throws ParseException{
        createThread();
        // メッセージの表示
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO ,"Success", "作成しました") );
    }
    
    public void createThread() throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String threadId = "thr".concat(sdf.format(new Date()));
        sdf.applyPattern("yyyy/MM/dd HH:mm:ss");
        BulletinBoard bb = new BulletinBoard(threadId,title,sdf.parse(sdf.format(new Date())),udm.getUser(),detail);
        bbf.create(bb);
        
        // 入力フォームの初期化
        clear();
    }
    
    /* レスポンス作成 */
    public void addResponse(){
        createResponse();
    }
    
    public void createResponse(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Response res = new Response();
        ResponsePK resPK = new ResponsePK();
        resPK.setThreadId(bdm.getThreadDetailData().getThreadId());
        resPK.setResponseId("res"+ sdf.format(new Date()));
        
        res.setResponsePK(resPK);
        res.setComment(comment);
        res.setPostDate(new Date());
        res.setUserId(udm.getUser());
        rf.create(res);
        //リストの更新
        bdm.setResponseList(rf.findByThreadId(bdm.getThreadDetailData().getThreadId()));
        
        //入力フォームの初期化
        clear();
        
        //メッセージの作成
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO ,"Success", "投稿しました") );
        
        System.out.println("レスを作成しました");
        System.out.println("投稿内容 : "+res.getComment()+" 投稿者 : "+res.getUserId().getName());
    }
    
    //スレッド一覧表示
    /*public List<ConvertBulletinBoard> getAllTheard(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        List<ConvertBulletinBoard> cList = new ArrayList<ConvertBulletinBoard>();
        for(BulletinBoard d : bbf.orderByPostDate()){
            ConvertBulletinBoard cbb = new ConvertBulletinBoard();
            cbb.setTitle(d.getTitle());
            cbb.setThreadId(d.getThreadId());
            cbb.setPostDate(sdf.format(d.getPostDate()));
            cbb.setUserId(d.getUserId());
            cbb.setDetail(d.getDetail());
            cList.add(cbb);
        }
        return cList;
    }*/   
    
    public List<BulletinBoard> getAllThread(){
      return bbf.orderByPostDate();
    }
    
    // レス数カウント
    public int resCount(BulletinBoard bb){
        return rf.findByThreadId(bb.getThreadId()).size();
    }
    
    /* スレッド詳細表示 */
    public String transToDetail(BulletinBoard bb){
        // パラメータ取得
        updateDetailData(bb);
        
        return "threaddetail.xhtml?faces-redirect=true";
    }
    
    public void updateDetailData(BulletinBoard bb){
        /*FacesContext fc = FacesContext.getCurrentInstance();
        Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
        String threadId = params.get("threadId");
        System.out.println("threadId : "+threadId);*/
        
        // データの保管
        bdm.setThreadDetailData(bb);
        System.out.println("cbb Title : "+bb.getTitle());
        bdm.setResponseList(rf.findByThreadId(bb.getThreadId()));
        System.out.println("Detail : "+bdm.getThreadDetailData().getDetail());
}
    
    
//レス一覧表示
    /*public String threadDetail(BulletinBoard bb){
        bdm.setDetaildata(bbf.finddetail(bb.getThreadId()));
        bdm.setThreadId(bb.getThreadId());
        bdm.setRes(getAllResponse(bb.getThreadId()));
        return "threaddetails.xhtml?faces-redirect=true";
    }*/
    

    
//レス削除
    public void removeResponse(Response response){
        // レス作成者かを確認する もしくは教師
        if(response.getUserId().getUserId().equals(udm.getUser().getUserId())||udm.getUser().getUsertype().equals("teacher")){
            rf.remove(response);
            bdm.setResponseList(rf.findByThreadId(bdm.getThreadDetailData().getThreadId()));
            //メッセージの表示
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO ,"Success", "削除しました") );
            System.out.println("削除対象 : " + response.getResponsePK().getResponseId());
            System.out.println("削除内容 : " + response.getComment());
            
        }else{
            //メッセージの表示
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR ,"Error", "投稿者のみ削除できます") );

            System.out.println("レスの作成者ではありません　削除失敗");
            
        }
        
}
    
    //スレッド削除
    public String removeThread(BulletinBoard bb) throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        //スレッドの作成者かを確認する
        if(bb.getUserId().getUserId().equals(udm.getUser().getUserId())||udm.getUser().getUsertype().equals("teacher")){
            bbf.remove(bb);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO ,"Success", "削除しました") );
        }else{
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR ,"Error", "スレッドの作成者ではありません") );
            System.out.println("スレッドの作成者ではありません");
        }
        return null;
    }

    // セッターゲッター
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment.replaceAll("\n","<br/>");
        System.out.println("comment : "+this.comment);
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail.replaceAll("\n","<br/>");
        this.detail = detail;
    }

    public Response getDelData() {
        return delData;
    }

    public void setDelData(Response delData) {
        this.delData = delData;
    }

    
    
}
