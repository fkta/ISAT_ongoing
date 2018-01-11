/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.BulletinBoard;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author glowo
 */
@Stateless
public class BulletinBoardFacade extends AbstractFacade<BulletinBoard> {

    @PersistenceContext(unitName = "ISATPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BulletinBoardFacade() {
        super(BulletinBoard.class);
    }
    
    public List<BulletinBoard> orderByPostDate(){
        TypedQuery<BulletinBoard> query = em.createNamedQuery("BulletinBoard.orderByPostDate",BulletinBoard.class);
        query.setFirstResult(query.getFirstResult());
        query.setMaxResults(query.getMaxResults());
        return query.getResultList();
    }
    
}
