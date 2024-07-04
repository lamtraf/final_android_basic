package vn.lamtrachang.budgetapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import vn.lamtrachang.budgetapp.placeholder.IncomeItem;
import vn.lamtrachang.budgetapp.placeholder.IncomeItemAdapter;

public class HomeActivity extends AppCompatActivity {
    private ArrayList<IncomeItem> mItems ;
    private RecyclerView mRecyclerItem;
    private IncomeItemAdapter mItemAdapter ;
    private TextView mTextDate;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.home);

        mTextDate = findViewById(R.id.date);
        mTextDate.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MMM yyyy")));
        ImageView menu = findViewById(R.id.setting);
        menu.setOnClickListener(v -> startActivity(new Intent(this, SettingActivity.class)));

        mRecyclerItem = findViewById(R.id.list_income);
        mItems = new ArrayList<>();
        createIncomeList();
        mItemAdapter = new IncomeItemAdapter(this,mItems);
        mRecyclerItem.setAdapter(mItemAdapter);
        mRecyclerItem.setLayoutManager(new LinearLayoutManager(this));

        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mRecyclerItem.addItemDecoration(itemDecoration);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null){
            return;
        }

        IncomeItem item = (IncomeItem) bundle.get("item");
        mItems.add(item);

    }
    private void createIncomeList() {
        mItems.add(new IncomeItem( "item 1",1, 1, "5730200"));
        mItems.add(new IncomeItem( "item 2",1, 0, "43500"));
        mItems.add(new IncomeItem( "item 3",0, 0, "3847200", "tiền học"));
        mItems.add(new IncomeItem( "item 4",1, 1, "3000"));
        mItems.add(new IncomeItem( "item 5",0, 1, "84200200", "mua nhà"));
        mItems.add(new IncomeItem( "item 6",1, 0, "32000"));
//        Toast.makeText(MainActivity.this, mItems.get(1).time, Toast.LENGTH_LONG).show();
    }

}