package app.controllers;

import app.database.entities.*;
import app.database.util.HibernateUtil;
import app.dto.PlaceOrderRequestBodyDto;
import app.exceptions.ResourceNotFoundException;
import org.hibernate.Transaction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class OrderController {
    @RequestMapping("/order/place-order")
    public ResponseEntity<String> placeOrder(@RequestBody PlaceOrderRequestBodyDto requestBody) {
        Transaction transaction = null;
        try (org.hibernate.Session hSession = HibernateUtil.getSessionFactory().openSession()) {
            transaction = hSession.beginTransaction();

            User user = hSession.get(User.class, requestBody.getUserId());

            if (user == null) {
                throw new ResourceNotFoundException("User not found");
            }

            Session session = hSession.get(Session.class, requestBody.getSessionId());

            if (session == null) {
                throw new ResourceNotFoundException("Session not found");
            }

            hSession.persist(session);

            Item item = hSession.get(Item.class, requestBody.getItemToOrderId());

            if (item == null) {
                throw new ResourceNotFoundException("Item not found");
            }

            hSession.persist(item);

            Order order = new Order(item, user, session);

            hSession.save(order);
            session.addOrder(order);

            if (session.getOrderSum() != null) {
                session.setOrderSum(session.getOrderSum() + item.getPrice());
            } else {
                session.setOrderSum(item.getPrice());
            }

            transaction.commit();
            hSession.close();

            return new ResponseEntity("Order placed", HttpStatus.OK);
        } catch (ResourceNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }
}
