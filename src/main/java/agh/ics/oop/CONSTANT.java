package agh.ics.oop;

import agh.ics.oop.auxiliary.GenomeVariants;
import agh.ics.oop.auxiliary.GrassFieldVariants;
import agh.ics.oop.auxiliary.MapVariants;
import agh.ics.oop.auxiliary.MutationVariants;

public class CONSTANT {
    public final int PLUSENERGY;
    public final int COSTTOCONCIEVECHILDREN;
    public final int COSTTOMOVE;

    public final int GENOMELENGTH;
    //ms
    public final int MOVEDELAY;
    public final int SIMULATIONLENGTH;
    public final int AMOUNTOFANIMALS;
    public final MapVariants mapVariant;
    public final GenomeVariants genomeVariant;
    public final MutationVariants mutationVariant;
    public final GrassFieldVariants grassFieldVariant;
    public final int MINIMALENERGYTOPROCREATE;
    public final int STARTINGENERGY;
    public final int MAPSIZE;
    public final int MAXMUTATIONS;
    public CONSTANT(int plusEnergy, int costToConcievieChildren, int genomeLength, int moveDelay, int simulationLength, int amountOfAnimals, MapVariants mv,
                    GenomeVariants gv, MutationVariants mutv, GrassFieldVariants gfv,int ctm,int metp, int se,int ms, int mm){
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
        COSTTOMOVE=ctm;
        MINIMALENERGYTOPROCREATE=metp;
        STARTINGENERGY=se;
        MAPSIZE=ms;
        MAXMUTATIONS=mm;

    }


}
