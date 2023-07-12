import org.biojava.nbio.genome.parsers.gff.Feature;
import org.biojava.nbio.genome.parsers.gff.FeatureI;
import org.biojava.nbio.genome.parsers.gff.FeatureList;
import org.biojava.nbio.genome.parsers.gff.Location;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Authors: Christopher Kolberg & Maximilian Wilhelm
 */
public class GFFReader {
    public static FeatureList read(String path) {
        FeatureList featureList = new FeatureList();

        try(BufferedReader br = new BufferedReader(new FileReader(path))){
            String line;
            while((line = br.readLine()) != null){
                // Skip comments and lines with less than 8 columns
                if(line.startsWith("#") || (line.split("\t").length<8)){
                    continue;
                }
                featureList.add(parseLine(line));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return featureList;
    }

    private static FeatureI parseLine(String line) {
        // Split line by tabs
        String[] lineSplit = line.split("\t");

        // Parse location
        char strand = lineSplit[6].charAt(0);
        Location location = Location.fromBio(Integer.parseInt(lineSplit[3]), Integer.parseInt(lineSplit[4]), strand);

        // Parse score (default to 0 if not parsable)
        double score;
        try {
            score = Double.parseDouble(lineSplit[5]);
        } catch (NumberFormatException e) {
            score = 0;
        }

        // Parse frame (default to 0 if not parsable)
        int frame;
        try{
            frame = Integer.parseInt(lineSplit[7]);
        } catch (NumberFormatException e) {
            frame = 0;
        }

        // Create feature
        return new Feature(lineSplit[0], lineSplit[1], lineSplit[2], location, score, frame, lineSplit[8]);

    }
}
