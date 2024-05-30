package com.example.thuctap.jwttoken;

import java.io.Serializable;

public class JwtRequest implements Serializable {
    private static final long serialVersionUID = 5926468583005150707L;
    private String ma;
    private String matkhau;

    public JwtRequest() {
    }

    public JwtRequest(String ma, String matkhau) {
        this.ma = ma;
        this.matkhau = matkhau;
    }


    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }
}
