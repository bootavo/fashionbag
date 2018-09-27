package com.tarjetic.store.utilities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.tarjetic.store.R;
import com.tarjetic.store.models.User;

import java.io.ByteArrayOutputStream;

public class ShareAppHelper {

    public static void shareApp(Context ctx, Activity activity){

        Bitmap bitmap = BitmapFactory.decodeResource(ctx.getResources(), R.mipmap.ic_launcher); // your bitmapG
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bs);

        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            //intent.setType("*/*");
            intent.putExtra(Intent.EXTRA_SUBJECT, "FashionBag");
            intent.putExtra(Intent.EXTRA_STREAM, bs.toByteArray());

            User user = PreferencesHelper.getMyUserPref(ctx);
            String sAux = "";
            if (user != null){
                sAux = "\nDescarga Ahora Tarjetic y aprovecha nuestras ofertas en licores\n\n"+
                        "Ingresa ahora el siguiente código: "+ user.getCodigo_app() +" y obten fichas para canjearls por productos gratis";
            }else{
                sAux = "\nDescarga Ahora Tarjetic y aprovecha nuestras ofertas en licores\n\n"+
                        "además obten fichas para canjearls por productos gratis";
            }

            //sAux = sAux + "https://play.google.com/store/apps/details?id="+ getActivity().getPackageName() +"\n\n";
            sAux = sAux + " https://play.google.com/store/apps/details?id="+ activity.getPackageName()+"\n\n";
            intent.putExtra(Intent.EXTRA_TEXT, sAux);
            activity.startActivity(Intent.createChooser(intent, "choose one"));
        } catch(Exception e) {
            e.getMessage();
            e.toString();
        }

    }

}
