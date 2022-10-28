/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos_timesquare.utils;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import org.jfree.chart.plot.PiePlotState;
import org.jfree.chart.plot.RingPlot;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author Acer
 */
public class CustomDonutPlot extends RingPlot {
    private static final long serialVersionUID = 1L;

    public CustomDonutPlot(DefaultPieDataset dataSet) {
        super(dataSet);
    }

    @Override
    protected void drawItem(Graphics2D g2, int section, Rectangle2D dataArea, PiePlotState state, int currentPass) {
        if (currentPass == 1 && section >=1 && section <= 3) {

        }
        Rectangle2D area = state.getPieArea();
        System.out.println("*** At section=" + section + ", pass="+currentPass);
        logDataArea(dataArea, "Data area");
        logDataArea(area, "Pie area");
        System.out.println(state.getInfo());

        super.drawItem(g2, section, dataArea, state, currentPass);


    }
    @Override
    protected Rectangle2D getArcBounds(Rectangle2D unexploded, Rectangle2D exploded, double angle, double extent, double explodePercent) {
        if(explodePercent > 0.0){
            this.setSectionDepth(0.33);//to match inner arc
            java.awt.geom.Arc2D.Double arc1 = new java.awt.geom.Arc2D.Double(unexploded, angle, extent / 2.0D, 0);
            Point2D point1 = arc1.getEndPoint();
            //java.awt.geom.Arc2D.Double arc2 = new java.awt.geom.Arc2D.Double(exploded, angle, extent / 2.0D, 0); //original code
            Rectangle2D mix = new Rectangle2D.Double(exploded.getX(), exploded.getY(), unexploded.getWidth(), unexploded.getHeight());
            java.awt.geom.Arc2D.Double arc2 = new java.awt.geom.Arc2D.Double(mix, angle, extent / 2.0D, 0);

            Point2D point2 = arc2.getEndPoint();
            double deltaX = (point1.getX() - point2.getX()) * explodePercent;
            double deltaY = (point1.getY() - point2.getY()) * explodePercent;
            //return new java.awt.geom.Rectangle2D.Double(unexploded.getX() - deltaX, unexploded.getY() - deltaY, unexploded.getWidth(), unexploded.getHeight()); original code
            return new java.awt.geom.Rectangle2D.Double(unexploded.getX() - deltaX, unexploded.getY() - deltaY, exploded.getWidth(), exploded.getHeight());
        } else {
            this.setSectionDepth(0.3);//default depth
            return super.getArcBounds(unexploded, exploded, angle, extent, explodePercent);
        }
    }


    private void logDataArea(Rectangle2D dataArea, String msg) {
        System.out.println(msg + " h="+dataArea.getHeight() + ", w=" + dataArea.getWidth() + ", x=" + dataArea.getX() + ",y="+dataArea.getY());
    }


}