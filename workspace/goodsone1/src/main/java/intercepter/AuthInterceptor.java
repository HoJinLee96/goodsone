package intercepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object Handler)
			throws Exception {
System.out.println("AuthInterceptor.preHandle() 실행");
		HttpSession session = request.getSession();
		String uri = request.getRequestURI();

		// /goodsone1/login 요청시
		if ("/login".equals(uri)) {
			if (session.getAttribute("user") != null) {
System.out.println("세션에 userSeq 있어서 /home 리다이렉트 ");
	            response.sendRedirect("/home"); // 또는 적절한 페이지로 리다이렉트
				return false; // 세션이 비어있으면 로그인 창으로
			}
System.out.println("세션 user 비어있어서 정상적으로 진행");
			return true;
		}
		
	     // 그 외의 경우 세션에서 userSeq 확인
	    if (session.getAttribute("user") == null) {
	        response.sendRedirect("/login");
	        return false;
	    }
		
	return true;
	}
}
