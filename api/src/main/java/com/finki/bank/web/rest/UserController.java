package com.finki.bank.web.rest;

import com.finki.bank.service.UserService;
import com.finki.bank.service.dto.RegisterUserDto;
import com.finki.bank.service.dto.UserDto;
import com.finki.bank.service.dto.UserPublicDetailsDto;
import com.finki.bank.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    //@PreAuthorize("hasAuthority(\"" + "ADMIN" + "\")")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody RegisterUserDto userDTO) throws URISyntaxException {
        //log.debug("REST request to save User : {}", userDTO);

        if (userDTO.getId() != null) {
            log.error("Request provides nonnull id");
            throw new BadRequestAlertException();
            // Lowercase the user login before comparing with database
        }
        UserDto newUser = userService.registerUser(userDTO);
        //mailService.sendCreationEmail(newUser);
        return ResponseEntity.created(new URI("/api/users/" + newUser.getEmail()))
//                .headers(HeaderUtil.createAlert(applicationName,  "A user is created with identifier " + newUser.getLogin(), newUser.getLogin()))
                .body(newUser);
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserPublicDetailsDto>> searchUsers(@RequestParam String email){
        return ResponseEntity.ok().body(userService.userSearch(email));
    }
}
