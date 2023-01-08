package agh.ics.oop;

import agh.ics.oop.auxiliary.GenomeVariants;
import agh.ics.oop.auxiliary.GrassFieldVariants;
import agh.ics.oop.auxiliary.MapVariants;
import agh.ics.oop.auxiliary.MutationVariants;

public class CONSTANT {
    public final int PLUSENERGY;
    public final int COSTTOCONCIEVECHILDREN;

    public final int GENOMELENGTH;
    //ms
    public final int MOVEDELAY;
    public final int SIMULATIONLENGTH;
    public final int AMOUNTOFANIMALS;
    public final MapVariants mapVariant;
    public final GenomeVariants genomeVariant;
    public final MutationVariants mutationVariant;
    public final GrassFieldVariants grassFieldVariant;

    public CONSTANT(int plusEnergy, int costToConcievieChildren, int genomeLength, int moveDelay, int simulationLength, int amountOfAnimals, MapVariants mv, GenomeVariants gv, MutationVariants mutv, GrassFieldVariants gfv){
        PLUSENERGY = plusEnergy;
        COSTTOCONCIEVECHILDREN=costToConcievieChildren;
        GENOMELENGTH=genomeLength;
        MOVEDELAY=moveDelay;
        SIMULATIONLENGTH=simulationLength;
        AMOUNTOFANIMALS=amountOfAnimals;
        grassFieldVariant=gfv;
        mutationVariant=mutv;
        genomeVariant=gv;
        mapVariant=mv;

    }


}
