package site.metacoding.blogv3.web;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import site.metacoding.blogv3.handler.ex.CustomException;
import site.metacoding.blogv3.service.UserService;
import site.metacoding.blogv3.util.UtilValid;
import site.metacoding.blogv3.web.dto.user.JoinReqDto;
import site.metacoding.blogv3.web.dto.user.PasswordResetReqDto;

@RequiredArgsConstructor
@Controller
public class UserController {

    // DI
    private final UserService userService;

    @GetMapping("/login-form")
    public String loginForm() {
        return "/user/loginForm";
    }

    @GetMapping("/join-form")
    public String joinForm() {
        return "/user/joinForm";
    }

    @GetMapping("/user/password-reset-form")
    public String passwordResetForm() {
        return "/user/passwordResetForm";
    }

    @PostMapping("/user/password-reset")
    public String passwordReset(@Valid PasswordResetReqDto passwordResetReqDto, BindingResult bindingResult) {

        UtilValid.요청에러처리(bindingResult);

        userService.패스워드초기화(passwordResetReqDto);

        return "redirect://login-form";
    }

    // ResponseEntity는 @ResponseBody를 붙이지 않아도 data를 리턴한다.
    @GetMapping("/api/user/username-same-check")
    public ResponseEntity<?> usernameSameCheck(String username) {
        boolean isNotSame = userService.유저네임중복체크(username); // true (같지 않다)
        return new ResponseEntity<>(isNotSame, HttpStatus.OK);
    }

    @PostMapping("/join")
    public String join(@Valid JoinReqDto joinReqDto, BindingResult bindingResult) {

        UtilValid.요청에러처리(bindingResult);

        // 핵심 로직
        userService.회원가입(joinReqDto.toEntity());

        return "redirect:/login-form";
    }
}