import org.biojava.nbio.genome.parsers.gff.FeatureI;
import org.biojava.nbio.genome.parsers.gff.FeatureList;
import org.biojava.nbio.genome.parsers.gff.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Authors: Christopher Kolberg & Maximilian Wilhelm
 */

public class CompareGFFs {

    // Ground truth feature list
    private final FeatureList groundtruth;

    // Predicted feature list of a model
    private final FeatureList prediction;

    // Length of the sequence which was used to predict coding regions
    private final int sequenceLength;

    // maps the position of a CDS to a boolean value which indicates whether the position is in a CDS or not
    private Map<Integer, Boolean> gtCDSMap;
    private Map<Integer, Boolean> predictionCDSMap;

    private int truePositives = 0;
    private int falsePositives = 0;
    private int trueNegatives = 0;
    private int falseNegatives = 0;

    public CompareGFFs(FeatureList gff1, FeatureList gff2, int seqLength) {
        this.groundtruth = getCDSRegions(gff1);
        this.prediction = getCDSRegions(gff2);
        this.sequenceLength = seqLength;

        this.gtCDSMap = getMapCDSLocations(groundtruth);
        this.predictionCDSMap = getMapCDSLocations(prediction);

        compute();
    }

    public FeatureList getCDSRegions(FeatureList gff) {
        return gff.selectByType("CDS");
    }

    public double computeSensitivity() {
        return (double) truePositives / (truePositives + falseNegatives);
    }

    public double computeSpecificity() {
        return (double) trueNegatives / (trueNegatives + falsePositives);
    }

    public double computeAccuracy() {
        return (double) (truePositives + trueNegatives) / (truePositives + trueNegatives + falsePositives + falseNegatives);
    }


    private Map<Integer, Boolean> getMapCDSLocations(FeatureList gff) {

        Map<Integer, Boolean> cdsLocations = new HashMap<>();
        // Iterate over all CDS features and add the CDS locations to the map with the value true
        for (FeatureI feature : gff) {
            Location location = feature.location();
            for (int i = location.start(); i < location.end(); i++) {
                cdsLocations.put(i, true);
            }
        }
        return cdsLocations;
    }

    private void compute() {
        // iterating over the sequence (both strands)
        for(int i=-sequenceLength; i<sequenceLength; i++) {

            boolean gtBool = gtCDSMap.getOrDefault(i, false);
            boolean predBool = predictionCDSMap.getOrDefault(i, false);

            if(gtBool && predBool) {
                truePositives++;
            } else if(gtBool) {
                falseNegatives++;
            } else if(predBool) {
                falsePositives++;
            } else {
                trueNegatives++;
            }

        }
    }



}
