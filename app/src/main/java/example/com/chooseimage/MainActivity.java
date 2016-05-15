package example.com.chooseimage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.etEmail)
    EditText etEmail;
    @Bind(R.id.etPassword)
    EditText etPassword;
    @Bind(R.id.btSubmit)
    Button btSubmit;
    String USER_ID;
    SharedPreferences sharedpreferences;
    public static final String LoginPreference = "LoginPreference";
//    private MainApplication objectPreference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
       }
//        objectPreference = (MainApplication) getApplication();
//        ComplexPreferences complexPreferences = objectPreference.getComplexPreference();
//        User user = complexPreferences.getObject("user", User.class);
//        if(user != null){
//
//        }
//        etEmail=(EditText)findViewById(R.id.etEmail);
//        etPassword=(EditText)findViewById(R.id.etPassword);
//        btSubmit=(Button)findViewById(R.id.btSubmit);
//        btSubmit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                doLogin(etEmail.getText().toString(), etPassword.getText().toString());

//            }
//        });

    @OnClick(R.id.btSubmit)
    public void submit(View view)
    {
        if (ConnectivityUtils.isConnected(this)) {
//            if (checkValidation ())
                doLogin(etEmail.getText().toString(), etPassword.getText().toString());
        }else{
//            Utils.showToast(getApplicationContext(), getString(R.string.ERR_NETWORK));
        }
    }
    private void doLogin(String email_id, String password) {
//        show_dialog();
        RestAdapter restAdapter = (RestAdapter) new RestAdapter.Builder().setEndpoint(getResources().getString(R.string.master_url)).build();
        final MyAPI post = restAdapter.create(MyAPI.class);
        post.login(email_id, password, new retrofit.Callback<User>() {
            @Override
            public void success(User res, Response response) {
                if (!res.getError()) {
//                    dismiss_dialog();
                    Toast.makeText(MainActivity.this, res.getMessage(),
                            Toast.LENGTH_SHORT).show();

                    sharedpreferences = getSharedPreferences(LoginPreference,
                            Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("user_id", String.valueOf(res.getUser_id()));
                    editor.commit();
                    Log.d("Check",""+res);
//                    ComplexPreferences complexPrefenreces = objectPreference.getComplexPreference();
//                    if (complexPrefenreces != null) {
//                        complexPrefenreces.putObject("user", res);
//                        complexPrefenreces.commit();
//                        session.set_login_status("2");
                        Intent oIntent = new Intent(MainActivity.this, UploadImage.class);
                        startActivity(oIntent);
                        finish();
//                    } else {
//                        Log.d("user", "Preference is null");
//                    }
                } else {
//                    dismiss_dialog();
                    Toast.makeText(MainActivity.this, res.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
//                dismiss_dialog();
                Log.d("Error", error.toString());
                Toast.makeText(MainActivity.this, R.string.retrofit_error,
                        Toast.LENGTH_SHORT).show();
            }
        });

    }
//    private void doLogin(String email_id, String password) {
//        RestAdapter restAdapter = (RestAdapter) new RestAdapter.Builder().setEndpoint(getResources().getString(R.string.master_url)).build();
//        final MyAPI post = restAdapter.create(MyAPI.class);
//        post.login(email_id, password, new retrofit.Callback<User>() {
//            @Override
//            public void success(User res, Response response) {
//                Log.d("response",""+response);
//                if (!res.getError()) {
////                    dismiss_dialog();
//                    Toast.makeText(MainActivity.this, res.getMessage(),
//                            Toast.LENGTH_SHORT).show();
//
//                    ComplexPreferences complexPrefenreces = objectPreference.getComplexPreference();
//                    if (complexPrefenreces != null) {
//                        complexPrefenreces.putObject("user", res);
//                        complexPrefenreces.commit();
////                        session.set_login_status("2");
//                        Intent oIntent = new Intent(MainActivity.this, UploadImage.class);
//                        startActivity(oIntent);
//                        finish();
////                    } else {
////                        Log.d("user", "Preference is null");
////                    }
//                } else {
////                    dismiss_dialog();
//                    Toast.makeText(MainActivity.this, res.getMessage(),
//                            Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
////                dismiss_dialog();
//                Log.d("Error", ""+error.toString());
//                Toast.makeText(MainActivity.this, R.string.retrofit_error,
//                        Toast.LENGTH_SHORT).show();
//            }
//          });
//
//    }
//
//
//}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
