package ejb;

import entity.TodoManage;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
public class TodoManageFacade extends AbstractFacade<TodoManage> {

    @PersistenceContext(unitName = "ISATPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TodoManageFacade() {
        super(TodoManage.class);
    }
    
    public List<TodoManage> findByTodoId(String todoId){
        TypedQuery query = em.createNamedQuery("TodoManage.findByTodoId", TodoManage.class);
        query.setParameter("todoId", todoId);
        query.setFirstResult(query.getFirstResult());
        query.setMaxResults(query.getMaxResults());
        
        return query.getResultList();
    }
    
    public List<TodoManage> findByUserId(String userId){
        TypedQuery query = em.createNamedQuery("findByUserId", TodoManage.class);
        query.setParameter("userId", userId);
        query.setFirstResult(query.getFirstResult());
        query.setMaxResults(query.getMaxResults());
        
        return query.getResultList();
    }
    
}
