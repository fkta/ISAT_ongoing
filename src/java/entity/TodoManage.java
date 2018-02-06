/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "todo_manage")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TodoManage.findAll", query = "SELECT t FROM TodoManage t")
    , @NamedQuery(name = "TodoManage.findByTodoId", query = "SELECT t FROM TodoManage t WHERE t.todoManagePK.todoId = :todoId")
    , @NamedQuery(name = "TodoManage.findByUserId", query = "SELECT t FROM TodoManage t WHERE t.todoManagePK.userId = :userId")
    , @NamedQuery(name = "TodoManage.findByState", query = "SELECT t FROM TodoManage t WHERE t.finishing = :finishing")})
public class TodoManage implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "finishing")
    private boolean finishing;

    @Basic(optional = false)
    @NotNull
    @Column(name = "display")
    private boolean display;

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TodoManagePK todoManagePK;
    @JoinColumn(name = "todo_id", referencedColumnName = "todo_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Todo todo;

    public TodoManage() {
    }

    public TodoManage(TodoManagePK todoManagePK) {
        this.todoManagePK = todoManagePK;
    }

    public TodoManage(String todoId, String userId) {
        this.todoManagePK = new TodoManagePK(todoId, userId);
    }

    public TodoManagePK getTodoManagePK() {
        return todoManagePK;
    }

    public void setTodoManagePK(TodoManagePK todoManagePK) {
        this.todoManagePK = todoManagePK;
    }


    public Todo getTodo() {
        return todo;
    }

    public void setTodo(Todo todo) {
        this.todo = todo;
    }
    
    public String Judge(){
        if(finishing == true){
            return "〇";
        }else{
            return "×";
        }
            
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (todoManagePK != null ? todoManagePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TodoManage)) {
            return false;
        }
        TodoManage other = (TodoManage) object;
        if ((this.todoManagePK == null && other.todoManagePK != null) || (this.todoManagePK != null && !this.todoManagePK.equals(other.todoManagePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        if(finishing == true){
            return "〇,"+todoManagePK.getUserId();
        }else{
            return "×,"+todoManagePK.getUserId();
        }
    }
    
    /*public String toString() {
        return "entity.TodoManage[ todoManagePK=" + todoManagePK + " ]";
    }*/

    public boolean getDisplay() {
        return display;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }

    public boolean getFinishing() {
        return finishing;
    }

    public void setFinishing(boolean finishing) {
        this.finishing = finishing;
    }
    
}
