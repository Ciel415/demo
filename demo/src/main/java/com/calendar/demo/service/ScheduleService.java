package com.calendar.demo.service;

import com.calendar.demo.dto.PagedScheduleResponse;
import com.calendar.demo.dto.ScheduleRequest;
import com.calendar.demo.dto.ScheduleResponse;
import com.calendar.demo.entity.Schedule;
import com.calendar.demo.entity.User;
import com.calendar.demo.repository.ScheduleRepository;
import com.calendar.demo.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    public ScheduleService(ScheduleRepository scheduleRepository, UserRepository userRepository) {
        this.scheduleRepository = scheduleRepository;
        this.userRepository = userRepository;
    }

    public ScheduleResponse create(ScheduleRequest request, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("사용자 없음"));

        Schedule schedule = new Schedule();
        schedule.setTitle(request.getTitle());
        schedule.setContent(request.getContent());
        schedule.setUser(user);

        return new ScheduleResponse(scheduleRepository.save(schedule));
    }

    public List<ScheduleResponse> getAll(Long userId) {
        return scheduleRepository.findByUserId(userId)
                .stream()
                .map(ScheduleResponse::new)
                .collect(Collectors.toList());
    }

    public ScheduleResponse update(Long id, ScheduleRequest request) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("일정 없음"));

        schedule.setTitle(request.getTitle());
        schedule.setContent(request.getContent());

        return new ScheduleResponse(scheduleRepository.save(schedule));
    }

    public Page<PagedScheduleResponse> getPagedSchedules(String keyword, Pageable pageable) {
        Pageable sorted = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize() == 0 ? 10 : pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC, "modifiedAt")
        );

        return scheduleRepository.findByTitleContainingOrContentContaining(keyword, keyword, sorted)
                .map(PagedScheduleResponse::new);
    }


    public void delete(Long id) {
        scheduleRepository.deleteById(id);
    }
}
