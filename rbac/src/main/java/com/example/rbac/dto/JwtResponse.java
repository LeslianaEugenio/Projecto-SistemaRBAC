package com.example.rbac.dto;

public class JwtResponse {
    private String token; // token JWT

    public JwtResponse() {}

    public JwtResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
}
