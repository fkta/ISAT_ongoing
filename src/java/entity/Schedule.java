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
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "schedule")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Schedule.findAll", query = "SELECT s FROM Schedule s")
    , @NamedQuery(name = "Schedule.findByScheduleId", query = "SELECT s FROM Schedule s WHERE s.scheduleId = :scheduleId")
    , @NamedQuery(name = "Schedule.findByTitle", query = "SELECT s FROM Schedule s WHERE s.title = :title")
    , @NamedQuery(name = "Schedule.findBySDate", query = "SELECT s FROM Schedule s WHERE s.sDate = :sDate")
    , @NamedQuery(name = "Schedule.findByEDate", query = "SELECT s FROM Schedule s WHERE s.eDate = :eDate")
    , @NamedQuery(name = "Schedule.findByContent", query = "SELECT s FROM Schedule s WHERE s.content = :content")})
public class Schedule implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 17)
    @Column(name = "schedule_id")
    private String scheduleId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "title")
    private String title;
    @Basic(optional = false)
    @NotNull
    @Column(name = "s_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "e_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date eDate;
    @Size(max = 200)
    @Column(name = "content")
    private String content;
    @JoinColumn(name = "owner", referencedColumnName = "user_id")
    @ManyToOne(optional = false)
    private UserData owner;

    public Schedule() {
    }

    public Schedule(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Schedule(String scheduleId, String title, Date sDate, Date eDate) {
        this.scheduleId = scheduleId;
        this.title = title;
        this.sDate = sDate;
        this.eDate = eDate;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getSDate() {
        return sDate;
    }

    public void setSDate(Date sDate) {
        this.sDate = sDate;
    }

    public Date getEDate() {
        return eDate;
    }

    public void setEDate(Date eDate) {
        this.eDate = eDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserData getOwner() {
        return owner;
    }

    public void setOwner(UserData owner) {
        this.owner = owner;
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
        if (!(object instanceof Schedule)) {
            return false;
        }
        Schedule other = (Schedule) object;
        if ((this.scheduleId == null && other.scheduleId != null) || (this.scheduleId != null && !this.scheduleId.equals(other.scheduleId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Schedule[ scheduleId=" + scheduleId + " ]";
    }
    
}
