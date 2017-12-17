package bean;

import entity.Department;
import entity.SecretQuestion;
import entity.UserData;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.nio.file.Path;
import java.util.List;
import javax.servlet.http.Part;

@Named(value = "fileManager")
@SessionScoped
public class DataManager implements Serializable {
    private Part file;
    private Path path;
    private List<UserData> userList;
    private List<Department> dptList;
    private List<SecretQuestion> squList;

    public void clear(){
        file = null;
        path = null;
        userList = null;
        dptList = null;
        squList = null;
    }
    
    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public List<UserData> getUserList() {
        return userList;
    }

    public void setUserList(List<UserData> userList) {
        this.userList = userList;
    }

    public List<Department> getDptList() {
        return dptList;
    }

    public void setDptList(List<Department> dptList) {
        this.dptList = dptList;
    }

    public List<SecretQuestion> getSquList() {
        return squList;
    }

    public void setSquList(List<SecretQuestion> squList) {
        this.squList = squList;
    }
    
    
    
}
