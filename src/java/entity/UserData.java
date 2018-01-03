package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "user_data")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserData.findAll", query = "SELECT u FROM UserData u")
    , @NamedQuery(name = "UserData.findByUserId", query = "SELECT u FROM UserData u WHERE u.userId = :userId")
    , @NamedQuery(name = "UserData.findByEntranceYear", query = "SELECT u FROM UserData u WHERE u.entranceYear = :entranceYear")
    , @NamedQuery(name = "UserData.findByGuraduationYear", query = "SELECT u FROM UserData u WHERE u.guraduationYear = :guraduationYear")
    , @NamedQuery(name = "UserData.findByName", query = "SELECT u FROM UserData u WHERE u.name = :name")
    , @NamedQuery(name = "UserData.findByNickname", query = "SELECT u FROM UserData u WHERE u.nickname = :nickname")
    , @NamedQuery(name = "UserData.findByGender", query = "SELECT u FROM UserData u WHERE u.gender = :gender")
    , @NamedQuery(name = "UserData.findByUsertype", query = "SELECT u FROM UserData u WHERE u.usertype = :usertype")
    , @NamedQuery(name = "UserData.findByPassword", query = "SELECT u FROM UserData u WHERE u.password = :password")
    , @NamedQuery(name = "UserData.findByQanswer", query = "SELECT u FROM UserData u WHERE u.qanswer = :qanswer")
    , @NamedQuery(name = "UserData.findByQanswer2", query = "SELECT u FROM UserData u WHERE u.qanswer2 = :qanswer2")
    , @NamedQuery(name = "UserData.authUser", query = "SELECT u FROM UserData u WHERE u.userId = :userId and u.password = :password")})
public class UserData implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "user_id")
    private String userId;
    @Size(max = 4)
    @Column(name = "entrance_year")
    private String entranceYear;
    @Size(max = 4)
    @Column(name = "guraduation_year")
    private String guraduationYear;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "name")
    private String name;
    @Size(max = 40)
    @Column(name = "nickname")
    private String nickname;
    @Basic(optional = false)
    @NotNull
    @Column(name = "gender")
    private Character gender;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "usertype")
    private String usertype;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "password")
    private String password;
    @Size(max = 100)
    @Column(name = "qanswer")
    private String qanswer;
    @Size(max = 100)
    @Column(name = "qanswer2")
    private String qanswer2;
    @JoinColumn(name = "department_id", referencedColumnName = "department_id")
    @ManyToOne(optional = false)
    private Department departmentId;
    @JoinColumn(name = "question_id2", referencedColumnName = "question_id")
    @ManyToOne
    private SecretQuestion questionId2;
    @JoinColumn(name = "question_id", referencedColumnName = "question_id")
    @ManyToOne
    private SecretQuestion questionId;

    public UserData() {
    }

    public UserData(String userId) {
        this.userId = userId;
    }

    public UserData(String userId, String name, Character gender, String usertype, String password) {
        this.userId = userId;
        this.name = name;
        this.gender = gender;
        this.usertype = usertype;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEntranceYear() {
        return entranceYear;
    }

    public void setEntranceYear(String entranceYear) {
        this.entranceYear = entranceYear;
    }

    public String getGuraduationYear() {
        return guraduationYear;
    }

    public void setGuraduationYear(String guraduationYear) {
        this.guraduationYear = guraduationYear;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Character getGender() {
        return gender;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getQanswer() {
        return qanswer;
    }

    public void setQanswer(String qanswer) {
        this.qanswer = qanswer;
    }

    public String getQanswer2() {
        return qanswer2;
    }

    public void setQanswer2(String qanswer2) {
        this.qanswer2 = qanswer2;
    }

    public Department getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Department departmentId) {
        this.departmentId = departmentId;
    }

    public SecretQuestion getQuestionId2() {
        return questionId2;
    }

    public void setQuestionId2(SecretQuestion questionId2) {
        this.questionId2 = questionId2;
    }

    public SecretQuestion getQuestionId() {
        return questionId;
    }

    public void setQuestionId(SecretQuestion questionId) {
        this.questionId = questionId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserData)) {
            return false;
        }
        UserData other = (UserData) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.UserData[ userId=" + userId + " ]";
    }
    
}
