package app.controllers;

import app.database.dao.UserDao;
import app.database.entities.User;
import app.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class UserController {
    @RequestMapping("user/{userId}/get-details")
    public ResponseEntity<User> getUserDetails(@PathVariable Integer userId) {
        try {
            User user = UserDao.getUserDetails(userId);

            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (ResourceNotFoundException ex) {
          throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }
}
