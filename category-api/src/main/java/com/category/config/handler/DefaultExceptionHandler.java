package com.category.config.handler;

import com.category.config.ErrorResponse;
import com.category.config.exception.EnumCode;
import com.category.config.exception.GlobalException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@ControllerAdvice("com.category")
public class DefaultExceptionHandler {
	
	@ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = Exception.class)
    public ErrorResponse defaultErrorHandler (HttpServletRequest httpServletRequest , Exception e) throws Exception {

        String errorCode = null;
        
        String errorMessage = null;

        boolean isGlobalException = false;
        
        if ( e instanceof GlobalException) {
        	isGlobalException = true;

            GlobalException globalException = (GlobalException) e;
            
            EnumCode enumCode = globalException.getCode();

            if ( enumCode == null) {
                errorMessage = e.getMessage();
            } else {
                errorCode = enumCode.getName();
                errorMessage = enumCode.getMessage();

                if (globalException.getArgs() != null && !globalException.getArgs().isEmpty() ) {
                	
                    Map<String,String> argsMap = globalException.getArgs();
                    
                    Iterator<Map.Entry<String,String>> iterator =  argsMap.entrySet().iterator();
                    
                    while (iterator.hasNext() ) {
                    	
                        Map.Entry<String,String> entry = iterator.next();
                        
                        String argsKey = "{" + entry.getKey() + "}";
                        
                        errorMessage = errorMessage.replace(argsKey , entry.getValue());
                    }
                }
            }
        } else if(e instanceof MethodArgumentNotValidException){
        	
            Map<String,String> errors = new HashMap<>();
            
            errorCode = "VALIDATION_ERROR";
            
             ((MethodArgumentNotValidException) e).getBindingResult()
                    .getAllErrors()
                    .forEach(c -> errors.put(((FieldError) c).getField(), c.getDefaultMessage()));
            errorMessage = errors.toString();
            
        }

        if ( errorMessage == null) {
            errorMessage = "SYSTEM ERROR";
        }

        if (isGlobalException) {
        	
            return ErrorResponse.error( errorCode , errorMessage);
            
        } else {
        	
            return ErrorResponse.systemError( errorCode , errorMessage);
        }
    }
}
