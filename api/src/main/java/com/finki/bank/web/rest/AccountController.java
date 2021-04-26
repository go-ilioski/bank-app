package com.finki.bank.web.rest;

import com.finki.bank.domain.enumerations.Role;
import com.finki.bank.service.AccountService;
import com.finki.bank.service.dto.AccountDto;
import com.finki.bank.service.dto.UserDto;
import com.finki.bank.util.Constants;
import com.finki.bank.web.rest.errors.BadRequestAlertException;
import org.apache.tomcat.util.http.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority(\"" + Constants.ADMIN_ROLE + "\")"
            + "|| hasAuthority(\"" + Constants.USER_ROLE + "\")" )
    public ResponseEntity<AccountDto> createAccount(@RequestBody AccountDto accountDto) throws URISyntaxException{

        if(accountDto.getId() != null){
            throw new BadRequestAlertException("A new accounts cannot already have an ID");
        }

        AccountDto newAccount = accountService.save(accountDto);

        return ResponseEntity.status(HttpStatus.OK)
//                .headers(HeaderUtil.createAlert(applicationName,  "A user is created with identifier " + newUser.getLogin(), newUser.getLogin()))
                .body(newAccount);


    }
    //TODO:UPDATE STATUS SUSPENDED SO SAMO ADMIN PRIVILEGIJA
    //public ResponseEntity<> updateStatus

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority(\"" + Constants.ADMIN_ROLE + "\")"
            + "|| hasAuthority(\"" + Constants.USER_ROLE + "\")" )
    public ResponseEntity<AccountDto> getAccounts(@PathVariable Long id) {
        //log.debug("REST request to get Accounts : {}", id);
        return accountService.findOne(id)
                .map(acc -> ResponseEntity.ok().body(acc))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    //TODO: sepak samo disable na acc ke napravime


//    @GetMapping("/merchants")
//    public ResponseEntity<List<AccountDto>> getMerchantAccounts(){
//
//        Role role = Role.MERCHANT;
//        return accountService.
//    }




}
