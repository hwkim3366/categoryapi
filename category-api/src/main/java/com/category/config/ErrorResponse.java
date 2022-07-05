package com.category.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ErrorResponse {

    private int code;
    
    private String message;
    
    private String errorCode;
    
    public static ErrorResponse error (String errorCode , String message ) {
    	
        ErrorResponse errorResponse = new ErrorResponse();
        
        errorResponse.code = -1;
        
        errorResponse.errorCode = errorCode;
        
        errorResponse.message = message;
        
        return errorResponse;
    }

    public static ErrorResponse systemError (String errorCode , String message ) {
    	
        ErrorResponse errorResponse = new ErrorResponse();
        
        errorResponse.code = -2;
        
        errorResponse.errorCode = errorCode;
        
        errorResponse.message = message;
        
        return errorResponse;
        
    }
    
}
