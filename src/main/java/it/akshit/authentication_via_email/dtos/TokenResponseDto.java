package it.akshit.authentication_via_email.dtos;

public class TokenResponseDto {
    private final String accessToken;
    private final String refreshToken;

    public TokenResponseDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public static TokenResponseDtoBuilder builder() {
        return new TokenResponseDtoBuilder();
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public static class TokenResponseDtoBuilder {
        private String accessToken;
        private String refreshToken;

        TokenResponseDtoBuilder() {
        }

        public TokenResponseDtoBuilder accessToken(String accessToken) {
            this.accessToken = accessToken;
            return this;
        }

        public TokenResponseDtoBuilder refreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
            return this;
        }

        public TokenResponseDto build() {
            return new TokenResponseDto(this.accessToken, this.refreshToken);
        }

        public String toString() {
            return "TokenResponseDto.TokenResponseDtoBuilder(accessToken=" + this.accessToken + ", refreshToken=" + this.refreshToken + ")";
        }
    }
}
