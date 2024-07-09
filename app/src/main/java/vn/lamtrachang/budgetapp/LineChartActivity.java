package vn.lamtrachang.budgetapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import vn.lamtrachang.budgetapp.placeholder.IncomeItem;
import vn.lamtrachang.budgetapp.placeholder.SQLiteHelper;

public class LineChartActivity extends AppCompatActivity {
    BarChart mBarChart;
    private SQLiteHelper dataSource = new SQLiteHelper(this);
    private List<String> months;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_line_chart);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationBar);
        bottomNavigationView.setSelectedItemId(R.id.Chart);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.Chart) {
                return true;
            } else if (item.getItemId() == R.id.Home) {
                startActivity(new Intent(this, HomeActivity.class));
                return true;
            } else if (item.getItemId() == R.id.Add) {
                startActivity(new Intent(this, DetailActivity.class));
                return true;
            }
            return false;
        });
        mBarChart = findViewById(R.id.chart);
        mBarChart.setDrawBarShadow(false);
        mBarChart.getAxisRight().setDrawLabels(false);
        ArrayList<IncomeItem> incomeItems = dataSource.getAllIncome();
        ArrayList<IncomeItem> expenseItems = dataSource.getAllExpenses();
        ArrayList<BarEntry> entriesIncome = new ArrayList<>();
        ArrayList<BarEntry> entriesExpense = new ArrayList<>();
        //DateTimeFormatter.ofPattern("HH:mm - dd.M yyyy")
        
        months = new ArrayList<>();

        ArrayList<IncomeItem> items = dataSource.getAll();

        for( int i = 0; i< items.size(); i++) {
            if (!months.contains(items.get(i).getTime().substring(11))) {
                months.add(items.get(i).getTime().substring(11));
            }
        }
        //sort months
        Collections.sort(months);
        

        //tính tổng tiền income và expenses theo tháng  và lưu vào entries

        for (int i = 0; i < months.size(); i++) {
            float income = 0;
            float expense = 0;
            for (int j = 0; j < incomeItems.size(); j++) {
                if (incomeItems.get(j).getTime().substring(11).equals(months.get(i))) {
                    income += Float.parseFloat(incomeItems.get(j).getMoney());
                }
            }
            for (int j = 0; j < expenseItems.size(); j++) {
                if (expenseItems.get(j).getTime().substring(11).equals(months.get(i))) {
                    expense += Float.parseFloat(expenseItems.get(j).getMoney());
                }
            }
            entriesIncome.add(new BarEntry(i, income));
            entriesExpense.add(new BarEntry(i, expense));
        }

       BarDataSet barDataSetIncome = new BarDataSet(entriesIncome, "Income");
       barDataSetIncome.setColor(Color.GREEN);


       BarDataSet barDataSetExpense = new BarDataSet(entriesExpense, "Expense");
       barDataSetExpense.setColors(Color.GRAY);


        BarData data = new BarData( barDataSetIncome, barDataSetExpense );
        mBarChart.setData(data);

        YAxis yAxis = mBarChart.getAxisLeft();
        yAxis.setAxisMinimum(0);
        yAxis.setAxisMaximum(20000000);


        XAxis xAxis = mBarChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(months));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1);

        mBarChart.setVisibleXRangeMaximum(3);
        mBarChart.animateY(1000);
        float barSpace = 0.08f;
        float groupSpace = 0.38f;
        data.setBarWidth(0.2f);
        mBarChart.getXAxis().setAxisMinimum(0);
        mBarChart.groupBars(0, groupSpace, barSpace);
        mBarChart.invalidate();



    }
}