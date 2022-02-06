
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author MWM
 */
public class Sigma 
{
    double user_value;
    double current_value;
    
    Sigma()
    {
        user_value = 0 ;
        current_value = 1 ;
    }
    Sigma(double user_value)
    {
        this.user_value = user_value ;
        current_value = 1 ;
    }
    Sigma(Sigma s)
    {
        this.user_value = s.user_value ;
        this.current_value = s.current_value ;
    }
    
    void update_sigma(int current_generation, int max_generations)
    {
        current_value = 1 - current_generation*((1-user_value)/max_generations);
    }
    
}
