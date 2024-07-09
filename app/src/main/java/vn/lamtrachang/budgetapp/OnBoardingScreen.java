package vn.lamtrachang.budgetapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class OnBoardingScreen extends AppCompatActivity {
    private OnBoardingAdapter onBoardingAdapter;
    private LinearLayout layoutOnBoardingIndicators;
    private FloatingActionButton buttonOnBoardingAction;
    public int clickCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding_screen);

        layoutOnBoardingIndicators = findViewById(R.id.layoutOnboardingIndicators);
        buttonOnBoardingAction = findViewById(R.id.buttonOnBoardingAction);

        setOnboardingItems();
        ViewPager2 onBoardingViewPager = findViewById(R.id.viewPagerOnBoarding);
        onBoardingViewPager.setAdapter(onBoardingAdapter);

        setOnBoardingIndicators();
        setCurrentOnBoardingIndicator(0);

        onBoardingViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                setCurrentOnBoardingIndicator(position);
            }
        });

        buttonOnBoardingAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onBoardingViewPager.getCurrentItem() + 1 < onBoardingAdapter.getItemCount()) {
                    onBoardingViewPager.setCurrentItem(onBoardingViewPager.getCurrentItem() + 1);
                } else {
                    startActivity(new Intent(getApplicationContext(), LoginScreen.class));
                    finish();
                }
            }
        });
    }

    private void setOnBoardingIndicators() {
        ImageView[] indicators = new ImageView[onBoardingAdapter.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8, 0, 8, 0);
        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(getResources().getDrawable(R.drawable.onboarding_indicator_inactive));
            indicators[i].setLayoutParams(layoutParams);
            layoutOnBoardingIndicators.addView(indicators[i]);
        }
    }

    @SuppressLint("SetTextI18n")
    private void setCurrentOnBoardingIndicator(int index) {
        int childCount = layoutOnBoardingIndicators.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) layoutOnBoardingIndicators.getChildAt(i);
            if (i == index) {
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.onboarding_indicator_active));
            } else {
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.onboarding_indicator_inactive));
            }
        }
        if (index == onBoardingAdapter.getItemCount() - 1) {
            buttonOnBoardingAction.setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);
        } else {
            buttonOnBoardingAction.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        }
    }

    private void setOnboardingItems() {
        List<OnBoardingItem> onBoardingItems = new ArrayList<>();

        OnBoardingItem itemFirst = new OnBoardingItem();
        itemFirst.setTitle("Welcome to Budget App");
        itemFirst.setDescription("Manage your budget easily with Budget App");
        itemFirst.setImage(R.drawable.onboarding1);

        OnBoardingItem itemSecond = new OnBoardingItem();
        itemSecond.setTitle("Add your income and expenses");
        itemSecond.setDescription("Add your income and expenses to keep track of your budget");
        itemSecond.setImage(R.drawable.onboarding2);

        OnBoardingItem itemThird = new OnBoardingItem();
        itemThird.setTitle("Set your budget limit");
        itemThird.setDescription("Set your budget limit to keep track of your expenses");
        itemThird.setImage(R.drawable.onboarding3);

        onBoardingItems.add(itemFirst);
        onBoardingItems.add(itemSecond);
        onBoardingItems.add(itemThird);

        onBoardingAdapter = new OnBoardingAdapter(onBoardingItems);
    }
}