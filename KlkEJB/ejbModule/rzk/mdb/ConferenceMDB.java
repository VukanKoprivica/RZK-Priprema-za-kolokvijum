package rzk.mdb;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.ws.WebServiceRef;

import confer.CountryInfoService;
import confer.CountryInfoServiceSoapType;
import model.Conference;

/**
 * Message-Driven Bean implementation class for: ConferenceMDB
 */
@MessageDriven(
		activationConfig = { @ActivationConfigProperty(
				propertyName = "destination", propertyValue = "java:/jms/queue/conference"), @ActivationConfigProperty(
				propertyName = "destinationType", propertyValue = "javax.jms.Queue")
		}, 
		mappedName = "java:/jms/queue/conference")
public class ConferenceMDB implements MessageListener {
	
	@WebServiceRef(CountryInfoService.class)
	CountryInfoServiceSoapType cisst;
	
	@PersistenceContext
	EntityManager em;
	
	@Resource(mappedName = "java:/jms/topic/confinfo")
	Destination destinationTopic;
	
	@Inject
	JMSContext ctx;
    /**
     * Default constructor. 
     */
    public ConferenceMDB() {
        // TODO Auto-generated constructor stub
    }
	
	/**
     * @see MessageListener#onMessage(Message)
     */
    public void onMessage(Message message) {
        // TODO Auto-generated method stub
    	try {
			ObjectMessage om = (ObjectMessage) message;
			Conference conf = (Conference) om.getObject();
			String code = cisst.countryISOCode(conf.getCountry());
			String dialingCode=cisst.countryIntPhoneCode(code);
			String currency = cisst.currencyName(cisst.fullCountryInfo(code).getSCurrencyISOCode());
			conf.setCountryCode(code);
			conf.setDialingCode(dialingCode);
			conf.setCurrency(currency);
			em.persist(conf);
			
			ObjectMessage send = ctx.createObjectMessage();
			send.setObject(conf);
			JMSProducer producer = ctx.createProducer();
			producer.send(destinationTopic, conf);
			
			
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }

}
