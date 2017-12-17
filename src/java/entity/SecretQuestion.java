/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author glowo
 */
@Entity
@Table(name = "secret_question")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SecretQuestion.findAll", query = "SELECT s FROM SecretQuestion s")
    , @NamedQuery(name = "SecretQuestion.findByQuestionId", query = "SELECT s FROM SecretQuestion s WHERE s.questionId = :questionId")
    , @NamedQuery(name = "SecretQuestion.findByQuestion", query = "SELECT s FROM SecretQuestion s WHERE s.question = :question")})
public class SecretQuestion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "question_id")
    private String questionId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "question")
    private String question;
    @OneToMany(mappedBy = "questionId2")
    private Collection<UserData> userDataCollection;
    @OneToMany(mappedBy = "questionId")
    private Collection<UserData> userDataCollection1;

    public SecretQuestion() {
    }

    public SecretQuestion(String questionId) {
        this.questionId = questionId;
    }

    public SecretQuestion(String questionId, String question) {
        this.questionId = questionId;
        this.question = question;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    @XmlTransient
    public Collection<UserData> getUserDataCollection() {
        return userDataCollection;
    }

    public void setUserDataCollection(Collection<UserData> userDataCollection) {
        this.userDataCollection = userDataCollection;
    }

    @XmlTransient
    public Collection<UserData> getUserDataCollection1() {
        return userDataCollection1;
    }

    public void setUserDataCollection1(Collection<UserData> userDataCollection1) {
        this.userDataCollection1 = userDataCollection1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (questionId != null ? questionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SecretQuestion)) {
            return false;
        }
        SecretQuestion other = (SecretQuestion) object;
        if ((this.questionId == null && other.questionId != null) || (this.questionId != null && !this.questionId.equals(other.questionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.SecretQuestion[ questionId=" + questionId + " ]";
    }
    
}
