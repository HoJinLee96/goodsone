package controller;

import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseStatus;

import exception.UserNotFoundException;

@ControllerAdvice
public class GlobalControllerAdvice {
	
	@ModelAttribute("userSeq")
	public Long addUserToModel(HttpSession session) {
		return  (Long) session.getAttribute("userSeq");
	}
	
    // UserNotFoundException이 발생했을 때 이 메서드가 호출됩니다.
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // HTTP 상태 코드를 400으로 설정
    public void handleUserNotFoundException(UserNotFoundException e) {
        // 예외 메시지를 ResponseEntity 추가
    	ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    	}
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleAllExceptions(Exception ex, Model model,HttpSession session) {
    	ex.printStackTrace();
    	session.invalidate();
        return "error"; // error.jsp로 이동
    }
    
//    @ExceptionHandler(SQLException.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public String handleAllExceptions(Exception ex, Model model,HttpSession session) {
//    	session.invalidate();
//        return "error"; // error.jsp로 이동
//    }
}
