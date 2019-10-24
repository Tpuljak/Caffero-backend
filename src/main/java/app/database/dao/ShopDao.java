package app.database.dao;

import app.database.entities.Shop;
import app.database.util.HibernateUtil;
import app.exceptions.ResourceNotFoundException;
import org.hibernate.Transaction;

import java.util.List;

public class ShopDao {
    private static String getShopByIdQueryFormat = "from shop where id = %d";

    public static void saveShop(Shop shop) {
        Transaction transaction = null;
        try (org.hibernate.Session hSession = HibernateUtil.getSessionFactory().openSession()) {
            transaction = hSession.beginTransaction();

            hSession.save(shop);

            transaction.commit();
            hSession.close();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public static List<Shop> getShops() {
        try (org.hibernate.Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from shop", Shop.class).list();
        }
    }

    public static Shop getShopById(Integer shopId) throws ResourceNotFoundException {
        String query = String.format(getShopByIdQueryFormat, shopId);

        try(org.hibernate.Session hSession = HibernateUtil.getSessionFactory().openSession()) {
            Shop shop = hSession.createQuery(query, Shop.class).getSingleResult();

            if (shop == null) {
                throw new ResourceNotFoundException("Shop not found");
            }

            return shop;
        }
    }
}
