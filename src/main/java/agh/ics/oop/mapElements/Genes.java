package agh.ics.oop.mapElements;

import agh.ics.oop.CONSTANT;
import agh.ics.oop.auxiliary.MutationVariants;

import java.util.LinkedList;

public class Genes {
    //to nie działa
    //z ustawien tu sie ustawi
    private final CONSTANT CONSTANT;
    private final MutationVariants mutationVariant;
    private final int genomeLength;
    private LinkedList<Integer> genome;

    public Integer getGene(int i){
        return this.genome.get(i);
    }

    public Genes(CONSTANT constant) {
        this.CONSTANT=constant;
        this.mutationVariant= constant.mutationVariant;
        this.genomeLength = constant.GENOMELENGTH;
        genome = new LinkedList<>();
        for (int l=genomeLength;l>0;l--){
             genome.add((int)(Math.random()*8));
        }
    }
    public int getGenomeLength(){
        return genomeLength;
    }
    @Override
    public String toString() {
        String genes ="| ";
        for (int i=0;i<CONSTANT.GENOMELENGTH;i++){
            genes += Integer.toString(genome.get(i)) + " | ";
        }
        return genes;
    }

    public Genes(Genes p1, Genes p2, int gS, CONSTANT constant){
        this.CONSTANT=constant;
        this.mutationVariant= constant.mutationVariant;
        this.genomeLength = constant.GENOMELENGTH;
        genome = new LinkedList<>();
        int threshold=(int)((float)gS/100*genomeLength);
        if (Math.random()<0.5){
            for (int i = 0,l=genomeLength;l>0;i++,threshold--,l--){
                if (threshold>0){
                    genome.add(p1.getGene(i));
                }
                else{
                    genome.add(p2.getGene(i));
                }
            }
        }
        else{
            for (int i = 0,l=genomeLength; l>0;i++,threshold--,l--){
                if (threshold>0){
                    genome.add(p2.getGene(i));
                }
                else{
                    genome.add(p1.getGene(i));
                }
            }
        }
    }

    public void mutate(){
        double mutationFactor = Math.random();
        for (int l=genomeLength-1;l>=0;l--){
            if(Math.random()>=mutationFactor){
                if(mutationVariant==MutationVariants.PartialRandomization){
                    if(genome.get(l)==0){
                        genome.set(l,1);
                    }
                    else if (genome.get(l)==8){
                        genome.set(l,7);
                    }
                    else{
                        if((Math.random())>0.5){
                            genome.set(l,genome.get(l)+1);
                        }
                        else{
                            genome.set(l,genome.get(l)-1);
                        }
                    }
                }
                else if(mutationVariant==MutationVariants.FullRandomization){
                    genome.set(l,(int)(Math.random()*8));
                }
            }
        }
    }
}
