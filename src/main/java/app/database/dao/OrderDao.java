package app.database.dao;

import app.database.entities.Order;
import app.database.util.HibernateUtil;
import org.hibernate.Transaction;

import java.util.List;

public class OrderDao {
    public static void saveOrder(Order order) {
        Transaction transaction = null;
        try (org.hibernate.Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.save(order);

            transaction.commit();
            session.close();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public static List<Order> getAllOrders() {
        try (org.hibernate.Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from order", Order.class).list();
        }
    }
}
