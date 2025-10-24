package com.example.rbac.dto;
/*DTO que representa a resposta enviada após o login.
  Campos:
 - token JWT
 - tipo de token (Bearer)

 */


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
