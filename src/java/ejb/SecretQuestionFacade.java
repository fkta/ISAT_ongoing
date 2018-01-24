package ejb;

import entity.SecretQuestion;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class SecretQuestionFacade extends AbstractFacade<SecretQuestion> {

    @PersistenceContext(unitName = "ISATPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SecretQuestionFacade() {
        super(SecretQuestion.class);
    }
    
}
