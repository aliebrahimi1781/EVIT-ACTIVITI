/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Personen;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Philipp Steiner
 */
@Stateless
public class PersonenFacade extends AbstractFacade<Personen> {
    @PersistenceContext(unitName = "Primeface_CRUDPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PersonenFacade() {
        super(Personen.class);
    }
    
}
