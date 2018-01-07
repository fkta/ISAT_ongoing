package ejb;

import entity.Pending;
import entity.PendingPK;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
public class PendingFacade extends AbstractFacade<Pending> {

    @PersistenceContext(unitName = "ISATPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PendingFacade() {
        super(Pending.class);
    }
    
    public List<Pending> findData(String userId){
        TypedQuery<Pending> query = em.createNamedQuery("Pending.findByUserId",Pending.class).setParameter("userId", userId);
        query.setFirstResult(query.getFirstResult());
        query.setMaxResults(query.getMaxResults());
        return query.getResultList();
    }
    
    public List<Pending> findUpdateData(String infoId){
        TypedQuery<Pending> query = em.createNamedQuery("Pending.findByInfoId",Pending.class).setParameter("infoId", infoId);
        query.setFirstResult(query.getFirstResult());
        query.setMaxResults(query.getMaxResults());
        return query.getResultList();
        
    }
    
    public List<Pending> findPKData(PendingPK penPK){
        TypedQuery<Pending> query = em.createNamedQuery("Pending.findByPK",Pending.class).setParameter("infoId", penPK.getInfoId());
        query.setParameter("userId", penPK.getUserId());
        query.setFirstResult(query.getFirstResult());
        query.setMaxResults(query.getMaxResults());
        return query.getResultList();
        
    }
    
}
