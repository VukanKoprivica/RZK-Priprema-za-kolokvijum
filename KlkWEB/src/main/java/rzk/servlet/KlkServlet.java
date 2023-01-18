package rzk.servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Conference;
import rzk.session.KlkStatelessBean;
import rzk.session.KlkStatelessBeanRemote;

/**
 * Servlet implementation class KlkServlet
 */
@WebServlet("/KlkServlet")
public class KlkServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
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

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public KlkServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		KlkStatelessBeanRemote bean = doLookup();
		String title = request.getParameter("title");
		String country = request.getParameter("country");
		String city = request.getParameter("city");
		String field = request.getParameter("field");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date fromDate = null;
		Date toDate = null;
		
		try {
			fromDate = sdf.parse(request.getParameter("fromDate"));
			toDate = sdf.parse(request.getParameter("toDate"));
			Conference conf  =new Conference();
			conf.setCity(city);
			conf.setCountry(country);
			conf.setTitle(title);
			conf.setField(field);
			conf.setDateFrom(fromDate);
			conf.setDateTo(toDate);
			bean.addConf(conf);
			request.getRequestDispatcher("index.jsp").forward(request, response);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
