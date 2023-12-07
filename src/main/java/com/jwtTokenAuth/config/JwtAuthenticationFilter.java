	package com.jwtTokenAuth.config;
	
	import java.io.IOException;
	
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
	import org.springframework.security.core.context.SecurityContextHolder;
	import org.springframework.security.core.userdetails.UserDetails;
	import org.springframework.security.core.userdetails.UserDetailsService;
	import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
	import org.springframework.web.filter.OncePerRequestFilter;
	import org.springframework.web.servlet.HandlerExceptionResolver;
	
	import com.jwtTokenAuth.service.JwtService;
	
	import io.jsonwebtoken.MalformedJwtException;
	import io.jsonwebtoken.security.SignatureException;
	import jakarta.servlet.FilterChain;
	import jakarta.servlet.ServletException;
	import jakarta.servlet.http.HttpServletRequest;
	import jakarta.servlet.http.HttpServletResponse;
	import jakarta.validation.constraints.NotNull;
	import lombok.RequiredArgsConstructor;
	
	
	@RequiredArgsConstructor
	public class JwtAuthenticationFilter extends OncePerRequestFilter {
		
		@Autowired
		private JwtService jwtService;
		
		@Autowired
		private UserDetailsService userDetailsService;
		
		private final HandlerExceptionResolver exceptionResolver;
	
		@Override
		protected void doFilterInternal(
				@NotNull HttpServletRequest request,
				@NotNull HttpServletResponse response,
				@NotNull FilterChain filterChain)
				throws ServletException, IOException {
		
			try {
		 final String authHeader = request.getHeader("Authorization");
			final String token;
			final String userEmail;
			
			if(authHeader == null  || !authHeader.startsWith("Bearer ")) {
				filterChain.doFilter(request, response);
				return ;
			}
			
			token = authHeader.substring(7);
			userEmail = jwtService.extarctUsername(token);
			
			if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
				if(jwtService.isValidToken(token, userDetails)) {
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userEmail, null, userDetails.getAuthorities());
					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authToken);
				}
				
			}
			filterChain.doFilter(request, response);
			}
			catch (SignatureException ex) {
			    System.out.println("Caught SignatureException");
			    ex.printStackTrace(); // Print the stack trace for more details
			    exceptionResolver.resolveException(request, response, null, ex);
			}
			catch (MalformedJwtException ex) {
			    System.out.println("Caught MalformedJwtException");
			    ex.printStackTrace(); // Print the stack trace for more details
			    exceptionResolver.resolveException(request, response, null, ex);
			}
			catch (Exception ex) {
			    System.out.println("Caught generic exception: " + ex.getClass().getName());
			    ex.printStackTrace(); // Print the stack trace for more details
			    exceptionResolver.resolveException(request, response, null, ex);
			}

		}
	
	}
