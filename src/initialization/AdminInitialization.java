package initialization;

import java.util.Date;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import domain.dao.HibernateGeneric;
import domain.model.Address;
import domain.model.users.Admin;

public class AdminInitialization implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		List<Object> users = HibernateGeneric.loadAllObjects(new Admin());
		if (users.isEmpty()) {
			Admin admin = new Admin();
			admin.setEmail("admin@naranair.com");
			admin.setName("Admin");
			admin.setPassword("admin");
			admin.setSecondName("Default");
			admin.setUsername("admin");
			admin.setBirthDate(new Date());

			Address a = new Address();
			a.setCity("Mondragon");
			a.setCountry("Spain");
			a.setPostCode("69420");
			a.setRegion("Basque Country");
			a.setStreetAndNumber("Goiru 2");
			admin.setAddress(a);

			HibernateGeneric.saveOrUpdateObject(a);
			HibernateGeneric.saveOrUpdateObject(admin);
		}
	}

}
