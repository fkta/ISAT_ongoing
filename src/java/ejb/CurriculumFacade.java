package ejb;

import entity.Curriculum;
import java.util.Calendar;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
public class CurriculumFacade extends AbstractFacade<Curriculum> {

    @PersistenceContext(unitName = "ISATPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CurriculumFacade() {
        super(Curriculum.class);
    }
    
    public List<Curriculum> findByDayCurriculum(int departmentId,char period){
        TypedQuery<Curriculum> query = em.createNamedQuery("Curriculum.findByDayCurriculum",Curriculum.class);
        query.setParameter("departmentId", departmentId);
        
        String[] week_name = {"sun", "mon", "tue", "wed", 
                          "thu", "fri", "sat"};
        
        Calendar cal = Calendar.getInstance();
        int soe = cal.get(Calendar.DAY_OF_WEEK) - 1;
        
        //土日は月曜の時間割を表示する
        if(soe == 0 || soe ==6){
            soe = 1;
            System.out.println("週末なので崇徳する時間割を月曜に設定しました");
        }
        
        
        query.setParameter("day", week_name[soe]);
        query.setParameter("period", period);
        
        query.setFirstResult(query.getFirstResult());
        query.setMaxResults(query.getMaxResults());
        
        return query.getResultList();
    }
    
}
