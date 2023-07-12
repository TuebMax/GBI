import org.biojava.nbio.genome.parsers.gff.FeatureList;

public class CompareGFFs {

    private final FeatureList gff1;

    private final FeatureList gff2;

    public CompareGFFs(FeatureList gff1, FeatureList gff2){
        this.gff1 = gff1;
        this.gff2 = gff2;
    }

    public double computeSensitivity( ) {
        double sensitivity = 0.0;
        /**
         * TODO: Add sensitivity computation
         */
        return sensitivity;
    }

    public double computeSpecificity( ) {
        double specificity = 0.0;
        /**
         * TODO: add specificity computation
         */
        return specificity;
    }

    public double computeAccuracy( ) {
        double accuracy = 0.0;
        /**
         * TODO: add accuracy computation
         */
        return accuracy;

    }



}
