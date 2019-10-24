package app.database.dao;

import app.database.entities.Item;
import app.database.util.HibernateUtil;
import org.hibernate.Transaction;

import java.util.List;

public class ItemDao {
    private static String getItemsForShopQueryFormat = "from item where shop_id = %d";

    public static void saveItem(Item item) {
        Transaction transaction = null;
        try (org.hibernate.Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.save(item);

            transaction.commit();
            session.close();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    public static List<Item> getItemsForShop(Integer shopId) {
        String query = String.format(getItemsForShopQueryFormat, shopId);

        try (org.hibernate.Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(query, Item.class).list();
        }
    }
}
