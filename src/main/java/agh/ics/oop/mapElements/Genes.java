package agh.ics.oop.mapElements;

import java.util.LinkedList;

public class Genes {
    //to nie działa
    //z ustawien tu sie ustawi
    private int genomeLength;
    private LinkedList<Integer> genome;

    public Integer getGene(int i){
        return this.genome.get(i);
    }

    public int getGenomeLength() {
        return genomeLength;
    }

    public Genes() {
        genome = new LinkedList<Integer>();
        for (int l=genomeLength;l>0;l--){
             genome.add((int)(Math.random()*genomeLength));
        }
    }
    public Genes(Genes p1, Genes p2, int gS){
        int threshold=gS/100*genomeLength;
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
                if (threshold<=0){
                    genome.add(p1.getGene(i));
                }
                else{
                    genome.add(p2.getGene(i));
                }
            }
        }
    }

    public void mutate(){
        double mutationFactor = Math.random();
        for (int l=genomeLength-1;l>=0;l--){
            if(Math.random()>=mutationFactor){
                genome.set(l,(int)(Math.random()*8));
            }
        }
    }
}
