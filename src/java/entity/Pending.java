/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author glowo
 */
@Entity
@Table(name = "pending")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pending.findAll", query = "SELECT p FROM Pending p order by p.postDate desc")
    , @NamedQuery(name = "Pending.findByInfoId", query = "SELECT p FROM Pending p WHERE p.pendingPK.infoId = :infoId")
    , @NamedQuery(name = "Pending.findByUserId", query = "SELECT p FROM Pending p WHERE p.pendingPK.userId = :userId")
    , @NamedQuery(name = "Pending.findByReason", query = "SELECT p FROM Pending p WHERE p.reason = :reason")
    , @NamedQuery(name = "Pending.findByPendingCategory", query = "SELECT p FROM Pending p WHERE p.pendingCategory = :pendingCategory")
    , @NamedQuery(name = "Pending.findByPostDate", query = "SELECT p FROM Pending p WHERE p.postDate = :postDate")
    , @NamedQuery(name = "Pending.findByPK", query = "SELECT p FROM Pending p WHERE p.pendingPK.infoId = :infoId and p.pendingPK.userId = :userId")})
public class Pending implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PendingPK pendingPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 400)
    @Column(name = "reason")
    private String reason;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "pending_category")
    private String pendingCategory;
    @Basic(optional = false)
    @NotNull
    @Column(name = "post_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date postDate;

    public Pending() {
    }

    public Pending(PendingPK pendingPK) {
        this.pendingPK = pendingPK;
    }

    public Pending(PendingPK pendingPK, String reason, String pendingCategory, Date postDate) {
        this.pendingPK = pendingPK;
        this.reason = reason;
        this.pendingCategory = pendingCategory;
        this.postDate = postDate;
    }

    public Pending(String infoId, String userId) {
        this.pendingPK = new PendingPK(infoId, userId);
    }

    public PendingPK getPendingPK() {
        return pendingPK;
    }

    public void setPendingPK(PendingPK pendingPK) {
        this.pendingPK = pendingPK;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getPendingCategory() {
        return pendingCategory;
    }

    public void setPendingCategory(String pendingCategory) {
        this.pendingCategory = pendingCategory;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pendingPK != null ? pendingPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pending)) {
            return false;
        }
        Pending other = (Pending) object;
        if ((this.pendingPK == null && other.pendingPK != null) || (this.pendingPK != null && !this.pendingPK.equals(other.pendingPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Pending[ pendingPK=" + pendingPK + " ]";
    }
    
}
