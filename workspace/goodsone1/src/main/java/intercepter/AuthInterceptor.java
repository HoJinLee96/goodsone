package intercepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor{
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
        HttpSession session = request.getSession();
        if (session.getAttribute("user") != null) {
            return true; // 세션이 유효하면 요청을 계속 처리
        }
        // 세션이 유효하지 않으면 원래 요청 URL을 세션에 저장하고 로그인 페이지로 리다이렉트
        session.setAttribute("redirectURI", request.getRequestURI());
        response.sendRedirect("/login");
        return false;
	}
}
