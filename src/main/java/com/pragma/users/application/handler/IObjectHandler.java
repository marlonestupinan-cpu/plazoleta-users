package com.pragma.users.application.handler;

import com.pragma.users.application.dto.request.ObjectRequestDto;
import com.pragma.users.application.dto.response.ObjectResponseDto;

import java.util.List;

public interface IObjectHandler {

    void saveObject(ObjectRequestDto objectRequestDto);

    List<ObjectResponseDto> getAllObjects();
}