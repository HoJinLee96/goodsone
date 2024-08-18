package intercepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginAuthInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object Handler)
			throws Exception {
System.out.println("LoginAuthInterceptor.preHandle() 실행");
		HttpSession session = request.getSession();
		String uri = request.getRequestURI();

		// /goodsone1/login 요청시
		if ("/login".equals(uri)) {
			if (session.getAttribute("userDto") != null || session.getAttribute("oAuthDto") != null || session.getAttribute("oAuthToken") != null) {
System.out.println("**비정상 접근** 세션에 userSeq 또는 oAuthDto 있어서 /home 리다이렉트 ");
	            response.sendRedirect("/home");
				return false; 
			}
System.out.println("세션 userDto, oAuthDto, oAuthToken 다 비어있어서 정상적으로 진행");
			return true; 
		}
		
	     // 그 외의 경우 세션에서 userSeq 확인
	    if (session.getAttribute("userDto") == null && session.getAttribute("oAuthDto") == null && session.getAttribute("oAuthToken") == null) {
//	        session.invalidate();
	        response.sendRedirect("/login");
	        return false;
	    }
		
	return true;
	}
}
