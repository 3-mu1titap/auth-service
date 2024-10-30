package com.multitap.auth.application;

public interface BlackListService {

    void addToBlacklist(String token, long expiration);
}
