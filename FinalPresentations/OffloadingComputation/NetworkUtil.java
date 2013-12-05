package edu.sdsmt.ThuryWulff.sentireaerissimia;


/// http://viralpatel.net/blogs/android-internet-connection-status-network-change/
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/***
 * Used for returning if there is network connectivity on Mobile ot WIFI,
 * Source: http://viralpatel.net/blogs/android-internet-connection-status-network-change/
 * 
 * @author Viral Patel
 */
public class NetworkUtil {
     
    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int TYPE_NOT_CONNECTED = 0;
     
    /***
     * Will return the connection type
     * 
     * @author Viral Patel
     * 
     * @param context context of the current activity
     * @return int index to what connection is being used
     */
    public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
 
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI;
             
            if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE;
        }
        return TYPE_NOT_CONNECTED;
    }
     
    /***
     * Will return the connection type
     * 
     * @author Viral Patel
     * 
     * @param context context of the current activity
     * @return String String of what connection is being used
     */
    public static String getConnectivityStatusString(Context context) {
        int conn = NetworkUtil.getConnectivityStatus(context);
        String status = null;
        if (conn == NetworkUtil.TYPE_WIFI) {
            status = "Wifi enabled";
        } else if (conn == NetworkUtil.TYPE_MOBILE) {
            status = "Mobile data enabled";
        } else if (conn == NetworkUtil.TYPE_NOT_CONNECTED) {
            status = "Not connected to Internet";
        }
        return status;
    }
}
