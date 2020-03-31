# GetCoordinatesSample_Kotlin

## 動作

fabを押すと位置情報取得が始まり、取得できた場合画面に位置情報を表示する。


## 得られた知見

1. GoogleのplayStoreをアンドロイドスタジオにインストールする事が必要。もしLocationServicesクラスをインポートできなかったら。
  implementation 'com.google.android.gms:play-services-location:17.0.0'
1. パーミッションとして以下を追記する。 uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"
