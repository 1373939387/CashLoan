package com.dumai.loan.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dumai.loan.R;
import com.dumai.loan.base.BaseActivity;
import com.dumai.loan.http.http.HttpCallBack;
import com.dumai.loan.http.http.HttpManager;
import com.dumai.loan.jparser.JsonLoginObject;
import com.dumai.loan.util.EmptyUtils;
import com.dumai.loan.util.LogUtil;
import com.dumai.loan.util.SharedUtils;
import com.dumai.loan.util.ToastUtils;
import com.dumai.loan.util.view.ToolbarHelper;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 注册
 * haoruigang
 * 2017-11-23 16:10:21
 */
public class RegisterActivity extends BaseActivity {

    @BindView(R.id.et_phone_number)
    EditText etPhoneNumber;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.btn_register)
    Button btnRegister;

    @Override
    protected int getContentViewId() {
        return R.layout.dm_activity_register;
    }

    @Override
    protected void initToolbar(ToolbarHelper toolbarHelper) {

        toolbarHelper.setTitle("注册");
        toolbarHelper.setLeftMenuTitle("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @OnClick(R.id.btn_register)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                setRegister();
                break;
        }
    }

    private void setRegister() {
        if (validate()) {

            HttpManager.getInstance().doReginster("RegisterActivity", phoneNum, psd, new HttpCallBack<JsonLoginObject>(RegisterActivity.this, true) {
                @Override
                public void onError(Throwable throwable) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils.showToastInThread("网络不可用!");
                        }
                    });
                }

                @Override
                public void onSuccess(JsonLoginObject date) {
                    if (date != null) {
                        final JsonLoginObject.ResultBean Value = date.getResult();
                        if (Value.getCode().equals("1")) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ToastUtils.showToastInThread("注册成功");
                                }
                            });
                            //保存登录成功后的信息
                            SharedUtils.putString(RegisterActivity.this, "regusername", phoneNum);
                            SharedUtils.putString(RegisterActivity.this, "regpsd", psd);
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            finish();
                        } else if (Value.getCode().equals("3")) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ToastUtils.showToastInThread("账户已存在!");
                                }
                            });
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ToastUtils.showToastInThread("注册失败!");
                                }
                            });
                        }
                        LogUtil.i(RegisterActivity.this, "注册" + Value.getMsg());
                    } else

                    {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.showToastInThread("网络不可用!");
                            }
                        });
                    }
                }
            });
        }

    }

    private String phoneNum, psd;

    private boolean validate() {
        phoneNum = etPhoneNumber.getText().toString();
        psd = etPassword.getText().toString().trim();
        if (!EmptyUtils.isNotEmpty(phoneNum)) {
            ToastUtils.showToast("账号为空!");
            return false;
        }
        if (!EmptyUtils.isNotEmpty(psd)) {
            ToastUtils.showToast("密码为空!");
            return false;
        }
        return true;
    }
}
