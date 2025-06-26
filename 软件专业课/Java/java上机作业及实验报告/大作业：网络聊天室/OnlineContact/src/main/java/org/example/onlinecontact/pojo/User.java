package org.example.onlinecontact.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
public class User implements Serializable {
    private Integer id;
    private String userName;
    private String passWord;
}
