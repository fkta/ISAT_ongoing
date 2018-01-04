package bean;

import ejb.UserDataFacade;
import entity.UserData;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    @EJB
    UserDataFacade udf;
    @Inject
    UserDataManager udm;
    
    /* ユーザ認証処理 */
    public String userAuth(){
        List<UserData> ud = udf.userAuth(userId, password);
        if(!ud.isEmpty()){
            udm.setUser(ud.get(0));
            return "/infomation/info_list.xhtml?faces-redirect=true";
        }else{
            errorMessage = "ユーザIDもしくはパスワードが間違っています";
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
    
    /* Profile関連 */
    public String transProfilePage(){
        return "/profile/profile.xhtml?faces-redirect=true";
    }
       
    /* 値の初期化 */
    public void clear(){
        this.userId = null;
        this.password = null;
    }
    

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
    
    
}
