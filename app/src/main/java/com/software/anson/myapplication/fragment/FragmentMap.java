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
    // 定位相关
    private LocationClient mLocationClient;
    private MyLocationListener mLocationListener;
    private boolean isFirstIn = true;
    private double mLatitude;
    private double mLongtitude;
    // 自定义定位图标
    private BitmapDescriptor mIconLocation;
    private MyOrientationListener myOrientationListener;
    private float mCurrentX;
    private MyLocationConfiguration.LocationMode mLocationMode;
    //设备定位相关
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
        //获取地图控件引用
        View v = inflater.inflate(R.layout.fragment_map, container, false);
        mToolbar4 = (Toolbar) v.findViewById(R.id.toolbar_map);

        // toolbar_device.setLogo(R.drawable.ic_launcher);
        mToolbar4.setTitle("");// 标题的文字需在setSupportActionBar之前，不然会无效
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
                            item.setTitle("实时交通(off)");
                        } else {
                            mBaiduMap.setTrafficEnabled(true);
                            item.setTitle("实时交通(on)");
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
//                overlayBtn.setText("设备已定位");
//                try {
//                    addMarkerOverlay();
//                } catch (Throwable throwable) {
//                    throwable.printStackTrace();
//                }
//            }
//        });

        // 初始化定位
        initLocation();
        isFirstIn = false;

        //测距
        LatLng gp1 = new LatLng(latitude, longitude);
        LatLng gp2 = new LatLng(mLatitude, mLongtitude);
        float distance = (float) DistanceUtil.getDistance(gp1, gp2);
        Log.i(TAG, "两点距离为：" + distance);
        if (distance > 50) {
            Boolean warming = true;
            Boolean command = true;
        }

        return v;
    }


    /*
    *初始化手机位置
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

        // 初始化图标
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

        // 对marker覆盖物添加点击事件
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker arg0) {
                if (arg0 == marker1) {
                    final LatLng latLng = arg0.getPosition();
                    // 将经纬度转换成屏幕上的点
                    // Point point =
                    // bdMap.getProjection().toScreenLocation(latLng);
                    Toast.makeText(getActivity(), latLng.toString(),
                            Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

        /**
         * 地图点击事件
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
     * 反地理编码得到地址信息
     */
    private void reverseGeoCode(LatLng latLng) {
        // 创建地理编码检索实例
        GeoCoder geoCoder = GeoCoder.newInstance();
        //
        OnGetGeoCoderResultListener listener = new OnGetGeoCoderResultListener() {
            // 反地理编码查询结果回调函数
            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
                if (result == null
                        || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    // 没有检测到结果
                    Toast.makeText(getActivity(), "抱歉，未能找到结果",
                            Toast.LENGTH_LONG).show();
                }
                Toast.makeText(getActivity(),
                        "位置：" + result.getAddress(), Toast.LENGTH_LONG)
                        .show();
            }

            // 地理编码查询结果回调函数
            @Override
            public void onGetGeoCodeResult(GeoCodeResult result) {
                if (result == null
                        || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    // 没有检测到结果
                }
            }
        };
        // 设置地理编码检索监听者
        geoCoder.setOnGetGeoCodeResultListener(listener);
        //
        geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(latLng));
        // 释放地理编码检索实例
        // geoCoder.destroy();
    }

    /**
     * 添加标注覆盖物
     */
    private void addMarkerOverlay() {
        mBaiduMap.clear();

//        MapCalculate mapCalculate = new MapCalculate();
//        float latitude = mapCalculate.BaiduY;
//        float longitude = mapCalculate.BaiduX;
        // 定义marker坐标点
        LatLng point = new LatLng(latitude, longitude);

        // 构建markerOption，用于在地图上添加marker
        OverlayOptions options = new MarkerOptions()//
                .position(point)// 设置marker的位置
                .icon(bitmap)// 设置marker的图标
                .zIndex(9)// 設置marker的所在層級
                .draggable(true);// 设置手势拖拽
        // 在地图上添加marker，并显示
        marker1 = (Marker) mBaiduMap.addOverlay(options);

    }

    /**
     * 显示弹出窗口覆盖物
     */
    private void displayInfoWindow(final LatLng latLng) {
        // 创建infowindow展示的view
        Button btn = new Button(getActivity().getApplicationContext());
        btn.setBackgroundResource(R.drawable.popup);
        btn.setTextColor(R.color.balck);
        btn.setText("点击获取详细地址~");
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory
                .fromView(btn);
        // infowindow点击事件
        InfoWindow.OnInfoWindowClickListener infoWindowClickListener = new InfoWindow.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick() {
                reverseGeoCode(latLng);
                //隐藏InfoWindow
                mBaiduMap.hideInfoWindow();
            }
        };
        // 创建infowindow
        InfoWindow infoWindow = new InfoWindow(bitmapDescriptor, latLng, -47,
                infoWindowClickListener);

        // 显示InfoWindow
        mBaiduMap.showInfoWindow(infoWindow);
    }

    /**
     * 定位到我的位置
     */
    public void centerToMyLocation() {
        LatLng latLng = new LatLng(mLatitude, mLongtitude);
        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
        mBaiduMap.animateMapStatus(msu);
    }

    /*
    * 判断用户室内外状态
    * */
    public void UserIndoorState(BDLocation location) {
        if (location.getUserIndoorState() == BDLocation.USER_INDDOR_TRUE) {
            mLocationClient.startIndoorMode();
            Log.i(TAG, "用户处于室内");
            float radius = location.getRadius();
            Log.i(TAG, String.valueOf(radius));
        } else if (location.getUserIndoorState() == BDLocation.USER_INDOOR_FALSE) {
            if (location.isIndoorLocMode() == true) {
                mLocationClient.stopIndoorMode();
            }
            Log.i(TAG, "用户处于室外");
        } else if (location.getUserIndoorState() == BDLocation.USER_INDOOR_UNKNOW) {
            if (location.isIndoorLocMode() == true) {
                mLocationClient.stopIndoorMode();
            }
            Log.i(TAG, "不能确定用户是否处于室内");
        }
    }

    /**
     * 定位监听器
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

            // 更新经纬度
            mLatitude = location.getLatitude();
            mLongtitude = location.getLongitude();

            // 设置自定义图标
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
        // 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        // 开启定位
        mBaiduMap.setMyLocationEnabled(true);
        if (!mLocationClient.isStarted())
            mLocationClient.start();
        // 开启方向传感器
        myOrientationListener.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        // 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();

        // 停止定位
        mBaiduMap.setMyLocationEnabled(false);
        mLocationClient.stop();
        // 停止方向传感器
        myOrientationListener.stop();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
        bitmap.recycle();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        Log.e(TAG, "onCreateOptionsMenu()");
        super.onCreateOptionsMenu(menu, inflater);
        menu.add(0, 0, 0, "普通地图").setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        menu.add(0, 1, 0, "卫星地图").setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        menu.add(0, 2, 0, "实时交通(off)").setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        menu.add(0, 3, 0, "我的位置").setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        menu.add(0, 4, 0, "普通模式").setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        menu.add(0, 5, 0, "跟随模式").setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        menu.add(0, 6, 0, "罗盘模式").setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
    }

}
