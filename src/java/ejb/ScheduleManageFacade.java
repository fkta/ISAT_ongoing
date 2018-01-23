package ejb;

import entity.ScheduleManage;
import entity.UserData;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
public class ScheduleManageFacade extends AbstractFacade<ScheduleManage> {

    @PersistenceContext(unitName = "ISATPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ScheduleManageFacade() {
        super(ScheduleManage.class);
    }
    
    public List<ScheduleManage> findUserId(String userId){
        TypedQuery<ScheduleManage> query = em.createNamedQuery("ScheduleManage.findByUserId",ScheduleManage.class);
        query.setParameter("userId", userId);
        query.setFirstResult(query.getFirstResult());
        query.setMaxResults(query.getMaxResults());
        return query.getResultList();
    }
    
    
    public List<ScheduleManage> findByScheduleId(String scheduleId){
        TypedQuery<ScheduleManage> query = em.createNamedQuery("ScheduleManage.findByScheduleId",ScheduleManage.class);
        query.setParameter("scheduleId", scheduleId);
        query.setFirstResult(query.getFirstResult());
        query.setMaxResults(query.getMaxResults());
        return query.getResultList();
    }
}
