package vn.lamtrachang.budgetapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import vn.lamtrachang.budgetapp.placeholder.IncomeItem;
import vn.lamtrachang.budgetapp.placeholder.SQLiteHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
//         ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.home_screen), (v, insets) -> {
//             Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//             v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//             return insets;
//         });

       Intent intent =new Intent(this, HomeActivity.class);
       startActivity(intent);

         startActivity(new Intent(this, LineChartActivity.class));

    }
}