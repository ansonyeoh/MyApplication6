package com.software.anson.myapplication.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.utils.DistanceUtil;
import com.software.anson.myapplication.R;
import com.software.anson.myapplication.util.MyOrientationListener;


public class FragmentMap extends Fragment {
    private final static String TAG = "FragmentMap";
    private MapView mMapView;
    public BaiduMap mBaiduMap;
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
    //�豸��λ���
    BitmapDescriptor bitmap = BitmapDescriptorFactory
            .fromResource(R.drawable.icon_marka);
    private Marker marker1;
    private Button overlayBtn;
    private double latitude = 29.13879;//39.992104228148264 + 0.00645
    private double longitude = 119.65046;//116.39671325683594 + 0.0064
    private Toolbar mToolbar4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        try {
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.class.getField("FLAG_NEEDS_MENU_KEY").getInt(null));
        } catch (NoSuchFieldException e) {
            // Ignore since this field won't exist in most versions of Android
        } catch (IllegalAccessException e) {
            Log.w("feelyou.info", "Could not access FLAG_NEEDS_MENU_KEY in addLegacyOverflowButton()", e);
        }

        //SDKInitializer.initialize(getActivity().getApplicationContext());
        //��ȡ��ͼ�ؼ�����
        View v = inflater.inflate(R.layout.fragment_map, container, false);
        mToolbar4 = (Toolbar) v.findViewById(R.id.toolbar_map);

        // toolbar_device.setLogo(R.drawable.ic_launcher);
        mToolbar4.setTitle("");// �������������setSupportActionBar֮ǰ����Ȼ����Ч
        // toolbar_device.setSubtitle("");
        mToolbar4.setTitleTextColor(R.color.white);
        mToolbar4.inflateMenu(R.menu.menu_main);
        this.context = getActivity();
        mToolbar4.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.id_map_common:
                        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                        break;

                    case R.id.id_map_site:
                        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                        break;

                    case R.id.id_map_traffic:
                        if (mBaiduMap.isTrafficEnabled()) {
                            mBaiduMap.setTrafficEnabled(false);
                            item.setTitle("ʵʱ��ͨ(off)");
                        } else {
                            mBaiduMap.setTrafficEnabled(true);
                            item.setTitle("ʵʱ��ͨ(on)");
                        }
                        break;
                    case R.id.id_map_location:
                        centerToMyLocation();
                        break;
                    case R.id.id_map_mode_common:
                        mLocationMode = MyLocationConfiguration.LocationMode.NORMAL;
                        break;
                    case R.id.id_map_mode_following:
                        mLocationMode = MyLocationConfiguration.LocationMode.FOLLOWING;
                        break;
                    case R.id.id_map_mode_compass:
                        mLocationMode = MyLocationConfiguration.LocationMode.COMPASS;
                        break;
                    case R.id.add:
                        addMarkerOverlay();
                        break;

                    default:
                        break;
                }
                return true;
            }
        });



        mMapView = (MapView) v.findViewById(R.id.id_bmapView);
        mBaiduMap = mMapView.getMap();
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(15.0f);
        mBaiduMap.setMapStatus(msu);

//        overlayBtn = (Button) v.findViewById(R.id.overlay_btn);
//        overlayBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                overlayBtn.setText("�豸�Ѷ�λ");
//                try {
//                    addMarkerOverlay();
//                } catch (Throwable throwable) {
//                    throwable.printStackTrace();
//                }
//            }
//        });

        // ��ʼ����λ
        initLocation();
        isFirstIn = false;

        //���
        LatLng gp1 = new LatLng(latitude, longitude);
        LatLng gp2 = new LatLng(mLatitude, mLongtitude);
        float distance = (float) DistanceUtil.getDistance(gp1, gp2);
        Log.i(TAG, "�������Ϊ��" + distance);
        if (distance > 50) {
            Boolean warming = true;
            Boolean command = true;
        }

        return v;
    }


    /*
    *��ʼ���ֻ�λ��
    * */
    private void initLocation() {

        mLocationMode = MyLocationConfiguration.LocationMode.FOLLOWING;
        mLocationClient = new LocationClient(getActivity());
        mLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mLocationListener);

        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
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

        // ��marker��������ӵ���¼�
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker arg0) {
                if (arg0 == marker1) {
                    final LatLng latLng = arg0.getPosition();
                    // ����γ��ת������Ļ�ϵĵ�
                    // Point point =
                    // bdMap.getProjection().toScreenLocation(latLng);
                    Toast.makeText(getActivity(), latLng.toString(),
                            Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

        /**
         * ��ͼ����¼�
         */
        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {

            @Override
            public boolean onMapPoiClick(MapPoi arg0) {
                return false;
            }

            @Override
            public void onMapClick(LatLng latLng) {
                displayInfoWindow(latLng);
            }
        });

    }


    /**
     * ���������õ���ַ��Ϣ
     */
    private void reverseGeoCode(LatLng latLng) {
        // ��������������ʵ��
        GeoCoder geoCoder = GeoCoder.newInstance();
        //
        OnGetGeoCoderResultListener listener = new OnGetGeoCoderResultListener() {
            // ����������ѯ����ص�����
            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
                if (result == null
                        || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    // û�м�⵽���
                    Toast.makeText(getActivity(), "��Ǹ��δ���ҵ����",
                            Toast.LENGTH_LONG).show();
                }
                Toast.makeText(getActivity(),
                        "λ�ã�" + result.getAddress(), Toast.LENGTH_LONG)
                        .show();
            }

            // ��������ѯ����ص�����
            @Override
            public void onGetGeoCodeResult(GeoCodeResult result) {
                if (result == null
                        || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    // û�м�⵽���
                }
            }
        };
        // ���õ���������������
        geoCoder.setOnGetGeoCodeResultListener(listener);
        //
        geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(latLng));
        // �ͷŵ���������ʵ��
        // geoCoder.destroy();
    }

    /**
     * ��ӱ�ע������
     */
    private void addMarkerOverlay() {
        mBaiduMap.clear();

//        MapCalculate mapCalculate = new MapCalculate();
//        float latitude = mapCalculate.BaiduY;
//        float longitude = mapCalculate.BaiduX;
        // ����marker�����
        LatLng point = new LatLng(latitude, longitude);

        // ����markerOption�������ڵ�ͼ�����marker
        OverlayOptions options = new MarkerOptions()//
                .position(point)// ����marker��λ��
                .icon(bitmap)// ����marker��ͼ��
                .zIndex(9)// �O��marker�����ڌӼ�
                .draggable(true);// ����������ק
        // �ڵ�ͼ�����marker������ʾ
        marker1 = (Marker) mBaiduMap.addOverlay(options);

    }

    /**
     * ��ʾ�������ڸ�����
     */
    private void displayInfoWindow(final LatLng latLng) {
        // ����infowindowչʾ��view
        Button btn = new Button(getActivity().getApplicationContext());
        btn.setBackgroundResource(R.drawable.popup);
        btn.setTextColor(R.color.balck);
        btn.setText("�����ȡ��ϸ��ַ~");
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory
                .fromView(btn);
        // infowindow����¼�
        InfoWindow.OnInfoWindowClickListener infoWindowClickListener = new InfoWindow.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick() {
                reverseGeoCode(latLng);
                //����InfoWindow
                mBaiduMap.hideInfoWindow();
            }
        };
        // ����infowindow
        InfoWindow infoWindow = new InfoWindow(bitmapDescriptor, latLng, -47,
                infoWindowClickListener);

        // ��ʾInfoWindow
        mBaiduMap.showInfoWindow(infoWindow);
    }

    /**
     * ��λ���ҵ�λ��
     */
    public void centerToMyLocation() {
        LatLng latLng = new LatLng(mLatitude, mLongtitude);
        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
        mBaiduMap.animateMapStatus(msu);
    }

    /*
    * �ж��û�������״̬
    * */
    public void UserIndoorState(BDLocation location) {
        if (location.getUserIndoorState() == BDLocation.USER_INDDOR_TRUE) {
            mLocationClient.startIndoorMode();
            Log.i(TAG, "�û���������");
            float radius = location.getRadius();
            Log.i(TAG, String.valueOf(radius));
        } else if (location.getUserIndoorState() == BDLocation.USER_INDOOR_FALSE) {
            if (location.isIndoorLocMode() == true) {
                mLocationClient.stopIndoorMode();
            }
            Log.i(TAG, "�û���������");
        } else if (location.getUserIndoorState() == BDLocation.USER_INDOOR_UNKNOW) {
            if (location.isIndoorLocMode() == true) {
                mLocationClient.stopIndoorMode();
            }
            Log.i(TAG, "����ȷ���û��Ƿ�������");
        }
    }

    /**
     * ��λ������
     */
    private class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {

            MyLocationData data = new MyLocationData.Builder()//
                    .direction(mCurrentX)//
                    .accuracy(location.getRadius())//
                    .latitude(location.getLatitude())//
                    .longitude(location.getLongitude())//
                    .build();
            mBaiduMap.setMyLocationData(data);

            // ���¾�γ��
            mLatitude = location.getLatitude();
            mLongtitude = location.getLongitude();

            // �����Զ���ͼ��
            MyLocationConfiguration config = new MyLocationConfiguration(
                    mLocationMode, true, mIconLocation);
            mBaiduMap.setMyLocationConfigeration(config);
            UserIndoorState(location);

            if (isFirstIn) {
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


    @Override
    public void onResume() {
        super.onResume();
        // ��activityִ��onResumeʱִ��mMapView. onResume ()��ʵ�ֵ�ͼ�������ڹ���
        mMapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        // ������λ
        mBaiduMap.setMyLocationEnabled(true);
        if (!mLocationClient.isStarted())
            mLocationClient.start();
        // �������򴫸���
        myOrientationListener.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        // ��activityִ��onDestroyʱִ��mMapView.onDestroy()��ʵ�ֵ�ͼ�������ڹ���
        mMapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();

        // ֹͣ��λ
        mBaiduMap.setMyLocationEnabled(false);
        mLocationClient.stop();
        // ֹͣ���򴫸���
        myOrientationListener.stop();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // ��activityִ��onDestroyʱִ��mMapView.onDestroy()��ʵ�ֵ�ͼ�������ڹ���
        mMapView.onDestroy();
        bitmap.recycle();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        Log.e(TAG, "onCreateOptionsMenu()");
        super.onCreateOptionsMenu(menu, inflater);
        menu.add(0, 0, 0, "��ͨ��ͼ").setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        menu.add(0, 1, 0, "���ǵ�ͼ").setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        menu.add(0, 2, 0, "ʵʱ��ͨ(off)").setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        menu.add(0, 3, 0, "�ҵ�λ��").setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        menu.add(0, 4, 0, "��ͨģʽ").setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        menu.add(0, 5, 0, "����ģʽ").setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        menu.add(0, 6, 0, "����ģʽ").setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
    }

}
