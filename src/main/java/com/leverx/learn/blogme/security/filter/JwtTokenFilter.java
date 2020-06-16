package com.leverx.learn.blogme.security.filter;

import com.leverx.learn.blogme.security.jwt.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filter checks request for token availability and if it is then tries to authenticate it.
 *
 * @author Viktar on 09.06.2020
 */
public class JwtTokenFilter extends BasicAuthenticationFilter {

    private final JwtTokenProvider tokenProvider;

    public JwtTokenFilter(AuthenticationManager authManager, JwtTokenProvider tokenProvider) {
        super(authManager);
        Assert.notNull(tokenProvider, "tokenProvider must not be null");

        this.tokenProvider = tokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String token = tokenProvider.resolveToken(req);
        if (StringUtils.isEmpty(token) || !tokenProvider.validateToken(token)){
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = tokenProvider.getAuthentication(token);
        if(authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(req, res);
    }
}
