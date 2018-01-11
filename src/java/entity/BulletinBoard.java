package entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "bulletin_board")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BulletinBoard.findAll", query = "SELECT b FROM BulletinBoard b")
    , @NamedQuery(name = "BulletinBoard.findByThreadId", query = "SELECT b FROM BulletinBoard b WHERE b.threadId = :threadId")
    , @NamedQuery(name = "BulletinBoard.findByTitle", query = "SELECT b FROM BulletinBoard b WHERE b.title = :title")
    , @NamedQuery(name = "BulletinBoard.findByPostDate", query = "SELECT b FROM BulletinBoard b WHERE b.postDate = :postDate")
    , @NamedQuery(name = "BulletinBoard.orderByPostDate", query = "SELECT b FROM BulletinBoard b ORDER BY b.postDate DESC")})
public class BulletinBoard implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 17)
    @Column(name = "thread_id")
    private String threadId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "title")
    private String title;
    @Basic(optional = false)
    @NotNull
    @Column(name = "post_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date postDate;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne(optional = false)
    private UserData userId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bulletinBoard")
    private Collection<Response> responseCollection;

    public BulletinBoard() {
    }

    public BulletinBoard(String threadId) {
        this.threadId = threadId;
    }

    public BulletinBoard(String threadId, String title, Date postDate, UserData userId) {
        this.threadId = threadId;
        this.title = title;
        this.postDate = postDate;
        this.userId = userId;
    }

    public String getThreadId() {
        return threadId;
    }

    public void setThreadId(String threadId) {
        this.threadId = threadId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public UserData getUserId() {
        return userId;
    }

    public void setUserId(UserData userId) {
        this.userId = userId;
    }

    @XmlTransient
    public Collection<Response> getResponseCollection() {
        return responseCollection;
    }

    public void setResponseCollection(Collection<Response> responseCollection) {
        this.responseCollection = responseCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (threadId != null ? threadId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BulletinBoard)) {
            return false;
        }
        BulletinBoard other = (BulletinBoard) object;
        if ((this.threadId == null && other.threadId != null) || (this.threadId != null && !this.threadId.equals(other.threadId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.BulletinBoard[ threadId=" + threadId + " ]";
    }
    
}
