package kr.hhplus.be.server.exception.custom;

import kr.hhplus.be.server.exception.ExceptionCode;

public class ResourceNotFoundException extends ecommerceServerException{

  public ResourceNotFoundException(ExceptionCode exceptionCode, String message) {
    super(exceptionCode, message);
  }

  public ResourceNotFoundException(String message) {
    super(ExceptionCode.RESOURCE_NOT_FOUND, message);
  }

  public ResourceNotFoundException(ExceptionCode exceptionCode) {
    super(exceptionCode);
  }

  public ResourceNotFoundException() {
    super(ExceptionCode.RESOURCE_NOT_FOUND);
  }

}
