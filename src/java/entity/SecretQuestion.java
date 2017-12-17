/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "question_id")
    private Integer questionId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "question")
    private String question;

    public SecretQuestion() {
    }

    public SecretQuestion(Integer questionId) {
        this.questionId = questionId;
    }

    public SecretQuestion(Integer questionId, String question) {
        this.questionId = questionId;
        this.question = question;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
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
