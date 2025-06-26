package org.example.onlinecontact.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response implements Serializable {
    private static final long serialVersionUID = 1L;
    private ResponseType type;
    private Object data;
    public enum ResponseType {
        USER_LIST, MESSAGE_LIST, SINGLE_MESSAGE, LOGIN_RESULT
    }
}

