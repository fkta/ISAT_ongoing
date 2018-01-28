package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "curriculum")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Curriculum.findAll", query = "SELECT c FROM Curriculum c")
    , @NamedQuery(name = "Curriculum.findByDepartmentId", query = "SELECT c FROM Curriculum c WHERE c.curriculumPK.departmentId = :departmentId")
    , @NamedQuery(name = "Curriculum.findByDayCurriculum", query = "SELECT c FROM Curriculum c WHERE c.curriculumPK.departmentId = :departmentId and c.curriculumPK.day = :day and c.curriculumPK.period = :period")
    , @NamedQuery(name = "Curriculum.findByDay", query = "SELECT c FROM Curriculum c WHERE c.curriculumPK.day = :day")
    , @NamedQuery(name = "Curriculum.findByPeriod", query = "SELECT c FROM Curriculum c WHERE c.curriculumPK.period = :period")
    , @NamedQuery(name = "Curriculum.findBySubject", query = "SELECT c FROM Curriculum c WHERE c.subject = :subject")})
public class Curriculum implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "classroom")
    private String classroom;

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CurriculumPK curriculumPK;
    @Size(max = 100)
    @Column(name = "subject")
    private String subject;

    public Curriculum() {
    }

    public Curriculum(CurriculumPK curriculumPK) {
        this.curriculumPK = curriculumPK;
    }

    public Curriculum(int departmentId, String day, Character period) {
        this.curriculumPK = new CurriculumPK(departmentId, day, period);
    }

    public CurriculumPK getCurriculumPK() {
        return curriculumPK;
    }

    public void setCurriculumPK(CurriculumPK curriculumPK) {
        this.curriculumPK = curriculumPK;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (curriculumPK != null ? curriculumPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Curriculum)) {
            return false;
        }
        Curriculum other = (Curriculum) object;
        if ((this.curriculumPK == null && other.curriculumPK != null) || (this.curriculumPK != null && !this.curriculumPK.equals(other.curriculumPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Curriculum[ curriculumPK=" + curriculumPK + " ]";
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }
    
}
