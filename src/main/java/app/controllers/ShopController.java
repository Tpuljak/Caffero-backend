package app.controllers;

import app.database.dao.ItemDao;
import app.database.dao.ShopDao;
import app.database.entities.Item;
import app.database.entities.Shop;
import app.database.entities.Staff;
import app.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

@RestController
public class ShopController {
    @RequestMapping("/shop/{shopId}/get-staff-list")
    public ResponseEntity<Set<Staff>> getStaffMembersForShop(@PathVariable Integer shopId) {
        try {
            Shop shop = ShopDao.getShopById(shopId);

            return new ResponseEntity<>(shop.getStaffMembers(), HttpStatus.OK);
        } catch (ResourceNotFoundException ex) {
          throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }

    @RequestMapping("/shop/{shopId}/get-items")
    public ResponseEntity<List<Item>> getShopItems(@PathVariable Integer shopId) {
        try {
            return new ResponseEntity<>(ItemDao.getItemsForShop(shopId), HttpStatus.OK);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }
}
