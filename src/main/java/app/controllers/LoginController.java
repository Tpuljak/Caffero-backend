package app.controllers;

import app.database.dao.UserDao;
import app.database.entities.User;
import app.dto.LoginRequestBodyDto;
import app.exceptions.ResourceNotFoundException;
import app.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.ok;

@RestController
public class LoginController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestBodyDto requestBody) {
        try {
            String username = requestBody.getEmail();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestBody.getPassword()));
            String token = jwtTokenProvider.createToken(username, UserDao.getUserDetails(requestBody.getEmail()).getAuthorities().stream().map(authority -> authority.toString()).collect(Collectors.toList()));

            User user;
            try {
                user = UserDao.getUserDetails(username);
            } catch (Exception ex) {
                throw new ResourceNotFoundException("User not found");
            }

            Map<Object, Object> model = new HashMap<>();
            model.put("username", username);
            model.put("token", token);
            model.put("userId", user.getId());

            return ok(model);
        } catch (ResourceNotFoundException e) {
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }
}
