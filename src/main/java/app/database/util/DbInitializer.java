package app.database.util;

import app.database.common.AuthorityType;
import app.database.common.StaffType;
import app.database.entities.*;
import org.hibernate.Transaction;

public class DbInitializer {
    public static void initialize() {
        Session session = new Session();
        Session session2 = new Session();

        QRCode code = new QRCode(1);
        QRCode code2 = new QRCode(2);
        QRCode codeWithoutSession = new QRCode(3);
        Shop shop = new Shop("test shop", 12);

        Staff staffMember = new Staff("Mate Matic", "mate.matic@gmail.com", StaffType.ADMIN);

        User user = new User("Ivo Ivic", "ivo.ivic@gmail.com", "password1");
        User user2 = new User("Ivo Ivanovic", "ivo.ivanovic@gmail.com", "password2");
        User userWithoutSession = new User("Bez Sessiona", "bez.sessiona@gmail.com", "password3");

        Authority auth1 = new Authority(AuthorityType.ROLE_ADMIN, user2);
        user2.addAuthority(auth1);

        Item item = new Item("coke", 15.0, user, shop);

        Order order = new Order(item, user);
        Order order2 = new Order(item, user2);

        staffMember.setShop(shop);

        session.setQrCode(code);
        session2.setQrCode(code2);

        session.setShop(shop);
        session.addOrder(order);

        session2.setShop(shop);
        session2.addOrder(order2);

        session.addUser(user);
        session2.addUser(user2);

        user.setActiveSession(session);
        user2.setActiveSession(session2);

        code.setSession(session);
        code2.setSession(session2);
        code.setShop(shop);
        code2.setShop(shop);
        codeWithoutSession.setShop(shop);

        shop.addStaffMember(staffMember);

        shop.addSession(session);
        shop.addSession(session2);
        shop.addQrCode(code);
        shop.addQrCode(code2);
        shop.addQrCode(codeWithoutSession);
        shop.addItem(item);

        order.setSession(session);
        order2.setSession(session2);

        Transaction transaction = null;

        try (org.hibernate.Session hSession = HibernateUtil.getSessionFactory().openSession()) {
            transaction = hSession.beginTransaction();

            hSession.save(order);
            hSession.save(order2);

            hSession.save(session2);
            hSession.save(session);

            hSession.save(user);
            hSession.save(user2);
            hSession.save(userWithoutSession);

            hSession.save(auth1);

            hSession.save(code);
            hSession.save(code2);
            hSession.save(codeWithoutSession);

            hSession.save(shop);

            hSession.save(item);

            hSession.save(staffMember);

            transaction.commit();
            hSession.close();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
