package ejb;

import entity.TodoManage;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
    
}
