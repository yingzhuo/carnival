/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.actuator.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpHeaders;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Base64;
import java.util.Objects;
import java.util.Set;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author 应卓
 * @since 1.6.33
 */
public class ActuatorSecurityFilter extends OncePerRequestFilter {

    private final Set<User> inMemoryUsers;

    public ActuatorSecurityFilter(Set<User> inMemoryUsers) {
        this.inMemoryUsers = inMemoryUsers;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, javax.servlet.http.HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String headerValue = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (inMemoryUsers != null && !inMemoryUsers.isEmpty()) {
            for (User user : inMemoryUsers) {
                if (Objects.equals(user.toString(), headerValue)) {
                    filterChain.doFilter(request, response);
                    return;
                }
            }
        }

        response.setContentType("application/json");
        response.setCharacterEncoding(UTF_8.displayName());
        response.getWriter().print("{}");
        response.getWriter().flush();
    }

    // ----------------------------------------------------------------------------------------------------------------

    @Getter
    @Setter
    public final static class User {
        private String username;
        private String password;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            User user = (User) o;
            return username.equals(user.username) &&
                    password.equals(user.password);
        }

        @Override
        public int hashCode() {
            return Objects.hash(username, password);
        }

        @Override
        public String toString() {
            return "Basic " + Base64.getUrlEncoder().encodeToString((username + ":" + password).getBytes(UTF_8));
        }
    }

}
