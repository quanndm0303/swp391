package com.app.zware.RequestEntities;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class UserRequest {
    private String email;
    private String password;
    private String name;
    private String role;
    private Date dateofbirth;
    private String phone;
    private String gender;
    private String avatar;
}
