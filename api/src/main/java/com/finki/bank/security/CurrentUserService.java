package com.finki.bank.security;

import com.finki.bank.domain.User;

public interface CurrentUserService {

    User getUser();

    Long getUserId();

}
