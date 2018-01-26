package ejb;

import entity.Todo;
import entity.UserData;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
public class TodoFacade extends AbstractFacade<Todo> {

    @PersistenceContext(unitName = "ISATPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TodoFacade() {
        super(Todo.class);
    }
    
    public List<Todo> findByGoindTodo(UserData owner){
        TypedQuery<Todo> query = em.createNamedQuery("Todo.findByGoingTodo",Todo.class);
        query.setParameter("nowtime", new Date());
        query.setParameter("owner", owner);
        query.setFirstResult(query.getFirstResult());
        query.setMaxResults(query.getMaxResults());
        //System.out.println("進行中のtodoデータ件数 : "+query.getResultList().size());
        return query.getResultList();
    }
    
    public List<Todo> findByFinishingTodo(){
        TypedQuery<Todo> query = em.createNamedQuery("Todo.findByFinishingTodo",Todo.class);
        query.setFirstResult(query.getFirstResult());
        query.setMaxResults(query.getMaxResults());
        //System.out.println("完了したtodoデータ件数 : "+query.getResultList().size());
        return query.getResultList();
    }
    
    public List<Todo> findByExpiredTodo(){
        TypedQuery<Todo> query = em.createNamedQuery("Todo.findByExpiredTodo",Todo.class);
        query.setParameter("nowtime", new Date());
        query.setFirstResult(query.getFirstResult());
        query.setMaxResults(query.getMaxResults());
        return query.getResultList();
    }
    
    public List<Todo> findBySharedTodo(String todoId){
        TypedQuery<Todo> query = em.createNamedQuery("Todo.findByShare",Todo.class);
        query.setParameter("nowtime", new Date());
        query.setParameter("todoId", todoId);
        query.setFirstResult(query.getFirstResult());
        query.setMaxResults(query.getMaxResults());
        return query.getResultList();
    }
}
