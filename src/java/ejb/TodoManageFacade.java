package ejb;

import entity.Todo;
import entity.TodoManage;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
public class TodoManageFacade extends AbstractFacade<TodoManage> {

    @EJB
    UserDataFacade udf;
    
    @EJB
    TodoFacade tf;
    
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
    
    public List<TodoManage> findOne(String todoId, String userId){
        TypedQuery query = em.createNamedQuery("TodoManage.findOne", TodoManage.class);
        query.setParameter("todoId", todoId);
        query.setParameter("userId", userId);
        query.setFirstResult(query.getFirstResult());
        query.setMaxResults(query.getMaxResults());
        
        return query.getResultList();
    }
    
    
    // 共有されたtodoかつ進行中のもの
    public List<Todo> findBySharedGoingTodo(String userId){
        List<TodoManage> tmList = em.createNativeQuery("select *"
                + " from todo t join todo_manage tm on t.todo_id = tm.todo_id"
                + " where tm.user_id = '"+userId+"' and t.term >= sysdate()"
                + " order by t.term asc"
                ,TodoManage.class).getResultList();
        
        List<Todo> tList = new ArrayList<>();
        
        System.out.println("tmList size : " + tmList.size());
        
        for(TodoManage tm : tmList){
            System.out.println("TodoManage : "+tm);
            
            
            if(tm.getFinishing() == Boolean.FALSE){
                if(tm.getTodo() != null){
                    Todo todo = tm.getTodo();
                    todo.setFinishing(Boolean.FALSE);
                    tList.add(todo);
                    System.out.println("todo label : "+tm.getTodo().getLabel());
                }else{
                    System.out.println("todo : "+tm.getTodo() + "データが壊れてる");
                    //Todoの有無によってはエラーが出る
                    Todo todo = tf.findByTodoId(tm.getTodoManagePK().getTodoId()).get(0);
                    todo.setFinishing(Boolean.FALSE);
                    tList.add(todo);
                    System.out.println("todo情報取得後 : " + todo.getLabel());
                }
                
            }
            
        }
        
       return tList;
        
    }
    
    // 共有されたtodoかつ完了したもの
    public List<Todo> findBySharedFinishingTodo(String userId){
        List<TodoManage> tmList = em.createNativeQuery("select *"
                + " from todo t join todo_manage tm on t.todo_id = tm.todo_id"
                + " where tm.user_id = '"+userId+"' and t.term >= sysdate()"
                + " order by t.term"
                ,TodoManage.class).getResultList();
        
        List<Todo> tList = new ArrayList<>();
        
        for(TodoManage tm : tmList){
            if(tm.getFinishing() == Boolean.TRUE){
                
                Todo todo = tm.getTodo();
                todo.setFinishing(Boolean.TRUE);
                tList.add(todo);
            }
            
        }
        
       return tList;
        
    }
    
    public List<TodoManage> endCheck(String todoId){
        System.out.println("check is run");
        TypedQuery<TodoManage> query = em.createNamedQuery("TodoManage.findByTodoId",TodoManage.class);
        query.setParameter("todoId", todoId);
        
        List<TodoManage> tmList = new ArrayList<>();
        // ユーザの名前を取得する処理
        for(TodoManage tm : query.getResultList()){
            Todo todo = tm.getTodo();
            System.out.println("list size : "+udf.findByUserId(tm.getTodoManagePK().getUserId()).size());
            todo.setOwner(udf.findByUserId(tm.getTodoManagePK().getUserId()).get(0));
            tm.setTodo(todo);
            
            tmList.add(tm);
            System.out.println("Name : " + tm.getTodo().getOwner().getName() + " finishing : "+tm.getJudge());
        }
     
        return tmList;
    }
    
}
