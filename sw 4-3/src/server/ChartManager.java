package server;

import java.awt.Color;
import java.awt.Font;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.TextAnchor;

public class ChartManager {

	public JFreeChart getChart() {
	       // ������ ����

        DefaultCategoryDataset  datasetNum= new DefaultCategoryDataset();                // bar chart 1

        // ������ �Է� ( ��, ����, ī�װ� )
        // �׷��� 1   - �Ǹŷ�
        for(int i :AccountManager.account.keySet()) {
        	if(i!=0) {
        		datasetNum.addValue(AccountManager.getSellNum(i), "�Ǹŷ�",Integer.toString(i));
        	}
        }

 

        // ������ ���� �� ����

        // ������ ����
        final BarRenderer renderer = new BarRenderer();//��
//        final LineAndShapeRenderer renderer2 = new LineAndShapeRenderer();//��

        // ���� �ɼ� ����
        final CategoryItemLabelGenerator generator = new StandardCategoryItemLabelGenerator();

        final ItemLabelPosition p_center = new ItemLabelPosition(

                ItemLabelAnchor.CENTER, TextAnchor.CENTER

            );

        final ItemLabelPosition p_below = new ItemLabelPosition(

                     ItemLabelAnchor.OUTSIDE9, TextAnchor.TOP_LEFT

                     );

        Font f = new Font("Gulim", Font.BOLD, 4);
        
        // ������ ����

        // �׷��� 1
        renderer.setBaseItemLabelGenerator(generator);
        renderer.setBaseItemLabelsVisible(true);
        renderer.setBasePositiveItemLabelPosition(p_center);
        renderer.setBaseItemLabelFont(f);
        renderer.setSeriesPaint(0, Color.LIGHT_GRAY);

        // plot ����
        final CategoryPlot plot = new CategoryPlot();

        // plot �� ������ ����
        plot.setDataset(datasetNum);
        plot.setRenderer(renderer);
 
        // plot �⺻ ����
        plot.setOrientation(PlotOrientation.VERTICAL);             // �׷��� ǥ�� ����
        plot.setRangeGridlinesVisible(true);                       // X�� ���̵� ���� ǥ�ÿ���
        plot.setDomainGridlinesVisible(true);                      // Y�� ���̵� ���� ǥ�ÿ���

        // ������ ���� ���� : dataset ��� ������� ������ ( ��, ���� ����Ѱ� �Ʒ��� �� )
        plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);

        // X�� ����
        plot.setDomainAxis(new CategoryAxis());              // X�� ���� ����
        plot.getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.STANDARD);       // ī�װ� �� ��ġ ����
        plot.getDomainAxis().setLowerMargin(0.03);
        plot.getDomainAxis().setUpperMargin(0.03);
 
        // Y�� ����
        plot.setRangeAxis(new NumberAxis());                 // Y�� ���� ����

        // ���õ� plot�� �������� chart ����
        final JFreeChart chart = new JFreeChart(plot);

        return chart;
	}
}
