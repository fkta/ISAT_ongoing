/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Info;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class InfoFacade extends AbstractFacade<Info> {

    @PersistenceContext(unitName = "ISATPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public InfoFacade() {
        super(Info.class);
    }
    
    public List<Info> findAllInfo() throws ParseException{
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        String fmt = sdf.format(dt);
        dt = sdf.parse(fmt);
        
        TypedQuery<Info> query = em.createNamedQuery("Info.findInfo",Info.class).setParameter("now", dt);
        query.setFirstResult(query.getFirstResult());
        query.setMaxResults(query.getMaxResults());
        return query.getResultList();
    }
    
    public List<Info> findAllInfoLimit3() throws ParseException{
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        String fmt = sdf.format(dt);
        dt = sdf.parse(fmt);
        
        TypedQuery<Info> query = em.createNamedQuery("Info.findInfo",Info.class).setParameter("now", dt);
        query.setFirstResult(query.getFirstResult());
        query.setMaxResults(3);
        return query.getResultList();
    }
    
    
    public Info findDetail(String infoId){
        TypedQuery<Info> query = em.createNamedQuery("Info.findByInfoId",Info.class).setParameter("infoId", infoId);
        return query.getSingleResult();
    }
    
    public List<Info> findUser(String userId){
        TypedQuery<Info> query = em.createNamedQuery("Info.findByUserId",Info.class).setParameter("userId", userId);
        query.setFirstResult(query.getFirstResult());
        query.setMaxResults(query.getMaxResults());
        return query.getResultList();
    }
    
}
