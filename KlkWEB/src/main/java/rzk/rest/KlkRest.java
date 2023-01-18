package rzk.rest;

import java.util.Properties;


import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import model.ConferencePredavac;
import rzk.session.KlkStatelessBean;
import rzk.session.KlkStatelessBeanRemote;

@Path("/conf")
public class KlkRest {

	private static Context initialContext;

	private static final String PKG_INTERFACES = "org.jboss.ejb.client.naming";

	public static Context getInitialContext() throws NamingException {
		if (initialContext == null) {
			Properties properties = new Properties();
			properties.put(Context.URL_PKG_PREFIXES, PKG_INTERFACES);
			initialContext = new InitialContext(properties);
		}
		return initialContext;
	}

	private static String getLookupName() {
		
		// The app name is the application name of the deployed EJBs. This is typically the ear name without the .ear suffix. 
        final String appName = "KlkJEAR";
        // This is the module name of the deployed EJBs on the server. This is typically the jar name of the EJB deployment, without the .jar suffix.
        final String moduleName = "KlkEJB";
        // JBossAS allows each deployment to have an (optional) distinct name. We haven't specified a distinct name for
        // our EJB deployment, so this is an empty string
        final String distinctName = "";
        // The EJB name which by default is the simple class name of the bean implementation class
        final String beanName = KlkStatelessBean.class.getSimpleName();
        // the remote interface fully qualified class name
        final String interfaceName = KlkStatelessBeanRemote.class.getName();
        // let's do the lookup
		String name = "ejb:" + appName + "/" + moduleName + "/" +
				distinctName    + "/" + beanName + "!" + interfaceName;
		return name;
	}

	private static KlkStatelessBeanRemote doLookup() {
		Context context = null;
		KlkStatelessBeanRemote bean = null;
		try {
			context = getInitialContext();
			String lookupName = getLookupName();
			System.out.println("JNDI ime:   "+lookupName);
			bean = (KlkStatelessBeanRemote) context.lookup(lookupName);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return bean;
	}
	
	KlkStatelessBeanRemote bean = doLookup();
	
	@POST
	@Path("/p")
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean register(ConferencePredavac p) {
		return bean.register(p);
	}

	
}
