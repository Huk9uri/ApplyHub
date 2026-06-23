package com.applyhub.auth.dto;

public record LoginResponse(                                                                                                                                                                                    
    String accessToken,                                                                                                                                                                                     
    String tokenType,                                                                                                                                                                                       
    long expiresIn,                                                                                                                                                                                         
    LoginUserResponse user                                                                                                                                                                                  
) {} 