package ejb;

import entity.Pending;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
    
}
