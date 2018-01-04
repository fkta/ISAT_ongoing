/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author glowo
 */
@Embeddable
public class PendingPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 17)
    @Column(name = "info_id")
    private String infoId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "user_id")
    private String userId;

    public PendingPK() {
    }

    public PendingPK(String infoId, String userId) {
        this.infoId = infoId;
        this.userId = userId;
    }

    public String getInfoId() {
        return infoId;
    }

    public void setInfoId(String infoId) {
        this.infoId = infoId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (infoId != null ? infoId.hashCode() : 0);
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PendingPK)) {
            return false;
        }
        PendingPK other = (PendingPK) object;
        if ((this.infoId == null && other.infoId != null) || (this.infoId != null && !this.infoId.equals(other.infoId))) {
            return false;
        }
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.PendingPK[ infoId=" + infoId + ", userId=" + userId + " ]";
    }
    
}
