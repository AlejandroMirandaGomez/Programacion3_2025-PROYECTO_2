package PROYECTO_2.presentation.dashboard;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.Map;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.ui.RectangleEdge;
import java.awt.geom.Ellipse2D;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.*;

public class ChartsFactory {
    // Estados en el orden que quieres en leyenda y gráfico
    public static final String EN_PROCESO     = "En Proceso";
    public static final String LISTA          = "Lista";
    public static final String ENTREGADA      = "Entregada";
    public static final String CONFECCIONADA  = "Confeccionada";

    // Construye el ChartPanel del pastel con estilo
    public static ChartPanel buildPieRecetasPorEstado(Map<String, Integer> conteo) {
        // Dataset en orden fijo (LinkedHashMap) para que leyenda/sectores sigan ese orden
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
        dataset.setValue(EN_PROCESO,    conteo.getOrDefault(EN_PROCESO, 0));
        dataset.setValue(LISTA,         conteo.getOrDefault(LISTA, 0));
        dataset.setValue(ENTREGADA,     conteo.getOrDefault(ENTREGADA, 0));
        dataset.setValue(CONFECCIONADA, conteo.getOrDefault(CONFECCIONADA, 0));

        // Crea el chart base
        JFreeChart chart = ChartFactory.createPieChart(
                "Recetas",   // título
                dataset,
                true,        // leyenda
                true,        // tooltips
                false        // URLs
        );

        // Estilo general
        chart.setBackgroundPaint(new Color(200, 200, 200)); // gris claro (fondo general)
        chart.getTitle().setFont(new Font("SansSerif", Font.BOLD, 24));

        PiePlot<String> plot = (PiePlot<String>) chart.getPlot();
        plot.setBackgroundPaint(new Color(200, 200, 200));  // gris en el plot
        plot.setOutlineVisible(false);

        // Colores por estado (rojo, azul, verde, amarillo)
        plot.setSectionPaint(EN_PROCESO,    new Color(231, 76, 60));   // rojo
        plot.setSectionPaint(LISTA,         new Color(52, 152, 219));  // azul
        plot.setSectionPaint(ENTREGADA,     new Color(46, 204, 113));  // verde
        plot.setSectionPaint(CONFECCIONADA, new Color(241, 196, 15));  // amarillo

        // Líneas con cajas de texto (no “simple labels”)
        plot.setSimpleLabels(false);
        plot.setLabelFont(new Font("SansSerif", Font.BOLD, 12));
        plot.setLabelBackgroundPaint(new Color(255, 255, 204)); // caja suave
        plot.setLabelOutlinePaint(Color.DARK_GRAY);
        plot.setLabelShadowPaint(null);

        // Etiquetas: NOMBRE = CUENTA (PORCENTAJE)
        plot.setLabelGenerator(
                new org.jfree.chart.labels.StandardPieSectionLabelGenerator(
                        "{0} = {1} ({2})",
                        new DecimalFormat("0"),   // cuenta sin decimales
                        new DecimalFormat("0%")   // porcentaje “29%”
                )
        );

        // Si quieres ocultar sectores de valor 0:
        plot.setIgnoreZeroValues(true);

        // Panel Swing contenedor
        ChartPanel panel = new ChartPanel(chart);
        panel.setMouseWheelEnabled(false);
        panel.setDomainZoomable(false);
        panel.setRangeZoomable(false);
        panel.setBackground(new Color(200, 200, 200)); // que combine con el fondo

        return panel;
    }

    /** Grafica N medicamentos vs mes (X) y cantidad (Y). */
    public static ChartPanel buildLineMedicamentosPorMes(
            Map<String, Map<YearMonth, Integer>> datos) {

        Locale locale = new Locale("es", "CR"); // para etiquetas tipo 2025-ago
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // 1) Orden cronológico de todos los meses presentes
        SortedSet<YearMonth> meses = new TreeSet<>();
        if (datos != null) {
            for (Map<YearMonth, Integer> serie : datos.values()) {
                if (serie != null) meses.addAll(serie.keySet());
            }
        }
        // 2) Volcar datos al dataset (faltantes -> 0)
        for (Map.Entry<String, Map<YearMonth, Integer>> e : datos.entrySet()) {
            String nombreMed = e.getKey();
            Map<YearMonth, Integer> serie = e.getValue();
            for (YearMonth ym : meses) {
                int v = (serie != null) ? serie.getOrDefault(ym, 0) : 0;
                dataset.addValue(v, nombreMed, etiquetaYM(ym, locale)); // X: "yyyy-ago"
            }
        }
        // 3) Crear chart
        JFreeChart chart = ChartFactory.createLineChart(
                "Medicamentos", "Mes", "Cantidad", dataset);

        chart.setBackgroundPaint(new Color(200, 200, 200));
        chart.getTitle().setFont(new Font("SansSerif", Font.BOLD, 26));
        if (chart.getLegend() != null) chart.getLegend().setPosition(RectangleEdge.BOTTOM);

        // 4) Estilos del plot/renderer
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(new Color(190, 190, 190));
        plot.setRangeGridlinePaint(new Color(230, 230, 230));
        plot.setOutlineVisible(false);

        LineAndShapeRenderer r = (LineAndShapeRenderer) plot.getRenderer();
        r.setDefaultShapesVisible(true);
        r.setDefaultShapesFilled(true);
        r.setAutoPopulateSeriesShape(false);
        r.setDefaultShape(new Ellipse2D.Double(-3, -3, 6, 6));
        r.setDefaultStroke(new BasicStroke(2.5f));

        // paleta básica
        Color[] palette = {
                new Color(231, 76, 60),   // rojo
                new Color(52, 152, 219),  // azul
                new Color(46, 204, 113),  // verde
                new Color(241, 196, 15),  // amarillo
                new Color(155, 89, 182),  // morado
                new Color(26, 188, 156),  // turquesa
                new Color(52, 73, 94),    // gris oscuro
                new Color(230, 126, 34)   // naranja
        };
        for (int i = 0; i < dataset.getRowCount(); i++) {
            r.setSeriesPaint(i, palette[i % palette.length]);
        }

        ChartPanel panel = new ChartPanel(chart);
        panel.setMouseWheelEnabled(true);
        panel.setBackground(new Color(200, 200, 200));
        return panel;
    }

    // Helper para rotular YearMonth como "ago-2025"
    private static String etiquetaYM(YearMonth ym, Locale locale) {
        String mes = ym.getMonth()
                .getDisplayName(TextStyle.SHORT, locale)  // "ago" o "ago."
                .replace(".", "")                          // quita punto final si existe
                .toLowerCase(locale);                      // "ago"
        return mes + "-" + ym.getYear();                          // "ago-2025"
    }

}
