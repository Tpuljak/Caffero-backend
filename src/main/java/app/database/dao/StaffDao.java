package app.database.dao;

import app.database.entities.Staff;
import app.database.util.HibernateUtil;
import org.hibernate.Transaction;

import java.util.List;

public class StaffDao {
    public static void saveStaffMember(Staff staff) {
        Transaction transaction = null;
        try (org.hibernate.Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.save(staff);

            transaction.commit();
            session.close();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    public static List<Staff> getStaff() {
        try (org.hibernate.Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from staff ", Staff.class).list();
        }
    }
}
