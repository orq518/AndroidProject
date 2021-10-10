package com.fxkj.huabei.ui.activity;

import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.DefaultAudience;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.fxkj.huabei.R;
import com.fxkj.huabei.ui.app.AppActivity;
import com.fxkj.huabei.ui.dialog.MessageDialog;
import com.fxkj.huabei.utils.Constant;
import com.fxkj.huabei.utils.LogUtil;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.hjq.base.BaseDialog;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

public final class LoginActivity extends AppActivity implements GoogleApiClient.OnConnectionFailedListener {

    private static final int RC_SIGN_IN_GOOGLE = 9001;
    @BindView(R.id.et_email)
    AppCompatEditText etEmail;
    @BindView(R.id.et_password)
    AppCompatEditText etPassword;
    GoogleSignInClient mGoogleSignInClient;
    CallbackManager mCallbackManager;
    GoogleApiAvailability googleApiAvailability;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login_layout;
    }

    @Override
    protected void initView() {
        initGoogleLogin();
        initFBSDK();
    }

    @Override
    protected void initData() {
    }

    @Override
    public void onStart() {
        super.onStart();
        int avaiable = isGoogleServiceAvailable();
        LogUtil.d("google服务是否可用：" + avaiable);
        checkGoogleLoginAccount();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mCallbackManager != null) {
            mCallbackManager.onActivityResult(requestCode, resultCode, data);//FB登录回调的
        }
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN_GOOGLE) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void updateUI(GoogleSignInAccount account) {
        LogUtil.d("####account:" + account);
        if (account != null) {
            LogUtil.d("####account-getDisplayName:" + account.getDisplayName());
            LogUtil.d("####account-getEmail:" + account.getEmail());
            LogUtil.d("####account-getFamilyName:" + account.getFamilyName());
            LogUtil.d("####account-getGivenName:" + account.getGivenName());
            LogUtil.d("####account-getId:" + account.getId());
            LogUtil.d("####account-getPhotoUrl():" + account.getPhotoUrl());
            Constant.userName = account.getDisplayName();
            Constant.userPhotoUrl = Objects.requireNonNull(account.getPhotoUrl()).toString();
            new MessageDialog.Builder(this)
                    .setTitle("已登录google")
                    .setMessage(account.getDisplayName() + "\n" + account.getEmail())
                    .setConfirm("退出登录")
                    .setCancel("关闭")
                    .setCancelable(false)
                    .setListener(new MessageDialog.OnListener() {
                        @Override
                        public void onConfirm(BaseDialog dialog) {
                            signOutGoogle();
                        }

                        @Override
                        public void onCancel(BaseDialog dialog) {

                        }
                    })
                    .show();
//            mStatusTextView.setText(getString(R.string.signed_in_fmt, account.getDisplayName()));
//
//            findViewById(R.id.sign_in_button).setVisibility(View.GONE);
//            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.VISIBLE);
        } else {
//            mStatusTextView.setText(R.string.signed_out);
//
//            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
//            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Deprecated
    @Override
    protected void onDestroy() {
        // 因为修复了一个启动页被重复启动的问题，所以有可能 Activity 还没有初始化完成就已经销毁了
        // 所以如果需要在此处释放对象资源需要先对这个对象进行判空，否则可能会导致空指针异常
        super.onDestroy();
    }

    @OnClick({R.id.tv_forgot_password, R.id.btn_login, R.id.tv_login_terms_use, R.id.tv_login_privacy_policy, R.id.iv_google, R.id.iv_fb, R.id.tv_sign_up})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_forgot_password:
                break;
            case R.id.btn_login:
                break;
            case R.id.tv_login_terms_use:
                break;
            case R.id.tv_login_privacy_policy:
                break;
            case R.id.iv_google:
                signInGoogle();
                break;
            case R.id.iv_fb:
                goFBLogin();
                break;
            case R.id.tv_sign_up:
                startActivity(new Intent(mActivity, RegistActivity.class));
                break;
        }
    }

    /**
     * google service 是否可用
     */
    private int isGoogleServiceAvailable() {
        if (googleApiAvailability != null) {
            return googleApiAvailability.isGooglePlayServicesAvailable(this);
        }
        return -1;
    }

    //google 登录信息
    private void initGoogleLogin() {
        googleApiAvailability = GoogleApiAvailability.getInstance();
        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestId()
                .requestProfile()
                .requestIdToken(getString(R.string.server_client_id))
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        // Build a GoogleApiClient with access to GoogleSignIn.API and the options above.
        new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    private void signInGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN_GOOGLE);
    }

    private void signOutGoogle() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        updateUI(null);
                    }
                });
    }

    public void checkGoogleLoginAccount() {
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            LogUtil.d("account:" + account);
            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            LogUtil.d("signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }


    private void revokeAccessGoogle() {
//        强烈建议您为使用Google登录的用户提供断开其Google帐户与您的应用的连接的功能。
//        如果用户删除其帐户，则必须删除您的应用程序从Google API获取的信息。
        mGoogleSignInClient.revokeAccess()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {
                        updateUI(null);
                    }
                });
    }


    //FaceBook  相关
    private void goFBLogin() {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
    }

    private void initFBSDK() {
        CallbackManager mCallbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                LogUtil.d("登录成功: " + loginResult.getAccessToken().getToken());
                loginResult.getAccessToken().getApplicationId();
                loginResult.getAccessToken().getUserId();
            }

            @Override
            public void onCancel() {
                LogUtil.d("登录取消");
            }

            @Override
            public void onError(FacebookException error) {
                LogUtil.d("登录错误");
            }
        });

    }

    @Override
    public void onConnectionFailed(@NonNull @NotNull ConnectionResult connectionResult) {

    }
}