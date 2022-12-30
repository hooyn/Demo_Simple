package solution.demo.domain.account.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import solution.demo.domain.account.Account;
import solution.demo.domain.account.Role;
import solution.demo.domain.account.dto.CreateAccountRequestDto;
import solution.demo.domain.account.dto.LoginRequestDto;
import solution.demo.domain.account.dto.LoginResponseDto;
import solution.demo.domain.account.dto.ReadAllAccountResponseDto;
import solution.demo.domain.account.repository.AccountRepository;
import solution.demo.global.exception.CustomException;
import solution.demo.global.exception.ErrorCode;
import solution.demo.global.util.UUIDUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원 가입
     */
    public void createAccount(CreateAccountRequestDto dto) {
        try{
            if(checkUserIdDuplicate(dto.getUserId()))
                throw new CustomException(ErrorCode.BAD_REQUEST, "아이디가 이미 사용중입니다.");

            Account newAccount = buildNewAccount(dto);
            accountRepository.save(newAccount);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.BAD_REQUEST, e.getMessage());
        }
    }

    private Account buildNewAccount(CreateAccountRequestDto dto) {
        return Account.builder()
                .username(dto.getUsername())
                .userId(dto.getUserId())
                .password(passwordEncoder.encode(dto.getPassword()))
                .email(dto.getEmail())
                .emailCheck(false)
                .role(Role.USER).build();
    }

    private boolean checkUserIdDuplicate(String userId) {
        return accountRepository.existsByUserId(userId);
    }

    /**
     * 전체 회원 조회
     */
    public List<ReadAllAccountResponseDto> readAllAccount() {
        try {
            List<Account> accounts = accountRepository.findAll();
            List<ReadAllAccountResponseDto> response = new ArrayList<>();

            for (Account account : accounts)
                response.add(buildReadAllAccountResponse(account));

            return response;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.BAD_REQUEST, e.getMessage());
        }
    }

    private static ReadAllAccountResponseDto buildReadAllAccountResponse(Account account) {
        return ReadAllAccountResponseDto.builder()
                .uuid(UUIDUtil.UUIDToString(account.getUuid()))
                .username(account.getUsername())
                .userId(account.getUserId())
                .email(account.getEmail())
                .emailCheck(account.getEmailCheck())
                .role(account.getRole().name())
                .build();
    }

    /**
     * 로그인
     */
    public LoginResponseDto login(LoginRequestDto dto) {
        try {
            Optional<Account> accountOptional = accountRepository.findByUserId(dto.getUserId());

            if(accountOptional.isEmpty())
                throw new CustomException(ErrorCode.BAD_REQUEST, "아이디 또는 비밀번호를 확인해주세요.");

            Account account = accountOptional.get();
            if(!checkPassword(dto.getPassword(), account.getPassword()))
                throw new CustomException(ErrorCode.BAD_REQUEST, "아이디 또는 비밀번호를 확인해주세요.");

            return buildLoginResponse(account);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.BAD_REQUEST, e.getMessage());
        }
    }

    private static LoginResponseDto buildLoginResponse(Account account) {
        return LoginResponseDto.builder()
                .uuid(UUIDUtil.UUIDToString(account.getUuid()))
                .userId(account.getUserId())
                .username(account.getUsername())
                .email(account.getEmail())
                .role(account.getRole().name()).build();
    }

    private boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
