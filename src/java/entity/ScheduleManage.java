package entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "schedule_manage")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ScheduleManage.findAll", query = "SELECT s FROM ScheduleManage s")
    , @NamedQuery(name = "ScheduleManage.findByScheduleId", query = "SELECT s FROM ScheduleManage s WHERE s.scheduleManagePK.scheduleId = :scheduleId")
    , @NamedQuery(name = "ScheduleManage.findByUserId", query = "SELECT s FROM ScheduleManage s WHERE s.scheduleManagePK.userId = :userId")})
public class ScheduleManage implements Serializable {

    @Size(max = 10)
    @Column(name = "emptyfield")
    private String emptyfield;

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ScheduleManagePK scheduleManagePK;

    public ScheduleManage() {
    }

    public ScheduleManage(ScheduleManagePK scheduleManagePK) {
        this.scheduleManagePK = scheduleManagePK;
    }

    public ScheduleManage(String scheduleId, String userId) {
        this.scheduleManagePK = new ScheduleManagePK(scheduleId, userId);
    }

    public ScheduleManagePK getScheduleManagePK() {
        return scheduleManagePK;
    }

    public void setScheduleManagePK(ScheduleManagePK scheduleManagePK) {
        this.scheduleManagePK = scheduleManagePK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (scheduleManagePK != null ? scheduleManagePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ScheduleManage)) {
            return false;
        }
        ScheduleManage other = (ScheduleManage) object;
        if ((this.scheduleManagePK == null && other.scheduleManagePK != null) || (this.scheduleManagePK != null && !this.scheduleManagePK.equals(other.scheduleManagePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.ScheduleManage[ scheduleManagePK=" + scheduleManagePK + " ]";
    }

    public String getEmptyfield() {
        return emptyfield;
    }

    public void setEmptyfield(String emptyfield) {
        this.emptyfield = emptyfield;
    }
    
}
