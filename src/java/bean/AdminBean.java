package bean;

import function.FileUpload;
import ejb.UserDataFacade;
import function.CsvReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.Part;

@Named(value = "adminBean")
@RequestScoped
public class AdminBean {
    private Part file;

    @EJB
    UserDataFacade udf;
    
    @Inject
    DataManager dm;
    
    public String next(){
        upload();
        csvRead();
        return "confirmation.xhtml";
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
    
    
    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }
    
    
}
