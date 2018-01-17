package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "schedule_manage")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ScheduleManage.findAll", query = "SELECT s FROM ScheduleManage s")
    , @NamedQuery(name = "ScheduleManage.findByScheduleId", query = "SELECT s FROM ScheduleManage s WHERE s.scheduleId = :scheduleId")
    , @NamedQuery(name = "ScheduleManage.findByUserId", query = "SELECT s FROM ScheduleManage s WHERE s.userId = :userId")})
public class ScheduleManage implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 17)
    @Column(name = "schedule_id")
    private String scheduleId;
    @JoinColumn(name = "user_Id", referencedColumnName = "user_id")
    @ManyToOne(optional = false)
    private UserData userId;

    public ScheduleManage() {
    }

    public ScheduleManage(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public UserData getUserId() {
        return userId;
    }

    public void setUserId(UserData userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (scheduleId != null ? scheduleId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ScheduleManage)) {
            return false;
        }
        ScheduleManage other = (ScheduleManage) object;
        if ((this.scheduleId == null && other.scheduleId != null) || (this.scheduleId != null && !this.scheduleId.equals(other.scheduleId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.ScheduleManage[ scheduleId=" + scheduleId + " ]";
    }
    
}
