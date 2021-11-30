package com.education.controller.system;

import com.education.common.Result;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2021-11-22-16-15
 */
@CrossOrigin
@RestController
@RequestMapping("/vue-element-admin")
public class example {
    @PostMapping("/user/login")
    public Object login() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("token","admin-token");
        return Result.success().data(map);
    }

    @GetMapping("/user/info")
    public Object info() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name","Super Admin");
        map.put("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        ArrayList<String> list = new ArrayList<>();
        list.add("admin");
        map.put("roles",list);
        map.put("introduction","I am a super administrator");
        return Result.success().data(map);
    }
}
