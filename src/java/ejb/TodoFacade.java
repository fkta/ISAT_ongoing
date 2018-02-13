package ejb;

import entity.Todo;
import entity.UserData;
import java.util.ArrayList;
import java.util.Calendar;
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
    
    public List<Todo> findByFinishingTodo(UserData userId){
        TypedQuery<Todo> query = em.createNamedQuery("Todo.findByFinishingTodo",Todo.class);
        query.setParameter("userId", userId);
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
    
    public List<Todo> findByGoingTodo2(){
        TypedQuery<Todo> query = em.createNamedQuery("Todo.findByGoingTodo2",Todo.class);
        query.setFirstResult(query.getFirstResult());
        query.setMaxResults(query.getMaxResults());
        for(Todo t : query.getResultList()){
            String str = t.getTodoManageCollection().toString();
            str = str.substring(2, 3);
            System.out.println("Nazo : " + str);
        }
        
        return query.getResultList();
    }
    
    public void findBySharedGoingTodo(){
        List<Todo> sharedList = em.createNativeQuery("select *"
                + " from todo t join todo_manage tm on t.todo_id = tm.todo_id"
                + " where tm.user_id = 'st20170001'"
                + " order by t.term"
                ,Todo.class).getResultList();
        
        int i = 1;
        for(Todo t : sharedList){
            System.out.println("List number = " + i + " Label : " + t.getLabel() + " owner name :" + t.getOwner().getName());
            i++;
        }
        
    }
    
    //
    public List<Todo> findByGoingTodoEx(String userId){
        TypedQuery<Todo> query = em.createNamedQuery("Todo.findByGoingTodo2",Todo.class);
        query.setFirstResult(query.getFirstResult());
        query.setMaxResults(query.getMaxResults());
        
        List<Todo> todoList = new ArrayList<>();
        //現在時刻
        Calendar currentCal = Calendar.getInstance();
        Calendar termCal = Calendar.getInstance();
        
        for(Todo t : query.getResultList()){
            
            //todoの期限を設定する
            termCal.setTime(t.getTerm());
            
            System.out.println("ラベル : " + t.getLabel() + " 日付の前後 : "+termCal.compareTo(currentCal));
            
            
            if(t.getTodoManageCollection().toString().length() > 10){
                
                System.out.println("toString : "+t.getTodoManageCollection().toString() + "length : "+t.getTodoManageCollection().toString().length());
                
                if(userId.equals(t.getTodoManageCollection().toString().substring(4,14)) && termCal.compareTo(currentCal) == 1){
                    String str = t.getTodoManageCollection().toString();
                    str = str.substring(2, 3);
                    switch(str){
                        case "×":
                            t.setFinishing(Boolean.FALSE);
                            todoList.add(t);
                            break;

                        default:
                            break;
                    }
                    //System.out.println("label : " + t.getLabel() + " manage judge : " + str);
                }
            }else{
                System.out.println("false toString : "+t.getTodoManageCollection().toString() + "length : "+t.getTodoManageCollection().toString().length());
            }
            
            
            
        }
        
        return todoList;
    }
    
    public List<Todo> findByFinishingTodoEx(String userId){
        TypedQuery<Todo> query = em.createNamedQuery("Todo.findByGoingTodo2",Todo.class);
        query.setFirstResult(query.getFirstResult());
        query.setMaxResults(query.getMaxResults());
        
        List<Todo> todoList = new ArrayList<>();
        
        //現在時刻
        Calendar currentCal = Calendar.getInstance();
        Calendar termCal = Calendar.getInstance();
        
        for(Todo t : query.getResultList()){
            
            //todoの期限を設定する
            termCal.setTime(t.getTerm());
            
            if(t.getTodoManageCollection().toString().length() > 4){
                if(userId.equals(t.getTodoManageCollection().toString().substring(4,14)) && termCal.compareTo(currentCal) == 1){
                    String str = t.getTodoManageCollection().toString();
                    str = str.substring(2, 3);
                    switch(str){
                        case "〇":
                            t.setFinishing(Boolean.TRUE);
                            todoList.add(t);
                            break;

                        default:
                            break;
                    }
                    //System.out.println("label : " + t.getLabel() + " manage judge : " + str);
                }
            }
            
            
        }
        
        return todoList;
    }
    
    public List<Todo> findByTodoId(String todoId){
        TypedQuery<Todo> query = em.createNamedQuery("Todo.findByTodoId",Todo.class);
        query.setParameter("todoId", todoId);
        query.setFirstResult(query.getFirstResult());
        query.setMaxResults(query.getMaxResults());
        
        return query.getResultList();
    }
    
}
