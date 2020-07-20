package com.example.angkot;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rengwuxian.materialedittext.MaterialEditText;

import dmax.dialog.SpotsDialog;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {
    Button mDriver, mCustomer;
    RelativeLayout rootLayout;
    FirebaseAuth mAuth;
    private final static int PERMISSION = 1000;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Arkhip_font.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());
        setContentView(R.layout.activity_main);

        mDriver = (Button) findViewById(R.id.driver);
        mCustomer = (Button) findViewById(R.id.customer);
        rootLayout = (RelativeLayout) findViewById(R.id.rootLayout);
        mAuth = FirebaseAuth.getInstance();

        mDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoginDialog();
            }
        });

        mCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoginDialog1();
            }
        });
    }

    private void showLoginDialog() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("DRIVER");
        dialog.setMessage("Tolong Gunakan Email Untuk Masuk");



        LayoutInflater inflater = LayoutInflater.from(this);
        View login_layout = inflater.inflate(R.layout.activity_driver_login, null);


        final MaterialEditText mEmail = login_layout.findViewById(R.id.editEmail);
        final MaterialEditText mPass = login_layout.findViewById(R.id.editPass);


        dialog.setView(login_layout);

        //set button
        dialog.setPositiveButton("SIGN IN", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //set disable button jika sedang prosessing
                //cek validasi
                if (TextUtils.isEmpty(mEmail.getText().toString())) {
                    Snackbar.make(rootLayout, "Tolong Masukkan Alamat Email", Snackbar.LENGTH_SHORT)
                            .show();
                    return;
                }
                if (TextUtils.isEmpty(mPass.getText().toString())) {
                    Snackbar.make(rootLayout, "Tolong Masukkan Kata Sandi", Snackbar.LENGTH_SHORT)
                            .show();
                    return;
                }
                if (mPass.getText().toString().length() < 6) {
                    Snackbar.make(rootLayout, "Kata Sandi Terlalu Pendek !!!", Snackbar.LENGTH_SHORT)
                            .show();
                    return;
                }

                final AlertDialog waitingDialog = new SpotsDialog(MainActivity.this);
                waitingDialog.show();
                //login
                mAuth.signInWithEmailAndPassword(mEmail.getText().toString(), mPass.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                waitingDialog.dismiss();
                                startActivity(new Intent(MainActivity.this, DriverMapActivity.class));
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                waitingDialog.dismiss();
                                Snackbar.make(rootLayout, "Gagal Login " + e.getMessage(), Snackbar.LENGTH_SHORT)
                                        .show();
                                //Aktif Button
                                mDriver.setEnabled(true);
                            }
                        });
            }

        });



        dialog.show();
    }
    private void showLoginDialog1() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("CUSTOMER");
        dialog.setMessage("Tolong Gunakan Email Untuk Masuk");

        LayoutInflater inflater = LayoutInflater.from(this);
        View login_layout = inflater.inflate(R.layout.activity_customer_login, null);

        final MaterialEditText mEmail = login_layout.findViewById(R.id.editEmail);
        final MaterialEditText mPass = login_layout.findViewById(R.id.editPass);
        dialog.setView(login_layout);

        //set button
        dialog.setPositiveButton("SIGN IN", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //set disable button jika sedang prosessing
                //cek validasi
                if (TextUtils.isEmpty(mEmail.getText().toString())) {
                    Snackbar.make(rootLayout, "Tolong Masukkan Alamat Email", Snackbar.LENGTH_SHORT)
                            .show();
                    return;
                }
                if (TextUtils.isEmpty(mPass.getText().toString())) {
                    Snackbar.make(rootLayout, "Tolong Masukkan Kata Sandi", Snackbar.LENGTH_SHORT)
                            .show();
                    return;
                }
                if (mPass.getText().toString().length() < 6) {
                    Snackbar.make(rootLayout, "Kata Sandi Terlalu Pendek !!!", Snackbar.LENGTH_SHORT)
                            .show();
                    return;
                }

                final AlertDialog waitingDialog = new SpotsDialog(MainActivity.this);
                waitingDialog.show();
                //login
                mAuth.signInWithEmailAndPassword(mEmail.getText().toString(), mPass.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                waitingDialog.dismiss();
                                startActivity(new Intent(MainActivity.this, CustomerMapActivity.class));
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                waitingDialog.dismiss();
                                Snackbar.make(rootLayout, "Gagal Login " + e.getMessage(), Snackbar.LENGTH_SHORT)
                                        .show();
                                //Aktif Button
                                mCustomer.setEnabled(true);
                            }
                        });
            }

        });
        dialog.setNegativeButton("REGISTER", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //set disable button jika sedang prosessing
                //cek validasi
                if (TextUtils.isEmpty(mEmail.getText().toString())) {
                    Snackbar.make(rootLayout, "Tolong Masukkan Alamat Email", Snackbar.LENGTH_SHORT)
                            .show();
                    return;
                }
                if (TextUtils.isEmpty(mPass.getText().toString())) {
                    Snackbar.make(rootLayout, "Tolong Masukkan Kata Sandi", Snackbar.LENGTH_SHORT)
                            .show();
                    return;
                }
                if (mPass.getText().toString().length() < 6) {
                    Snackbar.make(rootLayout, "Kata Sandi Terlalu Pendek !!!", Snackbar.LENGTH_SHORT)
                            .show();
                    return;
                }

                final AlertDialog waitingDialog = new SpotsDialog(MainActivity.this);
                waitingDialog.show();
                //login
                mAuth.createUserWithEmailAndPassword(mEmail.getText().toString(), mPass.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                waitingDialog.dismiss();
                                startActivity(new Intent(MainActivity.this, CustomerMapActivity.class));
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                waitingDialog.dismiss();
                                Snackbar.make(rootLayout, "Gagal Registrasi " + e.getMessage(), Snackbar.LENGTH_SHORT)
                                        .show();
                                //Aktif Button
                                mCustomer.setEnabled(true);
                            }
                        });
            }
        });

        dialog.show();
    }

}
