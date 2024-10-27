package com.todoapplication.todoapplication.security;

import com.todoapplication.todoapplication.domain.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTTokenGenerator implements ISecurityTokenGenrerator{
    @Override
    public Map<String, String> generateToken(User user) {
        String token = Jwts.builder().setSubject(user.getUserEmail())
                .setIssuedAt(new Date()).signWith(SignatureAlgorithm.HS256, "EncodedKey")
                .compact();
        Map<String,String> map = new HashMap<>();
        map.put("Token", token);
        map.put("Message", "Login Successfull");
        return map;
    }
}
