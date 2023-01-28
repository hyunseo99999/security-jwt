package com.jwt.web.user.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.jwt.core.filter.JwtProperties;
import com.jwt.web.user.dto.TokenDto;
import com.jwt.web.user.dto.UserDto;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class UserController {

    @ApiOperation(
            value = "로그인"
            , notes = "로그인하는 API 입니다. 사용자 아이디와 암호를 입력해야 합니다.")
    @ApiImplicitParams(
            {
                @ApiImplicitParam(
                        name = "password", value = "사용자 패스워드", required = true , dataType = "formData" , paramType = "path", defaultValue = ""
                ),
                @ApiImplicitParam(
                        name = "username", value = "사용자 아이디", required = true , dataType = "formData" , paramType = "path", defaultValue = ""
                )
            }
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "[완료]로그인이 완료되었습니다.")
            , @ApiResponse(code = 401, message = "[에러]비밀번호가 맞지 않아 로그인에 실패하였습니다")
            , @ApiResponse(code = 500, message = "[에러]서버에 문제가 있어 로그인하지 못했습니다")
    })
    @GetMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestParam String username , @RequestParam String password) {
        TokenDto tokenDto = new TokenDto();

        UserDto userDto = UserDto
                                .builder()
                                .username(username)
                                .password(password)
                                .build();

        String token = JWT.create()
                .withSubject(userDto.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                .withClaim("username", userDto.getUsername())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

        tokenDto.setToken(token);
        return new ResponseEntity<> (tokenDto, HttpStatus.OK);
    }
}
