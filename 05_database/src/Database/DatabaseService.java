package Database;

import Alarm.Alarm;
import Alarm.AlarmHM;
import Alarm.AlarmHMS;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class DatabaseService {
    public void insertAlarm(Alarm alarm) {
        if (!alarm.getClass().isAssignableFrom(AlarmHMS.class) && !alarm.getClass().isAssignableFrom(AlarmHM.class))
            return;
        Session session = DatabaseSessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(alarm);
        transaction.commit();
        session.close();
    }

    public void deleteAlarm(Alarm alarm) {
        if (!alarm.getClass().isAssignableFrom(AlarmHMS.class) && !alarm.getClass().isAssignableFrom(AlarmHM.class))
            return;
        Session session = DatabaseSessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(alarm);
        transaction.commit();
        session.close();
    }
}
