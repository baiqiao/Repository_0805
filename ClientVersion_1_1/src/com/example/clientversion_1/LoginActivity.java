package com.example.clientversion_1;

import java.text.SimpleDateFormat;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.clientversion_1.weobologin.AccessTokenKeeper;
import com.weibo.sdk.android.Oauth2AccessToken;
import com.weibo.sdk.android.Weibo;
import com.weibo.sdk.android.WeiboAuthListener;
import com.weibo.sdk.android.WeiboDialogError;
import com.weibo.sdk.android.WeiboException;
import com.weibo.sdk.android.sso.SsoHandler;
import com.weibo.sdk.android.util.Utility;

public class LoginActivity extends Activity implements OnClickListener, OnTouchListener{

	private Button btn_weibologin;
	

	private ImageButton sina;
	public Dialog dialog;
	private LayoutInflater inflater; // 布局器
	
	private Weibo mWeibo;
    private static final String CONSUMER_KEY = "652117364";// 替换为开发者的appkey，例如"1646212860";
    private static final String REDIRECT_URL = "http://www.sina.com";
    public static Oauth2AccessToken accessToken = null;
    public static final String TAG = "sinasdk";
    /**
     * SsoHandler 仅当sdk支持sso时有效，
     */
    SsoHandler mSsoHandler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		inflater = this.getLayoutInflater();
		mWeibo = Weibo.getInstance(CONSUMER_KEY, REDIRECT_URL);
		
		btn_weibologin = (Button)this.findViewById(R.id.btn_weibologin);
		btn_weibologin.setOnClickListener(this);
		
		
		LoginActivity.accessToken = AccessTokenKeeper.readAccessToken(this);
		Log.d("AAAA",accessToken.getToken()+"");
		
        if (LoginActivity.accessToken.isSessionValid()) {
            Weibo.isWifi = Utility.isWifi(this);
            try {
                Class sso = Class.forName("com.weibo.sdk.android.api.WeiboAPI");// 如果支持weiboapi的话,显示api功能演示入口按钮
                btn_weibologin.setVisibility(View.VISIBLE);
            } catch (ClassNotFoundException e) {
                // e.printStackTrace();
                Log.i(TAG, "com.weibo.sdk.android.api.WeiboAPI not found");

            }
            //login_thethird.setVisibility(View.INVISIBLE);
            //ssoBtn.setVisibility(View.INVISIBLE);
            //cancelBtn.setVisibility(View.VISIBLE);
            String date = new java.text.SimpleDateFormat("yyyy/MM/dd hh:mm:ss")
                    .format(new java.util.Date(LoginActivity.accessToken
                            .getExpiresTime()));
            //mText.setText("access_token 仍在有效期内,无需再次登录:  \naccess_token:" + LoginActivity.accessToken.getToken() + "\n有效期：" + date);
        } else {
            //mText.setText("使用SSO登录前，请检查手机上是否已经安装新浪微博客户端，目前仅3.0.0及以上微博客户端版本支持SSO；如果未安装，将自动转为Oauth2.0进行认证");
        }
        DialogView();
        
	}
	
	
	private void DialogView() {
		
		LinearLayout li=(LinearLayout)inflater.inflate(R.layout.dialog_thethird,null);
    	sina = (ImageButton)li.findViewById(R.id.sina);
    	sina.setOnClickListener(this);
    	sina.setOnTouchListener(this);
    	
    	dialog = new Dialog(this);
    	dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    	Window win = dialog.getWindow();
    	WindowManager.LayoutParams lp = win.getAttributes();
    	
    	lp.alpha = 1.0f;
    	lp.dimAmount = 0.9f;
    	lp.x = 0;
    	lp.y = 0;
    	lp.width = 500;
    	lp.height = 300;
    	
    	win.setAttributes(lp);
    	win.setGravity(Gravity.CENTER);
    	li.setBackgroundColor(Color.GRAY);
    	LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(500, 300);
    	dialog.setContentView(li, ll);
		
	}

	
	class AuthDialogListener implements WeiboAuthListener {
	
	    @Override
	    public void onComplete(Bundle values) {
	    	
	        String token = values.getString("access_token");
	        String expires_in = values.getString("expires_in");
	        
	        // 此处实现 WeiboDialogListener 接口，授权成功后即可在 onComplete 回调方法中 获得 accessToken 信息，此时可以自由保存处理。
	        LoginActivity.accessToken = new Oauth2AccessToken(token, expires_in);
	        if (LoginActivity.accessToken.isSessionValid()) {
	            String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
	                    .format(new java.util.Date(LoginActivity.accessToken.getExpiresTime()));
	            
	            Log.d("FFF", accessToken.getToken()+"");
	            //mText.setText("认证成功: \r\n access_token: " + token + "\r\n" + "expires_in: " + expires_in + "\r\n有效期" + date);
	            try {
	                Class sso = Class.forName("com.weibo.sdk.android.api.WeiboAPI");// 如果支持weiboapi的话，显示api功能演示入口按钮
	
	                //apiBtn.setVisibility(View.VISIBLE);
	            } catch (ClassNotFoundException e) {
	                // e.printStackTrace();
	                Log.i(TAG, "com.weibo.sdk.android.api.WeiboAPI not found");
	
	            }
	            //cancelBtn.setVisibility(View.VISIBLE);
	            AccessTokenKeeper.keepAccessToken(LoginActivity.this,
	                    accessToken);
	            Toast.makeText(LoginActivity.this, "认证成功", Toast.LENGTH_SHORT).show();
	            
	            
	        }
	    }
	
	    @Override
	    public void onError(WeiboDialogError e) {
	        Toast.makeText(getApplicationContext(),
	                "Auth error : " + e.getMessage(), Toast.LENGTH_LONG).show();
	    }
	
	    @Override
	    public void onCancel() {
	        Toast.makeText(getApplicationContext(), "Auth cancel",
	                Toast.LENGTH_LONG).show();
	    }
	
	    @Override
	    public void onWeiboException(WeiboException e) {
	        Toast.makeText(getApplicationContext(),
	                "Auth exception : " + e.getMessage(), Toast.LENGTH_LONG)
	                .show();
	    }
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	
	    /**
	     * 下面两个注释掉的代码，仅当sdk支持sso时有效，
	     */
	    if (mSsoHandler != null) {
	        mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
	    }
	}
	


	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.btn_weibologin) {
			
			dialog.show();
		}
		
		else if(v.getId() == R.id.sina) {
			mWeibo.authorize(LoginActivity.this, new AuthDialogListener());
		}
	}


	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		/**
		 * 
		 * 处理按钮背景
		 * @author yl
		 */
		
		if(event.getAction() == MotionEvent.ACTION_DOWN) {
			v.setBackgroundColor(getResources().getColor(R.color.pink));
			v.invalidate();
		}
		else {
			v.setBackgroundColor(0);
			v.invalidate();
		}
		return false;
	}

}
