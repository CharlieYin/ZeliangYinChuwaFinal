package com.chuwa.account.payload;

import java.util.Date;

public class AuthDto {
    private Date timestamp;
    private String message;
    private String accessToken;
    private String tokenType;

    public AuthDto(String message, String accessToken) {
        this.timestamp = new Date();
        this.message = message;
        this.accessToken = accessToken;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}
