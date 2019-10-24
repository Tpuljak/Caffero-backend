package app.database.dao;

import app.database.entities.User;
import app.database.util.HibernateUtil;
import app.exceptions.ResourceNotFoundException;
import org.hibernate.Transaction;

public class UserDao {
    private static final String getUserDetailsByIdQueryFormat = "from user where id = %d";
    private static final String getUserDetailsByEmailQueryFormat = "from user where email = '%s'";

    public static void saveUser(User user) {
        Transaction transaction = null;
        try (org.hibernate.Session hSession = HibernateUtil.getSessionFactory().openSession()) {
            transaction = hSession.beginTransaction();

            hSession.save(user);

            transaction.commit();
            hSession.close();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public static User getUserDetails(Integer userId) throws ResourceNotFoundException {
        String query = String.format(getUserDetailsByIdQueryFormat, userId);

        try (org.hibernate.Session hSession = HibernateUtil.getSessionFactory().openSession()) {
            User user = hSession.createQuery(query, User.class).getSingleResult();

            if (user == null) {
                throw new ResourceNotFoundException("User not found");
            }

            return user;
        }
    }

    public static User getUserDetails(String userEmail) throws ResourceNotFoundException {
        String query = String.format(getUserDetailsByEmailQueryFormat, userEmail);

        try (org.hibernate.Session hSession = HibernateUtil.getSessionFactory().openSession()) {
            User user = hSession.createQuery(query, User.class).getSingleResult();

            if (user == null) {
                throw new ResourceNotFoundException("User not found");
            }

            return user;
        }
    }
}
