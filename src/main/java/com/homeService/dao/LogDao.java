package com.homeService.dao;

import com.homeService.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogDao extends JpaRepository<Log, Long> {
}
