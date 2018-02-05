package ejb;

import entity.Todo;
import entity.TodoManage;
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
    
    public void findByGoingTodo2(){
        TypedQuery<Todo> query = em.createNamedQuery("Todo.findByGoingTodo2",Todo.class);
        query.setFirstResult(query.getFirstResult());
        query.setMaxResults(query.getMaxResults());
        for(Todo t : query.getResultList()){
            System.out.println("Label : "+t.getLabel() + " finishing : "+t.getTodoManageCollection().get(0).getFinishing());
        }
        System.out.println("list size : "+ query.getResultList().size());
    }
    
    public List<Todo> findByGoingTodoEx(String userId){
        List<Todo> list = em.createNativeQuery(
                "select t.todo_id,t.label,t.detail,t.term,t.owner,t.priority,tm.finishing from todo_manage tm right join todo t on tm.todo_id = t.todo_id where tm.user_id = '"+userId+"'"
                        + " union " + "select * from todo where owner = '"+userId+"'" + " order by term"
                ,Todo.class
                ).getResultList();
        return list;
    }
    
}
