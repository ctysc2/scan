package com.bolue.scan.zxing.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.bolue.scan.R;
import com.bolue.scan.application.App;
import com.bolue.scan.greendao.entity.Sign;
import com.bolue.scan.greendaohelper.SignHelper;
import com.bolue.scan.listener.AlertDialogListener;
import com.bolue.scan.mvp.entity.SignRequestEntity;
import com.bolue.scan.mvp.entity.base.BaseEntity;
import com.bolue.scan.mvp.presenter.impl.SignPresenterImpl;
import com.bolue.scan.mvp.ui.activity.MainActivity;
import com.bolue.scan.mvp.ui.activity.ParticipantDetailActivity;
import com.bolue.scan.mvp.ui.activity.base.BaseActivity;
import com.bolue.scan.mvp.view.DoSignView;
import com.bolue.scan.utils.DialogUtils;
import com.bolue.scan.utils.SystemTool;
import com.bolue.scan.utils.TransformUtils;
import com.bolue.scan.zxing.camera.CameraManager;
import com.bolue.scan.zxing.decoding.CaptureActivityHandler;
import com.bolue.scan.zxing.decoding.InactivityTimer;
import com.bolue.scan.zxing.view.ViewfinderView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observer;

/**
 * Initial the camera
 * @author Ryan.Tang
 */
public class CaptureActivity extends BaseActivity implements Callback,DoSignView {

	private CaptureActivityHandler handler;
	private ViewfinderView viewfinderView;
	private boolean hasSurface;
	private Vector<BarcodeFormat> decodeFormats;
	private String characterSet;
	private InactivityTimer inactivityTimer;
	private MediaPlayer mediaPlayer;
	private boolean playBeep;
	private static final float BEEP_VOLUME = 0.10f;
	private boolean vibrate;
	private ArrayList<String> checkcodes;
	private boolean isOnlineMode = true;
	private int id;
	@Inject
	SignPresenterImpl mSignPresenterImpl;
//	private Button cancelScanButton;

	@Override
	public int getLayoutId() {
		return R.layout.camera;
	}

	@Override
	public void initInjector() {
		mActivityComponent.inject(this);
	}

	@Override
	public void initViews() {
		Intent intent = getIntent();
		checkcodes = intent.getStringArrayListExtra("checkcodes");
		isOnlineMode = intent.getBooleanExtra("isOnlineMode",true);
		id = intent.getIntExtra("id",-1);
		mSignPresenterImpl.attachView(this);
	}
	@OnClick({R.id.rl_back})
	public void onClick(View v){
		switch (v.getId()){
			case R.id.rl_back:
				finish();
				break;
		}
	}
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		setContentView(R.layout.camera);
		//ViewUtil.addTopView(getApplicationContext(), this, R.string.scan_card);
		CameraManager.init(getApplication());
		viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
//		cancelScanButton = (Button) this.findViewById(R.id.btn_cancel_scan);
		hasSurface = false;
		inactivityTimer = new InactivityTimer(this);
		initViews();
	}

	@Override
	protected void onResume() {
		super.onResume();
		SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
		SurfaceHolder surfaceHolder = surfaceView.getHolder();
		if (hasSurface) {
			initCamera(surfaceHolder);
		} else {
			surfaceHolder.addCallback(this);
			/**
			 * SURFACE_TYPE_PUSH_BUFFERS表明该Surface不包含原生数据，Surface用到的数据由其他对象提供，
			 * 在Camera图像预览中就使用该类型的Surface，有Camera负责提供给预览Surface数据，这样图像预览会比较流畅。
			 */
			surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}
		decodeFormats = null;
		characterSet = null;

		playBeep = true;
		AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
		if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
			playBeep = false;
		}
		initBeepSound();
		vibrate = true;
		
		//quit the scan view
/*		cancelScanButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				CaptureActivity.this.finish();
			}
		});*/
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (handler != null) {
			handler.quitSynchronously();
			handler = null;
		}
		CameraManager.get().closeDriver();
	}

	@Override
	protected void onDestroy() {
		inactivityTimer.shutdown();
		super.onDestroy();
	}
	
	/**
	 * Handler scan result
	 * @param result
	 * @param barcode
	 */
	public void handleDecode(Result result, Bitmap barcode) {
		inactivityTimer.onActivity();
		playBeepSoundAndVibrate();
		String resultString = result.getText();
		//FIXME
		if (resultString.equals("")) {
			Toast.makeText(CaptureActivity.this, "扫描失败，请重试", Toast.LENGTH_SHORT).show();
		}else {
//			Intent resultIntent = new Intent();
//			Bundle bundle = new Bundle();
//			bundle.putString("qr_code", resultString);
//			resultIntent.putExtras(bundle);
//			this.setResult(RESULT_OK, resultIntent);
			//确认扫的是不是当前课程的码
			for(int i = 0;i<checkcodes.size();i++){

				//dummy
				if( resultString.equals(checkcodes.get(i))){

					if(isOnlineMode){
						//在线模式调用签到接口
						SignRequestEntity entity = new SignRequestEntity();
						ArrayList<SignRequestEntity.Check> checkList = new ArrayList<>();
						checkList.add(new SignRequestEntity.Check(resultString));
						entity.setSign_list(checkList);
						mSignPresenterImpl.beforeRequest();
						mSignPresenterImpl.doSign(entity);
					}else{
						//离线模式加入数据库缓存
						if(SignHelper.getInstance().getSign(id,resultString) != null){
							Toast.makeText(this,"该信息已在离线队列中",Toast.LENGTH_SHORT).show();
						}else{
							Sign sign = new Sign();
							sign.setId(id);
							sign.setCheckCode(resultString);
							SignHelper.getInstance().insertSign(sign);
							Toast.makeText(this,"签到信息添加成功",Toast.LENGTH_SHORT).show();

						}
						reScanDelay();
					}


					return;

				}
			}

			//没有查到
			Toast.makeText(this,"该二维码信息不属于当前课程,请确认",Toast.LENGTH_SHORT).show();

			reScanDelay();

		}
		//CaptureActivity.this.finish();
	}
	
	private void initCamera(SurfaceHolder surfaceHolder) {
		try {
			CameraManager.get().openDriver(surfaceHolder);
		} catch (IOException ioe) {
			return;
		} catch (RuntimeException e) {
			return;
		}
		if (handler == null) {
			handler = new CaptureActivityHandler(this, decodeFormats, characterSet);
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (!hasSurface) {
			hasSurface = true;
			initCamera(holder);
		}

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		hasSurface = false;

	}

	public ViewfinderView getViewfinderView() {
		return viewfinderView;
	}

	public Handler getHandler() {
		return handler;
	}

	public void drawViewfinder() {
		viewfinderView.drawViewfinder();

	}
	public void doclick(View v)
	{
		finish();		
	}
	private void initBeepSound() {
		if (playBeep && mediaPlayer == null) {
			// The volume on STREAM_SYSTEM is not adjustable, and users found it
			// too loud,
			// so we now play on the music stream.
			setVolumeControlStream(AudioManager.STREAM_MUSIC);
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setOnCompletionListener(beepListener);

			AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.beep);
			try {
				mediaPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
				file.close();
				mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
				mediaPlayer.prepare();
			} catch (IOException e) {
				mediaPlayer = null;
			}
		}
	}

	private static final long VIBRATE_DURATION = 200L;

	private void playBeepSoundAndVibrate() {
		if (playBeep && mediaPlayer != null) {
			mediaPlayer.start();
		}
		if (vibrate) {
			Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
			vibrator.vibrate(VIBRATE_DURATION);
		}
	}

	/**
	 * When the beep has finished playing, rewind to queue up another one.
	 */
	private final OnCompletionListener beepListener = new OnCompletionListener() {
		public void onCompletion(MediaPlayer mediaPlayer) {
			mediaPlayer.seekTo(0);
		}
	};

	@Override
	public void showProgress(int reqType) {
		if(mLoadDialog == null){
			mLoadDialog = DialogUtils.create(this);
			mLoadDialog.show("正在上传签到信息");
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
		Toast.makeText(this,"签到失败:"+msg,Toast.LENGTH_SHORT).show();
		reScanDelay();
	}

	@Override
	public void doSignCompleted(BaseEntity entity) {

		if(entity != null && entity.getErr() == null){
			Toast.makeText(this,"签到成功!",Toast.LENGTH_SHORT).show();
		}
		reScanDelay();

	}

	private void reScanDelay(){

		rx.Observable.timer(1, TimeUnit.SECONDS).compose(TransformUtils.<Object>defaultSchedulers())
				.subscribe(new Observer<Object>() {
					@Override
					public void onCompleted() {
						onPause();
						onResume();
					}

					@Override
					public void onError(Throwable e) {

					}

					@Override
					public void onNext(Object data) {

					}

				});

	}

	@Override
	public void onNetChange(int networkType) {
		if(networkType == SystemTool.NETWORK_NONE){

			if(isOnlineMode == false)
				return;

			if(App.isKicked == true)
				return;

			if(mAlertDialog != null && mAlertDialog.isShowing())
				mAlertDialog.dismiss();

			mAlertDialog = DialogUtils.create(this);
			mAlertDialog.show(new AlertDialogListener() {
				@Override
				public void onConFirm() {
					mAlertDialog.dismiss();
					Intent intent = new Intent(CaptureActivity.this,MainActivity.class);
					intent.putExtra("isToOffline",true);
					startActivity(intent);

				}

				@Override
				public void onCancel() {
					mAlertDialog.dismiss();
				}
			},"网络异常","没有检测到网络连接,是否切换至离线模式","取消","看离线");




		}
	}
}