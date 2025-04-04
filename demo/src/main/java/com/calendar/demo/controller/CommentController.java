package com.calendar.demo.controller;

import com.calendar.demo.dto.CommentRequest;
import com.calendar.demo.dto.CommentResponse;
import com.calendar.demo.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<CommentResponse> create(@RequestBody @Valid CommentRequest request) {
        return ResponseEntity.ok(commentService.create(request));
    }

    @GetMapping("/schedule/{scheduleId}")
    public ResponseEntity<List<CommentResponse>> getBySchedule(@PathVariable Long scheduleId) {
        return ResponseEntity.ok(commentService.getCommentsBySchedule(scheduleId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentResponse> update(@PathVariable Long id, @RequestBody String content) {
        return ResponseEntity.ok(commentService.update(id, content));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        commentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
