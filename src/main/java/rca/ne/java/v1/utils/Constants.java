package rca.ne.java.v1.utils;

import rca.ne.java.v1.exceptions.BadRequestException;

public interface Constants {

    public  String DEFAULT_PAGE_NUMBER = "0";



    public  String DEFAULT_PAGE_SIZE = "100";



    public  int MAX_PAGE_SIZE = 1000;


    public static void validatePageNumberAndSize(int pageNumber, int pageSize) {
        if (pageNumber < 1) {
            throw new BadRequestException("Page number is less than zero.");
        }

        if (pageSize > Constants.MAX_PAGE_SIZE) {
            throw new BadRequestException("Page size is greater than " + Constants.MAX_PAGE_SIZE);
        }
    }
}
