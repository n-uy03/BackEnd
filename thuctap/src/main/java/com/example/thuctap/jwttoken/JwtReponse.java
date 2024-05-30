package com.example.thuctap.jwttoken;

import java.io.Serializable;

public class JwtReponse implements Serializable {


    private static final long serialVersionUID = -8091879091924046844L;
    private final String jwttoken;
    private final String role;

    public JwtReponse(String jwttoken, String role) {
        this.jwttoken = jwttoken;
        this.role = role;
    }
    public String getToken() {
        return this.jwttoken;
    }
    public String getRole() {
        return this.role;
    }
}
