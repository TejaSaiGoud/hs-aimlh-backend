package com.anurag.homeService.exception;

public class ResourceNotFoundException extends RuntimeException {

      public ResourceNotFoundException(String message) {
            super(message);
      }
}
