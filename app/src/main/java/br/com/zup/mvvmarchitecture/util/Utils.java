package br.com.zup.mvvmarchitecture.util;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.com.zup.mvvmarchitecture.MyApplication;



public class Utils {

    public static final String SP_CUSTOMER_INFO = "customer-info-key";
    public static final String SENT_TOKEN_TO_SERVER = "sentTokenToServer";
    public static final String REGISTRATION_COMPLETE = "registrationComplete";

    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = null;
        if (cm != null) {
            netInfo = cm.getActiveNetworkInfo();
        }
        return (netInfo != null && netInfo.isConnected());
    }

    /**
     * @param ctx      Context
     * @param jsonFile The file on assets
     * @return
     */
    public static String loadJSONFromAsset(Context ctx, String jsonFile) {
        String json;
        try {
            InputStream is = ctx.getAssets().open(jsonFile);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public static String loadJsonFromRawFolder(Context ctx, int rawAsset) {

        try {
            InputStream is = ctx.getResources().openRawResource(rawAsset);
            int size = is.available();

            Writer writer = new StringWriter();
            char[] buffer = new char[size];

            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
            is.close();

            return writer.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getCountry() {
        return "Brasil"; // fixme
    }

    public static boolean alreadyInList(List<?> list, Object o) {
        for (Object object : list) {
            if (o.equals(object)) return true;
        }

        return false;
    }


    public static boolean isPackageInstalled(String packageName) {
        Context context = MyApplication.getInstance();
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static void shareByPackage(Context ctx, String message, String pack) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, message);
        sendIntent.setType("text/plain");
        if (pack != null)
            sendIntent.setPackage(pack);

        try {
            ctx.startActivity(sendIntent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void shareByEmail(Context ctx, String message) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, message);
        sendIntent.setData(Uri.parse("mailto:")); // only email apps should handle this
        sendIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{});
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "White Label Invite");
        sendIntent.setType("message/rfc822");
        ctx.startActivity(sendIntent);
    }

    public static boolean useInfinity(String value) {
        try {
            return Double.valueOf(value) >= 999999 || Double.valueOf(value) < 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean useInfinity(Double value) {
        return value >= 999999 || value < 0;
    }

    public static int getNavHeight(Context ctx) {
        Resources resources = ctx.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    public static Bitmap screenShot(View view) {
        try {
            Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            view.draw(canvas);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void copyToClipboard(Context ctx, String s) {
        ClipboardManager clipboard = (ClipboardManager) ctx.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("whitelabelapp", s);
        if (clipboard != null) {
            clipboard.setPrimaryClip(clip);
        }
    }

    public static Map<String, String> sortMap(Set<Map.Entry<String, String>> unsortedMap) {

        List<Map.Entry<String, String>> list = new LinkedList<>(unsortedMap);

        // sort list based on comparator
        Collections.sort(list, (o1, o2) -> o1.getValue().compareTo(o2.getValue()));

        // put sorted list into map again
        Map<String, String> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<String, String> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

    public static void hideKeyboard(Activity activity) {
        View view = activity.findViewById(android.R.id.content);
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void showKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    public static boolean verifyLocationIsOn(Context ctx){
        LocationManager lm = (LocationManager)ctx.getSystemService(Context.LOCATION_SERVICE);
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }


}
