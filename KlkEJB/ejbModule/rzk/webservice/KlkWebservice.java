package rzk.webservice;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import model.Conference;

/**
 * Session Bean implementation class KlkWebservice
 */
@Stateless
@LocalBean
@WebService
public class KlkWebservice implements KlkWebserviceRemote {

	@PersistenceContext
	EntityManager em;
	
    /**
     * Default constructor. 
     */
    public KlkWebservice() {
        // TODO Auto-generated constructor stub
    }

	@Override
	@WebMethod
	public List<Conference> toBe(String field) {
		// TODO Auto-generated method stub
		return em.createQuery("SELECT c FROM Conference c WHERE c.field like :field"
				,Conference.class)
				.setParameter("field", field)
				.getResultList();
	}

}
