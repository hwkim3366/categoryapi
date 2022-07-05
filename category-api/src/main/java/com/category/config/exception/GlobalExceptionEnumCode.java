package com.category.config.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public enum GlobalExceptionEnumCode implements EnumCode {

    NOT_FOUND_DATA( "해당 데이터 없음" ),
    
    NOT_FOUND_PARENT_CATEGORY( "상위 카테고리 없음" ),
	
    CAN_NOT_UPDATE( "상위 카테고리 수정 불가" );
    

    private String message;


    @Override
    public String getMessage() {
    	
        return message;
    }

    @Override
    public String getName() {
    	
        return this.name();
    }
}
