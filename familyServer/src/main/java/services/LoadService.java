package services;

import models.*;
import response.Response;
import response.SuccessResponse;
import response.ErrorResponse;
import request.LoadRequest;

/**
 * Add multiple users, people and events
 * Handle DataBaseException.
 */
 
public class LoadService{
    
    public LoadService(){}
    
    /**
     * Add each user, person and event in the request.
     * 
     * @param   req: request with user, person and event arrays.
     * @return  SuccessResponse or ErrorResponse with explanation message.
     */
    public static Response loadDataBase(LoadRequest req){
        
        return null;
    }
}