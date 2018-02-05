package ejb;

import entity.Transmit;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
public class TransmitFacade extends AbstractFacade<Transmit> {

    @PersistenceContext(unitName = "ISATPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TransmitFacade() {
        super(Transmit.class);
    }
    
}
