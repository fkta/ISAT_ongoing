package bean;

import ejb.CategoryFacade;
import ejb.DepartmentFacade;
import ejb.TransmitFacade;
import ejb.UserDataFacade;
import entity.Category;
import entity.Department;
import entity.Transmit;
import entity.UserData;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import org.primefaces.model.DualListModel;

@Named(value = "transBean")
@RequestScoped
public class transBean {

    private String sub_;
    private Integer cat_;
    private String des_;
    private String tex_;
//entity
    private Category category_;
    private Department department_;
    private Transmit transmit_ ;
    private UserData userData_;
//ejb
    @EJB CategoryFacade cf;
    @EJB DepartmentFacade df;
    @EJB TransmitFacade tf;
    @EJB UserDataFacade udf;
    
//Inject
    @Inject UserDataManager udm;
    
//DB取得
    public List<Category> getCategoryAll() { return cf.findAll(); }
    public List<Department> getDepartmentAll() { return df.findAll(); }
    public List<Transmit> getTransmitAll() { return tf.findAll(); }
    public List<UserData> getUserDataAll() { return udf.findAll(); }
//ｲﾝｽﾀﾝｽ生成
    private DualListModel<String> cities;
    public DualListModel<String> getCities() { return cities; }
    public void setCities(DualListModel<String> cities) { this.cities = cities; }
    @PostConstruct
    private void init() {
        category_ = new Category();
        department_ = new Department();
        transmit_ = new Transmit();
        userData_ = new UserData();
        
        //pickList
        List<String> citiesSource = new ArrayList<>();
        List<String> citiesTarget = new ArrayList<>();
        
        citiesSource.add("San Francisco");
        citiesSource.add("London");
        citiesSource.add("Paris");
        citiesSource.add("Istanbul");
        citiesSource.add("Berlin");
        citiesSource.add("Barcelona");
        citiesSource.add("Rome");
        
        cities = new DualListModel<>(citiesSource, citiesTarget);
        /**************************/
    }
//再表示
    public String agein() {
        System.out.println("件名："+sub_);
        createTransmit();
        return null;
    }
//新規作成
    public void createTransmit(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        transmit_.setTransmitId("tra"+sdf.format(new Date()));
        transmit_.setCategoryId(cf.findByCategoryId(cat_).get(0));
        transmit_.setSender(udf.findByUserId(udm.getUser().getUserId()).get(0));
        transmit_.setDestination(udf.findByUserId(des_).get(0));
        Date date = new Date();
        transmit_.setSenddate(date);
        tf.create(transmit_);
    }
/***********/
    public void showDetail() {
        
    }
    
    
    private String class_;
    public String getClass_() {
        return class_;
    }
    public void setClass_(String class_) {
        this.class_ = class_;
    }
/***********/
    
/*
    SelectItem(value,label)
    list.add
*/
    
//getset
    public String getSub_() {
        return sub_;
    }

    public void setSub_(String sub_) {
        this.sub_ = sub_;
    }

    public Integer getCat_() {
        return cat_;
    }

    public void setCat_(Integer cat_) {
        this.cat_ = cat_;
    }

    public String getDes_() {
        return des_;
    }

    public void setDes_(String des_) {
        this.des_ = des_;
    }
    
//getsetentity
    public Category getCategory_() {
        return category_;
    }

    public void setCategory_(Category category_) {
        this.category_ = category_;
    }

    public Department getDepartment_() {
        return department_;
    }

    public void setDepartment_(Department department_) {
        this.department_ = department_;
    }

    public Transmit getTransmit_() {
        return transmit_;
    }

    public void setTransmit_(Transmit transmit_) {
        this.transmit_ = transmit_;
    }

    public UserData getUserData_() {
        return userData_;
    }

    public void setUserData_(UserData userData_) {
        this.userData_ = userData_;
    }

    public String getTex_() {
        return tex_;
    }

    public void setTex_(String tex_) {
        this.tex_ = tex_;
    }
    
}
