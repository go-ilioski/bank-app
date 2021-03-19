package com.finki.bank.service;

import com.finki.bank.domain.User;
import com.finki.bank.service.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {

    UserDto save(UserDto userDto);

    List<UserDto> findAll();

    Page<UserDto> findAllWithEagerRelationships(Pageable pageable);

    Optional<UserDto> findOne(Long id);

    void delete(Long id);


}
