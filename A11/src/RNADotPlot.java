import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.AxesChartStyler;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.markers.SeriesMarkers;

import java.util.ArrayList;
import java.awt.*;

/**
 * Assignment 11
 * Authors: Christopher Kolberg, Maximilian Wilhelm
 * <p>
 * Class skeleton to generate RNA dot-plots.
 */
public class RNADotPlot {

    /**
     * {@link String} representation of the stored RNA sequence.
     */
    private final String rnaSequence;

    /**
     * {@link ArrayList} of {@link Integer} values that represent bases of {@link RNADotPlot#rnaSequence} forming bonds.
     * The i-th entries xi and yi of xData and yData, resp., indicate that the xi-th and yi-th base of the stored RNA sequence form a bond.
     */
    private final ArrayList<Integer> xData = new ArrayList<>();

    /**
     * {@link ArrayList} of {@link Integer} values that represent bases of {@link RNADotPlot#rnaSequence} forming bonds.
     * The i-th entries xi and yi of xData and yData, resp., indicate that the xi-th and yi-th base of the stored RNA sequence form a bond.
     */
    private final ArrayList<Integer> yData = new ArrayList<>();

    /**
     * Constructor of {@link RNADotPlot} class.
     *
     * @param rnaSequence            {@link String}; Represents RNA sequence.
     * @param rnaDotBracketStructure {@link String}; Represents RNA secondary structure in dot-bracket notation.
     */
    public RNADotPlot(String rnaSequence, String rnaDotBracketStructure) {
        if(rnaSequence.length() != rnaDotBracketStructure.length())
            throw new IllegalArgumentException("RNA sequence and structure must have the same length!");
        this.rnaSequence = rnaSequence;
        parseRnaDbString(rnaDotBracketStructure);
    }

    /**
     * Parses a RNA secondary structure representation in dot-bracket format into two integer list. Fills the xData and yData attributes.
     * Example: the input String "((...))" results in the two lists xData = [1, 2] and yData = [7, 6], as base-pairs (1,6)
     * and (2,7) form a bond
     *
     * @param rnaDbString {@link String} Represents RNA secondary structure in dot-bracket notation.
     */
    private void parseRnaDbString(String rnaDbString) {
        ArrayList<Integer> openingBrackets = new ArrayList<>();
        char[] charArray = rnaDbString.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            if (charArray[i] == '(') {
                openingBrackets.add(i);
            } else if (charArray[i] == ')') {
                xData.add(openingBrackets.get(openingBrackets.size() - 1) +1);
                openingBrackets.remove(openingBrackets.size() - 1);
                yData.add(i+1);
            }
        }
    }

    /**
     * Creates a dot-plot of the stored RNA sequence. The plot is returned as an {@link XYChart} object. The chart is configured
     * such that the x- and y-axis represent the indices of the RNA sequence. Further styling and configuration of the chart
     * is made to match the look of the given example.
     *
     * @return {@link XYChart} object representing the dot-plot of the stored RNA sequence.
     */
    public XYChart getDotPlot() {
        String title = "RNA Dot Plot";
        int chartWidth = 1000;
        int chartHeight = 1000;
        XYChart chart = new XYChartBuilder().width(chartWidth).height(chartHeight).title(title).theme(Styler.ChartTheme.Matlab).build();

        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
        chart.getStyler().setChartTitleVisible(true);
        chart.getStyler().setLegendVisible(false);
        chart.getStyler().setMarkerSize(12);

        // Create a strike which only shows the intersection of the grid lines
        BasicStroke stroke = new BasicStroke(1.0f,
                BasicStroke.CAP_SQUARE,
                BasicStroke.JOIN_MITER,
                1.0f,
                new float[]{1f, 6.f},
                5f);
        chart.getStyler().setPlotGridLinesStroke(stroke);

        // Setup the data series twice since the binding is symmetric
        XYSeries series = chart.addSeries("Sequence", xData, yData);
        XYSeries seriesMirror = chart.addSeries("Sequence Mirror", yData, xData);
        series.setLineColor(new Color(0, 0, 0, 0));
        series.setMarker(SeriesMarkers.SQUARE);
        series.setMarkerColor(Color.BLACK);
        seriesMirror.setLineColor(new Color(0, 0, 0, 0));
        seriesMirror.setMarker(SeriesMarkers.SQUARE);
        seriesMirror.setMarkerColor(Color.BLACK);


        // Styling
        chart.getStyler().setYAxisMin(2.0);
        chart.getStyler().setYAxisMax((double) rnaSequence.length()-1);
        chart.getStyler().setXAxisMin(2.0);
        chart.getStyler().setXAxisLabelRotation(90);
        chart.getStyler().setXAxisMax((double) rnaSequence.length()-1);
        chart.getStyler().setXAxisTickMarkSpacingHint(1);
        chart.getStyler().setYAxisTickMarkSpacingHint(1);
        chart.setCustomYAxisTickLabelsFormatter(aDouble -> String.valueOf(aDouble.intValue()));
        chart.setCustomXAxisTickLabelsFormatter(aDouble -> String.valueOf(aDouble.intValue()));

        XYSeries series1 = chart.addSeries("Diagonal", new double[]{1.0, rnaSequence.length()}, new double[]{1.0, rnaSequence.length()});
        series1.setLineColor(Color.BLACK);
        series1.setMarker(SeriesMarkers.NONE);
        BasicStroke stroke1 = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1.0f, new float[]{1.0f, 1.0f}, 0.0f);
        series1.setLineStyle(stroke1);

        return chart;
    }

}
