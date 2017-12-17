package bean;

import ejb.DepartmentFacade;
import function.FileUpload;
import ejb.UserDataFacade;
import entity.Department;
import function.CsvReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.Part;

@Named(value = "adminBean")
@RequestScoped
public class AdminBean {
    private Part file;
    private List<Department> dptList;

    @EJB
    UserDataFacade udf;
    @EJB
    DepartmentFacade df;
    
    @Inject
    DataManager dm;
    
    public String next(){
        upload();
        csvRead();
        dptList = dm.getDptList();
        return "confirmation.xhtml";
    }
    
    public String create(){
        dptList = dm.getDptList();
        for(int i = 0; i < dptList.size(); i++){
            Department dpt = dptList.get(i);
            df.create(dpt);
        }
        return "dataaddmenu.xhtml";
    }
    
    public void upload(){
        Path path;
        path = Paths.get(System.getProperty("user.home"),"sotsuken", "files", file.getSubmittedFileName());
        dm.clear();
        dm.setPath(path);
        FileUpload fu = new FileUpload(file,path);
        fu.submit();
    }
    
    public void csvRead(){
        CsvReader cr = new CsvReader();
        dm.setDptList(cr.read_dpt(dm.getPath().toString()));
    }
    
    /* 登録済みデータのゲッター */
    public List<Department> getAllDpt(){
        return df.findAll();
    }
        
    /* セッターゲッター */
    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public List<Department> getDptList() {
        return dptList;
    }

    public void setDptList(List<Department> dptList) {
        this.dptList = dptList;
    }
    
    
    
}
