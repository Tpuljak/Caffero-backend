package app.database.dao;

import app.database.entities.QRCode;
import app.database.util.HibernateUtil;
import org.hibernate.Transaction;

public class QRCodeDao {
    private static String getQRCodeByIdQueryFormat = "from qrcode where id = %d";

    public static void saveQRCode(QRCode code) {
        Transaction transaction = null;
        try (org.hibernate.Session hSession = HibernateUtil.getSessionFactory().openSession()) {
            transaction = hSession.beginTransaction();

            hSession.save(code);

            transaction.commit();
            hSession.close();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public static QRCode getCodeById(Integer qrCodeId) {
        String query = String.format(getQRCodeByIdQueryFormat, qrCodeId);

        try(org.hibernate.Session hSession = HibernateUtil.getSessionFactory().openSession()) {
            return hSession.createQuery(query, QRCode.class).getSingleResult();
        }
    }
}
