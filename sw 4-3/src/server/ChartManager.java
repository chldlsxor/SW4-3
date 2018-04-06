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
	       // 데이터 생성

        DefaultCategoryDataset  datasetNum= new DefaultCategoryDataset();                // bar chart 1

        // 데이터 입력 ( 값, 범례, 카테고리 )
        // 그래프 1   - 판매량
        for(int i :AccountManager.account.keySet()) {
        	if(i!=0) {
        		datasetNum.addValue(AccountManager.getSellNum(i), "판매량",Integer.toString(i));
        	}
        }

 

        // 렌더링 생성 및 세팅

        // 렌더링 생성
        final BarRenderer renderer = new BarRenderer();//바
//        final LineAndShapeRenderer renderer2 = new LineAndShapeRenderer();//선

        // 공통 옵션 정의
        final CategoryItemLabelGenerator generator = new StandardCategoryItemLabelGenerator();

        final ItemLabelPosition p_center = new ItemLabelPosition(

                ItemLabelAnchor.CENTER, TextAnchor.CENTER

            );

        final ItemLabelPosition p_below = new ItemLabelPosition(

                     ItemLabelAnchor.OUTSIDE9, TextAnchor.TOP_LEFT

                     );

        Font f = new Font("Gulim", Font.BOLD, 4);
        
        // 렌더링 세팅

        // 그래프 1
        renderer.setBaseItemLabelGenerator(generator);
        renderer.setBaseItemLabelsVisible(true);
        renderer.setBasePositiveItemLabelPosition(p_center);
        renderer.setBaseItemLabelFont(f);
        renderer.setSeriesPaint(0, Color.LIGHT_GRAY);

        // plot 생성
        final CategoryPlot plot = new CategoryPlot();

        // plot 에 데이터 적재
        plot.setDataset(datasetNum);
        plot.setRenderer(renderer);
 
        // plot 기본 설정
        plot.setOrientation(PlotOrientation.VERTICAL);             // 그래프 표시 방향
        plot.setRangeGridlinesVisible(true);                       // X축 가이드 라인 표시여부
        plot.setDomainGridlinesVisible(true);                      // Y축 가이드 라인 표시여부

        // 렌더링 순서 정의 : dataset 등록 순서대로 렌더링 ( 즉, 먼저 등록한게 아래로 깔림 )
        plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);

        // X축 세팅
        plot.setDomainAxis(new CategoryAxis());              // X축 종류 설정
        plot.getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.STANDARD);       // 카테고리 라벨 위치 조정
        plot.getDomainAxis().setLowerMargin(0.03);
        plot.getDomainAxis().setUpperMargin(0.03);
 
        // Y축 세팅
        plot.setRangeAxis(new NumberAxis());                 // Y축 종류 설정

        // 세팅된 plot을 바탕으로 chart 생성
        final JFreeChart chart = new JFreeChart(plot);

        return chart;
	}
}
