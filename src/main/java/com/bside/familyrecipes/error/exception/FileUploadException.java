package com.bside.familyrecipes.error.exception;

import com.bside.familyrecipes.error.ErrorType;

public class FileUploadException extends BusinessException {
    public FileUploadException() {
        super(ErrorType.FILE_UPLOAD_FAIL);
    }
}
