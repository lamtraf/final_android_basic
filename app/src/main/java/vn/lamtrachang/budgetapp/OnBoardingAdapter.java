package vn.lamtrachang.budgetapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OnBoardingAdapter extends RecyclerView.Adapter<OnBoardingAdapter.OnBoardingViewHolder> {

    private static final int[] LAYOUTS = {
            R.layout.onboarding_page1,
            R.layout.onboarding_page2,
            R.layout.onboarding_page3
    };

    @NonNull
    @Override
    public OnBoardingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new OnBoardingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OnBoardingViewHolder holder, int position) {
        // No binding needed as each page has static content
    }

    @Override
    public int getItemCount() {
        return LAYOUTS.length;
    }

    @Override
    public int getItemViewType(int position) {
        return LAYOUTS[position];
    }

    static class OnBoardingViewHolder extends RecyclerView.ViewHolder {
        OnBoardingViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}