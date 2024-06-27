package rca.ne.java.v1.utils;
import rca.ne.java.v1.exceptions.*;
import rca.ne.java.v1.payload.ApiResponse;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpServerErrorException;
import rca.ne.java.v1.exceptions.*;

public class ExceptionsUtils {

    public static  <T> ResponseEntity<ApiResponse> handleControllerExceptions(Exception e) {
        System.out.println("Exception caught in controller:");

        if (e instanceof ChangeSetPersister.NotFoundException) {
            return new ResponseEntity<>(new ApiResponse(
                    false,
                    e.getMessage()
            ), HttpStatus.NOT_FOUND);
        } else if(e instanceof InvalidUUIdException){
            return new ResponseEntity<>(new ApiResponse(
                    false,
                    e.getMessage()
            ), HttpStatus.BAD_REQUEST);
        }else if(e instanceof NotFoundException){
            return new ResponseEntity<>(new ApiResponse(
                    false,
                    e.getMessage()
            ), HttpStatus.NOT_FOUND);
        } else if (e instanceof IllegalArgumentException){
            return new ResponseEntity<>(new ApiResponse(
                    false,
                    e.getMessage()
            ), HttpStatus.BAD_REQUEST);
        }else if (e instanceof HttpServerErrorException.InternalServerError) {
            return new ResponseEntity<>(new ApiResponse(
                    false,
                    e.getMessage()
            ), HttpStatus.INTERNAL_SERVER_ERROR);
        } else if(e instanceof BadRequestException){
            return new ResponseEntity<>(new ApiResponse(
                    false,
                    e.getMessage()
            ), HttpStatus.BAD_REQUEST);
        }else  if(e instanceof UnauthorizedException){
            return new ResponseEntity<>(new ApiResponse(
                    false,
                    "You are not allowed to access this resource"
            ), HttpStatus.UNAUTHORIZED);
        } else {
            // Handle other exceptions as needed
            return new ResponseEntity<>(new ApiResponse(
                    false,
                    e.getMessage()
            ), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public static <T> void handleServiceExceptions(Exception e) {
        System.out.println("Exception caught in service:");
        if(e instanceof IllegalArgumentException){
            throw new IllegalArgumentException(e.getMessage());
        }else if( e instanceof NotFoundException){
            throw new NotFoundException(e.getMessage());
        }else if (e instanceof HttpServerErrorException) {
            throw new InternalServerErrorException(e.getMessage());
        } else if (e instanceof BadRequestException){
            throw new BadRequestException(e.getMessage());
        }else  if(e instanceof UnauthorizedException){
            throw  new UnauthorizedException("You are not allowed to access this resource");
        } else {
            throw new RuntimeException("Failed!! Something went wrong " + e.getMessage(), e);
        }
    }
}
