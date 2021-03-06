package Database;

import Alarm.AlarmHMS;
import Alarm.AlarmHM;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class DatabaseSessionFactory {
    private static SessionFactory sessionFactory;

    private DatabaseSessionFactory() {
    }

    public static SessionFactory getInstance() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration().configure("/cfg.xml");
                configuration.addAnnotatedClass(AlarmHMS.class);
                configuration.addAnnotatedClass(AlarmHM.class);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}
