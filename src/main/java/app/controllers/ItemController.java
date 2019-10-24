package app.controllers;

import app.database.entities.Item;
import app.database.entities.Shop;
import app.database.entities.User;
import app.database.util.HibernateUtil;
import app.dto.AddNewItemToShopRequestBodyDto;
import app.dto.RemoveItemFromShopRequestBodyDto;
import app.exceptions.ResourceNotFoundException;
import org.hibernate.Transaction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class ItemController {
    @RequestMapping("/item/add-new")
    public ResponseEntity<String> addNewItemToShop(@RequestBody AddNewItemToShopRequestBodyDto requestBody) {
        Transaction transaction = null;
        try (org.hibernate.Session hSession = HibernateUtil.getSessionFactory().openSession()) {
            transaction = hSession.beginTransaction();

            User user = hSession.get(User.class, requestBody.getCreatedByUserId());

            if (user == null) {
                throw new ResourceNotFoundException("User not found");
            }

            Shop shop = hSession.get(Shop.class, requestBody.getShopId());

            if (shop == null) {
                throw new ResourceNotFoundException("Shop not found");
            }

            Item newItem = new Item(requestBody.getItemName(), requestBody.getItemPrice(), user, shop);

            hSession.save(newItem);

            transaction.commit();
            hSession.close();

            return new ResponseEntity("Item added", HttpStatus.OK);
        } catch (ResourceNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }

    @RequestMapping("/item/remove")
    public ResponseEntity<String> removeItemFromShop(@RequestBody RemoveItemFromShopRequestBodyDto requestBody) {
        Transaction transaction = null;
        try (org.hibernate.Session hSession = HibernateUtil.getSessionFactory().openSession()) {
            transaction = hSession.beginTransaction();

            Item itemToRemove = hSession.get(Item.class, requestBody.getItemId());

            if (itemToRemove == null) {
                throw new ResourceNotFoundException("Item not found");
            }

            Shop itemShop = hSession.get(Shop.class, requestBody.getShopId());

            if (itemShop == null) {
                throw new ResourceNotFoundException("Shop not found");
            }

            hSession.remove(itemToRemove);
            itemShop.removeItem(itemToRemove);

            transaction.commit();
            hSession.close();

            return new ResponseEntity("Item removed", HttpStatus.OK);
        } catch (ResourceNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }
}
