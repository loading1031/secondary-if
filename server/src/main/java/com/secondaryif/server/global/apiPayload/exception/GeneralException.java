package com.secondaryif.server.global.apiPayload.exception;

import com.secondaryif.server.global.apiPayload.code.BaseErrorCode;
import com.secondaryif.server.global.apiPayload.code.ErrorReasonDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GeneralException extends RuntimeException{
    private BaseErrorCode code;
    public ErrorReasonDto getErrorReason(){
        return this.code.getReason();
    }
    public ErrorReasonDto getErrorReasonHttpStatus(){
        return this.code.getReasonHttpStatus();
    }
}
