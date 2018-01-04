package bean;

import entity.UserData;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

@Named(value = "userDataManager")
@SessionScoped
public class UserDataManager implements Serializable {
    private UserData user;
    private String errorMessage;
    
    public void clear(){
        this.user = null;
    }

    public UserData getUser() {
        return user;
    }

    public void setUser(UserData user) {
        this.user = user;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
    
}
