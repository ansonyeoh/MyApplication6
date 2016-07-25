package com.software.anson.myapplication;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

public class FragmentMap extends Fragment {
	private MapView mMapView;
	public BaiduMap mBaiduMap;
	private DisplayMetrics dm;

	private Context context;

	// ��λ���
	private LocationClient mLocationClient;
	private MyLocationListener mLocationListener;
	private boolean isFirstIn = true;
	private double mLatitude;
	private double mLongtitude;
	// �Զ��嶨λͼ��
	private BitmapDescriptor mIconLocation;
	private MyOrientationListener myOrientationListener;
	private float mCurrentX;
	private MyLocationConfiguration.LocationMode mLocationMode;
	private ImageView menu;
	private PopupWindow popWindow;
	private Context mContext = null;
	private Button bt_common, bt_site,bt_traffic_off , bt_traffic_on, bt_location, bt_mode_common, bt_mode_following,bt_mode_compass ;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		try {
			getActivity().getWindow().addFlags(WindowManager.LayoutParams.class.getField("FLAG_NEEDS_MENU_KEY").getInt(null));
		}catch (NoSuchFieldException e) {
			// Ignore since this field won't exist in most versions of Android
		}catch (IllegalAccessException e) {
			Log.w("feelyou.info", "Could not access FLAG_NEEDS_MENU_KEY in addLegacyOverflowButton()", e);
		}
		SDKInitializer.initialize(getActivity().getApplicationContext());
        //��ȡ��ͼ�ؼ�����
        View v = inflater.inflate(R.layout.fragment_map, container, false);
		this.context = getActivity();

		menu = (ImageView) v.findViewById(R.id.menu);
		menu.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View parent) {
				showPopupWindow(parent);
			}
		});

		mMapView = (MapView) v.findViewById(R.id.id_bmapView);;
		mBaiduMap = mMapView.getMap();
		MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(15.0f);
		mBaiduMap.setMapStatus(msu);
		// ��ʼ����λ
		initLocation();

		return v;
	}


	private void initLocation()
	{

		mLocationMode = MyLocationConfiguration.LocationMode.FOLLOWING;
		mLocationClient = new LocationClient(getActivity());
		mLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mLocationListener);

		LocationClientOption option = new LocationClientOption();
		option.setCoorType("bd09ll");
		option.setIsNeedAddress(true);
		option.setOpenGps(true);
		option.setScanSpan(1000);
		mLocationClient.setLocOption(option);
		// ��ʼ��ͼ��
		mIconLocation = BitmapDescriptorFactory
				.fromResource(R.drawable.navi_map_gps_locked);
		myOrientationListener = new MyOrientationListener(context);

		myOrientationListener
				.setOnOrientationListener(new MyOrientationListener.OnOrientationListener() {
					@Override
					public void onOrientationChanged(float x) {
						mCurrentX = x;
					}
				});

		mLocationMode = MyLocationConfiguration.LocationMode.FOLLOWING;
	}

	@Override
	public void onResume()
	{
		super.onResume();
		// ��activityִ��onResumeʱִ��mMapView. onResume ()��ʵ�ֵ�ͼ�������ڹ���
		mMapView.onResume();
	}

	@Override
	public void onStart()
	{
		super.onStart();
		// ������λ
		mBaiduMap.setMyLocationEnabled(true);
		if (!mLocationClient.isStarted())
			mLocationClient.start();
		// �������򴫸���
		myOrientationListener.start();
	}

	@Override
	public void onPause()
	{
		super.onPause();
		// ��activityִ��onDestroyʱִ��mMapView.onDestroy()��ʵ�ֵ�ͼ�������ڹ���
		mMapView.onPause();
	}

	@Override
	public void onStop()
	{
		super.onStop();

		// ֹͣ��λ
		mBaiduMap.setMyLocationEnabled(false);
		mLocationClient.stop();
		// ֹͣ���򴫸���
		myOrientationListener.stop();

	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
		// ��activityִ��onDestroyʱִ��mMapView.onDestroy()��ʵ�ֵ�ͼ�������ڹ���
		mMapView.onDestroy();
	}

	private void showPopupWindow(View parent) {
		if (popWindow == null) {
			LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			View view = layoutInflater.inflate(R.layout.popwindow_menu, null);
			dm = new DisplayMetrics();
			getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
			// ����һ��PopuWidow����
			popWindow = new PopupWindow(view, dm.widthPixels, LinearLayout.LayoutParams.WRAP_CONTENT);

			bt_common = (Button) view.findViewById(R.id.bt_common);

			bt_site = (Button) view.findViewById(R.id.bt_site);

			bt_location = (Button) view.findViewById(R.id.bt_location);

			bt_mode_common = (Button) view.findViewById(R.id.bt_mode_common);
			bt_mode_common.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					mLocationMode = MyLocationConfiguration.LocationMode.NORMAL;
				}
			});
			bt_mode_compass = (Button) view.findViewById(R.id.bt_mode_compass);
			bt_mode_compass.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					mLocationMode = MyLocationConfiguration.LocationMode.COMPASS;
				}
			});
			bt_mode_following = (Button) view.findViewById(R.id.bt_mode_following);
			bt_mode_following.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {

				}
			});
			bt_traffic_off = (Button) view.findViewById(R.id.bt_traffic_off);
			bt_traffic_off.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					mBaiduMap.setTrafficEnabled(false);
				}
			});
			bt_traffic_on = (Button) view.findViewById(R.id.bt_traffic_on);
			bt_traffic_on.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					mBaiduMap.setTrafficEnabled(true);
				}
			});
		}
		// ʹ��ۼ� ��Ҫ������˵���ؼ����¼��ͱ���Ҫ���ô˷���
		popWindow.setFocusable(true);
		// ����������������ʧ
		popWindow.setOutsideTouchable(true);
		// ���ñ����������Ϊ�˵��������Back��Ҳ��ʹ����ʧ�����Ҳ�����Ӱ����ı���
		popWindow.setBackgroundDrawable(new BitmapDrawable());
		// PopupWindow����ʾ��λ������
		// popWindow.showAtLocation(parent, Gravity.FILL, 0, 0);
		popWindow.showAsDropDown(parent, 0, 0);

		bt_common.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
			}
		});
		bt_location.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				centerToMyLocation();
			}
		});
		bt_site.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
			}
		});

		// ���������¼�
		popWindow.setTouchInterceptor(new View.OnTouchListener() {
			public boolean onTouch(View view, MotionEvent event) {
				// �ı���ʾ�İ�ťͼƬΪ����״̬
				popWindow.dismiss();
				return false;
			}
		});}


	/**
	 * ��λ���ҵ�λ��
	 */
	public void centerToMyLocation()
	{
		LatLng latLng = new LatLng(mLatitude, mLongtitude);
		MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
		mBaiduMap.animateMapStatus(msu);
	}

	private class MyLocationListener implements BDLocationListener
	{
		@Override
		public void onReceiveLocation(BDLocation location)
		{

			MyLocationData data = new MyLocationData.Builder()//
					.direction(mCurrentX)//
					.accuracy(location.getRadius())//
					.latitude(location.getLatitude())//
					.longitude(location.getLongitude())//
					.build();
			mBaiduMap.setMyLocationData(data);
			// �����Զ���ͼ��
			MyLocationConfiguration config = new MyLocationConfiguration(
					mLocationMode, true, mIconLocation);
			mBaiduMap.setMyLocationConfigeration(config);

			// ���¾�γ��
			mLatitude = location.getLatitude();
			mLongtitude = location.getLongitude();

			if (isFirstIn)
			{
				LatLng latLng = new LatLng(location.getLatitude(),
						location.getLongitude());
				MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
				mBaiduMap.animateMapStatus(msu);
				isFirstIn = false;

				Toast.makeText(context, location.getAddrStr(),
						Toast.LENGTH_SHORT).show();
			}

		}
	}

}
