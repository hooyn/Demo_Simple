package solution.demo.domain.account.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import solution.demo.domain.account.dto.CreateAccountRequestDto;
import solution.demo.domain.account.dto.LoginRequestDto;
import solution.demo.domain.account.email.dto.ConfirmEmailAuthRequestDto;
import solution.demo.domain.account.email.service.EmailService;
import solution.demo.domain.account.service.AccountService;
import solution.demo.global.response.ResponseWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account")
public class AccountController {

    private final HttpServletRequest request;
    private final AccountService accountService;
    private final EmailService emailService;


    @ApiOperation("회원 가입")
    @PostMapping("")
    public ResponseEntity<?> createAccount(@Valid @RequestBody CreateAccountRequestDto dto) {
        accountService.createAccount(dto);
        return new ResponseEntity<>(new ResponseWrapper(request, HttpStatus.OK,
                true, "회원가입이 완료되었습니다.", List.of()), HttpStatus.OK);
    }

    @ApiOperation("회원 조회")
    @GetMapping("")
    public ResponseEntity<?> readAllAccount() {
        return new ResponseEntity<>(new ResponseWrapper(request, HttpStatus.OK,
                true, "전체 회원이 조회되었습니다.", accountService.readAllAccount()), HttpStatus.OK);
    }

    @ApiOperation("로그인")
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDto dto) {
        return new ResponseEntity<>(new ResponseWrapper(request, HttpStatus.OK,
                true, "로그인 되었습니다.", accountService.login(dto)), HttpStatus.OK);
    }

    @ApiOperation("이메일 인증")
    @PostMapping("/email")
    public ResponseEntity<?> confirmEmailAuth(@RequestBody ConfirmEmailAuthRequestDto dto) {

        //빠른 응답을 하고 로직은 비동기처리를 하기 위해 인증 코드를 생성하고,
        String authCode = emailService.makeAuthCode();

        Map<String, String> responseData = new HashMap<>();
        responseData.put("Email", dto.getEmail());
        responseData.put("AuthCode", authCode);
        //비동기로 처리하기 위해 인증 코드를 전달한다.
        emailService.sendAuthEmail(dto.getEmail(), authCode);

        return new ResponseEntity<>(new ResponseWrapper(request, HttpStatus.OK,
                true, "이메일이 성공적으로 전송되었습니다.", responseData), HttpStatus.OK);
    }
}
