package org.example.onlinecontact.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
@Data
@Builder
@AllArgsConstructor
public class MessageDTO implements Serializable {
    private String sendUsername;
    private String getUsername;
    private String message;
    private LocalDateTime sendTime;
    private Integer sendUserId;
    private Integer getUserId;
}
