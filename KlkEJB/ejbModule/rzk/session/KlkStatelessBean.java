package rzk.session;

import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.ObjectMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import model.Conference;
import model.ConferencePredavac;

/**
 * Session Bean implementation class KlkStatelessBean
 */
@Stateless
@LocalBean
public class KlkStatelessBean implements KlkStatelessBeanRemote {
	
	@Inject
	JMSContext ctx;
	
	@Resource(mappedName = "java:/jms/queue/conference")
	Destination destionationQueue;

	@PersistenceContext
	EntityManager em;
	
    /**
     * Default constructor. 
     */
    public KlkStatelessBean() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public boolean addConf(Conference conf) {
		// TODO Auto-generated method stub
		
		try {
			ObjectMessage om = ctx.createObjectMessage();
			om.setObject(conf);
			JMSProducer producer = ctx.createProducer();
			producer.send(destionationQueue,om );
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return false;
	}

	@Override
	public boolean register(ConferencePredavac cp) {
		// TODO Auto-generated method stub
		try {
			em.persist(cp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

}
