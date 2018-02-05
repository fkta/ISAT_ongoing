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
@Table(name = "transmit")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Transmit.findAll", query = "SELECT t FROM Transmit t")
    , @NamedQuery(name = "Transmit.findByTransmitId", query = "SELECT t FROM Transmit t WHERE t.transmitId = :transmitId")
    , @NamedQuery(name = "Transmit.findByTitle", query = "SELECT t FROM Transmit t WHERE t.title = :title")
    , @NamedQuery(name = "Transmit.findByContent", query = "SELECT t FROM Transmit t WHERE t.content = :content")
    , @NamedQuery(name = "Transmit.findBySenddate", query = "SELECT t FROM Transmit t WHERE t.senddate = :senddate")})
public class Transmit implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 17)
    @Column(name = "transmit_id")
    private String transmitId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "title")
    private String title;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1000)
    @Column(name = "content")
    private String content;
    @Basic(optional = false)
    @NotNull
    @Column(name = "senddate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date senddate;
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    @ManyToOne(optional = false)
    private Category categoryId;@JoinColumn(name = "sender", referencedColumnName = "user_id")
    @ManyToOne(optional = false)
    private UserData sender;
    @JoinColumn(name = "destination", referencedColumnName = "user_id")
    @ManyToOne(optional = false)
    private UserData destination;

    public Transmit() {
    }

    public Transmit(String transmitId) {
        this.transmitId = transmitId;
    }

    public Transmit(String transmitId, String title, String content, Date senddate) {
        this.transmitId = transmitId;
        this.title = title;
        this.content = content;
        this.senddate = senddate;
    }

    public String getTransmitId() {
        return transmitId;
    }

    public void setTransmitId(String transmitId) {
        this.transmitId = transmitId;
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

    public Date getSenddate() {
        return senddate;
    }

    public void setSenddate(Date senddate) {
        this.senddate = senddate;
    }

    public Category getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Category categoryId) {
        this.categoryId = categoryId;
    }

    public UserData getSender() {
        return sender;
    }

    public void setSender(UserData sender) {
        this.sender = sender;
    }

    public UserData getDestination() {
        return destination;
    }

    public void setDestination(UserData destination) {
        this.destination = destination;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (transmitId != null ? transmitId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Transmit)) {
            return false;
        }
        Transmit other = (Transmit) object;
        if ((this.transmitId == null && other.transmitId != null) || (this.transmitId != null && !this.transmitId.equals(other.transmitId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Transmit[ transmitId=" + transmitId + " ]";
    }
    
}
