package com.yumpro.ddogo.main.repository;

import org.springframework.dao.DataAccessException;

import java.util.HashMap;
import java.util.List;

public interface MainRepository {

    public List<HashMap<String, Object>> eatjjim();

    public List<HashMap<String, Object>> cafejjim();

}
