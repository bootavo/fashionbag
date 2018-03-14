package info.fashion.bag.utilities;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by gtufinof on 15/01/18.
 */

public class ToastHelper {

    public static void showToast(Context ctx, String message){
        Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
    }

}
