package com.epam.esm.util;

public class PaginationValidator {

  public static boolean isPageValid(Integer page) {
    boolean result = true;
    if (page <= 0) {
      result = false;
    }
    return result;
  }

  public static boolean isSizeValid(Integer size) {
    boolean result = true;
    if (size <= 0 || size > Constant.MAX_SIZE) {
      result = false;
    }
    return result;
  }
}
