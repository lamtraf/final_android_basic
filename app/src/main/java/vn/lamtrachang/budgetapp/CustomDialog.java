package vn.lamtrachang.budgetapp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class CustomDialog extends Dialog {
    public Context context;
    private Button buttonOK;
    private Button buttonCancel;
    private DialogInterface.OnClickListener onClick;
    public CustomDialog(@NonNull Context context, DialogInterface.OnClickListener onClick) {
        super(context);
        this.onClick = onClick;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.yes_no_dialog);

//        this.textView = findViewById(R.id.title_dialog);
        this.buttonOK = (Button) findViewById(R.id.button_ok);
        this.buttonCancel  = (Button) findViewById(R.id.button_cancel);

        this.buttonOK .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onClick(CustomDialog.this, DialogInterface.BUTTON_POSITIVE);
                dismiss();
                
            }
        });
        this.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onClick(CustomDialog.this, DialogInterface.BUTTON_NEGATIVE);
                dismiss();
             
            }
        });
    }

}