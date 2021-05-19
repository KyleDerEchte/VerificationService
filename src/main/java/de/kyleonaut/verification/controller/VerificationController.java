package de.kyleonaut.verification.controller;


import de.kyleonaut.verification.user.User;
import de.kyleonaut.verification.user.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@SpringBootApplication
@RestController
@RequestMapping("/verification")
public class VerificationController {
    private final Logger logger = Logger.getLogger("Verification");
    
    @Autowired
    private UserService userService;


    @ApiOperation(value = "Returns a user given by the UUID")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/user")
    public User findUser(@RequestParam(value = "uniqueId") UUID uuid) {
        logger.log(Level.INFO, String.format("Queried User with UUID: %s", uuid));
        return userService.findUser(uuid);
    }

    @ApiOperation(value = "Returns all users given by the STATUS")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/users")
    public Collection<User> findUsersByStatus(@RequestParam(value = "status") User.STATUS status) {
        logger.log(Level.INFO, String.format("Queried Users with Status: %s", status));
        return userService.findUsersByStatus(status);
    }

    @ApiOperation(value = "Save or update an user")
    @PostMapping(
            value = "/user/save",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object saveUser(@RequestBody User user, @RequestHeader(value = "auth") UUID token) {
        if (token == null || !token.toString().equals("3efac26d-e737-48d3-8671-45ad7cb4d119")) {
            logger.log(Level.INFO, "Request not authorized.");
            return HttpStatus.UNAUTHORIZED;
        }
        userService.saveUser(user);
        logger.log(Level.INFO, String.format("Saved User with UUID: %s", user.getUuid()));
        return user;
    }

    @ApiOperation(value = "Delete an user by the given UUID")
    @DeleteMapping(value = "/user")
    public HttpStatus removeUser(@RequestParam(value = "uniqueId") UUID uuid, @RequestHeader(value = "auth") UUID token) {
        if (!token.toString().equals("3efac26d-e737-48d3-8671-45ad7cb4d119")) {
            logger.log(Level.INFO, "Request not authorized.");
            return HttpStatus.UNAUTHORIZED;
        }
        if (userService.findUser(uuid) == null) {
            logger.log(Level.INFO, String.format("User with UUID: %s not found.", uuid));
            return HttpStatus.NOT_FOUND;
        }
        userService.remove(uuid);
        logger.log(Level.INFO, String.format("Deleted User with UUID: %s", uuid));
        return HttpStatus.OK;
    }
}
