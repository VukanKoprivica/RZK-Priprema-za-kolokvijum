package rzk.session;

import javax.ejb.Remote;

import model.Conference;
import model.ConferencePredavac;

@Remote
public interface KlkStatelessBeanRemote {
	
	public boolean addConf(Conference conf);
	public boolean register(ConferencePredavac cp);

}
