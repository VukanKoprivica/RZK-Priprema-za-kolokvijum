package rzk.webservice;

import java.util.List;

import javax.ejb.Remote;

import model.Conference;

@Remote
public interface KlkWebserviceRemote {

	public List<Conference> toBe(String field);
}
