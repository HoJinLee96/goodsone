package controller;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {
	
//	@ModelAttribute("userSeq")
//	public Long addUserToModel(HttpSession session) {
//	       if (session != null) {
//	            Long userSeq = (Long) session.getAttribute("userSeq");
//	            if (userSeq != null) {
//	                return userSeq;
//	            }
//	        }
//	        return null;  // null 값을 반환하지 않도록 변경
//	}
	
//    // UserNotFoundException이 발생했을 때 이 메서드가 호출됩니다.
//    @ExceptionHandler(UserNotFoundException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST) // HTTP 상태 코드를 400으로 설정
//    public void handleUserNotFoundException(UserNotFoundException e) {
//        // 예외 메시지를 ResponseEntity 추가
//    	ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//    	}
//    
//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public String handleAllExceptions(Exception ex, Model model,HttpSession session) {
//    	ex.printStackTrace();
//    	session.invalidate();
//        return "error"; // error.jsp로 이동
//    }
    
//    @ExceptionHandler(SQLException.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public String handleAllExceptions(Exception ex, Model model,HttpSession session) {
//    	session.invalidate();
//        return "error"; // error.jsp로 이동
//    }
}
