package com.finki.bank.service.mapper;

import com.finki.bank.domain.Account;
import com.finki.bank.domain.Request;
import com.finki.bank.service.dto.AccountDto;
import com.finki.bank.service.dto.RequestDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class RequestMapper {

    public RequestDto convertToDto(Request request){

        ModelMapper mapper = new ModelMapper();
        RequestDto requestDto = mapper.map(request,RequestDto.class);
        return requestDto;
    }

    public Request convertToRequest(RequestDto requestDto){

        ModelMapper mapper = new ModelMapper();
        Request request = mapper.map(requestDto,Request.class);
        return request;
    }
}
