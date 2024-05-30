package com.example.thuctap.Controller;

import com.example.thuctap.Security.CustomUserDetailsService;
import com.example.thuctap.jwttoken.JwtReponse;
import com.example.thuctap.jwttoken.JwtRequest;
import com.example.thuctap.jwttoken.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "http://localhost:3000")
@RestController

public class JwtAuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getMa(), authenticationRequest.getMatkhau());

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getMa());

        final String token = jwtTokenUtil.generateToken(userDetails);
        final String role = userDetails.getAuthorities().iterator().next().getAuthority(); // Lấy vai trò đầu tiên

        return ResponseEntity.ok(new JwtReponse(token, role));
    }

    private void authenticate(String ma, String matkhau) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(ma, matkhau));
        } catch (DisabledException e) {
            throw new Exception("NHANVIEN_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

}
