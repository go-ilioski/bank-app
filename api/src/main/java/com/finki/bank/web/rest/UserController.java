package com.finki.bank.web.rest;

import com.finki.bank.domain.enumerations.Currency;
import com.finki.bank.domain.enumerations.Role;
import com.finki.bank.security.CurrentUserService;
import com.finki.bank.service.AccountService;
import com.finki.bank.service.UserService;
import com.finki.bank.service.dto.AccountDto;
import com.finki.bank.service.dto.RegisterUserDto;
import com.finki.bank.service.dto.UserDto;
import com.finki.bank.service.dto.UserPublicDetailsDto;
import com.finki.bank.service.mapper.UserMapper;
import com.finki.bank.util.Constants;
import com.finki.bank.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    private final CurrentUserService currentUserService;

    private final UserMapper userMapper;

    private final AccountService accountService;

    public UserController(UserService userService, CurrentUserService currentUserService, UserMapper userMapper, AccountService accountService) {
        this.userService = userService;
        this.currentUserService = currentUserService;
        this.userMapper = userMapper;
        this.accountService = accountService;
    }

    @PostMapping("/register")
//    @PreAuthorize("hasAuthority(\"" + Constants.ADMIN_ROLE + "\")"
//            + "|| hasAuthority(\"" + Constants.USER_ROLE + "\")" )
    //@PreAuthorize("hasAuthority(\"" + Constants.USER_ROLE + "\")")
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
    @PreAuthorize("hasAuthority(\"" + Constants.ADMIN_ROLE + "\")"
            + "|| hasAuthority(\"" + Constants.USER_ROLE + "\")" )
    public ResponseEntity<List<UserPublicDetailsDto>> searchUsers(@RequestParam String email){
        return ResponseEntity.ok().body(userService.userSearch(email));
    }

    @GetMapping("accounts")
    @PreAuthorize("hasAuthority(\"" + Constants.ADMIN_ROLE + "\")"
            + "|| hasAuthority(\"" + Constants.USER_ROLE + "\")" )
    public ResponseEntity<List<AccountDto>> getUserAccounts(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.listUserAccounts());
    }

    @GetMapping("/authenticated")
    //@PreAuthorize("hasAuthority(\"" + Constants.USER_ROLE + "\")")
    public ResponseEntity<UserDto> getAuthenticatedUser() {
        UserDto userDto = userMapper.convertToDto(currentUserService.getUser());
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/register/merchant")
    @PreAuthorize("hasAuthority(\"" + Constants.ADMIN_ROLE + "\")")
    public ResponseEntity<UserDto> createMerchant(@Valid @RequestBody RegisterUserDto userDTO) throws URISyntaxException {
        //log.debug("REST request to save User : {}", userDTO);

        if (userDTO.getId() != null) {
            log.error("Request provides nonnull id");
            throw new BadRequestAlertException();
            // Lowercase the user login before comparing with database
        }

        userDTO.setRole(Role.MERCHANT.name());
        UserDto newUser = userService.registerUser(userDTO);

        AccountDto accountDto = new AccountDto();
        accountDto.setCurrency(Currency.MKD.name());
        accountDto.setOwnerId(newUser.getId());
        accountService.save(accountDto);


        //mailService.sendCreationEmail(newUser);
        return ResponseEntity.created(new URI("/api/users/" + newUser.getEmail()))
//                .headers(HeaderUtil.createAlert(applicationName,  "A user is created with identifier " + newUser.getLogin(), newUser.getLogin()))
                .body(newUser);
    }


    @GetMapping("merchants")
    @PreAuthorize("hasAuthority(\"" + Constants.ADMIN_ROLE + "\")"
            + "|| hasAuthority(\"" + Constants.USER_ROLE + "\")" )
    public ResponseEntity<List<UserPublicDetailsDto>> getAllMerchants(){
        return ResponseEntity.ok().body(userService.listAllMerchants());
    }

    @GetMapping("favorites")
    @PreAuthorize("hasAuthority(\"" + Constants.USER_ROLE + "\")")
    public ResponseEntity<List<UserPublicDetailsDto>> getAllFavorites(){
        return ResponseEntity.ok().body(userService.findAllFavouriteUsers());
    }

    @PostMapping("favorites")
    @PreAuthorize("hasAuthority(\"" + Constants.USER_ROLE + "\")")
    public ResponseEntity<List<UserPublicDetailsDto>> addFavorite(@RequestBody UserPublicDetailsDto request) {
        Long userId = request.getId();
        if (userId == null) {
            throw new BadRequestAlertException("No user id provided");
        }
        return ResponseEntity.ok().body(userService.addFavouriteUser(userId));
    }

    @DeleteMapping("favorites/{id}")
    @PreAuthorize("hasAuthority(\"" + Constants.USER_ROLE + "\")")
    public ResponseEntity<Void> removeFavorite(@PathVariable Long id) {
        userService.removeFavouriteUser(id);
        return ResponseEntity.ok().build();
    }

}
