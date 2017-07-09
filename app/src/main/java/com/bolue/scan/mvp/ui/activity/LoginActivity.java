package com.bolue.scan.mvp.ui.activity;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bolue.scan.R;
import com.bolue.scan.mvp.entity.LoginEntity;
import com.bolue.scan.mvp.presenter.impl.LoginPresenterImpl;
import com.bolue.scan.mvp.ui.activity.base.BaseActivity;
import com.bolue.scan.mvp.view.LoginView;
import com.bolue.scan.utils.DialogUtils;
import com.bolue.scan.utils.DimenUtil;
import com.bolue.scan.utils.KeyBoardUtils;
import com.bolue.scan.utils.PreferenceUtils;
import com.bolue.scan.utils.TransformUtils;

import java.lang.reflect.Array;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscription;

public class LoginActivity extends BaseActivity implements LoginView{


    @BindView(R.id.bt_login)
    Button mLogin;

    @BindView(R.id.v_bac)
    ImageView vBac;

    @BindView(R.id.top_sperate)
    View mSperate;

    @BindView(R.id.et_user)
    EditText mEdUser;

    @BindView(R.id.et_password)
    EditText mEdPassword;


    @BindView(R.id.iv_show_password)
    ImageView mShowPassWord;

    @BindView(R.id.tv_err)
    TextView mTvErr;


    @BindView(R.id.iv_clear_user)
    ImageView mClearUser;

    @BindView(R.id.ll_password_right)
    RelativeLayout mPassWordRight;


    @Inject
    LoginPresenterImpl mLoginPresenterImpl;

    private View rootView;

    private int rootViewVisibleHeight;//纪录根视图的显示高度

    private Subscription mTimerSubscription;

    private Subscription mErrSubscription;

    private ViewTreeObserver.OnGlobalLayoutListener listener;


    private TextWatcher mWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            onTextInputChanged();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    @OnClick({R.id.bt_login,R.id.iv_clear_user,R.id.iv_clear_password,R.id.iv_show_password})
    public void onClick(View v){

        switch(v.getId()){
            case R.id.bt_login:
                checkInput();
                break;
            case R.id.iv_clear_user:
                mEdUser.setText("");
                break;
            case R.id.iv_clear_password:
                mEdPassword.setText("");
                break;
            case R.id.iv_show_password:
                if(mShowPassWord.isSelected()){
                    mEdPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    mShowPassWord.setSelected(false);
                    mEdPassword.setSelection(mEdPassword.getText().length());

                }else{
                    mShowPassWord.setSelected(true);
                    mEdPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    mEdPassword.setSelection(mEdPassword.getText().length());
                }
                break;

        }

    }

    private void checkInput() {

        if(mEdUser.getText().toString().equals("")){
            showErrMessage("*用户名不能为空");
        }else if(mEdPassword.getText().toString().equals("")){
            showErrMessage("*密码不能为空");
        }else{
            doLogin();
        }

    }

    private void showErrMessage(String msg){


        mTvErr.setText(msg);

        final ScaleAnimation animation =new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(200);
        mTvErr.startAnimation(animation);
        if(mErrSubscription != null)
            mErrSubscription.unsubscribe();
        mErrSubscription = Observable.timer(3, TimeUnit.SECONDS).compose(TransformUtils.<Object>defaultSchedulers())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onCompleted() {
                        mTvErr.setText("");

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Object data) {

                    }

                });
    }

    private void doLogin() {
        mLoginPresenterImpl.beforeRequest();
        mLoginPresenterImpl.doLogin(mEdUser.getText().toString(),mEdPassword.getText().toString());
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public void initViews() {
        mTvErr.setText("");
        mLoginPresenterImpl.attachView(this);
        mEdUser.setText("15502145237");
        mEdPassword.setText("cty070912");
        mEdUser.addTextChangedListener(mWatcher);
        mEdPassword.addTextChangedListener(mWatcher);
        onTextInputChanged();
    }

    private void onTextInputChanged(){

        mClearUser.setVisibility(View.VISIBLE);
        mPassWordRight.setVisibility(View.VISIBLE);


        if(mEdUser.getText().toString().equals("")||mEdPassword.getText().toString().equals("")){
            mLogin.setEnabled(false);

            if(mEdUser.getText().toString().equals("")){
                mClearUser.setVisibility(View.GONE);
            }

            if(mEdPassword.getText().toString().equals("")){
                mPassWordRight.setVisibility(View.GONE);
            }

        } else{

            mLogin.setEnabled(true);

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        setOnKeyboardChangeListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeOnKeyboardChangeListener();
    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent event) {
//        InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        View v = getCurrentFocus();
//        new KeyBoardUtils(event,im,v).hideKeyBoardIfNecessary();
//        return super.dispatchTouchEvent(event);
//    }

    private void startTransAnimation(){
        LinearLayout.LayoutParams l = (LinearLayout.LayoutParams) vBac.getLayoutParams();

        if(l.height < (int)DimenUtil.dp2px(100)){
            return;
        }

        ValueAnimator mAnimator = ValueAnimator.ofInt(150, 0);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animatorValue = (int)animation.getAnimatedValue();
                LinearLayout.LayoutParams l = (LinearLayout.LayoutParams) vBac.getLayoutParams();
                l.height = (int)DimenUtil.dp2px(animatorValue);
                vBac.setLayoutParams(l);
            }
        });
        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mSperate.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        mAnimator.setDuration(200);
        mAnimator.setTarget(vBac);
        mAnimator.start();
    }

    private void endTransAnimation(){

        LinearLayout.LayoutParams l = (LinearLayout.LayoutParams) vBac.getLayoutParams();

        if(l.height > (int)DimenUtil.dp2px(100)){
            return;
        }

        ValueAnimator mAnimator = ValueAnimator.ofInt(0,150);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animatorValue = (int)animation.getAnimatedValue();
                LinearLayout.LayoutParams l = (LinearLayout.LayoutParams) vBac.getLayoutParams();
                l.height = (int)DimenUtil.dp2px(animatorValue);
                vBac.setLayoutParams(l);


            }
        });
        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mSperate.setBackgroundColor(Color.parseColor("#21B7C4"));

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        mAnimator.setDuration(200);
        mAnimator.setTarget(vBac);
        mAnimator.start();

    }

    private  void setOnKeyboardChangeListener(){
        rootView = getWindow().getDecorView();
        if(listener == null){
            listener = new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    //获取当前根视图在屏幕上显示的大小
                    Log.i("keyboard","onGlobalLayout");
                    Rect r = new Rect();
                    rootView.getWindowVisibleDisplayFrame(r);

                    final int visibleHeight = r.height();
                    System.out.println(""+visibleHeight);
                    if (rootViewVisibleHeight == 0) {
                        rootViewVisibleHeight = visibleHeight;
                    }

                    //根视图显示高度没有变化，可以看作软键盘显示／隐藏状态没有改变
                    if (rootViewVisibleHeight == visibleHeight) {
                        return;
                    }

                    //根视图显示高度变小超过200，可以看作软键盘显示了
                    if (rootViewVisibleHeight - visibleHeight > 200) {


//                        startTransAnimation();
//                        rootViewVisibleHeight = visibleHeight;
//
//                        return;
                        if(mTimerSubscription!=null)
                            mTimerSubscription.unsubscribe();

                        mTimerSubscription = Observable.timer(300, TimeUnit.MILLISECONDS).compose(TransformUtils.<Object>defaultSchedulers())
                                .subscribe(new Observer<Object>() {
                                    @Override
                                    public void onCompleted() {
                                        startTransAnimation();
//                                        rootViewVisibleHeight = visibleHeight;

                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }

                                    @Override
                                    public void onNext(Object data) {

                                    }

                                });
                        rootViewVisibleHeight = visibleHeight;
                        return;

                    }

                    //根视图显示高度变大超过200，可以看作软键盘隐藏了
                    if (visibleHeight - rootViewVisibleHeight > 200) {


//                        endTransAnimation();
//                        rootViewVisibleHeight = visibleHeight;
//
//                        return;

                        if(mTimerSubscription!=null)
                            mTimerSubscription.unsubscribe();

                        mTimerSubscription = Observable.timer(300, TimeUnit.MILLISECONDS).compose(TransformUtils.<Object>defaultSchedulers())
                                .subscribe(new Observer<Object>() {
                                    @Override
                                    public void onCompleted() {
                                        endTransAnimation();
//                                        rootViewVisibleHeight = visibleHeight;
                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }

                                    @Override
                                    public void onNext(Object data) {

                                    }

                                });
                        rootViewVisibleHeight = visibleHeight;
                        return;

                    }
                }
            };
            rootView.getViewTreeObserver().addOnGlobalLayoutListener(listener);
            Rect r = new Rect();
            rootView.getWindowVisibleDisplayFrame(r);
            rootViewVisibleHeight = r.height();
            Log.i("keyboard","setListener");
        }

    }

    private void removeOnKeyboardChangeListener(){
        if(rootView!=null && listener != null){
            rootView.getViewTreeObserver().removeOnGlobalLayoutListener(listener);
            listener = null;
        }
    }

    @Override
    public void showProgress(int reqType) {
        if(mLoadDialog == null){
            mLoadDialog = DialogUtils.create(this);
            mLoadDialog.show("登录中,请稍候...");
        }
    }

    @Override
    public void hideProgress(int reqType) {
        if(mLoadDialog != null){
            mLoadDialog.dismiss();
            mLoadDialog = null;
        }
    }

    @Override
    public void showErrorMsg(int reqType, String msg) {
            showErrMessage("*"+msg);
    }

    @Override
    public void doLoginCompleted(LoginEntity entity) {

        if(entity.getErr() == null && entity.getResult() != null){

            PreferenceUtils.setPrefString(this,"userName",mEdUser.getText().toString());
            PreferenceUtils.setPrefString(this,"passWord",mEdPassword.getText().toString());
            PreferenceUtils.setPrefString(this,"openid",entity.getResult().getOpenid());
            PreferenceUtils.setPrefString(this,"code",entity.getResult().getCode());

            startActivity(new Intent(this,MainActivity.class));

            finish();

            Toast.makeText(this,"登录成功",Toast.LENGTH_SHORT).show();

        }else{

            showErrMessage("*"+entity.getErr());
        }
    }
}
