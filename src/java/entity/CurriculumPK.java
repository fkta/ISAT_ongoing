package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Embeddable
public class CurriculumPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "department_id")
    private int departmentId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "day")
    private String day;
    @Basic(optional = false)
    @NotNull
    @Column(name = "period")
    private Character period;

    public CurriculumPK() {
    }

    public CurriculumPK(int departmentId, String day, Character period) {
        this.departmentId = departmentId;
        this.day = day;
        this.period = period;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Character getPeriod() {
        return period;
    }

    public void setPeriod(Character period) {
        this.period = period;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) departmentId;
        hash += (day != null ? day.hashCode() : 0);
        hash += (period != null ? period.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CurriculumPK)) {
            return false;
        }
        CurriculumPK other = (CurriculumPK) object;
        if (this.departmentId != other.departmentId) {
            return false;
        }
        if ((this.day == null && other.day != null) || (this.day != null && !this.day.equals(other.day))) {
            return false;
        }
        if ((this.period == null && other.period != null) || (this.period != null && !this.period.equals(other.period))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.CurriculumPK[ departmentId=" + departmentId + ", day=" + day + ", period=" + period + " ]";
    }
    
}
