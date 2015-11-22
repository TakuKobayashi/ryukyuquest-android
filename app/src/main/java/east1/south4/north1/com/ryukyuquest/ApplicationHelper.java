package east1.south4.north1.com.ryukyuquest;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.webkit.WebView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ApplicationHelper {

	//ImageViewを使用したときのメモリリーク対策
	public static void releaseImageView(ImageView imageView){
		if (imageView != null) {
			BitmapDrawable bitmapDrawable = (BitmapDrawable)(imageView.getDrawable());
			if (bitmapDrawable != null) {
				bitmapDrawable.setCallback(null);
			}
			imageView.setImageBitmap(null);
		}
	}

	//WebViewを使用したときのメモリリーク対策
	public static void releaseWebView(WebView webview){
		webview.stopLoading();
		webview.setWebChromeClient(null);
		webview.setWebViewClient(null);
		webview.destroy();
		webview = null;
	}

	public static String makeUrlParams(Bundle params){
		Set<String> keys = params.keySet();
		ArrayList<String> paramList = new ArrayList<String>();
		for (String key : keys) {
			paramList.add(key + "=" + params.get(key).toString());
		}
		return ApplicationHelper.join(paramList, "&");
	}

	public static String makeUrlParams(Map<String, Object> params){
		Set<String> keys = params.keySet();
		ArrayList<String> paramList = new ArrayList<String>();
		for(Map.Entry<String, Object> e : params.entrySet()) {
			paramList.add(e.getKey() + "=" + e.getValue().toString());
		}
		return ApplicationHelper.join(paramList, "&");
	}

	public static String join(String[] list, String with) {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < list.length; i++) {
			if (i != 0) { buf.append(with);}
			buf.append(list[i]);
		}
		return buf.toString();
	}

	public static String join(ArrayList<String> list, String with) {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			if (i != 0) { buf.append(with);}
			buf.append(list.get(i));
		}
		return buf.toString();
	}

	public static ArrayList<String> getSettingPermissions(Context context){
		ArrayList<String> list = new ArrayList<String>();
		PackageInfo packageInfo = null;
		try {
			packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_PERMISSIONS);
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		if(packageInfo == null || packageInfo.requestedPermissions == null) return list;

		for(String permission : packageInfo.requestedPermissions){
			list.add(permission);
		}
		return list;
	}

	public static boolean hasSelfPermission(Context context, String permission) {
		if(Build.VERSION.SDK_INT < 23) return true;
		return context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
	}

	public static Bitmap bitmapRotate(Bitmap bmp, int orientation) {
		Matrix matrix = new Matrix();
		matrix.postRotate(orientation);
		return Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
	}
}
