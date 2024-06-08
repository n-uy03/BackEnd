package com.example.thuctap.jwttoken;

import com.example.thuctap.NhanVienService.Impl.UrlApiRoleServiceImpl;
import com.example.thuctap.Repository.UrlApiRepository;
import com.example.thuctap.Security.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Log4j2
public class JwtRequestFilter extends OncePerRequestFilter {



    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private UrlApiRoleServiceImpl urlApiRoleServiceImpl;

    @Autowired
    private UrlApiRepository urlApiRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");


        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String jwt = authorizationHeader.substring(7);

            // Kiểm tra tính hợp lệ của token
            if (StringUtils.hasText(jwt) && jwtUtil.validateToken(jwt)) {
                // Trích xuất mã người dùng từ token
                String ma = jwtUtil.extractMa(jwt);


                UserDetails userDetails = customUserDetailsService.loadUserByUsername(ma);
                if (userDetails != null) {
                    UsernamePasswordAuthenticationToken authenticationToken
                            = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
                    List<String> roleNames = authorities.stream()
                            .map(GrantedAuthority::getAuthority)
                            .collect(Collectors.toList());
                    List<String> allowedUrls = urlApiRepository.findByUrlByRoleName(roleNames);
                    log.info("Các path có quyền trong role " +allowedUrls.toString());
                    String requestUrl = request.getRequestURI();
                    if (!isUrlAllowed(requestUrl, allowedUrls)) {
                        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                        response.getWriter().write(String.valueOf(Map.of("message", "Không có quyền truy cập")));
                        return;
                    }
                    // Lấy danh sách quyền của người dùng

                    // Kiểm tra xem có quyền nào được yêu cầu không
                }
            }
        }
        // Chuyển tiếp yêu cầu tới các bộ lọc tiếp theo trong chuỗi
        filterChain.doFilter(request, response);
    }
    private boolean isUrlAllowed(String requestUrl, List<String> allowedUrls) {
        for (String url : allowedUrls) {
            if (requestUrl.equals(url) || requestUrl.startsWith(url)) {
                return true;
            }
        }
        return false;
    }


}


