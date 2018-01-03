package bean;

import ejb.UserDataFacade;
import entity.UserData;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
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
    
    public String userAuth(){
        UserData ud = udf.userAuth(userId, password);
        if(ud != null){
            udm.setUser(ud);
            clear();
            return "/infomation/info_list.xhtml?faces-redirect=true";
        }else{
            errorMessage = "ユーザIDもしくはパスワードが間違っています";
            clear();
            return null;
       }
    }
    
    /* ログアウト処理 */
    public String logout(){
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
