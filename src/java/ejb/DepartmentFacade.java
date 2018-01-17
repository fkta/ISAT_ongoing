package ejb;

import entity.Department;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
public class DepartmentFacade extends AbstractFacade<Department> {

    @PersistenceContext(unitName = "ISATPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DepartmentFacade() {
        super(Department.class);
    }
    
    public List<Department> findByClass(char departmentCode,String classCode){
        TypedQuery<Department> query = em.createNamedQuery("Department.findClass",Department.class);
        query.setParameter("departmentCode", departmentCode);
        query.setParameter("classCode", classCode);
        query.setFirstResult(query.getFirstResult());
        query.setMaxResults(query.getMaxResults());
        return query.getResultList();
    }
    
}
