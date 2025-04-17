package com.jvyacheslav.messenger.dto;


import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class InputMessage {
    private String content;
    private String attachment;
    private String type;
}
