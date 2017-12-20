package bean;

import ejb.InfoFacade;
import entity.Info;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

@Named(value = "infomationBean")
@RequestScoped
public class InfomationBean {
    @EJB
    InfoFacade inf;
    
    public List<Info> getAllInfo(){
        return inf.findAll();
    }
    
}
