package com.logitics.erp.common.filter;

import com.logitics.erp.common.util.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

	private final JwtProvider jwtProvider;
    private static final Set<String> EXCLUDE_PATHS = Set.of(
            "/api/v1/employees/login",
            "/api/v1/employees/logout",
            "/api/v1/employees/joinErp",
            "/api/v1/employees/oauth/kakao"
    );

	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

		String bearerToken = request.getHeader("Authorization");


        String path = request.getRequestURI();

        // 로그인 요청은 토큰 검사 제외
        if (EXCLUDE_PATHS.contains(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 토큰 없는 경우
        if (bearerToken == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 추천
            response.setContentType("application/json;charset=UTF-8");

            response.getWriter().write("""
					{
					  "success": false,
					  "message": "토큰이 만료되었거나 유효하지 않습니다.",
					  "data": null
					}
				""");

            return;
        }


        // 토큰은 있지만 올바른 토큰이 아닌지 확인
		if (bearerToken != null && bearerToken.startsWith("Bearer")) {
			String token = bearerToken.substring(7);

			if (jwtProvider.validateToken(token)) {
				String name = jwtProvider.getName(token);

				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(name, null, List.of());
				SecurityContextHolder.getContext().setAuthentication(authentication);

//				UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
//								"tom",
//								null,
//								List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))
//				);

			} else {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.setContentType("application/json;charset=UTF-8");

				response.getWriter().write("""
					{
					  "success": false,
					  "message": "토큰이 만료되었거나 유효하지 않습니다.",
					  "data": null
					}
				""");

				return;
			}
		}

		filterChain.doFilter(request, response);
	}
}
