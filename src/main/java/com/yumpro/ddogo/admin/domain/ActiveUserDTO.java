package com.yumpro.ddogo.admin.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.text.DateFormat;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class ActiveUserDTO {
    private DateFormat month;
    private int count;
}
