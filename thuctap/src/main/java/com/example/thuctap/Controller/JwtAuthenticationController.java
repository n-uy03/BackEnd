package com.example.thuctap.Controller;

import com.example.thuctap.Security.CustomUserDetailsService;
import com.example.thuctap.jwttoken.JwtResponse;
import com.example.thuctap.jwttoken.JwtRequest;
import com.example.thuctap.jwttoken.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/auth")
public class JwtAuthenticationController {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        final UserDetails userDetails = customUserDetailsService.loadUserByUsername(jwtRequest.getMa());

        if (!userDetails.getPassword().equals(jwtRequest.getMatkhau())) {
            throw new BadCredentialsException("Mã hoặc mật khẩu không đúng");
        }

        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(jwt));
    }
}
