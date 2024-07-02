//
//package vn.lamtrachang.budgetapp;
//
//import android.app.Activity;
//import android.app.AppOpsManager;
//import android.app.Dialog;
//import android.app.PendingIntent;
//import android.appwidget.AppWidgetManager;
//import android.content.ActivityNotFoundException;
//import android.content.ComponentName;
//import android.content.Context;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.content.res.Configuration;
//import android.graphics.Bitmap;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.drawable.Drawable;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Environment;
//import android.provider.DocumentsContract;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.View;
//import android.view.WindowManager;
//import android.widget.Toast;
//
//import androidx.activity.result.ActivityResultLauncher;
//import androidx.activity.result.contract.ActivityResultContracts;
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.annotation.RequiresApi;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.biometric.BiometricManager;
//import androidx.biometric.BiometricPrompt;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//import androidx.core.content.FileProvider;
//import androidx.lifecycle.ViewModel;
//import androidx.lifecycle.ViewModelProvider;
//
//import com.google.android.gms.auth.api.signin.GoogleSignIn;
//import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
//import com.google.android.gms.auth.api.signin.GoogleSignInClient;
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
//import com.google.android.gms.common.api.ApiException;
//import com.google.android.gms.tasks.Task;
//import com.google.android.material.datepicker.MaterialDatePicker;
//import com.google.android.material.timepicker.MaterialTimePicker;
//import com.google.android.material.timepicker.TimeFormat;
//import com.ivy.IvyNavGraph;
//import com.ivy.design.api.IvyUI;
//import com.ivy.domain.RootScreen;
//import com.ivy.home.customerjourney.CustomerJourneyCardsProvider;
//import com.ivy.legacy.Constants;
//import com.ivy.legacy.IvyWalletCtx;
//import com.ivy.legacy.appDesign;
//import com.ivy.legacy.utils.activityForResultLauncher;
//import com.ivy.legacy.utils.convertLocalToUTC;
//import com.ivy.legacy.utils.sendToCrashlytics;
//import com.ivy.legacy.utils.simpleActivityForResultLauncher;
//import com.ivy.legacy.utils.timeNowLocal;
//import com.ivy.navigation.Navigation;
//import com.ivy.navigation.NavigationRoot;
//import com.ivy.ui.R;
//import com.ivy.wallet.ui.applocked.AppLockedScreen;
//import com.ivy.widget.balance.WalletBalanceWidgetReceiver;
//import com.ivy.widget.transaction.AddTransactionWidget;
//import com.ivy.widget.transaction.AddTransactionWidgetCompact;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.OutputStream;
//import java.time.LocalDate;
//import java.time.LocalTime;
//import java.time.ZoneId;
//import java.time.format.DateTimeFormatter;
//import java.util.Locale;
//
//public class RootActivity extends AppCompatActivity implements RootScreen {
//
//    private IvyWalletCtx ivyContext;
//    private Navigation navigation;
//    private CustomerJourneyCardsProvider customerJourneyLogic;
//    private ActivityResultLauncher<GoogleSignInClient> googleSignInLauncher;
//    private ActivityResultLauncher<String> createFileLauncher;
//    private ActivityResultLauncher<Void> openFileLauncher;
//    private ViewModel viewModel;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        setupActivityForResultLaunchers();
//
//        // Make the app drawing area fullscreen (draw behind status and nav bars)
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//
//        setupDatePicker();
//        setupTimePicker();
//
//        AddTransactionWidget.updateBroadcast(this);
//        AddTransactionWidgetCompact.updateBroadcast(this);
//        WalletBalanceWidgetReceiver.updateBroadcast(this);
//
//        viewModel = new ViewModelProvider(this).get(ViewModel.class);
//
//        setContentView(R.layout.activity_root);
//
//        LaunchedEffect(isSystemInDarkTheme()) {
//            viewModel.start(isSystemInDarkTheme(), getIntent());
//        }
//
//        val appLocked by viewModel.appLocked.collectAsState()
//        when (appLocked) {
//            null -> {
//                // display nothing
//            }
//
//            true -> {
//                IvyUI(
//                        design = appDesign(ivyContext)
//                ) {
//                    AppLockedScreen(
//                            onShowOSBiometricsModal = {
//                                    authenticateWithOSBiometricsModal(
//                                            biometricPromptCallback = viewModel.handleBiometricAuthResult()
//                                    )
//                            },
//                            onContinueWithoutAuthentication = {
//                                    viewModel.unlockApp()
//                            }
//                    )
//                }
//            }
//
//            false -> {
//                NavigationRoot(navigation = navigation) { screen ->
//                        IvyUI(
//                                design = appDesign(ivyContext),
//                                includeSurface = screen?.isLegacy ?: true
//                    ) {
//                        IvyNavGraph(screen)
//                    }
//                }
//            }
//        }
//    }
//
//    private companion object {
//        private const val MILLISECONDS_IN_DAY = 24 * 60 * 60 * 1000
//    }
//
//    private fun setupDatePicker() {
//        ivyContext.onShowDatePicker = { minDate, maxDate, initialDate, onDatePicked ->
//                val datePicker =
//                MaterialDatePicker.Builder.datePicker()
//                        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
//                        .build()
//                datePicker.show(getSupportFragmentManager(), "datePicker")
//                datePicker.addOnPositiveButtonClickListener {
//                onDatePicked(LocalDate.ofEpochDay(it / MILLISECONDS_IN_DAY))
//        }
//
//        if (minDate != null) {
//            datePicker.addOnCancelListener {
//                onDatePicked(minDate)
//            }
//        }
//
//        if (maxDate != null) {
//            datePicker.addOnCancelListener {
//                onDatePicked(maxDate)
//            }
//        }
//
//        if (initialDate != null) {
//            datePicker.addOnCancelListener {
//                onDatePicked(initialDate)
//            }
//        }
//        }
//    }
//
//    private fun setupTimePicker() {
//        ivyContext.onShowTimePicker = { onTimePicked ->
//                val nowLocal = timeNowLocal()
//                val picker =
//                MaterialTimePicker.Builder()
//                        .setTimeFormat(TimeFormat.CLOCK_12H)
//                        .setHour(nowLocal.hour)
//                        .setMinute(nowLocal.minute)
//                        .build()
//                picker.show(getSupportFragmentManager(), "timePicker")
//                picker.addOnPositiveButtonClickListener {
//                onTimePicked(LocalTime.of(picker.hour, picker.minute).convertLocalToUTC().withSecond(0))
//        }
//        }
//    }
//
//    private fun setupActivityForResultLaunchers() {
//        googleSignInLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), this::onGoogleSignInResult);
//
//        createFileLauncher = registerForActivityResult(new ActivityResultContracts.CreateDocument(), this::onFileCreated);
//
//        openFileLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), this::onFileOpened);
//    }
//
//    private void onGoogleSignInResult(ActivityResult result) {
//        if (result.getResultCode() == Activity.RESULT_OK) {
//            val data: Intent? = result.getData()
//            try {
//                val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data);
//                val account: GoogleSignInAccount = task.getResult(ApiException::class.java);
//                val idToken = account.getIdToken();
//                Log.d("idToken", "idToken = $idToken");
//
//                onGoogleSignInIdTokenResult(idToken);
//            } catch (ApiException e) {
//                e.sendToCrashlytics("GOOGLE_SIGN_IN - registerGoogleSignInContract(): ApiException");
//                e.printStackTrace();
//                onGoogleSignInIdTokenResult(null);
//            }
//        }
//    }
//
//    private void onFileCreated(Uri uri) {
//        onFileCreatedCallback.invoke(uri);
//    }
//
//    private void onFileOpened(Uri uri) {
//        onFileOpenedCallback.invoke(uri);
//    }
//
//    private void onGoogleSignInIdTokenResult(String idToken) {
//        // Handle the ID token result
//    }
//
//    private void createFile(String fileName) {
//        val intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
//        intent.addCategory(Intent.CATEGORY_OPENABLE);
//        intent.setType("application/csv");
//        intent.putExtra(Intent.EXTRA_TITLE, fileName);
//
//        // Optionally, specify a URI for the directory that should be opened in
//        // the system file picker before your app creates the document.
//        intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toURI());
//
//        createFileLauncher.launch(intent);
//    }
//
//    private void openFile() {
//        val intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
//        intent.addCategory(Intent.CATEGORY_OPENABLE);
//        intent.setType("*/*");
//
//        openFileLauncher.launch(intent);
//    }
//
//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        if (viewModel.isAppLockEnabled() && !hasFocus) {
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
//        } else {
//            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_SECURE);
//        }
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (viewModel.isAppLockEnabled()) {
//            viewModel.checkUserInactiveTimeStatus();
//        }
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        if (viewModel.isAppLockEnabled()) {
//            viewModel.startUserInactiveTimeCounter();
//        }
//    }
//
//    private void authenticateWithOSBiometricsModal(BiometricPrompt.AuthenticationCallback biometricPromptCallback) {
//        val executor = ContextCompat.getMainExecutor(this);
//        val biometricPrompt = new BiometricPrompt(this, executor, biometricPromptCallback);
//
//        val promptInfo = new BiometricPrompt.PromptInfo.Builder()
//                .setTitle(getString(R.string.authentication_required))
//                .setSubtitle(getString(R.string.authentication_required_description))
//                .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_WEAK or BiometricManager.Authenticators.DEVICE_CREDENTIAL)
//                .setConfirmationRequired(false)
//                .build();
//
//        biometricPrompt.authenticate(promptInfo);
//    }
//
//    @Override
//    public void onBackPressed() {
//        if (viewModel.isAppLocked()) {
//            super.onBackPressed();
//        } else {
//            if (!navigation.onBackPressed()) {
//                super.onBackPressed();
//            }
//        }
//    }
//
//    @Override
//    public void openUrlInBrowser(String url) {
//        try {
//            val browserIntent = new Intent(Intent.ACTION_VIEW);
//            browserIntent.setData(Uri.parse(url));
//            startActivity(browserIntent);
//        } catch (Exception e) {
//            e.printStackTrace();
//            e.sendToCrashlytics("Cannot open URL in browser, intent not supported.");
//            Toast.makeText(this, "No browser app found. Visit manually: $url", Toast.LENGTH_LONG).show();
//        }
//    }
//
//    @Override
//    public void shareIvyWallet() {
//        val share = Intent.createChooser(
//                Intent().apply {
//            action = Intent.ACTION_SEND
//            putExtra(Intent.EXTRA_TEXT, Constants.URL_IVY_WALLET_GOOGLE_PLAY)
//            type = "text/plain"
//        },
//        null
//        );
//        startActivity(share);
//    }
//
//    @Override
//    public void openGooglePlayAppPage(String appId) {
//        try {
//            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$appId")));
//        } catch (ActivityNotFoundException e) {
//            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$appId")));
//        }
//    }
//
//    @Override
//    public void shareCSVFile(Uri fileUri) {
//        val intent = Intent.createChooser(
//                Intent().apply {
//            action = Intent.ACTION_SEND
//            putExtra(Intent.EXTRA_STREAM, fileUri)
//            type = "text/csv"
//        },
//        null
//        );
//        startActivity(intent);
//    }
//
//    @Override
//    public void shareZipFile(Uri fileUri) {
//        val intent = Intent.createChooser(
//                Intent().apply {
//            action = Intent.ACTION_SEND
//            putExtra(Intent.EXTRA_STREAM, fileUri)
//            type = "application/zip"
//        },
//        null
//        );
//        startActivity(intent);
//    }
//
//    @Override
//    public boolean isDebug() {
//        return BuildConfig.DEBUG;
//    }
//
//    @Override
//    public String getBuildVersionName() {
//        return BuildConfig.VERSION_NAME;
//    }
//
//    @Override
//    public int getBuildVersionCode() {
//        return BuildConfig.VERSION_CODE;
//    }
//
//    @Override
//    public void reviewIvyWallet(boolean dismissReviewCard) {
//        // TODO: Implement reviewIvyWallet
//    }
//
//    @Override
//    public <T> void pinWidget(Class<T> widget) {
//        val appWidgetManager: AppWidgetManager = getSystemService(AppWidgetManager.class);
//        val addTransactionWidget = ComponentName(this, widget);
//        appWidgetManager.requestPinAppWidget(addTransactionWidget, null, null);
//    }
//}
//
