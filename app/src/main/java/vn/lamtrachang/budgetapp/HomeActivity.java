package vn.lamtrachang.budgetapp;


import android.content.Context;
import android.content.Intent;
import android.icu.text.DecimalFormat;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.home);

        mTextDate = findViewById(R.id.date);
        mTextDate.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MMM yyyy")));
        ImageView menu = findViewById(R.id.setting);
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
//         mRecyclerItem.setLayoutManager(new LinearLayoutManager(this));
//         RecyclerView.ItemDecoration itemDecoration = new
//                 DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL);
//         mRecyclerItem.addItemDecoration(itemDecoration);

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

        ImageView add = findViewById(R.id.account_button);
        add.setOnClickListener(v -> {
            Intent intent = new Intent(this, DetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("item", new IncomeItem());
            intent.putExtras(bundle);
            startActivity(intent);
        });

    }

    //thêm dữ liệu mẫu từ tháng 5/2024
    public void initData() {
        dataSource.deleteAll();
//            dataSource.addIncome(new IncomeItem("item 98","90000","", 1, 1,  "17:11 - 15.4 2024", 0));
        dataSource.addIncome(new IncomeItem("item 1", "284000", "", 1, 1, "14:11 - 14.5 2024", 0));
        dataSource.addIncome(new IncomeItem("item 2", "5730200", "tiền học", 1, 1, "22:11 - 14.5 2024", 1));
        dataSource.addIncome(new IncomeItem("item 3", "435000", "", 0, 0, "11:17 - 15.5 2024", 2));
        dataSource.addIncome(new IncomeItem("item 4", "3847000", "mua nhà", 0, 0, "06:11 - 18.5 2024", 3));
        dataSource.addIncome(new IncomeItem("item 5", "300000", "", 1, 1, "10:41 - 18.5 2024", 6));
        dataSource.addIncome(new IncomeItem("item 6", "32000", "đi chợ ", 1, 1, "04:18 - 19.5 2024", 5));
        dataSource.addIncome(new IncomeItem("item 7", "100000", "sửa quần áo", 0, 0, "13:31 - 21.5 2024", 6));
        dataSource.addIncome(new IncomeItem("item 8", "200000", "", 0, 0, "15:13 - 21.5 2024", 7));
        dataSource.addIncome(new IncomeItem("item 16", "70000", "mua trái cây", 0, 0, "04:11 - 29.5 2024", 2));
        dataSource.addIncome(new IncomeItem("item 17", "80000", "", 1, 1, "02:11 - 30.5 2024", 12024));
        dataSource.addIncome(new IncomeItem("item 18", "90000", "tiền điện", 1, 0, "01:11 - 31.5 2024", 0));
        dataSource.addIncome(new IncomeItem("item 19", "100000", "tiền nước", 0, 0, "00:11 - 01.6 2024", 1));
        dataSource.addIncome(new IncomeItem("item 20", "110000", "tiền điện thoại", 0, 0, "23:11 - 01.6 2024", 2));
        dataSource.addIncome(new IncomeItem("item 21", "120000", "tiền mạng", 1, 1, "22:11 - 02.6 2024", 3));
        dataSource.addIncome(new IncomeItem("item 22", "130000", "tiền nước", 1, 0, "21:11 - 03.6 2024", 4));
        dataSource.addIncome(new IncomeItem("item 24", "150000", "", 0, 0, "19:11 - 05.6 2024", 6));
        dataSource.addIncome(new IncomeItem("item 25", "160000", "tiền điện", 1, 1, "18:11 - 06.6 2024", 7));
        dataSource.addIncome(new IncomeItem("item 26", "170000", "sửa máy giặt", 1, 0, "17:11 - 07.6 2024", 0));
        dataSource.addIncome(new IncomeItem("item 27", "180000", "mua sách", 0, 0, "16:11 - 08.6 2024", 1));
        dataSource.addIncome(new IncomeItem("item 28", "190000", "mua quần áo", 0, 0, "15:11 - 09.6 2024", 2));
        dataSource.addIncome(new IncomeItem("item 29", "200000", "", 1, 1, "14:11 - 10.6 2024", 3));
        dataSource.addIncome(new IncomeItem("item 38", "290000", "", 1, 0, "05:11 - 19.6 2024", 4));
        dataSource.addIncome(new IncomeItem("item 39", "300000", "", 0, 0, "04:11 - 20.6 2024", 5));
        dataSource.addIncome(new IncomeItem("item 9", "500000", "mua sách", 1, 1, "18:45 - 22.6 2024", 4));
        dataSource.addIncome(new IncomeItem("item 10", "100000", "", 1, 0, "16:24 - 23.6 2024", 3));
        dataSource.addIncome(new IncomeItem("item 11", "200000", "bán điện thoại", 0, 0, "14:53 - 24.6 2024", 7));
        dataSource.addIncome(new IncomeItem("item 12", "300000", "tiền điện", 1, 1, "12:11 - 25.6 2024", 6));
        dataSource.addIncome(new IncomeItem("item 13", "400000", "", 1, 1, "10:26 - 26.5 2024", 5));
        dataSource.addIncome(new IncomeItem("item 14", "50000", "tiền xăng", 1, 1, "08:31 - 27.6 2024", 4));
        dataSource.addIncome(new IncomeItem("item 15", "600000", "bán quâng áo", 0, 0, "16:51 - 28.6 2024", 3));
        dataSource.addIncome(new IncomeItem("item 50", "410000", "", 1, 0, "17:11 - 30.6 2024", 0));
        dataSource.addIncome(new IncomeItem("item 53", "440000", "", 1, 1, "14:11 - 03.7 2024", 3));
        dataSource.addIncome(new IncomeItem("item 61", "520000", "", 1, 1, "06:11 - 11.7 2024", 3));
        dataSource.addIncome(new IncomeItem("item 2", "5730200", "tiền học", 1, 1, "22:11 - 14.7 2024", 1));
        dataSource.addIncome(new IncomeItem("item 3", "435000", "", 0, 0, "11:17 - 15.7 2024", 2));
        dataSource.addIncome(new IncomeItem("item 4", "3847000", "mua nhà", 0, 0, "06:11 - 18.7 2024", 3));
        dataSource.addIncome(new IncomeItem("item 5", "300000", "", 1, 1, "10:41 - 18.7 2024", 6));
        dataSource.addIncome(new IncomeItem("item 6", "32000", "đi chợ ", 1, 1, "04:18 - 19.7 2024", 5));
        dataSource.addIncome(new IncomeItem("item 7", "100000", "sửa quần áo", 0, 0, "13:31 - 21.7 2024", 6));
        dataSource.addIncome(new IncomeItem("item 83", "740000", "", 0, 0, "08:11 - 01.8 2024", 1));
        dataSource.addIncome(new IncomeItem("item 85", "760000", "", 1, 1, "06:11 - 03.8 2024", 3));
        dataSource.addIncome(new IncomeItem("item 86", "770000", "", 1, 0, "05:11 - 04.8 2024", 4));
        dataSource.addIncome(new IncomeItem("item 87", "780000", "", 0, 0, "04:11 - 05.8 2024", 5));
        dataSource.addIncome(new IncomeItem("item 89", "800000", "", 1, 1, "02:11 - 07.8 2024", 7));
        dataSource.addIncome(new IncomeItem("item 91", "820000", "", 0, 0, "00:11 - 09.8 2024", 1));
        dataSource.addIncome(new IncomeItem("item 93", "840000", "", 1, 1, "22:11 - 10.8 2024", 3));
        dataSource.addIncome(new IncomeItem("item 94", "850000", "", 1, 0, "21:11 - 11.8 2024", 4));
        dataSource.addIncome(new IncomeItem("item 95", "860000", "", 0, 0, "20:11 - 12.8 2024", 5));
        dataSource.addIncome(new IncomeItem("item 97", "880000", "", 1, 1, "18:11 - 14.8 2024", 7));
//    dataSource.addIncome(new IncomeItem("item 98","890000","", 1, 1,  "17:11 - 15.9 2024", 0));


    }
}
