package bean;

import entity.BulletinBoard;
import entity.Response;
import function.ConvertBulletinBoard;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;

@Named(value = "boardDataManager")
@SessionScoped
public class BoardDataManager implements Serializable {
    private BulletinBoard threadDetailData;
    private List<ConvertBulletinBoard> bList;
    private ConvertBulletinBoard cbbDetail;
    private List<Response> responseList;

    public List<ConvertBulletinBoard> getbList() {
        return bList;
    }

    public void setbList(List<ConvertBulletinBoard> bList) {
        this.bList = bList;
    }

    public ConvertBulletinBoard getCbbDetail() {
        return cbbDetail;
    }

    public void setCbbDetail(ConvertBulletinBoard cbbDetail) {
        this.cbbDetail = cbbDetail;
    }

    public List<Response> getResponseList() {
        return responseList;
    }

    public void setResponseList(List<Response> responseList) {
        this.responseList = responseList;
    }

    public BulletinBoard getThreadDetailData() {
        return threadDetailData;
    }

    public void setThreadDetailData(BulletinBoard threadDetailData) {
        this.threadDetailData = threadDetailData;
    }

}
