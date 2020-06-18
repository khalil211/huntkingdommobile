/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybersquad.huntkingdom.gui.Dog;

import com.codename1.charts.ChartComponent;
import com.codename1.charts.models.CategorySeries;
import com.codename1.charts.renderers.DefaultRenderer;
import com.codename1.charts.renderers.SimpleSeriesRenderer;
import com.codename1.charts.util.ColorUtil;
import com.codename1.charts.views.PieChart;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BorderLayout;
import com.cybersquad.huntkingdom.entities.coach.Coach;
import com.cybersquad.huntkingdom.services.Dog.DogService;
import static javafx.scene.text.Font.font;
import static javafx.scene.text.Font.font;

/**
 *
 * @author Louay
 */
public class testpie {
   private DogService dogS ;
    private Coach ch;
    private Coach cl;
    private Coach cb;
    
    /**

     * Creates a renderer for the specified colors.

     */
    public testpie()
    {
        
    }
    

    private DefaultRenderer buildCategoryRenderer(int[] colors) {

        DefaultRenderer renderer = new DefaultRenderer();

        renderer.setLabelsTextSize(70);

        renderer.setLegendTextSize(70);

        renderer.setMargins(new int[]{20, 30, 15, 0});

        for (int color : colors) {

            SimpleSeriesRenderer r = new SimpleSeriesRenderer();

            r.setColor(color);

            renderer.addSeriesRenderer(r);

        }

        return renderer;

    }



    /**

     * Builds a category series using the provided values.

     *

     * @param titles the series titles

     * @param values the values

     * @return the category series

     */

    protected CategorySeries buildCategoryDataset(String title, double[] values) {
        
        CategorySeries series = new CategorySeries(title);

        dogS= new DogService();
       
        int useri=17;
        ch=dogS.getHigher(17);
        System.out.println(ch.getNbr());
         cl=dogS.getLower(17);
        System.out.println(cl.getNbr());
         cb=dogS.getBetween(17);
        System.out.println(cb.getNbr());
       

            series.add("inferior to 3",ch.getNbr());
            series.add("Between 3 and 5",cb.getNbr() );
            series.add("superior to 5", cl.getNbr());

        



        return series;

    }



    public Form createPieChartForm() {

           

        // Generate the values

        double[] values = new double[]{5, 6, 1};
        
           
           

        // Set up the renderer

        int[] colors = new int[]{ColorUtil.BLUE, ColorUtil.GREEN, ColorUtil.MAGENTA};

        DefaultRenderer renderer = buildCategoryRenderer(colors);

        renderer.setZoomButtonsVisible(true);

        renderer.setZoomEnabled(true);

        renderer.setChartTitleTextSize(20);
        
        renderer.setDisplayValues(true);

        renderer.setShowLabels(true);
        SimpleSeriesRenderer r = renderer.getSeriesRendererAt(0);
        renderer.setLabelsColor(ColorUtil.BLACK);
        renderer.setLabelsTextSize(35);
        r.setGradientEnabled(false);

        r.setGradientStart(0, ColorUtil.BLUE);

        r.setGradientStop(0, ColorUtil.GREEN);
        

        r.setHighlighted(true);



        // Create the chart ... pass the values and renderer to the chart object.

        PieChart chart = new PieChart(buildCategoryDataset("Project budget", values), renderer);



        // Wrap the chart in a Component so we can add it to a form

        ChartComponent c = new ChartComponent(chart);



        // Create a form and show it.

        Form f = new Form("Budget");

        f.setLayout(new BorderLayout());

        f.addComponent(BorderLayout.CENTER, c);
        
        return f;

    }
}
