/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import entity.Info;
import entity.Pending;
import entity.PendingPK;
import function.ConvertedInfo;
import function.ConvertedPending;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;

@Named(value = "infoDataManager")
@SessionScoped
public class InfoDataManager implements Serializable {

    private Info detailData;
    private ConvertedInfo convData;
    private List<Pending> pendData;
    private List<ConvertedPending> convPendData;
    private PendingPK pendPK;
    private Pending pend;
    
    public void clear(){
        this.convData = null;
        this.detailData = null;
    }

    public Info getDetailData() {
        return detailData;
    }

    public void setDetailData(Info detailData) {
        this.detailData = detailData;
    }

    public ConvertedInfo getConvData() {
        return convData;
    }

    public void setConvData(ConvertedInfo convData) {
        this.convData = convData;
    }

    public List<Pending> getPendData() {
        return pendData;
    }

    public void setPendData(List<Pending> pendData) {
        this.pendData = pendData;
    }

    public List<ConvertedPending> getConvPendData() {
        return convPendData;
    }

    public void setConvPendData(List<ConvertedPending> convPendData) {
        this.convPendData = convPendData;
    }

    public PendingPK getPendPK() {
        return pendPK;
    }

    public void setPendPK(PendingPK pendPK) {
        this.pendPK = pendPK;
    }

    public Pending getPend() {
        return pend;
    }

    public void setPend(Pending pend) {
        this.pend = pend;
    }
    
}
