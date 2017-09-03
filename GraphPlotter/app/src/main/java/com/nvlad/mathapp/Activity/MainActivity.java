package com.nvlad.mathapp.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.nvlad.mathapp.Exception.AnalysisException;
import com.nvlad.mathapp.Exception.ErrorAlert;
import com.nvlad.mathapp.Exception.ParseException;
import com.nvlad.mathapp.Exception.UnknownException;
import com.nvlad.mathapp.Model.FunctionLab;
import com.nvlad.mathapp.R;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.BasicStroke;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.achartengine.tools.PanListener;
import org.achartengine.tools.ZoomEvent;
import org.achartengine.tools.ZoomListener;

import java.util.Formatter;


public class MainActivity extends AppCompatActivity {
    private static int NGraphMax = 7;
    private static int NGraph;
    private Boolean isExtrema, isRoots, isIntersect;
    private GraphicalView mChart;
    private ErrorAlert err1;
    Button btnChart;
    Button btnFunc;
    private static FunctionLab FLab;
    double x0[][];
    double y0[][];
    //private XYMultipleSeriesRenderer m_multiRenderer;
    private static final String KEY_INDEX1 = "index1";
    private static final String KEY_INDEX2 = "index2";
    private static final String KEY_INDEX3 = "index3";
    private static final String KEY_INDEX4 = "index4";
    private static final String TAG = "MainActivity";
    private double xmin_last,xmax_last,ymin_last,ymax_last;
    private boolean isGraphDrawn = false;
    private boolean BusyFlag=false;
    private static XYMultipleSeriesDataset dataset;

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putBoolean(KEY_INDEX1, isExtrema);
        savedInstanceState.putBoolean(KEY_INDEX2, isRoots);
        savedInstanceState.putBoolean(KEY_INDEX3, isIntersect);
        savedInstanceState.putParcelable(KEY_INDEX4, FLab);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataset = new XYMultipleSeriesDataset();
        isExtrema=false;
        isRoots=false;
        isIntersect=false;
        if (savedInstanceState != null) {
            isExtrema = savedInstanceState.getBoolean(KEY_INDEX1, false);
            isRoots = savedInstanceState.getBoolean(KEY_INDEX2, false);
            isIntersect = savedInstanceState.getBoolean(KEY_INDEX3, false);
            FLab = savedInstanceState.getParcelable(KEY_INDEX4);
        }
        err1 = new ErrorAlert();
        if (FLab==null) {
            FLab = new FunctionLab();
/*            try {
//                FLab.AddFunction("x+a");
//                FLab.AddFunction("(x-1)^2");
//                FLab.AddFunction("x*a+exp(x)");
//                FLab.AddFunction("x/4");

//                FLab.AddFunction("x!");

//                FLab.AddFunction("x*a+exp(x)-(x/4)");

//                FLab.AddFunction("x-0");
//                FLab.AddFunction("4");
//                FLab.AddFunction("sin(x/2)");


//                FLab.AddFunction("ctg(x-1)");
//                FLab.AddFunction("x-2");
//                FLab.AddFunction("(x-1)*(x-3)*(x-7)");
//                FLab.AddFunction("sin(x/2)");
            } catch (ParseException e) {
                err1.ShowError(MainActivity.this, 1);
            }*/
        }
        x0 = new double[NGraphMax][FLab.getnumberofdots()];
        y0 = new double[NGraphMax][FLab.getnumberofdots()];
        NGraph = FLab.getNFunctions();
      /*  if (NGraph==0){
            NGraph++;
            try{
                FLab.AddFunction();
            }
            catch (ParseException e){
                err1.ShowError(MainActivity.this,1);
            }
        }*/
        err1.setError(0);
// Getting reference to the button btn_chart
        btnChart = (Button) findViewById(R.id.btn_chart);
        btnFunc = (Button) findViewById(R.id.btn_func);
// Defining click event listener for the button btn_chart
        OnClickListener clickListener = new OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    NGraph = FLab.getNFunctions();
                    FLab.ParseFunctions();
                }
                catch(ParseException e){
                    if ((e.getID()>=0)&(e.getID() < FLab.getnumberofdots())){
                        err1.ShowError(MainActivity.this, 1, e.getID());
                    }
                    else err1.ShowError(MainActivity.this,1);
                }
//                if (!BusyFlag) new UpdateAsync().execute();
                updateFLab(xmin_last,xmax_last,ymin_last,ymax_last);
                openChart();
            }
        };
        OnClickListener clickListener2 = new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(MainActivity.this, MultigraphActivity.class);
                startActivity(k);
            }
        };

// Setting event click listener for the button btn_chart of the MainActivity layout
        btnChart.setOnClickListener(clickListener);
        btnFunc.setOnClickListener(clickListener2);
        xmin_last = 0.75*FLab.getXmin()+0.25*FLab.getXmax();
        xmax_last = 0.25*FLab.getXmin()+0.75*FLab.getXmax();
        ymin_last = 0.75*FLab.getYmin()+0.25*FLab.getYmax();
        ymax_last = 0.25*FLab.getYmin()+0.75*FLab.getYmax();
//        openChart();
    }

    private class UpdateAsync extends AsyncTask<Void,Void,Integer> {
        @Override
        protected Integer doInBackground(Void... params) {
//            try {
                  BusyFlag=true;
                  updateData();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            return 1;
        }
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            if (isGraphDrawn) mChart.repaint();
            BusyFlag = false;
            updateGraph(xmin_last,xmax_last,ymin_last,ymax_last);
        }
        protected void onCancelled(Integer result) {
            super.onPostExecute(result);
            BusyFlag = false;
        }
    }

    private void updateData(){
//        XYMultipleSeriesDataset dataset1 = new XYMultipleSeriesDataset();
//        if (isGraphDrawn) dataset.clear();
        final double xmin = 0.75*FLab.getXmin()+0.25*FLab.getXmax();
        final double xmax = 0.25*FLab.getXmin()+0.75*FLab.getXmax();
        final double ymin = 0.75*FLab.getYmin()+0.25*FLab.getYmax();
        final double ymax = 0.25*FLab.getYmin()+0.75*FLab.getYmax();
        final double LowerBound = 5.5*FLab.getYmin()-4.5*FLab.getYmax();
        final double UpperBound = 5.5*FLab.getYmax()-4.5*FLab.getYmin();
        final double LowerBoundX = 5.5*FLab.getXmin()-4.5*FLab.getXmax();
        final double UpperBoundX = 5.5*FLab.getXmax()-4.5*FLab.getXmin();
        double x, r, phi;
        err1.setError(0);
        try {
            FLab.EvaluateFunctions();
        }
        catch(ParseException e){
            if ((e.getID()>=0)&(e.getID()< FLab.getnumberofdots())){
                err1.ShowError(MainActivity.this,1,e.getID());
            }
            else err1.ShowError(MainActivity.this,1);
        }
        catch(UnknownException e){
            err1.ShowError(MainActivity.this,2);
        }

//        expr.setExpression("x+2");
        XYSeries[] fxSeries = new XYSeries[NGraphMax];
        for (int j=0;j<NGraph;j++) {
            fxSeries[j] = new XYSeries("f"+j+"(x)");
            for (int i = 0; i < FLab.getnumberofdots(); i++) {
                double y=FLab.getY(j,i);
                switch (FLab.getMode(j)) {
                    case 0:
                        if ((y > LowerBound) & (y < UpperBound)) fxSeries[j].add(FLab.getX(i), y);
                        break;
                    case 1:
                        if ((y > LowerBoundX) & (y < UpperBoundX)) fxSeries[j].add(y, FLab.getX(i));
                        break;
                    case 2:
                        x=y*Math.cos(FLab.getX(i));
                        y=y*Math.sin(FLab.getX(i));
                        if ((y > LowerBound) & (y < UpperBound) & (x > LowerBoundX) & (x < UpperBoundX))
                            fxSeries[j].add(x,y);
                        break;
                    case 3:
                        break;
                    default:
                        break;
                }
                if (err1.isError()) break;
            }
        }
        for (int j=NGraph;j<NGraphMax;j++) {
            fxSeries[j] = new XYSeries("f"+j+"(x)");
            fxSeries[j].add(10*xmax, 10*ymax);
        }
        if ((isExtrema)||(isRoots)) {
            try {
                FLab.CalculateExtrema();
            }
            catch (ParseException e) {
                if ((e.getID() >= 0) & (e.getID() < FLab.getnumberofdots())) {
                    err1.ShowError(MainActivity.this, 1, e.getID());
                } else err1.ShowError(MainActivity.this, 1);
            }
            catch (UnknownException e) {
                err1.ShowError(MainActivity.this, 2);
            }
        }
        XYSeries ExtremaSeries = new XYSeries("");
        if (isExtrema) {
            for (int j=0;j<NGraph;j++) {
                for (int i = 0; i < FLab.getExtrNum(j); i++) {
                    Formatter fmtX = new Formatter();
                    Formatter fmtY = new Formatter();
                    fmtX.format("%.4f", FLab.getExtrX(j,i));
                    fmtY.format("%.4f", FLab.getExtrY(j,i));
                    ExtremaSeries.add(FLab.getExtrX(j,i), FLab.getExtrY(j,i));
                    String t = '(' + fmtX.toString() + ';' + fmtY.toString() + ')';
                    ExtremaSeries.addAnnotation(t, FLab.getExtrX(j, i), FLab.getExtrY(j, i));
                }
            }
        }
        XYSeries RootSeries = new XYSeries("");
        if (isRoots) {
            try {
                FLab.CalculateRoots();
                for (int j=0;j<NGraph;j++) {
                    for (int i = 0; i < FLab.getRootNum(j); i++) {
                        Formatter fmt = new Formatter();
                        fmt.format("%.4f", FLab.getRoot(j,i));
                        RootSeries.add(FLab.getRoot(j,i), 0.0);
                        String t = '(' + String.valueOf(fmt.toString()) + ";0.0)";
                        RootSeries.addAnnotation(t, FLab.getRoot(j,i), 0.0);
                    }
                }
            }
            catch(ParseException e){
                if ((e.getID()>=0)&(e.getID()< FLab.getnumberofdots())){
                    err1.ShowError(MainActivity.this,1,e.getID());
                }
                else err1.ShowError(MainActivity.this,1);
            }
            catch(UnknownException e){
                err1.ShowError(MainActivity.this,2);
            }
            catch(AnalysisException e){
                err1.ShowError(MainActivity.this,3);
            }
        }
        XYSeries IntersectSeries = new XYSeries("");
        if (isIntersect) {
            try {
                FLab.CalculateIntersections();
            }
            catch(ParseException e){
                err1.ShowError(MainActivity.this,1);
            }
            catch(UnknownException e){
                err1.ShowError(MainActivity.this,2);
            }
            catch(AnalysisException e){
                err1.ShowError(MainActivity.this,3);
            }
            for (int i = 0; i < FLab.getIntersectionsNum(); i++) {
                Formatter fmtX = new Formatter();
                Formatter fmtY = new Formatter();
                fmtX.format("%.4f", FLab.getIntersectX(i));
                fmtY.format("%.4f", FLab.getIntersectY(i));
                IntersectSeries.add(FLab.getIntersectX(i), FLab.getIntersectY(i));
                String t = '(' + fmtX.toString() + ';' + fmtY.toString() + ')';
                IntersectSeries.addAnnotation(t, FLab.getIntersectX(i), FLab.getIntersectY(i));
            }
        }
        if (!err1.isError()){
            dataset.clear();
            for (int j=0;j<NGraphMax;j++)   dataset.addSeries(fxSeries[j]);
            if (isExtrema) dataset.addSeries(ExtremaSeries);
            if (isRoots) dataset.addSeries(RootSeries);
            if (isIntersect) dataset.addSeries(IntersectSeries);
//            if (isGraphDrawn) dataset.clear();
//            dataset=dataset1;
//            if (isGraphDrawn) mChart.repaint();
        }
    }

    private void openChart(){
        final double xmin = 0.75*FLab.getXmin()+0.25*FLab.getXmax();
        final double xmax = 0.25*FLab.getXmin()+0.75*FLab.getXmax();
        final double ymin = 0.75*FLab.getYmin()+0.25*FLab.getYmax();
        final double ymax = 0.25*FLab.getYmin()+0.75*FLab.getYmax();
        updateData();
/*        if ((isGraphDrawn)&(!BusyFlag)) new UpdateAsync().execute();
        else updateData();*/
        if (!err1.isError()){
            XYSeriesRenderer[] fxRenderer = new XYSeriesRenderer[NGraphMax];
            for (int j=0;j<NGraphMax;j++) {
                fxRenderer[j] = new XYSeriesRenderer();
                if (j==0) fxRenderer[j].setColor(Color.BLACK);
                if (j==1) fxRenderer[j].setColor(Color.BLUE);
                if (j==2) fxRenderer[j].setColor(Color.RED);
                if (j==3) fxRenderer[j].setColor(Color.GREEN);
                if (j==4) fxRenderer[j].setColor(Color.CYAN);
                if (j==5) fxRenderer[j].setColor(Color.MAGENTA);
                if (j==6) fxRenderer[j].setColor(Color.DKGRAY);
                fxRenderer[j].setFillPoints(false);
                fxRenderer[j].setLineWidth(2f);
                fxRenderer[j].setDisplayChartValues(false);
                fxRenderer[j].setShowLegendItem(false);
                fxRenderer[j].setStroke(BasicStroke.SOLID);
            }
//       fxRenderer.setZoomEnabled(enabled);

        XYSeriesRenderer ExtremaRenderer = new XYSeriesRenderer();
        if (isExtrema){
            ExtremaRenderer.setColor(Color.RED);
            ExtremaRenderer.setFillPoints(true);
            ExtremaRenderer.setLineWidth(0.00001f);
            ExtremaRenderer.setDisplayChartValues(false);
            ExtremaRenderer.setDisplayChartValuesDistance(10);
            ExtremaRenderer.setPointStyle(PointStyle.SQUARE);
            ExtremaRenderer.setAnnotationsColor(Color.DKGRAY);
            ExtremaRenderer.setAnnotationsTextSize(15);
            ExtremaRenderer.setAnnotationsTextAlign(Align.CENTER);
            ExtremaRenderer.setShowLegendItem(false);
        }
        XYSeriesRenderer RootRenderer = new XYSeriesRenderer();
        if (isRoots){
            RootRenderer.setColor(Color.RED);
            RootRenderer.setFillPoints(true);
            RootRenderer.setLineWidth(0.00001f);
            RootRenderer.setDisplayChartValues(false);
            RootRenderer.setDisplayChartValuesDistance(10);
            RootRenderer.setPointStyle(PointStyle.SQUARE);
            RootRenderer.setAnnotationsColor(Color.DKGRAY);
            RootRenderer.setAnnotationsTextSize(15);
            RootRenderer.setAnnotationsTextAlign(Align.CENTER);
            RootRenderer.setShowLegendItem(false);
        }
        XYSeriesRenderer IntersectRenderer = new XYSeriesRenderer();
        if (isIntersect){
            IntersectRenderer.setColor(Color.RED);
            IntersectRenderer.setFillPoints(true);
            IntersectRenderer.setLineWidth(0.00001f);
            IntersectRenderer.setDisplayChartValues(false);
            IntersectRenderer.setDisplayChartValuesDistance(10);
            IntersectRenderer.setPointStyle(PointStyle.SQUARE);
            IntersectRenderer.setAnnotationsColor(Color.DKGRAY);
            IntersectRenderer.setAnnotationsTextSize(15);
            IntersectRenderer.setAnnotationsTextAlign(Align.CENTER);
            IntersectRenderer.setShowLegendItem(false);
        }
//        ExtremaRenderer.setStroke(BasicStroke.DOTTED);



// Creating a XYMultipleSeriesRenderer to customize the whole chart
        final XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
        multiRenderer.setXLabels(0);
        multiRenderer.setChartTitle("y=f(x)");
        multiRenderer.setXTitle("x");
        multiRenderer.setYTitle("y");
        //multiRenderer.setPointSize(15f);

//setting text size of the title
        multiRenderer.setChartTitleTextSize(28);
//        multiRenderer.addXTextLabel(1.0,"11111");
//setting text size of the axis title
        multiRenderer.setAxisTitleTextSize(24);
//setting text size of the graph lable
        multiRenderer.setLabelsTextSize(24);
//setting zoom buttons visibility
        multiRenderer.setZoomButtonsVisible(true);
//setting pan enablity which uses graph to move on both axis
        multiRenderer.setPanEnabled(true, true);
//setting click false on graph
        multiRenderer.setClickEnabled(false);
//setting zoom to false on both axis
        multiRenderer.setZoomEnabled(true, true);
//setting lines to display on y axis
        multiRenderer.setShowGridY(true);
//setting lines to display on x axis
        multiRenderer.setShowGridX(true);
//setting legend to fit the screen size
        multiRenderer.setFitLegend(true);
//setting displaying line on grid
        multiRenderer.setShowGrid(true);
//setting zoom to false
        multiRenderer.setZoomEnabled(false);
//setting external zoom functions to false
        multiRenderer.setExternalZoomEnabled(false);
//setting displaying lines on graph to be formatted(like using graphics)
        multiRenderer.setAntialiasing(true);
//setting to in scroll to false
        multiRenderer.setInScroll(false);
//setting to set legend height of the graph
        multiRenderer.setLegendHeight(30);
//setting x axis label align
        multiRenderer.setXLabelsAlign(Align.CENTER);
//setting y axis label to align
        multiRenderer.setYLabelsAlign(Align.LEFT);
//setting text style
        multiRenderer.setTextTypeface("sans_serif", Typeface.NORMAL);
//setting no of values to display in y axis
        multiRenderer.setXLabels(5);
        multiRenderer.setYLabels(10);
// setting y axis max value, Since i'm using static values inside the graph so i'm setting y max value to 4000.
// if you use dynamic values then get the max y value and set here
        multiRenderer.setYAxisMin(ymin);
        multiRenderer.setYAxisMax(ymax);
//setting used to move the graph on xaxiz to .5 to the right
        multiRenderer.setXAxisMin(xmin);
//setting used to move the graph on xaxiz to .5 to the right
        multiRenderer.setXAxisMax(xmax);
//setting bar size or space between two bars
//multiRenderer.setBarSpacing(0.5);
//Setting background color of the graph to transparent
        multiRenderer.setBackgroundColor(Color.TRANSPARENT);
//Setting margin color of the graph to transparent
        multiRenderer.setMarginsColor(getResources().getColor(R.color.transparent_background));
        multiRenderer.setApplyBackgroundColor(true);
        multiRenderer.setScale(2f);
//setting x axis point size
        multiRenderer.setPointSize(2f);
//setting the margin size for the graph in the order top, left, bottom, right
        multiRenderer.setMargins(new int[]{30, 30, 30, 30});

        for (int j=0;j<NGraphMax;j++)  multiRenderer.addSeriesRenderer(fxRenderer[j]);
        if (isExtrema) multiRenderer.addSeriesRenderer(ExtremaRenderer);
        if (isRoots) multiRenderer.addSeriesRenderer(RootRenderer);
        if (isIntersect) multiRenderer.addSeriesRenderer(IntersectRenderer);

//this part is used to display graph on the xml
        LinearLayout chartContainer = (LinearLayout) findViewById(R.id.chart);
//remove any views before u paint the chart
        chartContainer.removeAllViews();

        mChart = ChartFactory.getLineChartView(MainActivity.this, dataset, multiRenderer);
        xmin_last=xmin;
        xmax_last=xmax;
        ymin_last=ymin;
        ymax_last=ymax;
        //m_multiRenderer=multiRenderer;

//adding the view to the linearlayout
        chartContainer.addView(mChart);
        isGraphDrawn=true;
        mChart.addPanListener(new PanListener() {
                @Override
                public void panApplied() {
                    updateGraph(multiRenderer);
                }
            });
      mChart.addZoomListener(new ZoomListener() {
            @Override
            public void zoomReset() {
                updateGraph(multiRenderer);
            }

            @Override
            public void zoomApplied(ZoomEvent e) {
                updateGraph(multiRenderer);
            }
        },true,true);
        }
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        if (!isExtrema) menu.findItem(R.id.menu_item_extrema).setTitle(R.string.add_extrema);
        else menu.findItem(R.id.menu_item_extrema).setTitle(R.string.remove_extrema);

        if (!isRoots) menu.findItem(R.id.menu_item_roots).setTitle(R.string.add_roots);
        else menu.findItem(R.id.menu_item_roots).setTitle(R.string.remove_roots);

        if (!isIntersect) menu.findItem(R.id.menu_item_intersect).setTitle(R.string.add_intersect);
        else menu.findItem(R.id.menu_item_intersect).setTitle(R.string.remove_intersect);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public void onPause(){
        BusyFlag=false;
        updateFLab(xmin_last,xmax_last,ymin_last,ymax_last);
        super.onPause();
    }

    @Override
    public void onResume(){
        super.onResume();
        try {
            NGraph = FLab.getNFunctions();
            FLab.ParseFunctions();
        }
        catch(ParseException e){
            if ((e.getID()>=0)&(e.getID()< FLab.getnumberofdots())){
                err1.ShowError(MainActivity.this, 1, e.getID());
            }
            else err1.ShowError(MainActivity.this,1);
        }
//                if (!BusyFlag) new UpdateAsync().execute();
        openChart();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.menu_item_help:
                Intent i = new Intent(MainActivity.this, HelpActivity.class);
                startActivity(i);
                return true;
            case R.id.menu_item_options:
                Intent j = new Intent(MainActivity.this, OptionsActivity.class);
                startActivity(j);
                return true;
            case R.id.menu_item_addfunction:
                Intent k = new Intent(MainActivity.this, MultigraphActivity.class);
                startActivity(k);
                return true;
            case R.id.menu_item_params:
                Intent l = new Intent(MainActivity.this, ParamsActivity.class);
                startActivity(l);
                return true;
            case R.id.menu_item_extrema:
                isExtrema = !isExtrema;
                updateFLab(xmin_last,xmax_last,ymin_last,ymax_last);
//                updateFLab(m_multiRenderer.getXAxisMin(),m_multiRenderer.getXAxisMax(),m_multiRenderer.getYAxisMin(),m_multiRenderer.getYAxisMax());
                openChart();
                invalidateOptionsMenu();
                return true;
            case R.id.menu_item_roots:
                isRoots = !isRoots;
                updateFLab(xmin_last,xmax_last,ymin_last,ymax_last);
                openChart();
                invalidateOptionsMenu();
                return true;
            case R.id.menu_item_intersect:
                isIntersect = !isIntersect;
                updateFLab(xmin_last,xmax_last,ymin_last,ymax_last);
                openChart();
                invalidateOptionsMenu();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static void setXmin(double x){
        FLab.setXmin(x);
        FLab.setAllFalse();
    }
    public static void setXmax(double x){
        FLab.setXmax(x);
        FLab.setAllFalse();
    }
    public static void setYmin(double x){
        FLab.setYmin(x);
        FLab.setAllFalse();
    }
    public static void setYmax(double x){
        FLab.setYmax(x);
        FLab.setAllFalse();
    }

    public static double getXmin(){
        return FLab.getXmin();
    }
    public static double getXmax(){
        return FLab.getXmax();
    }
    public static double getYmin(){
        return FLab.getYmin();
    }
    public static double getYmax(){
        return FLab.getYmax();
    }
    public static FunctionLab getFLab() {return FLab;}

    private void updateGraph(XYMultipleSeriesRenderer mRenderer){
        final double minX = mRenderer.getXAxisMin();
        final double maxX = mRenderer.getXAxisMax();
        final double minY = mRenderer.getYAxisMin();
        final double maxY = mRenderer.getYAxisMax();
        xmin_last=minX;
        xmax_last=maxX;
        ymin_last=minY;
        ymax_last=maxY;
        if (((minX < FLab.getXmin())||(maxX>FLab.getXmax())||(5*(maxX-minX)<FLab.getXmax()-FLab.getXmin()))||
                ((minY < FLab.getYmin())||(maxY>FLab.getYmax())||(5*(maxY-minY)<FLab.getYmax()-FLab.getYmin()))
                        &(!BusyFlag)) {
            updateFLab(minX, maxX, minY, maxY);
//            updateData();
            new UpdateAsync().execute();
        }
//        Log.d(TAG, "updateGraph() called");
    }

    private void updateGraph(double xmin, double xmax, double ymin, double ymax){
        xmin_last=xmin;
        xmax_last=xmax;
        ymin_last=ymin;
        ymax_last=ymax;
        if (((xmin < FLab.getXmin())||(xmax>FLab.getXmax())||(5*(xmax-xmin)<FLab.getXmax()-FLab.getXmin()))||
                ((ymin < FLab.getYmin())||(ymax>FLab.getYmax())||(5*(ymax-ymin)<FLab.getYmax()-FLab.getYmin()))
                        &(!BusyFlag)) {
            updateFLab(xmin, xmax, ymin, ymax);
//            updateData();
            new UpdateAsync().execute();
        }
//        Log.d(TAG, "updateGraph() called");
    }

    private void updateFLab(double minX, double maxX, double minY, double maxY){
        FLab.setXmin(1.5*minX-0.5*maxX);
        FLab.setXmax(1.5*maxX-0.5*minX);
        FLab.setYmin(1.5*minY-0.5*maxY);
        FLab.setYmax(1.5*maxY-0.5*minY);
        FLab.setAllFalse();
    }

}

