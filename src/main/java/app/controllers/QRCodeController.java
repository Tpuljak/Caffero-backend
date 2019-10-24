package app.controllers;

import app.database.entities.QRCode;
import app.database.entities.Shop;
import app.database.util.HibernateUtil;
import app.dto.GetNewQrCodeRequestBodyDto;
import app.exceptions.ResourceNotFoundException;
import org.hibernate.Transaction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class QRCodeController {
    @RequestMapping("/qrcode/new-code")
    public ResponseEntity<QRCode> getNewCode(@RequestBody GetNewQrCodeRequestBodyDto requestBody) {
        Transaction transaction = null;
        try (org.hibernate.Session hSession = HibernateUtil.getSessionFactory().openSession()) {
            transaction = hSession.beginTransaction();

            Shop shop = hSession.get(Shop.class, requestBody.getShopId());

            if (shop == null) {
                throw new ResourceNotFoundException("Shop not found");
            }

            hSession.persist(shop);

            QRCode newCode = new QRCode(shop, requestBody.getTableNumber());

            shop.addQrCode(newCode);

            hSession.save(newCode);

            transaction.commit();
            hSession.close();

            return new ResponseEntity<>(newCode, HttpStatus.OK);
        } catch (ResourceNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }
}
