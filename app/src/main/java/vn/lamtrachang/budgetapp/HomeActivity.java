package vn.lamtrachang.budgetapp;


import android.content.Context;
import android.content.Intent;
import android.icu.text.DecimalFormat;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

import vn.lamtrachang.budgetapp.placeholder.IncomeItem;
import vn.lamtrachang.budgetapp.placeholder.IncomeItemAdapter;
import vn.lamtrachang.budgetapp.placeholder.SQLiteHelper;

public class HomeActivity extends AppCompatActivity {
    private ArrayList<IncomeItem> mItems;
    private RecyclerView mRecyclerItem;
    private IncomeItemAdapter mItemAdapter;
    private TextView mTextDate;
    private SQLiteHelper dataSource = new SQLiteHelper(this);
    private TextView textSavings;
    private TextView textIncome;
    private TextView textExpenses;
    private ImageView imgEye;


    // viết lại hàm onBackPressed để khi ấn nút back sẽ hiện thông báo mà không thoát ứng dụng
    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.home_screen), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        mTextDate = findViewById(R.id.date);
        mTextDate.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MMM yyyy")));
        ImageView menu = findViewById(R.id.setting);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationBar);
        bottomNavigationView.setSelectedItemId(R.id.Home);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.Home) {
                return true;
            } else if (item.getItemId() == R.id.Chart) {

                startActivity(new Intent(this, LineChartActivity.class));
                bottomNavigationView.setSelectedItemId(R.id.Home);
                return true;
            } else if (item.getItemId() == R.id.Add) {

                startActivity(new Intent(this, DetailActivity.class));
                bottomNavigationView.setSelectedItemId(R.id.Home);
                return true;
            }
            return false;
        });
        menu.setOnClickListener(v -> startActivity(new Intent(this, SettingActivity.class)));


        LinearLayout incomeFrame = findViewById(R.id.widget_income);
        LinearLayout expensesFrame = findViewById(R.id.widget_expenses);
        incomeFrame.setOnClickListener(v -> {
            Intent intent = new Intent(this, income_screen.class);
            intent.putExtra("type", "income");
            startActivity(intent);
        });

        expensesFrame.setOnClickListener(v -> {
            Intent intent = new Intent(this, income_screen.class);
            intent.putExtra("type", "expenses");
            startActivity(intent);
        });

        mRecyclerItem = findViewById(R.id.list_income);
        LinearLayout noTask = findViewById(R.id.no_task);

        if (dataSource.getAll().isEmpty()) {
            noTask.setVisibility(View.VISIBLE);
        } else {
            noTask.setVisibility(View.GONE);
        }
//        dataSource.deleteAll();
//       initData(); 

        mItems = dataSource.getAll();
        mItemAdapter = new IncomeItemAdapter(this, mItems);
        mRecyclerItem.setAdapter(mItemAdapter);

        textSavings = findViewById(R.id.text_savings);
        textIncome = findViewById(R.id.text_income);
        textExpenses = findViewById(R.id.text_expenses);
        imgEye = findViewById(R.id.img_eye);

        textSavings.setText(dataSource.getSavings());
        textIncome.setText(dataSource.sumIncome());
        textExpenses.setText(dataSource.sumExpenses());
        imgEye.setOnClickListener(v -> {
            if (textSavings.getText().toString().equals("*******")) {
                textSavings.setText(dataSource.getSavings());
                textIncome.setText(dataSource.sumIncome());
                textExpenses.setText(dataSource.sumExpenses());
                imgEye.setImageResource(R.drawable.baseline_remove_red_eye_24);
            } else {
                textSavings.setText("*******");
                textIncome.setText("*******");
                textExpenses.setText("*******");
                imgEye.setImageResource(R.drawable.baseline_visibility_off_24);
            }
        });

    }

    //thêm dữ liệu mẫu từ tháng 5/2024
    public void initData() {
        dataSource.deleteAll();
//            dataSource.addIncome(new IncomeItem("item 98","900000","", 1, 1,  "17:11 - 15.4 2024", 0));
        dataSource.addIncome(new IncomeItem("item 1", "284000", "", 1, 1, "14:11 - 14.5 2024", 0));
        dataSource.addIncome(new IncomeItem("item 2", "5730200", "tiền học", 1, 1, "22:11 - 14.5 2024", 1));
        dataSource.addIncome(new IncomeItem("item 3", "435000", "", 0, 0, "11:17 - 15.5 2024", 2));
        dataSource.addIncome(new IncomeItem("item 4", "3847000", "mua nhà", 0, 0, "06:11 - 18.5 2024", 3));
        dataSource.addIncome(new IncomeItem("item 5", "300000", "", 1, 1, "10:41 - 18.5 2024", 6));
        dataSource.addIncome(new IncomeItem("item 6", "32000", "đi chợ ", 1, 1, "04:18 - 19.5 2024", 5));
        dataSource.addIncome(new IncomeItem("item 7", "100000", "sửa quần áo", 0, 0, "13:31 - 21.5 2024", 6));
        dataSource.addIncome(new IncomeItem("item 8", "200000", "", 0, 0, "15:13 - 21.5 2024", 7));
        dataSource.addIncome(new IncomeItem("item 16", "70000", "mua trái cây", 0, 0, "04:11 - 29.5 2024", 2));
        dataSource.addIncome(new IncomeItem("item 17", "80000", "", 1, 1, "02:11 - 30.5 2024", 1));
        dataSource.addIncome(new IncomeItem("item 18", "90000", "tiền điện", 1, 0, "01:11 - 31.5 2024", 0));
        dataSource.addIncome(new IncomeItem("item 9", "500000", "mua sách", 1, 1, "18:45 - 22.6 2024", 4));
        dataSource.addIncome(new IncomeItem("item 10", "100000", "", 1, 0, "16:24 - 23.6 2024", 3));
        dataSource.addIncome(new IncomeItem("item 11", "200000", "bán điện thoại", 0, 0, "14:53 - 24.6 2024", 7));
        dataSource.addIncome(new IncomeItem("item 12", "300000", "tiền điện", 1, 1, "12:11 - 25.6 2024", 6));
        dataSource.addIncome(new IncomeItem("item 13", "400000", "", 1, 1, "10:26 - 26.5 2024", 5));
        dataSource.addIncome(new IncomeItem("item 14", "50000", "tiền xăng", 1, 1, "08:31 - 27.6 2024", 4));
        dataSource.addIncome(new IncomeItem("item 15", "600000", "bán quâng áo", 0, 0, "16:51 - 28.6 2024", 3));
        dataSource.addIncome(new IncomeItem("item 50", "410000", "", 1, 0, "17:11 - 30.6 2024", 0));
        dataSource.addIncome(new IncomeItem("item 53", "440000", "", 1, 1, "14:11 - 03.8 2024", 3));
        dataSource.addIncome(new IncomeItem("item 61", "520000", "", 1, 1, "06:11 - 11.8 2024", 3));
        dataSource.addIncome(new IncomeItem("item 2", "5730200", "tiền học", 1, 1, "22:11 - 14.8 2024", 1));
        dataSource.addIncome(new IncomeItem("item 3", "435000", "", 0, 0, "11:17 - 15.8 2024", 2));
        dataSource.addIncome(new IncomeItem("item 4", "3847000", "mua nhà", 0, 0, "06:11 - 18.8 2024", 3));
        dataSource.addIncome(new IncomeItem("item 5", "300000", "", 1, 1, "10:41 - 18.8 2024", 6));
        dataSource.addIncome(new IncomeItem("item 6", "32000", "đi chợ ", 1, 1, "04:18 - 19.8 2024", 5));
        dataSource.addIncome(new IncomeItem("item 7", "100000", "sửa quần áo", 0, 0, "13:31 - 21.8 2024", 6));
//    dataSource.addIncome(new IncomeItem("item 98","890000","", 1, 1,  "17:11 - 15.9 2024", 0));


    }

}
