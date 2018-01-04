/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.UserData;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author glowo
 */
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
    
}
