package com.calendar.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleRequest {

    @NotBlank(message = "제목은 필수 항목입니다.")
    @Size(max = 10, message = "제목은 10자 이내여야 합니다.")
    private String title;

    @NotBlank(message = "내용은 필수 항목입니다.")
    private String content;
}
