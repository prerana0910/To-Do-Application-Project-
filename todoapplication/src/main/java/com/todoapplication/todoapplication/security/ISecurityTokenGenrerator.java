package com.todoapplication.todoapplication.security;

import com.todoapplication.todoapplication.domain.User;
import java.util.Map;

public interface ISecurityTokenGenrerator {
    public Map<String,String> generateToken(User user);
}
