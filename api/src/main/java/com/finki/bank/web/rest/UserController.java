package com.finki.bank.web.rest;

import com.finki.bank.domain.User;
import com.finki.bank.service.UsersService;
import com.finki.bank.service.dto.RegisterUserDto;
import com.finki.bank.service.dto.UserDto;
import com.finki.bank.service.exceptions.EmailAlreadyUsedException;
import com.finki.bank.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/users")
public class UserController {

    private final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UsersService usersService;

    public UserController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping()
    //@PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody RegisterUserDto userDTO) throws URISyntaxException {
        //log.debug("REST request to save User : {}", userDTO);

        if (userDTO.getId() != null) {
            log.error("Request provides nonnull id");
            throw new BadRequestAlertException();
            // Lowercase the user login before comparing with database
        }
        UserDto newUser = usersService.registerUser(userDTO);
        //mailService.sendCreationEmail(newUser);
        return ResponseEntity.created(new URI("/api/users/" + newUser.getEmail()))
//                .headers(HeaderUtil.createAlert(applicationName,  "A user is created with identifier " + newUser.getLogin(), newUser.getLogin()))
                .body(newUser);
    }
}
