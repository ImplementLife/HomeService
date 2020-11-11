package com.homeService.services;

import com.homeService.dao.LogDao;
import com.homeService.entity.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class LogService {
    @Autowired
    LogDao logDao;

    public List<Log> findAll() {
        return logDao.findAll();
    }

    public <S extends Log> S save(S s) {
        s.setDate(new Date());
        return logDao.save(s);
    }
}
