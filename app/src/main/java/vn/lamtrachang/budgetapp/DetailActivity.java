package vn.lamtrachang.budgetapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
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

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import vn.lamtrachang.budgetapp.placeholder.IncomeItem;
import vn.lamtrachang.budgetapp.placeholder.SQLiteHelper;

public class DetailActivity extends Activity{ //AppCompatActivity {
    private Spinner spinnerState;
    private Spinner spinnerType;
    private EditText editTextName;
    private EditText editTextMoney;
    private EditText editTextDetail;
    private Context mContext;
    private SQLiteHelper dataSource= new SQLiteHelper(this);
    private ChipGroup chipGroup;


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

        

        Bundle bundle = getIntent().getExtras();
        IncomeItem item = (IncomeItem) (bundle != null ? bundle.get("item") : null);
        editTextName = findViewById(R.id.task_name);
        editTextMoney = findViewById(R.id.task_money);
        editTextDetail = findViewById(R.id.task_detail);

        spinnerState.setSelection(item.getState());
        spinnerType.setSelection(item.getType());
        editTextName.setText(item.getName());
        editTextMoney.setText(item.getMoney());
        editTextDetail.setText(item.getDetail());
        chipGroup.getChildAt(item.getCategory()).setSelected(true);

        chipGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId != item.getCategory()) {
                chipGroup.getChildAt(item.getCategory()).setSelected(false);
            }
        });



        LinearLayout buttonSave = findViewById(R.id.submit);
        buttonSave.setOnClickListener(v -> {
            int category = 0;
            String name = editTextName.getText().toString().trim();
            String money = editTextMoney.getText().toString();
            String detail = editTextDetail.getText().toString();
            int state = spinnerState.getSelectedItemPosition();
            int type = spinnerType.getSelectedItemPosition();
            for (int i=0; i<chipGroup.getChildCount();i++){
                Chip chip = (Chip)chipGroup.getChildAt(i);
                if (chip.isChecked()){
                    category = i;
                }
            }
            if (name.isEmpty()|| name.isBlank())
            {
                editTextName.setError("Task name is required");
                editTextName.requestFocus();
                return;
            }
            if (money.isEmpty())
            {
                editTextMoney.setError("Task money is required");
                editTextName.requestFocus();
                return;
            }

            if(item.getName()== null)
            {
                String time = String.valueOf(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm - dd.M")));
                IncomeItem newItem = new IncomeItem(name, money, detail, type, state, time, category);
                dataSource.addIncome(newItem);
            Toast.makeText(this, "Item added", Toast.LENGTH_LONG).show();
            Intent intent =new Intent(this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            }
            else
            {
                String time = item.getTime();
                IncomeItem newItem = new IncomeItem(name, money, detail, type, state, time, category);
                dataSource.updateIncome(item,newItem);
                Toast.makeText(this, "Item updated", Toast.LENGTH_LONG).show();
                Intent intent =new Intent(this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }

        });

        ImageView buttonCancel = findViewById(R.id.button_back);
        buttonCancel.setOnClickListener(v -> finish());

        ImageView buttonRemove = findViewById(R.id.button_remove);
        buttonRemove.setOnClickListener(v -> {
            dataSource.deleteIncome(item.getId());
            Toast.makeText(this, "Item removed", Toast.LENGTH_LONG).show();
            Intent intent =new Intent(this, HomeActivity.class);
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

}