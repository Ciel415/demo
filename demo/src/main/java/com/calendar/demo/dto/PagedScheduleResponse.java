package com.calendar.demo.dto;

import com.calendar.demo.entity.Schedule;
import lombok.Getter;

@Getter
public class PagedScheduleResponse {
    private Long id;
    private String title;
    private String content;
    private String username;
    private int commentCount;
    private String createdAt;
    private String modifiedAt;

    public PagedScheduleResponse(Schedule schedule) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.content = schedule.getContent();
        this.username = schedule.getUser().getUsername();
        this.commentCount = schedule.getComments() != null ? schedule.getComments().size() : 0;
        this.createdAt = schedule.getCreatedAt().toString();
        this.modifiedAt = schedule.getModifiedAt().toString();
    }
}
