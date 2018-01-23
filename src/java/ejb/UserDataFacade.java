package ejb;

import entity.Department;
import entity.UserData;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
public class UserDataFacade extends AbstractFacade<UserData> {

    @PersistenceContext(unitName = "ISATPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserDataFacade() {
        super(UserData.class);
    }
    
    public List<UserData> userAuth(String userId,String password){
            TypedQuery<UserData> query = em.createNamedQuery("UserData.authUser",UserData.class).setParameter("userId", userId).setParameter("password", password);
            query.setFirstResult(query.getFirstResult());
            query.setMaxResults(query.getMaxResults());
            return query.getResultList();
        
    }
    
    public UserData findUserId(String userId){
        TypedQuery<UserData> query = em.createNamedQuery("UserData.findByUserId",UserData.class).setParameter("userId", userId);
        return query.getSingleResult();
    }
    
    
    public List<UserData> findByUserType(String usertype){
            TypedQuery<UserData> query = em.createNamedQuery("UserData.findByUsertype",UserData.class);
            query.setParameter("usertype", usertype);
            query.setFirstResult(query.getFirstResult());
            query.setMaxResults(query.getMaxResults());
            return query.getResultList();
        
    }
    
    public List<UserData> findByDepartment(Department departmentId, int year, int gakunen){
        TypedQuery<UserData> query = em.createNamedQuery("UserData.findByDepartment",UserData.class);
        query.setParameter("departmentId", departmentId);
        query.setParameter("year", year);
        query.setParameter("gakunen", gakunen);
        query.setFirstResult(query.getFirstResult());
        query.setMaxResults(query.getMaxResults());
        return query.getResultList();
    }
    
    
    public List<UserData> findSecret(String userId){
        TypedQuery<UserData> query = em.createNamedQuery("UserData.findSequret",UserData.class);
        query.setParameter("userId", userId);
        query.setFirstResult(query.getFirstResult());
        query.setMaxResults(query.getMaxResults());
        return query.getResultList();
    }
    
}
