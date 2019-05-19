package services;

import response.Response;
import response.SuccessResponse;
import response.ErrorResponse;
import models.*;

/**
 * Generate a user's family tree for a given number of generation
 * Handle DataBaseException.
 */
public class FillService{
    
    
    public FillService(){}
    
    /**
     * Method called by other classes. 
     * Find a user, it's person and generate his family tree.
     * 
     * @param   user_name: the user.
     * @param   num_generations: number of generations to create.
     * @return  SuccessResponse or ErrorResponse with an explanation message.
     */
    public static Response fillUserTree(String user_name, int num_generations){
        
        return null;
    }
    
    /**
     * fillUserTree helper.
     * Recursively generate random family tree.
     * 
     * @param   child: person who's parents will be generated and then attached to.
     * @param   num_generations: number of generations to generate.
     * @param   current_generation: number giving the generation it the method is creating.
     */
    private static void generateTree(Person child, int num_generations, int current_generation){
        
    }
    
}