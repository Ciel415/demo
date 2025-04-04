package com.calendar.demo.service;

import com.calendar.demo.dto.CommentRequest;
import com.calendar.demo.dto.CommentResponse;
import com.calendar.demo.entity.Comment;
import com.calendar.demo.entity.Schedule;
import com.calendar.demo.entity.User;
import com.calendar.demo.repository.CommentRepository;
import com.calendar.demo.repository.ScheduleRepository;
import com.calendar.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;

    public CommentService(CommentRepository commentRepository, UserRepository userRepository, ScheduleRepository scheduleRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.scheduleRepository = scheduleRepository;
    }

    public CommentResponse create(CommentRequest request) {
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new IllegalArgumentException("사용자 없음"));
        Schedule schedule = scheduleRepository.findById(request.getScheduleId()).orElseThrow(() -> new IllegalArgumentException("일정 없음"));

        Comment comment = new Comment();
        comment.setContent(request.getContent());
        comment.setUser(user);
        comment.setSchedule(schedule);

        return new CommentResponse(commentRepository.save(comment));
    }

    public List<CommentResponse> getCommentsBySchedule(Long scheduleId) {
        return commentRepository.findByScheduleId(scheduleId).stream()
                .map(CommentResponse::new)
                .collect(Collectors.toList());
    }

    public CommentResponse update(Long commentId, String content) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("댓글 없음"));
        comment.setContent(content);
        return new CommentResponse(commentRepository.save(comment));
    }

    public void delete(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
