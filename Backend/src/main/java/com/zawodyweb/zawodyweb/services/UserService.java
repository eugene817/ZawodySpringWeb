package com.zawodyweb.zawodyweb.services;

import com.zawodyweb.zawodyweb.database.dto.AuthenticationRequest;
import com.zawodyweb.zawodyweb.database.dto.RegisterRequest;
import com.zawodyweb.zawodyweb.database.dto.UserResponse;

public interface UserService {

  UserResponse register(RegisterRequest request);

  UserResponse authenticate(AuthenticationRequest request);

  UserResponse getThreadUser();
}
