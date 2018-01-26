package entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
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
@Table(name = "todo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Todo.findAll", query = "SELECT t FROM Todo t")
    , @NamedQuery(name = "Todo.findByTodoId", query = "SELECT t FROM Todo t WHERE t.todoId = :todoId")
    , @NamedQuery(name = "Todo.findByShare", query = "SELECT t FROM Todo t WHERE t.todoId = :todoId and t.term >= :nowtime")
    , @NamedQuery(name = "Todo.findByLabel", query = "SELECT t FROM Todo t WHERE t.label = :label")
    , @NamedQuery(name = "Todo.findByDetail", query = "SELECT t FROM Todo t WHERE t.detail = :detail")
    , @NamedQuery(name = "Todo.findByTerm", query = "SELECT t FROM Todo t WHERE t.term = :term")
    , @NamedQuery(name = "Todo.findByPriority", query = "SELECT t FROM Todo t WHERE t.priority = :priority")
    , @NamedQuery(name = "Todo.findByFinishing", query = "SELECT t FROM Todo t WHERE t.finishing = :finishing")
    , @NamedQuery(name = "Todo.findByGoingTodo", query = "SELECT t FROM Todo t WHERE t.owner = :owner and t.finishing = false and t.term >= :nowtime")
    , @NamedQuery(name = "Todo.findByFinishingTodo", query = "SELECT t FROM Todo t WHERE t.finishing = true")
    , @NamedQuery(name = "Todo.findByExpiredTodo", query = "SELECT t FROM Todo t WHERE t.term < :nowtime")})
public class Todo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 17)
    @Column(name = "todo_id")
    private String todoId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "label")
    private String label;
    @Size(max = 400)
    @Column(name = "detail")
    private String detail;
    @Basic(optional = false)
    @NotNull
    @Column(name = "term")
    @Temporal(TemporalType.TIMESTAMP)
    private Date term;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "priority")
    private String priority;
    @Column(name = "finishing")
    private Boolean finishing;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "todo")
    private Collection<TodoManage> todoManageCollection;
    @JoinColumn(name = "owner", referencedColumnName = "user_id")
    @NotNull
    @ManyToOne(optional = false)
    private UserData owner;

    public Todo() {
    }

    public Todo(String todoId) {
        this.todoId = todoId;
    }

    public Todo(String todoId, String label, Date term, String priority) {
        this.todoId = todoId;
        this.label = label;
        this.term = term;
        this.priority = priority;
    }

    public String getTodoId() {
        return todoId;
    }

    public void setTodoId(String todoId) {
        this.todoId = todoId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Date getTerm() {
        return term;
    }
    
    public String getConvTerm(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        return sdf.format(term);
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

    public Boolean getFinishing() {
        return finishing;
    }

    public void setFinishing(Boolean finishing) {
        this.finishing = finishing;
    }

    public UserData getOwner() {
        return owner;
    }

    public void setOwner(UserData owner) {
        this.owner = owner;
    }
    
    public String getJudge(){
        if(finishing == false){
            return "×";
        }else{
            return "〇";
        }
    }
    
    public String getConvPriority(){
        String cp;
        switch(priority){
            case "low":
                cp = "低い";
                break;
                
            case "normal":
                cp = "普通";
                break;
                
            case "high":
                cp = "高い";
                
            default:
                cp = "Error";
        }
        return cp;
    }

    @XmlTransient
    public Collection<TodoManage> getTodoManageCollection() {
        return todoManageCollection;
    }

    public void setTodoManageCollection(Collection<TodoManage> todoManageCollection) {
        this.todoManageCollection = todoManageCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (todoId != null ? todoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Todo)) {
            return false;
        }
        Todo other = (Todo) object;
        if ((this.todoId == null && other.todoId != null) || (this.todoId != null && !this.todoId.equals(other.todoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Todo[ todoId=" + todoId + " ]";
    }
    
}
