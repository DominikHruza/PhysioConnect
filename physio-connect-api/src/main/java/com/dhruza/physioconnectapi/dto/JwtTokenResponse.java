package com.dhruza.physioconnectapi.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class JwtTokenResponse {
    private String accessToken;
    private String refreshToken;
}
