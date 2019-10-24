package app.database.dao;

import app.database.entities.Session;
import app.database.entities.User;
import app.database.util.HibernateUtil;
import app.exceptions.ResourceNotFoundException;
import org.hibernate.Transaction;

import java.util.*;

public class SessionDao {
    public static final String getConnectedUsersQueryFormat = "from session where id = %d";
    public static final String getActiveSessionsQueryFormat = "from session where shop_id = %d";
    public static final String getSessionByQrCodeIdQueryFormat = "from session where qrCode_id = %d";

    public static void saveSession(Session newSession) {
        Transaction transaction = null;
        try (org.hibernate.Session hSession = HibernateUtil.getSessionFactory().openSession()) {
            transaction = hSession.beginTransaction();

            hSession.save(newSession);

            transaction.commit();
            hSession.close();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public static List<Session> getActiveSessions(Integer shopId) {
        String query = String.format(getActiveSessionsQueryFormat, shopId);

        try (org.hibernate.Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(query, Session.class).list();
        }
    }

    public static Set<User> getConnectedUsers(Integer reqSessionId) throws ResourceNotFoundException {
        String query = String.format(getConnectedUsersQueryFormat, reqSessionId);

        try (org.hibernate.Session hSession = HibernateUtil.getSessionFactory().openSession()) {
            Session session = hSession.createQuery(query, Session.class).getSingleResult();

            if (session == null) {
                throw new ResourceNotFoundException("Session not found");
            }

            return session.getUsers();
        }
    }

    public static Session getSessionByQrCode(Integer qrCodeId) {
        String query = String.format(getSessionByQrCodeIdQueryFormat, qrCodeId);

        try (org.hibernate.Session hSession = HibernateUtil.getSessionFactory().openSession()) {
            return hSession.createQuery(query, Session.class).getSingleResult();
        }
    }

    public static Session getSessionById(Integer id) {
        String query = String.format(getConnectedUsersQueryFormat, id);

        try (org.hibernate.Session hSession = HibernateUtil.getSessionFactory().openSession()) {
            return hSession.createQuery(query, Session.class).getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }

}
