package com.sdk.sdklibrary.ui;

//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//
//import com.example.sdklibrary.R;
//import com.stripe.android.ApiResultCallback;
//import com.stripe.android.PaymentConfiguration;
//import com.stripe.android.PaymentIntentResult;
//import com.stripe.android.Stripe;
//import com.stripe.android.model.ConfirmPaymentIntentParams;
//import com.stripe.android.model.PaymentIntent;
//import com.stripe.android.model.PaymentMethodCreateParams;
//import com.stripe.android.view.CardInputWidget;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.IOException;
//import java.lang.ref.WeakReference;
//import java.util.Objects;
//
//import okhttp3.Call;
//import okhttp3.Callback;
//import okhttp3.MediaType;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.RequestBody;
//import okhttp3.Response;
//import okhttp3.ResponseBody;

//暂时不用，此处是SDK本地的Strip支付

//public class StripPaymentActivity extends AppCompatActivity {
//    private static final String TAG = "CheckoutActivity";
//    private static final String BACKEND_URL = "http://10.0.2.2:4242";
//
//    private String paymentIntentClientSecret;
//    private PaymentSheet paymentSheet;
//
//    private Button payButton;
//
//    private Stripe stripe;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        setContentView(R.layout.activity_checkout);
//
//        // Hook up the pay button
//        payButton = findViewById(R.id.payButton);
//        payButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onPayClicked(v);
//            }
//        });
//
//    }
//
//
//    private void showAlert(String title, @Nullable String message) {
//        runOnUiThread(() -> {
//            AlertDialog dialog = new AlertDialog.Builder(this)
//                    .setTitle(title)
//                    .setMessage(message)
//                    .setPositiveButton("Ok", null)
//                    .create();
//            dialog.show();
//        });
//    }
//
//    private void showToast(String message) {
//        runOnUiThread(() -> Toast.makeText(this, message, Toast.LENGTH_LONG).show());
//    }
//
//
//
//    private void onPayClicked(View view) {
//        CardInputWidget cardInputWidget = findViewById(R.id.cardInputWidget);
//        PaymentMethodCreateParams params = cardInputWidget.getPaymentMethodCreateParams();
//        if (params != null) {
//            if (paymentIntentClientSecret == null) {
//                return;
//            }
//            ConfirmPaymentIntentParams confirmParams = ConfirmPaymentIntentParams
//                    .createWithPaymentMethodCreateParams(params, paymentIntentClientSecret);
//
//            String stripePublishableKey = PaymentConfiguration.getInstance(this).getPublishableKey();
////            LogUtils.d("stripePublishableKey==" + stripePublishableKey);
//            // Configure the SDK with your Stripe publishable key so that it can make requests to the Stripe API
//            Stripe stripe = new Stripe(
//                    getApplicationContext(),
//                    Objects.requireNonNull(stripePublishableKey)
//            );
//
//            stripe.confirmPayment(this, confirmParams);
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        // Handle the result of stripe.confirmPayment
//        stripe.onPaymentResult(requestCode, data, new PaymentResultCallback(this));
//    }
//
//    private static final class PaymentResultCallback
//            implements ApiResultCallback<PaymentIntentResult> {
//        @NonNull
//        private final WeakReference<StripPaymentActivity> activityRef;
//
//        PaymentResultCallback(@NonNull StripPaymentActivity activity) {
//            activityRef = new WeakReference<>(activity);
//        }
//
//        @Override
//        public void onSuccess(@NonNull PaymentIntentResult result) {
//            final StripPaymentActivity activity = activityRef.get();
//            if (activity == null) {
//                return;
//            }
//
//            PaymentIntent paymentIntent = result.getIntent();
//            PaymentIntent.Status status = paymentIntent.getStatus();
//            if (status == PaymentIntent.Status.Succeeded) {
//                // Payment completed successfully
//               /* Gson gson = new GsonBuilder().setPrettyPrinting().create();
//                String toJson = gson.toJson(paymentIntent);
//                activity.displayAlert(
//                        activity.getString(R.string.payment_successful),
//                        "",
//                        true
//                );*/
//
////                activity.showDialogPaySuccess();
//
//            } else if (status == PaymentIntent.Status.RequiresPaymentMethod) {
//                // Payment failed – allow retrying using a different payment method
//
////                activity.displayAlert(
////                        activity.getString(R.string.payment_failed),
////                        Objects.requireNonNull(paymentIntent.getLastPaymentError()).getMessage(),
////                        false
////                );
//            }
//        }
//
//        @Override
//        public void onError(@NonNull Exception e) {
//            final StripPaymentActivity activity = activityRef.get();
//            if (activity == null) {
//                return;
//            }
//
//            // Payment request failed – allow retrying using the same payment method
////            activity.displayAlert(activity.getString(R.string.payment_failed), e.getMessage(), false);
//        }
//    }
//
//
//
//}


