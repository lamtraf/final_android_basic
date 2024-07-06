package vn.lamtrachang.budgetapp;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import vn.lamtrachang.budgetapp.placeholder.IncomeItem;
import vn.lamtrachang.budgetapp.placeholder.IncomeItemAdapter;
import vn.lamtrachang.budgetapp.placeholder.SQLiteHelper;

public class income_screen extends AppCompatActivity {
    private ArrayList<IncomeItem> mItems;
    private RecyclerView mRecyclerItem;
    private IncomeItemAdapter mItemAdapter ;
    private TextView mTextTitle ;
    private TextView mTextMoney ;
    private SQLiteHelper dataSource= new SQLiteHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_income_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mTextTitle = findViewById(R.id.title);
        mTextMoney = findViewById(R.id.value);
        ImageView back = findViewById(R.id.back_icon);
        back.setOnClickListener(v -> finish());
        //getExtras()

        mRecyclerItem = findViewById(R.id.list_income);
        LinearLayout noTask = findViewById(R.id.no_task);

        if (dataSource.getAll().isEmpty()) {
            noTask.setVisibility(View.VISIBLE);
        } else {
            noTask.setVisibility(View.GONE);
        }

        mItems = dataSource.getAll();
        mItemAdapter = new IncomeItemAdapter(this, mItems);
        mRecyclerItem.setAdapter(mItemAdapter);
        String str = getIntent().getStringExtra("type");



        if (str != null) {
            if (str.equals("income")) {
                mTextTitle.setText("Income");
                mTextMoney.setText(dataSource.sumIncome());
                //income
            } else {
                mTextTitle.setText("Expenses");
                //expenses
            }
        }



        

    }
}