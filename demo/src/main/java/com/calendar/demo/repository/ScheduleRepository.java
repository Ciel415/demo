package com.calendar.demo.repository;

import com.calendar.demo.entity.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    Page<Schedule> findByTitleContainingOrContentContaining(String title, String content, Pageable pageable);

    List<Schedule> findByUserId(Long userId);
}