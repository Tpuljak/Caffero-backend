package app.controllers;

import app.database.dao.SessionDao;
import app.database.entities.*;
import app.database.util.HibernateUtil;
import app.dto.*;
import app.exceptions.ResourceNotFoundException;
import org.hibernate.Transaction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

@RestController
public class SessionController {

    @RequestMapping("session/{sessionId}/connected-users")
    public ResponseEntity<Set<User>> getConnectedUsers(@PathVariable Integer sessionId) {
        try {
            return new ResponseEntity<>(SessionDao.getConnectedUsers(sessionId), HttpStatus.OK);
        } catch (ResourceNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }

    @RequestMapping("session/active-sessions")
    public ResponseEntity<List<Session>> getActiveSessions(@RequestBody GetActiveSessionsRequestBodyDto requestBody) {
        try {
            return new ResponseEntity<>(SessionDao.getActiveSessions(requestBody.getShopId()), HttpStatus.OK);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }

    @RequestMapping("session/open-session")
    public ResponseEntity<String> openSession(@RequestBody OpenSessionRequestBodyDto requestBody) {
        Transaction transaction = null;
        try (org.hibernate.Session hSession = HibernateUtil.getSessionFactory().openSession()){
            transaction = hSession.beginTransaction();

            QRCode qrCode = hSession.get(QRCode.class, requestBody.getQrCodeId());

            if (qrCode == null) {
                throw new ResourceNotFoundException("QrCode not found");
            }

            hSession.persist(qrCode);

            Session newSession = new Session(qrCode);

            qrCode.setSession(newSession);

            hSession.save(newSession);
            transaction.commit();
            hSession.close();

            return new ResponseEntity<>("Session opened", HttpStatus.OK);
        } catch (ResourceNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }

    @RequestMapping("session/connect")
    public ResponseEntity<String> connectToSession(@RequestBody ConnectToSessionRequestBodyDto requestBody) {
        Transaction transaction = null;
        try (org.hibernate.Session hSession = HibernateUtil.getSessionFactory().openSession()) {
            transaction = hSession.beginTransaction();

            User user = hSession.get(User.class, requestBody.getUserId());

            if (user == null) {
                throw new ResourceNotFoundException("User not found");
            }

            String sessionQuery = String.format(SessionDao.getSessionByQrCodeIdQueryFormat, requestBody.getQrCodeId());
            Session session = hSession.createQuery(sessionQuery, Session.class).getSingleResult();

            if (session == null) {
                throw new ResourceNotFoundException("Session not found");
            }

            hSession.persist(user);
            hSession.persist(session);

            user.setActiveSession(session);
            session.addUser(user);

            transaction.commit();
            hSession.close();

            return new ResponseEntity<>("Successfully connected to session", HttpStatus.OK);
        } catch (ResourceNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }

    @RequestMapping("session/disconnect")
    public ResponseEntity<String> disconnectFromSession(@RequestBody DisconnectFromSessionRequestBodyDto requestBody) {
        Transaction transaction = null;
        try (org.hibernate.Session hSession = HibernateUtil.getSessionFactory().openSession()) {
            transaction = hSession.beginTransaction();

            User user = hSession.get(User.class, requestBody.getUserId());

            if (user == null) {
                throw new ResourceNotFoundException("User not found");
            }

            Session session = hSession.get(Session.class, user.getActiveSession().getId());

            if (session == null) {
                throw new ResourceNotFoundException("Session not found");
            }

            user.setActiveSession(null);
            session.removeUser(user);

            transaction.commit();
            hSession.close();

            return new ResponseEntity<>("Successfully disconnected from session", HttpStatus.OK);
        } catch (ResourceNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        }
        catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }

    @RequestMapping("session/close")
    public ResponseEntity<Double> closeSession(@RequestBody CloseSessionRequestBodyDto requestBody) {
        Transaction transaction = null;
        try (org.hibernate.Session hSession = HibernateUtil.getSessionFactory().openSession()) {
            transaction = hSession.beginTransaction();

            Session session = hSession.get(Session.class, requestBody.getSessionId());

            if (session == null) {
                throw new ResourceNotFoundException("Session not found");
            }

            session.setActive(false);

            session.getUsers()
                    .stream()
                    .forEach(user -> user.setActiveSession(null));

            transaction.commit();
            hSession.close();

            return new ResponseEntity<>(session.getOrderSum(), HttpStatus.OK);
        } catch (ResourceNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        }
        catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }


    @RequestMapping("session/{sessionId}/session-details")
    public ResponseEntity<Session> getSessionItems(@PathVariable Integer sessionId) {
        try {
            return new ResponseEntity<>(SessionDao.getSessionById(sessionId), HttpStatus.OK);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }

}
