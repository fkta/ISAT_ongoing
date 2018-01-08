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

@Entity
@Table(name = "info")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Info.findAll", query = "SELECT i FROM Info i")
    , @NamedQuery(name = "Info.findByInfoId", query = "SELECT i FROM Info i WHERE i.infoId = :infoId order by i.term desc")
    , @NamedQuery(name = "Info.findByTitle", query = "SELECT i FROM Info i WHERE i.title = :title")
    , @NamedQuery(name = "Info.findByContent", query = "SELECT i FROM Info i WHERE i.content = :content")
    , @NamedQuery(name = "Info.findByTerm", query = "SELECT i FROM Info i WHERE i.term = :term")
    , @NamedQuery(name = "Info.findByPriority", query = "SELECT i FROM Info i WHERE i.priority = :priority")
    , @NamedQuery(name = "Info.findByInfotype", query = "SELECT i FROM Info i WHERE i.infotype = :infotype")
    , @NamedQuery(name = "Info.findInfo", query = "SELECT i FROM Info i WHERE i.term >= :now and i.infotype = 'display' or i.infotype = 'delete_pending' order by i.term desc")
    , @NamedQuery(name = "Info.findByUserId", query = "SELECT i FROM Info i WHERE i.userId.userId = :userId")})
public class Info implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 17)
    @Column(name = "info_id")
    private String infoId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "title")
    private String title;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2000)
    @Column(name = "content")
    private String content;
    @Basic(optional = false)
    @NotNull
    @Column(name = "term")
    @Temporal(TemporalType.TIMESTAMP)
    private Date term;
    @Basic(optional = false)
    @NotNull
    @Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "priority")
    private String priority;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "infotype")
    private String infotype;
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    @ManyToOne(optional = false)
    private Category categoryId;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne(optional = false)
    private UserData userId;

    public Info() {
    }

    public Info(String infoId) {
        this.infoId = infoId;
    }

    public Info(String infoId, String title, String content, Date term, String priority, String infotype) {
        this.infoId = infoId;
        this.title = title;
        this.content = content;
        this.term = term;
        this.priority = priority;
        this.infotype = infotype;
    }

    public String getInfoId() {
        return infoId;
    }

    public void setInfoId(String infoId) {
        this.infoId = infoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTerm() {
        return term;
    }

    public void setTerm(Date term) {
        this.term = term;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getInfotype() {
        return infotype;
    }

    public void setInfotype(String infotype) {
        this.infotype = infotype;
    }

    public Category getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Category categoryId) {
        this.categoryId = categoryId;
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
        hash += (infoId != null ? infoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Info)) {
            return false;
        }
        Info other = (Info) object;
        if ((this.infoId == null && other.infoId != null) || (this.infoId != null && !this.infoId.equals(other.infoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Info[ infoId=" + infoId + " ]";
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    
}
