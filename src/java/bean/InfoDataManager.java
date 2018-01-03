/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import entity.Info;
import function.ConvertedInfo;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

@Named(value = "infoDataManager")
@SessionScoped
public class InfoDataManager implements Serializable {

    private Info detailData;
    private ConvertedInfo convData;
    
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
    
    
    
}
