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
public class ResponsePK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 17)
    @Column(name = "thread_id")
    private String threadId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 17)
    @Column(name = "response_id")
    private String responseId;

    public ResponsePK() {
    }

    public ResponsePK(String threadId, String responseId) {
        this.threadId = threadId;
        this.responseId = responseId;
    }

    public String getThreadId() {
        return threadId;
    }

    public void setThreadId(String threadId) {
        this.threadId = threadId;
    }

    public String getResponseId() {
        return responseId;
    }

    public void setResponseId(String responseId) {
        this.responseId = responseId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (threadId != null ? threadId.hashCode() : 0);
        hash += (responseId != null ? responseId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ResponsePK)) {
            return false;
        }
        ResponsePK other = (ResponsePK) object;
        if ((this.threadId == null && other.threadId != null) || (this.threadId != null && !this.threadId.equals(other.threadId))) {
            return false;
        }
        if ((this.responseId == null && other.responseId != null) || (this.responseId != null && !this.responseId.equals(other.responseId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.ResponsePK[ threadId=" + threadId + ", responseId=" + responseId + " ]";
    }
    
}
