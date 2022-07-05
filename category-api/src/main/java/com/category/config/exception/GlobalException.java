package com.category.config.exception;

import lombok.Getter;

import java.util.Map;

@SuppressWarnings("serial")
@Getter
public class GlobalException extends RuntimeException {

    private final EnumCode code;
    
    private Map<String,String> args;

    public GlobalException(EnumCode code) {
    	
        super(code.getName() + "/" + code.getMessage());
        
        this.code = code;
    }
    
    public GlobalException(EnumCode code , String message) {
    	
        super(message);
        
        this.code = code;
    }

    public GlobalException(EnumCode code , Map<String,String> args) {
    	
        super(code.getName() + "/" + code.getMessage() + "/" + args);
        
        this.code = code;
        
        this.args = args;
    }

    public GlobalException(EnumCode code , Map<String,String> args , String message) {
    	
        super(message);
        
        this.code = code;
        
        this.args = args;
    }
}
