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
    private IncomeItemAdapter mItemAdapter ;
    private TextView mTextDate;
    private SQLiteHelper dataSource= new SQLiteHelper(this);
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
//        dataSource.addIncome(new IncomeItem("item 1","2842000","", 1, 1,  String.valueOf(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm - dd.M yyyy"))), 0 ));
//        dataSource.addIncome(new IncomeItem("item 2","5730200", "tiền học", 1, 0,  String.valueOf(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm - dd.M yyyy"))), 1 ));
//        dataSource.addIncome(new IncomeItem("item 3","43500","", 0, 0,  String.valueOf(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm - dd.M yyyy"))), 2));
//        dataSource.addIncome(new IncomeItem("item 4","38472000", "mua nhà", 0, 0,  String.valueOf(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm - dd.M yyyy"))), 3));
//        dataSource.addIncome(new IncomeItem("item 5","3000","", 1, 1,  String.valueOf(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm - dd.M yyyy"))), 6));
//        dataSource.addIncome(new IncomeItem("item 6","32000","", 1, 0,  String.valueOf(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm - dd.M yyyy"))), 5));
//        dataSource.addIncome(new IncomeItem("item 7","100000","", 0, 0,  String.valueOf(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm - dd.M yyyy"))), 6));
//        dataSource.addIncome(new IncomeItem("item 8","200000","", 0, 0,  String.valueOf(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm - dd.M yyyy"))), 7));
//        dataSource.addIncome(new IncomeItem("item 9","500000","", 1, 1,  String.valueOf(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm - dd.M yyyy"))), 4));
//        dataSource.addIncome(new IncomeItem("item 10","1000000","", 1, 0,  String.valueOf(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm - dd.M yyyy"))), 3));
//        dataSource.addIncome(new IncomeItem("item 11","2000000","", 0, 0,  String.valueOf(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm - dd.M yyyy"))), 7));
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
             if (textSavings.getText().toString().equals("*******") ) {
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
            bundle.putSerializable("item", new IncomeItem() );
            intent.putExtras(bundle);
            startActivity(intent);
        });
        
    }
}
