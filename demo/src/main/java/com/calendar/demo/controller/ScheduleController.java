package com.calendar.demo.controller;

import com.calendar.demo.dto.PagedScheduleResponse;
import com.calendar.demo.dto.ScheduleRequest;
import com.calendar.demo.dto.ScheduleResponse;
import com.calendar.demo.service.ScheduleService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ResponseEntity<ScheduleResponse> create(@RequestBody @Valid ScheduleRequest request, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return ResponseEntity.status(401).build();
        return ResponseEntity.ok(scheduleService.create(request, userId));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<ScheduleResponse>> getAll(@PathVariable Long userId) {
        return ResponseEntity.ok(scheduleService.getAll(userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScheduleResponse> update(@PathVariable Long id, @RequestBody @Valid ScheduleRequest request) {
        return ResponseEntity.ok(scheduleService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        scheduleService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/page")
    public ResponseEntity<Page<PagedScheduleResponse>> getAllPaged(
            @RequestParam(required = false, defaultValue = "") String keyword,
            Pageable pageable) {
        return ResponseEntity.ok(scheduleService.getPagedSchedules(keyword, pageable));
    }
}
