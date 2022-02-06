
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author MWM
 */
public class ReferencePoint 
{
    double quality ;
    double complexity ;
    double unprecision ;
    
    double objectives [] ;
    
    ReferencePoint()
    {
        this.quality = 0 ;
        this.complexity = 0 ;
        this.unprecision = 0 ;
    }
    
    ReferencePoint(double complexity,double quality, double unprecision)
    {
        this.quality = quality ;
        this.complexity = complexity ;
        this.unprecision = unprecision ;
    }
    ReferencePoint(ReferencePoint ref)
    {
        this.quality = ref.quality ;
        this.complexity = ref.complexity ;
        this.unprecision = ref.unprecision ;
    }
}
