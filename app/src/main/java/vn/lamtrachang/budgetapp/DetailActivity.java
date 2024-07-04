package vn.lamtrachang.budgetapp;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import vn.lamtrachang.budgetapp.placeholder.IncomeItem;

public class DetailActivity extends AppCompatActivity {
    private Spinner spinnerState;
    private Spinner spinnerType;
    private EditText editTextName;
    private EditText editTextMoney;
    private EditText editTextDetail;
    private Context mContext;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
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
            return;
        }

        IncomeItem item = (IncomeItem) bundle.get("item");
        editTextName = findViewById(R.id.task_name);
        editTextMoney = findViewById(R.id.task_money);
        editTextDetail = findViewById(R.id.task_detail);

        editTextName.setText(item.getName());
        editTextMoney.setText(item.getMoney());
        editTextDetail.setText(item.getDetail());

        LinearLayout buttonSave = findViewById(R.id.submit);
        buttonSave.setOnClickListener(v -> {
            IncomeItem temp = new IncomeItem(editTextName.getText().toString(), spinnerState.getSelectedItemPosition(), spinnerType.getSelectedItemPosition(), editTextMoney.getText().toString(), editTextDetail.getText().toString());
            Intent intent = new Intent(mContext, HomeActivity.class);
            Bundle bd = new Bundle();
            bd.putSerializable("item", temp);
            intent.putExtras(bd);
            mContext.startActivity(intent);
        });

        ImageView buttonCancel = findViewById(R.id.button_back);
        buttonCancel.setOnClickListener(v -> finish());

    }


    private void onItemSelectedHandler(AdapterView<?> adapterView, View view, int position, long id) {
        Adapter adapter = adapterView.getAdapter();
        String item = (String) adapter.getItem(position);
    }

}