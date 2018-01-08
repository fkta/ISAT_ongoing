package bean;

import ejb.UserDataFacade;
import entity.UserData;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Named(value = "managedBean")
@RequestScoped
public class ManagedBean {
    private String userId;
    private String password;
    private String errorMessage;
    private boolean nicknameToggle;
    private String nickname;
    
    @EJB
    UserDataFacade udf;
    @Inject
    UserDataManager udm;
    
    @PostConstruct
    public void init(){
        nicknameToggle = false;
    }
    
    /* ユーザ認証処理 */
    public String userAuth(){
        System.out.println("ユーザ認証開始");
        List<UserData> ud = udf.userAuth(userId, password);
        if(!ud.isEmpty()){
            udm.setUser(ud.get(0));
            if(udm.getUser().getUsertype().equals("admin")||udm.getUser().getUsertype().equals("teacher")){
                return "/infomation/infolist_upper.xhtml?faces-redirect=true";
            }else if(udm.getUser().getUsertype().equals("student")){
                return "/infomation/info_list.xhtml?faces-redirect=true";
            }else{
                errorMessage = "ユーザIDもしくはパスワードが間違っています2";
                System.out.println("input data user id :"+userId + "password : "+password);
                System.out.println("errorMessage : " + errorMessage);
                return null;
            }
            
        }else{
            errorMessage = "ユーザIDもしくはパスワードが間違っています";
            System.out.println("input data user id :"+userId + "password : "+password);
            System.out.println("errorMessage : " + errorMessage);
            return null;
       }
    }
    
    /* リロード処理 */
    public void reload() throws IOException { 
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI()); 
    } 
    
    /* ログアウト処理 */
    public String logout(){
        invalidate();
        return "/login/login.xhtml?faces-redirect=true";
    }
    
    // セッション破棄
    public void invalidate(){
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        HttpSession session = (HttpSession)ec.getSession(false);
        if(session != null){
            try{
                session.invalidate();
                System.out.println("session invalidate success");
            }catch(IllegalStateException e){
                System.out.println("error : "+e);
            }
        }
    }
    
    /*info関連*/
    public String transToInfoList(){
        if(udm.getUser().getUsertype().equals("admin")||udm.getUser().getUsertype().equals("teacher")){
            System.out.println("インフォ一覧 userId : "+udm.getUser().getUsertype());
            return "/infomation/infolist_upper.xhtml?faces-redirect=true";
        }else if(udm.getUser().getUsertype().equals("student")){
            System.out.println("インフォ一覧 userId : "+udm.getUser().getUsertype());
            return "/infomation/info_list.xhtml?faces-redirect=true";
        }else{
            System.out.println("ユーザ種別が不正です。");
            udm.setErrorMessage("セッションがタイムアウトしました　再度ログインしてください。");
            return "/login/login.xhtml?faces-redirect=true";
        }
    }
    
    /* Profile関連 */
    public String transProfilePage(){
        return "/profiles/profile_index.xhtml?faces-redirect=true";
    }
    
    public String updateUserData(){
        UserData ud = udm.getUser();
        ud.setNickname(nickname);
        udf.edit(ud);
        System.out.println("ニックネームを更新しました");
        udm.setUser(ud);
        System.out.println("変更後のニックネーム : "+udm.getUser().getNickname());
        return null;
    }
    
    public void toggleNickname(){
        if(nicknameToggle = true){
            nickname = null;
        }else{
            getNickname();
        }
    }
        
       
    /* 値の初期化 */
    public void clear(){
        this.userId = null;
        this.password = null;
    }
    
    /* 値の取得 */
    
    
    public String getSecretQuestion(){
        String sq;
        if(udm.getUser().getQuestionId() != null){
            sq = udm.getUser().getQuestionId().getQuestion();
        }else{
            sq = "設定されていません";
        }
        return sq;
    }
    
    public String getSecretQuestion2(){
        String sq;
        if(udm.getUser().getQuestionId2() != null){
            sq = udm.getUser().getQuestionId2().getQuestion();
        }else{
            sq = "設定されていません";
        }
        return sq;
    }
    
    /* セッターゲッター */
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public boolean isNicknameToggle() {
        return nicknameToggle;
    }

    public void setNicknameToggle(boolean nicknameToggle) {
        System.out.println("setnicknameToggle : "+nicknameToggle);
        this.nicknameToggle = nicknameToggle;
    }
    
    public String getNickname(){
        if(udm.getUser().getNickname() != null){
            nickname = udm.getUser().getNickname();
        }else{
            nickname = "設定されていません";
        }
            
        return nickname;
    }
    
    public void setNickname(String nickname){
        System.out.println("setnickname : "+nickname);
        this.nickname = nickname;
    }
    
}
