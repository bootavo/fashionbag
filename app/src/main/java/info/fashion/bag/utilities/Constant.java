package info.fashion.bag.utilities;

import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class Constant {

    public static String BASE_URL = "https://tarjeticstore.cfapps.io/api/";
    //public static String BASE_URL = "http://192.168.0.9:8080/api/";

    public static String ORDER_KIND_PAY_COINS = "FICHAS";
    public static String ORDER_KIND_PAY_CASH = "EFECTIVO";

    public static String ORDER_PAY_EFFECTIVE = "EFECTIVO";
    public static String ORDER_PAY_CREDIT_CARD = "TARJETA";
    public static String ORDER_PAY_COINS = "FICHAS";
    public static String ORDER_PAY_CREDIT_CARD_COINS = "TARJETA/FICHAS";
    public static String ORDER_PAY_EFFECTIVE_COINS = "EFECTIVO/FICHAS";

    public static String ORDER_PENDANT = "PENDIENTE";
    public static String ORDER_CANCEL = "CANCELADO";
    public static String ORDER_DONE = "ATENDIDO";

    public static String REGISTER_APP = "APP";
    public static String REGISTER_WEBPP = "WEB";
    public static String REGISTER_SHARE_APP = "SHARE_APP";

    public static float COINS_MULTIPLIER = 5;

    public static String TOKEN_FORMAT = "Token ";

    public static int NOTIFICATION_ID = 0;
    public static int NOTIFICATION_ID_BIG_IMAGE = 0;

    public static String PRODUCT_NAME = "";

    public static boolean IS_MAJORITY_USER = false;
    public static String RESERVA = "";
    public static int SALES_ORDERS_ID = 0;
    public static int SALES_ORDERS_DETAIL_ID = 0;

    public static float SALE_PRICE = 0.0f;
    public static float DIRECTOR_AMMOUNT = 0.0f;

    //UNIQUE DISCOUNT_OFFER
    public static int ID_PRODUCT = 0;
    public static float SUGGESTED_PRICE = 0.0f;
    public static float OFFER_PRICE = 0.0f;
    public static boolean ACTIVE_PERCENT = false;
    public static boolean ACTIVE_AMOUNT = false;
    public static float DISCOUNT_PERCENT = 0.0f;
    public static float DISCOUNT_AMOUNT = 0.0f;
    public static List<Integer> CATEGORIES_DISCOUNT_OFFER = null;

    public static int getCoinsByOrderDone(float total_payment){

        int coins = 0;

        if (total_payment >= 20.0 && total_payment <= 35.0){
            coins = 20;
        }else if(total_payment >= 35.1 && total_payment <= 50.0){
            coins = 45;
        }else if (total_payment >= 50.1 && total_payment <= 100){
            coins = 75;
        }else if(total_payment >= 100.1 && total_payment <= 200){
            coins = 150;
        }else if(total_payment >= 200.1 && total_payment <= 400){
            coins = 300;
        }else if(total_payment >= 400.1){
            coins = 500;
        }

        return coins;
    }

}