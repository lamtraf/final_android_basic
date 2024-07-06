package vn.lamtrachang.budgetapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class OnBoardingScreen extends AppCompatActivity {

    private ViewPager2 viewPager;
    private FloatingActionButton fabNext;
    private OnBoardingAdapter adapter;
    private int clickCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding_screen);

        viewPager = findViewById(R.id.viewPager);
        fabNext = findViewById(R.id.fabNext);
        adapter = new OnBoardingAdapter();
        viewPager.setAdapter(adapter);

        fabNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int currentItem = viewPager.getCurrentItem();
                    if (currentItem < adapter.getItemCount() - 1) {
                        viewPager.setCurrentItem(currentItem + 1);
                    }

                    clickCount++;
                    if (clickCount == 3) {
                        startLoginScreen();
                    }
                }
            });
        }

        private void startLoginScreen() {
            Intent intent = new Intent(OnBoardingScreen.this, LoginScreen.class);
            startActivity(intent);
            finish();
        }
    }
