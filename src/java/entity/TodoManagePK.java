/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author glowo
 */
@Embeddable
public class TodoManagePK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 17)
    @Column(name = "todo_id")
    private String todoId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "user_id")
    private String userId;

    public TodoManagePK() {
    }

    public TodoManagePK(String todoId, String userId) {
        this.todoId = todoId;
        this.userId = userId;
    }

    public String getTodoId() {
        return todoId;
    }

    public void setTodoId(String todoId) {
        this.todoId = todoId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (todoId != null ? todoId.hashCode() : 0);
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TodoManagePK)) {
            return false;
        }
        TodoManagePK other = (TodoManagePK) object;
        if ((this.todoId == null && other.todoId != null) || (this.todoId != null && !this.todoId.equals(other.todoId))) {
            return false;
        }
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.TodoManagePK[ todoId=" + todoId + ", userId=" + userId + " ]";
    }
    
}
