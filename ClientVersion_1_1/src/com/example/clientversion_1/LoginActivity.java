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
	private LayoutInflater inflater; // ������
	
	private Weibo mWeibo;
    private static final String CONSUMER_KEY = "652117364";// �滻Ϊ�����ߵ�appkey������"1646212860";
    private static final String REDIRECT_URL = "http://www.sina.com";
    public static Oauth2AccessToken accessToken = null;
    public static final String TAG = "sinasdk";
    /**
     * SsoHandler ����sdk֧��ssoʱ��Ч��
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
                Class sso = Class.forName("com.weibo.sdk.android.api.WeiboAPI");// ���֧��weiboapi�Ļ�,��ʾapi������ʾ��ڰ�ť
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
            //mText.setText("access_token ������Ч����,�����ٴε�¼:  \naccess_token:" + LoginActivity.accessToken.getToken() + "\n��Ч�ڣ�" + date);
        } else {
            //mText.setText("ʹ��SSO��¼ǰ�������ֻ����Ƿ��Ѿ���װ����΢���ͻ��ˣ�Ŀǰ��3.0.0������΢���ͻ��˰汾֧��SSO�����δ��װ�����Զ�תΪOauth2.0������֤");
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
	        
	        // �˴�ʵ�� WeiboDialogListener �ӿڣ���Ȩ�ɹ��󼴿��� onComplete �ص������� ��� accessToken ��Ϣ����ʱ�������ɱ��洦��
	        LoginActivity.accessToken = new Oauth2AccessToken(token, expires_in);
	        if (LoginActivity.accessToken.isSessionValid()) {
	            String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
	                    .format(new java.util.Date(LoginActivity.accessToken.getExpiresTime()));
	            
	            Log.d("FFF", accessToken.getToken()+"");
	            //mText.setText("��֤�ɹ�: \r\n access_token: " + token + "\r\n" + "expires_in: " + expires_in + "\r\n��Ч��" + date);
	            try {
	                Class sso = Class.forName("com.weibo.sdk.android.api.WeiboAPI");// ���֧��weiboapi�Ļ�����ʾapi������ʾ��ڰ�ť
	
	                //apiBtn.setVisibility(View.VISIBLE);
	            } catch (ClassNotFoundException e) {
	                // e.printStackTrace();
	                Log.i(TAG, "com.weibo.sdk.android.api.WeiboAPI not found");
	
	            }
	            //cancelBtn.setVisibility(View.VISIBLE);
	            AccessTokenKeeper.keepAccessToken(LoginActivity.this,
	                    accessToken);
	            Toast.makeText(LoginActivity.this, "��֤�ɹ�", Toast.LENGTH_SHORT).show();
	            
	            
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
	     * ��������ע�͵��Ĵ��룬����sdk֧��ssoʱ��Ч��
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
		 * ����ť����
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
