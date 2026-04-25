package com.example.medicalzone;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

public class CustomBarChartView extends View {

    private List<Float> salesData;  // Sales data to be plotted
    private List<Float> profitData; // Profit data to be plotted

    private Paint salesBarPaint, profitBarPaint, axisPaint, textPaint, gridPaint;
    private static final float MAX_VALUE = 200000; // Maximum value for X-axis scaling (for sales and profit)
    private static final int X_AXIS_INTERVAL = 25000; // Interval for X-axis
    private static final float BAR_HEIGHT = 50f; // Height of each bar
    private static final float BAR_SPACING = 30f; // Spacing between bars
    private static final int GRID_LINES = 5; // Number of grid lines

    public CustomBarChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        // Set up the paint objects for the bars
        salesBarPaint = new Paint();
        salesBarPaint.setAntiAlias(true); // Smooth bars
        salesBarPaint.setShader(new LinearGradient(0, 0, 100, 0, Color.parseColor("#4CAF50"), Color.parseColor("#388E3C"), Shader.TileMode.CLAMP)); // Green gradient for Sales

        profitBarPaint = new Paint();
        profitBarPaint.setAntiAlias(true); // Smooth bars
        profitBarPaint.setShader(new LinearGradient(0, 0, 100, 0, Color.parseColor("#FF9800"), Color.parseColor("#F57C00"), Shader.TileMode.CLAMP)); // Orange gradient for Profit

        axisPaint = new Paint();
        axisPaint.setColor(Color.BLACK); // Axis color
        axisPaint.setStrokeWidth(3);     // Axis thickness

        textPaint = new Paint();
        textPaint.setColor(Color.BLACK); // Text color
        textPaint.setTextSize(30);       // Text size

        gridPaint = new Paint();
        gridPaint.setColor(Color.parseColor("#D3D3D3")); // Light Gray color for grid lines
        gridPaint.setStrokeWidth(1);     // Grid line thickness
    }

    // Set the data for both sales and profit
    public void setData(List<Float> salesData, List<Float> profitData) {
        this.salesData = salesData;
        this.profitData = profitData;
        invalidate(); // Redraw the view when data changes
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (salesData == null || profitData == null || salesData.isEmpty() || profitData.isEmpty()) return;

        int width = getWidth();
        int height = getHeight();

        // Padding for axis
        int padding = 100;
        int chartHeight = height - padding * 2;
        int chartWidth = width - padding * 2;

        // Draw X and Y axis
        canvas.drawLine(padding, height - padding, width - padding, height - padding, axisPaint); // X-axis
        canvas.drawLine(padding, height - padding, padding, padding, axisPaint); // Y-axis

        // Draw X-axis labels with an interval of 25,000 (as per your request)
        for (int i = 0; i <= MAX_VALUE / X_AXIS_INTERVAL; i++) {
            float x = padding + (i * chartWidth / (MAX_VALUE / X_AXIS_INTERVAL)); // Scale for X-axis intervals (25,000, 50,000, ...)
            canvas.drawText(String.valueOf(i * X_AXIS_INTERVAL), x - 20, height - padding + 40, textPaint); // Display 0, 25,000, 50,000, etc.
        }

        // Draw horizontal grid lines for better visualization
        for (int i = 1; i <= GRID_LINES; i++) {
            float y = height - padding - (i * chartHeight / (GRID_LINES + 1));
            canvas.drawLine(padding, y, width - padding, y, gridPaint);
        }

        // Set a fixed bar width (this is the horizontal width in a horizontal bar chart)
        float yPosition = padding; // Start at top for the bars

        // Draw the bars
        for (int i = 0; i < salesData.size(); i++) {
            // Scale for each data point, calculate the width of each bar
            float salesBarWidth = scaleData(salesData.get(i), chartWidth); // Calculate width for sales bar
            float profitBarWidth = scaleData(profitData.get(i), chartWidth); // Calculate width for profit bar

            // Draw the sales bar (Soft Green with gradient)
            canvas.drawRoundRect(padding, yPosition, padding + salesBarWidth, yPosition + BAR_HEIGHT, 10, 10, salesBarPaint);

            // Draw the profit bar (Soft Orange with gradient)
            canvas.drawRoundRect(padding, yPosition + BAR_HEIGHT + BAR_SPACING, padding + profitBarWidth, yPosition + BAR_HEIGHT * 2 + BAR_SPACING, 10, 10, profitBarPaint);

            // Optionally, add labels or data points (show value at each bar)
            canvas.drawText(String.format("%.2f", salesData.get(i)), padding + salesBarWidth + 10, yPosition + BAR_HEIGHT / 2, textPaint);
            canvas.drawText(String.format("%.2f", profitData.get(i)), padding + profitBarWidth + 10, yPosition + BAR_HEIGHT * 1.5f + BAR_SPACING, textPaint);

            yPosition += BAR_HEIGHT * 2 + BAR_SPACING; // Move to the next bar's position (vertically)
        }
    }

    // Scale the data to fit within the width of the chart (for 25,000 intervals)
    private float scaleData(float value, int chartWidth) {
        return (value / MAX_VALUE) * chartWidth;
    }
}
