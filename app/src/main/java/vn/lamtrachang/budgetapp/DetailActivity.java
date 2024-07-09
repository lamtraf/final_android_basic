package vn.lamtrachang.budgetapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import vn.lamtrachang.budgetapp.placeholder.IncomeItem;
import vn.lamtrachang.budgetapp.placeholder.SQLiteHelper;

public class DetailActivity extends Activity { //AppCompatActivity {
    private Spinner spinnerState;
    private Spinner spinnerType;
    private EditText editTextName;
    private EditText editTextMoney;
    private EditText editTextDetail;
    private Context mContext;
    private SQLiteHelper dataSource = new SQLiteHelper(this);
    private ChipGroup chipGroup;

    @Override
    public void onBackPressed() {
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        this.spinnerState = (Spinner) findViewById(R.id.spinner_state);
        this.spinnerType = (Spinner) findViewById(R.id.spinner_type);
        editTextName = findViewById(R.id.task_name);
        editTextMoney = findViewById(R.id.task_money);
        editTextDetail = findViewById(R.id.task_detail);
        chipGroup = findViewById(R.id.chip_group);

        String[] stateItems = {"Income", "Expenses"};
        String[] typeItems = {"Cash", "Bank"};

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item,
                stateItems);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item,
                typeItems);
        // Layout for All ROWs of Spinner.  (Optional for ArrayAdapter).
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        this.spinnerState.setAdapter(adapter1);
        this.spinnerType.setAdapter(adapter2);
        // When user select a List-Item.
        this.spinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                onItemSelectedHandler(parent, view, position, id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        this.spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                onItemSelectedHandler(parent, view, position, id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationBar);
        Bundle bundle = getIntent().getExtras();
        
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.Add) {
                return true;
            } else if (item.getItemId() == R.id.Chart) {
                bottomNavigationView.setSelectedItemId(R.id.Add);
                startActivity(new Intent(this, LineChartActivity.class));
                finish();
                return true;
            } else if (item.getItemId() == R.id.Home) {
                bottomNavigationView.setSelectedItemId(R.id.Add);
                startActivity(new Intent(this, HomeActivity.class));
                finish();
                return true;
            }
            return false;
        });
        IncomeItem item = (IncomeItem) (bundle != null ? bundle.get("item") : null);

        if (item == null) {
            item = new IncomeItem("", "", 0, 0, "", 0);
            bottomNavigationView.setVisibility(View.VISIBLE);
            bottomNavigationView.setSelectedItemId(R.id.Add);
        }
        spinnerState.setSelection(item.getState());
        spinnerType.setSelection(item.getType());
        editTextName.setText(item.getName());
        editTextMoney.setText(item.getMoney());
        editTextDetail.setText(item.getDetail());
        chipGroup.getChildAt(item.getCategory()).setSelected(true);

        IncomeItem finalItem = item;
        chipGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId != finalItem.getCategory()) {
                chipGroup.getChildAt(finalItem.getCategory()).setSelected(false);
            }
        });


        LinearLayout buttonSave = findViewById(R.id.submit);
        IncomeItem finalItem1 = item;
        buttonSave.setOnClickListener(v -> {
            int category = finalItem1.getCategory();
            String name = editTextName.getText().toString().trim();
            String money = editTextMoney.getText().toString();
            String detail = editTextDetail.getText().toString();
            int state = spinnerState.getSelectedItemPosition();
            int type = spinnerType.getSelectedItemPosition();
            for (int i = 0; i < chipGroup.getChildCount(); i++) {
                Chip chip = (Chip) chipGroup.getChildAt(i);
                if (chip.isChecked()) {
                    category = i;
                }
            }
            if (name.isEmpty() || name.isBlank()) {
                editTextName.setError("Task name is required");
                editTextName.requestFocus();
                return;
            }
            if (money.isEmpty()) {
                editTextMoney.setError("Task money is required");
                editTextName.requestFocus();
                return;
            }


            if (finalItem1.getName() == "") {
                String time = String.valueOf(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm - dd.M yyyy")));
                IncomeItem newItem = new IncomeItem(name, money, detail, type, state, time, category);
                dataSource.addIncome(newItem);
                Toast.makeText(this, "Item added", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            } else {
                String time = finalItem1.getTime();
                IncomeItem newItem = new IncomeItem(name, money, detail, type, state, time, category);
                dataSource.updateIncome(finalItem1, newItem);
                Toast.makeText(this, "Item updated", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }

        });

        ImageView buttonCancel = findViewById(R.id.button_back);
        buttonCancel.setOnClickListener( v -> {
//            Intent intent = new Intent(this, HomeActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
                    bottomNavigationView.setSelectedItemId(R.id.Home);
        finish();
        }
        );

        ImageView buttonRemove = findViewById(R.id.button_remove);
        IncomeItem finalItem2 = item;
        buttonRemove.setOnClickListener(v -> {
            dataSource.deleteIncome(finalItem2.getId());
            Toast.makeText(this, "Item removed", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });
    }


    private void onItemSelectedHandler(@NonNull AdapterView<?> adapterView, View view, int position, long id) {
        Adapter adapter = adapterView.getAdapter();
        String item = (String) adapter.getItem(position);
    }

//    public boolean isExistItem(IncomeItem item) {
//        ArrayList<IncomeItem> list = dataSource.getAll();
//        for (IncomeItem i : list) {
//            if (i.getName().toUpperCase() == item.getName().toUpperCase() && i.getMoney() == item.getMoney() && i.getState() == item.getState() && i.getCategory() == item.getCategory()) {
//                return true;
//            }
//        }
//        return false;
//    }
}

