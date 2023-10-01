package com.example.pastebin.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Builder
@Data
@AllArgsConstructor
public class JwtTokenDto {
    @JsonProperty("access_token")
    String access_token;
    @JsonProperty("refresh_token")
    String refresh_token;
}
