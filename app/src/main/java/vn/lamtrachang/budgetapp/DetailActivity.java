package vn.lamtrachang.budgetapp;

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
        if (bundle == null){
            return ;
        }
        IncomeItem item = (IncomeItem) bundle.get("item");
        editTextName = findViewById(R.id.task_name);
        editTextMoney = findViewById(R.id.task_money);
        editTextDetail = findViewById(R.id.task_detail);

        spinnerState.setSelection(item.getState());
        spinnerType.setSelection(item.getType());
        editTextName.setText(item.getName());
        editTextMoney.setText(item.getMoney());
        editTextDetail.setText(item.getDetail());

        LinearLayout buttonSave = findViewById(R.id.submit);
        buttonSave.setOnClickListener(v -> {
            String name = String.valueOf(editTextName.getText());
            String money = editTextMoney.getText().toString();
            String detail = editTextDetail.getText().toString();
            int state = spinnerState.getSelectedItemPosition();
            int type = spinnerType.getSelectedItemPosition();
            String time = String.valueOf(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm - dd.M yyyy")));
            IncomeItem newItem = new IncomeItem(name, money, detail, type, state, time);
//            dataSource.addIncome(newItem);
            dataSource.updateIncome(item,newItem);
            Toast.makeText(this, "Item updated", Toast.LENGTH_LONG).show();
            Intent intent =new Intent(this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();



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