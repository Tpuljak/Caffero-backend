package app.database.util;

import app.database.entities.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;

public class HibernateUtil {
    private static SessionFactory sessionFactory;
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                Properties settings = new Properties();
                mySql(settings);
//                h2(settings);
                settings.put(Environment.SHOW_SQL, "true");

                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

                settings.put(Environment.HBM2DDL_AUTO, "create-drop");

                configuration.setProperties(settings);

                configuration.addAnnotatedClass(Session.class);
                configuration.addAnnotatedClass(QRCode.class);
                configuration.addAnnotatedClass(Shop.class);
                configuration.addAnnotatedClass(Staff.class);
                configuration.addAnnotatedClass(User.class);
                configuration.addAnnotatedClass(Item.class);
                configuration.addAnnotatedClass(Order.class);
                configuration.addAnnotatedClass(Authority.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
    
	private static void h2(Properties settings) {
		settings.put(Environment.DRIVER, "org.h2.Driver");
		settings.put(Environment.URL, "jdbc:h2:mem:AZ;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
		settings.put(Environment.USER, "sa");
		settings.put(Environment.PASS, "");
		settings.put(Environment.DIALECT, "org.hibernate.dialect.H2Dialect");
	}
	
	
	private static void mySql(Properties settings) {
		settings.put(Environment.DRIVER, "com.mysql.jdbc.Driver");
		settings.put(Environment.URL, "jdbc:mysql://localhost/caffero");
		settings.put(Environment.USER, "cafferoAdmin");
		settings.put(Environment.PASS, "test1234");
		settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
	}
}
