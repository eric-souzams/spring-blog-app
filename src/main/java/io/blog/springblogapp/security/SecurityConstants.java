package io.blog.springblogapp.security;

public class SecurityConstants {

    public static final long EXPIRATION_TIME = 864000000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/api/v1/users";
    public static final String LOGIN_URL = "/api/v1/auth/login";
    public static final String TOKEN_SECRET = "wGsqKX4uQc2ZRqq98Hu8rer99JIRit1AaoL1Cdf8tq2dfg7486749wtCrdQJLp4T2U2df";

}
