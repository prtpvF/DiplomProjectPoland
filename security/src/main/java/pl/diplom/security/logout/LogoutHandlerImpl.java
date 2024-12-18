package pl.diplom.security.logout;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import pl.diplom.security.jwt.JwtUtil;

@Service
@RequiredArgsConstructor
public class LogoutHandlerImpl implements LogoutHandler {

        private final JwtUtil jwtUtil;

        @Override
        public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
            String token = request.getHeader("token");
            if(token!=null){
                String username = jwtUtil.validateTokenAndRetrieveClaim(token);

            }
        }
}