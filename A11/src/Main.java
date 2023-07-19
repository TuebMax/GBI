import org.apache.commons.cli.*;
import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.markers.SeriesMarkers;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Assignment 11
 * Authors: Christopher Kolberg, Maximilian Wilhelm
 * <p>
 * You do not have to change the code here!
 */
public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("GBI - Exercise Sheet 11");

        // For command line interface parsing you can use the commons-cli package as follows (however, feel free to use any other approach):
        Options cliOptions = new Options();
        cliOptions.addOption(
                Option.builder().option("seq").longOpt("rna_sequence")
                        .hasArg(true)
                        .required(false)
                        .desc("RNA sequence to build dot-plot from.")
                        .build()
        );
        cliOptions.addOption(
                Option.builder().option("struc").longOpt("db_structure")
                        .hasArg(true)
                        .required(false)
                        .desc("RNA sequence secondary structure in dot-bracket notation to build dot-plot from.")
                        .build()
        );
        CommandLineParser parser = new DefaultParser(); // Init. parser object.
        CommandLine params = parser.parse(cliOptions, args); // Parse built cli options from args.
        if (!(params.hasOption("seq") && params.hasOption("struc"))) {
            // If no sequence and structure are provided, example chart is displayed.
            System.out.println("No RNA sequence and dot-bracket structure notation provided: Display example chart.");
            XYChart exampleChart = getExamplePlot();
            //noinspection unchecked,rawtypes
            new SwingWrapper(exampleChart).displayChart();
        } else {
            RNADotPlot rnaDotPlot = new RNADotPlot(params.getOptionValue("seq"), params.getOptionValue("struc"));
            XYChart dotPlotChart = rnaDotPlot.getDotPlot();
            //noinspection unchecked,rawtypes
            new SwingWrapper(dotPlotChart).displayChart();
            // You can also save your chart by uncommenting the following line.
            //BitmapEncoder.saveBitmapWithDPI(dotPlotChart, "./dot_plot_chart", BitmapEncoder.BitmapFormat.PNG, 300);
        }
    }

    /**
     * Tutorial method to generate an {@link XYChart} with the Java Swing XChart library.
     *
     * @return {@link XYChart} instance.
     */
    private static XYChart getExamplePlot() {
        /*
         * 1. The XYChart in general enables to display data on a 2D grid in various render styles (line, scatter, etc.); You can think of the
         * rendering process as a matrix of |xData| x |yData| (imagine xData and yData as two lists of integer values) grid points of which
         * the first entry of the two lists specify a cell in the matrix that should be used for rendering.
         * In other words, imagine a data series [ (x1,y1), (x2,y2), ..., (xn,yn) ] we want to render: For XChart this information is split
         * into two series [ x1, x2, ..., xn ] and [ y1, y2, ..., yn ].
         *
         * For the example plot we use a data series of length 10.
         */
        ArrayList<Integer> xData = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        ArrayList<Integer> yData = new ArrayList<>(Arrays.asList(13, 19, 14, 13, 15, 6, 1, 13, 5, 2));
        /*
         * 2. The next thing we want to do is to set up a bunch of constant meta-data values for the chart. I.e., labels and pixel-dimensions.
         */
        String title = "Example Plot";
        String xAxisLabel = "X-Axis Positions";
        String yAxisLabel = "Y-Axis Positions";
        int chartWidth = 800;
        int chartHeight = 800;
        /*
         * 3. Next, we can instantiate the chart with the XYChartBuilder factory class. For this, we can specify a set of constant parameters
         * we want to use for the chart (not the data!). The idea is the same as for the command line parser arguments ;)
         */
        XYChart chart = new XYChartBuilder()
                .width(chartWidth) // Width of the chart in pixels.
                .height(chartHeight) // Height of the chart in pixels.
                .title(title) // The title of the chart.
                .xAxisTitle(xAxisLabel) // The x-Axis label of the chart.
                .yAxisTitle(yAxisLabel) // The y-Axis label of the chart.
                .theme(Styler.ChartTheme.Matlab) // The theme of the chart; Feel free to experiment with the different Styler.ChartTheme.... options.
                .build(); // Finally, we build the chart instance.
        /*
         * 4. Some additional options can not be passed to the factory class, but have to be set for the chart instance.
         */
        chart.getStyler()
                .setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line); // The render style, e.g., XYSeries.XYSeriesRenderStyle.Line or XYSeries.XYSeriesRenderStyle.Scatter
        chart.getStyler()
                .setChartTitleVisible(true); // We want to display the chart title.
        chart.getStyler()
                .setLegendPosition(Styler.LegendPosition.OutsideS); // We want to place a legend at the bottom.
        chart.getStyler()
                .setMarkerSize(10); // Set the marker size.
        /*
        To set custom axis tick labels we have to access the setCustomXAxisTickLabelsFormatter and setCustomYAxisTickLabelsFormatter methods:
        - The methods consume a function instance that maps a double to a String value.
        - For this example we want to replace the x-axis tick labels with the characters a to j.
         */
        String[] customXAxisTickLabels = new String[]{
                "a", "b", "c", "d", "e", "f", "g", "h", "i", "j"
        };
        chart.setCustomXAxisTickLabelsFormatter(aDouble -> customXAxisTickLabels[aDouble.intValue() - 1]);
        /*
         * 5. The final step is to add a set of series to the chart. This is the step at which the data is injected to the chart instance.
         * This can be done by using the addSeries(...) method of the chart. The method returns a series object to which a set of setter-methods
         * can be applied afterwards in order to change the marker style and color. You can also add multiple series.
         */
        XYSeries series = chart.addSeries("Example Series", xData, yData);
        series.setMarker(SeriesMarkers.DIAMOND); // We want to use diamond markers.
        series.setMarkerColor(Color.ORANGE); // ...and use orange color.
        series.setLineColor(Color.YELLOW); // For the line color we want to use yellow.
        /*
         * Note: All the different settings can be accessed via static variables stored in the SeriesMarkers, Color, Styler, etc. classes.
         * Feel free to experiment a bit with the chart options.
         */
        return chart;
    }

}
