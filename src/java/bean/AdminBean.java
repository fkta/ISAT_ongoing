package bean;

import ejb.CategoryFacade;
import ejb.DepartmentFacade;
import ejb.SecretQuestionFacade;
import ejb.ServiceFacade;
import function.FileUpload;
import ejb.UserDataFacade;
import entity.Category;
import entity.Department;
import entity.SecretQuestion;
import entity.Service;
import entity.UserData;
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
    private List<SecretQuestion> squList;
    private List<UserData> udList;
    private List<Category> catList;
    private List<Service> srvList;

    @EJB
    UserDataFacade udf;
    @EJB
    DepartmentFacade df;
    @EJB
    SecretQuestionFacade sqf;
    @EJB
    CategoryFacade cf;
    @EJB
    ServiceFacade sf;
    
    @Inject
    AdminDataManager adm;
    
    public void upload(){
        Path path;
        path = Paths.get(System.getProperty("user.home"),"sotsuken", "files", file.getSubmittedFileName());
        adm.clear();
        adm.setPath(path);
        FileUpload fu = new FileUpload(file,path);
        fu.submit();
    }
    
    /*department関連*/
    public String addDpt(){
        if(file != null){
            if(file.getSubmittedFileName().endsWith(".csv")){
                System.out.println("csvファイルを認識しました。");
                upload();
                CsvReader cr = new CsvReader();
                adm.setDptList(cr.readDpt(adm.getPath().toString()));
                dptList = adm.getDptList();
                return "dptconfirmation.xhtml";
            }else{
                System.out.println("csvファイルではありません。");
                return null;
            }
        }else{
            System.out.println("ファイルがありません");
            return null;
        }
    }
    
    public String dptCreate(){
        dptList = adm.getDptList();
        for(int i = 0; i < dptList.size(); i++){
            Department dpt = dptList.get(i);
            df.create(dpt);
        }
        return "dataaddmenu.xhtml";
    }
    
    
    
    public String dptDelete(Department dpt){
        df.remove(dpt);
        return null;
    }
    
    /*SecretQuestion関連*/
    public String addSqu(){
        if(file != null){
            if(file.getSubmittedFileName().endsWith(".csv")){
                System.out.println("csvファイルを認識しました。");
                upload();
                CsvReader cr = new CsvReader();
                adm.setSquList(cr.readSqu(adm.getPath().toString()));
                squList = adm.getSquList();
                return "squconfirmation.xhtml";
            }else{
                System.out.println("csvファイルではありません。");
                return null;
            }
        }else{
            System.out.println("ファイルがありません");
            return null;
        }
    }
    
    public String squCreate(){
        squList = adm.getSquList();
        for(int i = 0; i < squList.size(); i++){
            SecretQuestion sq = squList.get(i);
            sqf.create(sq);
        }
        return "dataaddmenu.xhtml";
    }
    
    public String squDelete(SecretQuestion sq){
        sqf.remove(sq);
        return null;
    }
    
    /* UserData関連 */
    public String addUd(){
        if(file != null){
            if(file.getSubmittedFileName().endsWith(".csv")){
                System.out.println("csvファイルを認識しました。");
                upload();
                CsvReader cr = new CsvReader();
                adm.setUserList(cr.readUd(adm.getPath().toString()));
                udList = adm.getUserList();
                return "udconfirmation.xhtml";
            }else{
                System.out.println("csvファイルではありません。");
                return null;
            }
        }else{
            System.out.println("ファイルがありません");
            return null;
        }
    }
    
    public String udCreate(){
        udList = adm.getUserList();
        for(int i = 0; i < udList.size(); i++){
            UserData ud = udList.get(i);
            udf.create(ud);
        }
        return "dataaddmenu.xhtml";
    }
    
    public String udDelete(UserData ud){
        udf.remove(ud);
        return null;
    }
    
    /*Category関連 */
    public String addCat(){
        if(file != null){
            if(file.getSubmittedFileName().endsWith(".csv")){
                System.out.println("csvファイルを認識しました。");
                upload();
                CsvReader cr = new CsvReader();
                adm.setCatList(cr.readCat(adm.getPath().toString()));
                catList = adm.getCatList();
                return "catconfirmation.xhtml";
            }else{
                System.out.println("csvファイルではありません。");
                return null;
            }
        }else{
            System.out.println("ファイルがありません");
            return null;
        }
    }
    
    public String catCreate(){
        catList = adm.getCatList();
        for(int i = 0; i < catList.size(); i++){
            Category cat = catList.get(i);
            cf.create(cat);
        }
        return "dataaddmenu.xhtml";
    }
    
    public String catDelete(Category cat){
        cf.remove(cat);
        return null;
    }
    
    /* Service関連 */
    public String addSrv(){
        if(file != null){
            if(file.getSubmittedFileName().endsWith(".csv")){
                System.out.println("csvファイルを認識しました。");
                upload();
                CsvReader cr = new CsvReader();
                adm.setSrvList(cr.readSrv(adm.getPath().toString()));
                srvList = adm.getSrvList();
                return "srvconfirmation.xhtml";
            }else{
                System.out.println("csvファイルではありません。");
                return null;
            }
        }else{
            System.out.println("ファイルがありません");
            return null;
        }
    }
    
    public String srvCreate(){
        srvList = adm.getSrvList();
        for(int i = 0; i < srvList.size(); i++){
            Service srv = srvList.get(i);
            sf.create(srv);
        }
        return "dataaddmenu.xhtml";
    }
    
    public String srvDelete(Service srv){
        sf.remove(srv);
        return null;
    }
    
    /* 登録済みデータのゲッター */    
    public List<Department> getAllDpt(){
        return df.findAll();
    }
    
    public List<SecretQuestion> getAllSqu(){
        return sqf.findAll();
    }
    
    public List<UserData> getAllUd(){
        return udf.findAll();
    }
    
    public List<Category> getAllCat(){
        return cf.findAll();
    }
    
    public List<Service> getAllSrv(){
        return sf.findAll();
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

    public List<SecretQuestion> getSquList() {
        return squList;
    }

    public void setSquList(List<SecretQuestion> squList) {
        this.squList = squList;
    }

    public List<UserData> getUdList() {
        return udList;
    }

    public void setUdList(List<UserData> udList) {
        this.udList = udList;
    }

    public List<Category> getCatList() {
        return catList;
    }

    public void setCatList(List<Category> catList) {
        this.catList = catList;
    }

    public List<Service> getSrvList() {
        return srvList;
    }

    public void setSrvList(List<Service> srvList) {
        this.srvList = srvList;
    }
    
    
}
