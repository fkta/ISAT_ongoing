package bean;

import ejb.BulletinBoardFacade;
import ejb.ResponseFacade;
import entity.BulletinBoard;
import entity.Response;
import entity.ResponsePK;
import function.ConvertBulletinBoard;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

@Named(value = "boardBean")
@RequestScoped
public class BoardBean {
    
    private String title;
    private String comment;
    
    @EJB
    BulletinBoardFacade bbf;
    @EJB
    ResponseFacade rf;
    @Inject
    UserDataManager udm;
    @Inject
    BoardDataManager bdm;
    
    /* スレッド作成 */
    public String addThread() throws ParseException{
        createThread();
        return null;
    }
    
    public void createThread() throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String threadId = "thr".concat(sdf.format(new Date()));
        sdf.applyPattern("yyyy/MM/dd HH:mm:ss");
        BulletinBoard bb = new BulletinBoard(threadId,title,sdf.parse(sdf.format(new Date())),udm.getUser());
        bbf.create(bb);
    }
    
    /* レスポンス作成 */
    public String addResponse(){
        createResponse();
        return null;
    }
    
    public void createResponse(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Response res = new Response();
        ResponsePK resPK = new ResponsePK();
        resPK.setThreadId(bdm.getCbbDetail().getThreadId());
        resPK.setResponseId("res"+ sdf.format(new Date()));
        
        res.setResponsePK(resPK);
        res.setComment(comment);
        res.setPostDate(new Date());
        res.setUserId(udm.getUser());
        rf.create(res);
        //リストの更新
        bdm.setResponseList(rf.findByThreadId(bdm.getCbbDetail().getThreadId()));
        System.out.println("レスを作成しました");
        System.out.println("投稿内容 : "+res.getComment()+" 投稿者 : "+res.getUserId().getName());
    }
    
//スレッド一覧表示
    public List<ConvertBulletinBoard> getAllTheard(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        List<ConvertBulletinBoard> cList = new ArrayList<ConvertBulletinBoard>();
        for(BulletinBoard d : bbf.orderByPostDate()){
            ConvertBulletinBoard cbb = new ConvertBulletinBoard();
            cbb.setTitle(d.getTitle());
            cbb.setThreadId(d.getThreadId());
            cbb.setPostDate(sdf.format(d.getPostDate()));
            cbb.setUserId(d.getUserId());
            cList.add(cbb);
        }
        return cList;
    }   
    
    /* スレッド詳細表示 */
    public String transToDetail(ConvertBulletinBoard cbb){
        // パラメータ取得
        updateDetailData(cbb);
        
        return "threaddetail.xhtml?faces-redirect=true";
    }
    
    public void updateDetailData(ConvertBulletinBoard cbb){
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
        String threadId = params.get("threadId");
        System.out.println("threadId : "+threadId);
        
        // データの保管
        bdm.setCbbDetail(cbb);
        bdm.setResponseList(rf.findByThreadId(threadId));
}
    
    
//レス一覧表示
    /*public String threadDetail(BulletinBoard bb){
        bdm.setDetaildata(bbf.finddetail(bb.getThreadId()));
        bdm.setThreadId(bb.getThreadId());
        bdm.setRes(getAllResponse(bb.getThreadId()));
        return "threaddetails.xhtml?faces-redirect=true";
    }*/
    

    
//レス削除
    public String deleteResponse(Response response){
        // レス作成者かを確認する
        if(response.getUserId().getUserId().equals(udm.getUser().getUserId())){
            rf.remove(response);
            bdm.setResponseList();
            System.out.println("削除対象 : " + response.getResponsePK().getResponseId());
            System.out.println("削除内容 : " + response.getComment());
        }else{
            System.out.println("レスの作成者ではありません　削除失敗");
            
        }
        
    return null;
}
    
    //スレッド削除
    public String deleteThread(ConvertBulletinBoard cbb) throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        //スレッドの作成者かを確認する
        if(cbb.getUserId().getUserId().equals(udm.getUser().getUserId())){
            BulletinBoard bb = new BulletinBoard(cbb.getThreadId(), cbb.getTitle(), sdf.parse(cbb.getPostDate()), cbb.getUserId());
            bbf.remove(bb);
        }else{
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
        this.comment = comment;
    }

    
    
}
