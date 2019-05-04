package com.leyou.user.web;

import com.leyou.user.pojo.User;
import com.leyou.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
//@RequestMapping("/user")不用加，因为已经在网关中配了/user/**了
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 实现用户数据的校验，主要包括对：手机号、用户名的唯一性校验
     */
    @GetMapping("/check/{data}/{type}")
    public ResponseEntity<Boolean> checkData(
            @PathVariable("data") String data, @PathVariable("type") Integer type) {
        return ResponseEntity.ok(userService.checkData(data, type));
    }

    /**
     * 发送短信
     */
    @PostMapping("code")
    public ResponseEntity<Void> sendCode(@RequestParam("phone") String phone) {
        userService.sendCode(phone);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 实现用户注册
     */
    @PostMapping("register")
    public ResponseEntity<Void> register(@Valid User user, BindingResult result, @RequestParam("code") String code) {
        //加了BindingResult result后SpringMVC不帮你处理user了
        if(result.hasFieldErrors()){
            throw new RuntimeException(result.getFieldErrors().stream().
                    map(e -> e.getDefaultMessage()).collect(Collectors.joining("|")));//错误信息以|隔开
        }
        userService.register(user, code);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    /**
     * 根据用户名和密码查询用户
     * */
    @GetMapping("/query")
    public ResponseEntity<User> queryUserByUsernameAndPassword(
            @RequestParam("username") String username,
            @RequestParam("password") String password
    ){
        return ResponseEntity.ok(userService.queryUserByUsernameAndPassword(username, password));
    }
}
