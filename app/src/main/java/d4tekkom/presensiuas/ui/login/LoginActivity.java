package d4tekkom.presensiuas.ui.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import d4tekkom.presensiuas.PresensiApplication;
import d4tekkom.presensiuas.R;
import d4tekkom.presensiuas.ui.main.MainActivity;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    @Inject
    LoginContract.Presenter mPresenter;

    @BindView(R.id.img_icon)
    ImageView imgIcon;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.input_password)
    TextInputLayout inputPassword;
    @BindView(R.id.input_nip)
    TextInputLayout inputNip;

    ProgressDialog mProgress;
    @BindView(R.id.edt_password)
    EditText edtPassword;
    @BindView(R.id.edt_nip)
    EditText edtNip;

    public static Intent getStartedIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        DaggerLoginComponent.builder()
                .applicationComponent(((PresensiApplication) getApplication()).getApplicationComponent())
                .loginModule(new LoginModule(this))
                .build()
                .inject(this);
    }

    @OnClick(R.id.btn_login)
    public void onViewClicked() {
        mPresenter.onLogin(edtNip.getText().toString(),
                edtPassword.getText().toString());
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setupView() {
        mProgress = new ProgressDialog(this);
        mProgress.setMessage(" Loading");
        mProgress.setIndeterminate(true);
    }

    @Override
    public void showProgressDialog() {
        mProgress.show();
    }

    @Override
    public void dismissProgressDialog() {
        mProgress.dismiss();
    }

    @Override
    public void startMainActivity() {
        startActivity(MainActivity.getStartedIntent(this));
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.subscribe();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unsubscribe();
    }
}
