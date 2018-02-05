package ejb;

import entity.Schedule;
import static entity.Schedule_.scheduleId;
import entity.UserData;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
public class ScheduleFacade extends AbstractFacade<Schedule> {

    @PersistenceContext(unitName = "ISATPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ScheduleFacade() {
        super(Schedule.class);
    }
    
    public Schedule search(String title){
        TypedQuery<Schedule> query = em.createNamedQuery("Schedule.findByTitle",Schedule.class).setParameter("title", title);
        return query.getSingleResult();
    }
    
    public List<Schedule> searchSchedule(UserData owner){
       TypedQuery<Schedule> query = em.createNamedQuery("Schedule.findByOwner",Schedule.class).setParameter("owner", owner);
       query.setFirstResult(query.getFirstResult());
       query.setMaxResults(query.getMaxResults());
       return query.getResultList();
    }
    
    public List<Schedule> findByScheduleId(String scheduleId){
       TypedQuery<Schedule> query = em.createNamedQuery("Schedule.findByScheduleId",Schedule.class);
       query.setParameter("scheduleId", scheduleId);
       query.setFirstResult(query.getFirstResult());
       query.setMaxResults(query.getMaxResults());
       return query.getResultList();
    }
    
    public List<Schedule> findByWeekSchedule(UserData owner,Date start){
       TypedQuery<Schedule> query = em.createNamedQuery("Schedule.findByWeekSchedule",Schedule.class);
       query.setParameter("owner", owner);
       query.setParameter("date", start);
       query.setFirstResult(query.getFirstResult());
       query.setMaxResults(query.getMaxResults());
       return query.getResultList();
    }
    
    public List<Schedule> findByShareSchedule(String scheduleId,Date start){
       TypedQuery<Schedule> query = em.createNamedQuery("Schedule.findByShareSchedule",Schedule.class);
       query.setParameter("scheduleId", scheduleId);
       query.setParameter("date", start);
       query.setFirstResult(query.getFirstResult());
       query.setMaxResults(query.getMaxResults());
       return query.getResultList();
    }
    
}
