package info.fashion.bag.modules.menu.reserve.register_order;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import info.fashion.bag.R;
import info.fashion.bag.apis.ApiRetrofitClient;
import info.fashion.bag.interfaces.OrderInterface;
import info.fashion.bag.interfaces.ShoppingCarInterface;
import info.fashion.bag.models.JsonRequest;
import info.fashion.bag.models.Order;
import info.fashion.bag.models.User;
import info.fashion.bag.modules.menu.reserve.ReserveActivity;
import info.fashion.bag.utilities.BaseActivity;
import info.fashion.bag.utilities.Constant;
import info.fashion.bag.utilities.JsonPretty;
import info.fashion.bag.utilities.NetworkHelper;
import info.fashion.bag.utilities.PreferencesHelper;
import info.fashion.bag.utilities.ProgressDialogHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderRegisterOne extends BaseActivity {

    @BindView(R.id.tv_date) TextView mDate;
    @BindView(R.id.et_address) EditText mAddress;
    @BindView(R.id.et_contact_phone) EditText mContactPhone;

    @BindView(R.id.btn_register_order) Button btnRegister;
    @BindView(R.id.iv_back) ImageView btnBack;

    @BindView(R.id.tv_cash) TextView mCash;
    @BindView(R.id.tv_coins) TextView mCoins;

    @BindView(R.id.rl_pay_cash) LinearLayout mLLPayCash;
    @BindView(R.id.sw_pay_cash) Switch mSWitchCash;

    @BindView(R.id.rl_pay_card) LinearLayout mLLPayCard;
    @BindView(R.id.sw_pay_card) Switch mSWitchCard;

    @BindView(R.id.rl_pay_coins) LinearLayout mLLPayCoins;
    @BindView(R.id.sw_pay_coins) Switch mSWitchCoins;

    private String TAG = OrderRegisterOne.class.getSimpleName();
    public static Activity activity;
    private Context ctx = this;

    private ProgressDialogHelper mPD;
    private int cantidad = 0;
    private int coins = 0;
    private float cash = 0;

    private int state_cash = 0;
    private int state_card = 0;
    private int state_coins = 0;

    String date = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_register_one);
        initButterKinife();
        init();
        setDetail();
        initSwitches();

        setCurrentDate();
        mAddress.setText("Miguel de Cervantes Saavedra 148");
        mContactPhone.setText("956573855");

        mPD = new ProgressDialogHelper(ctx);

        activity = this;
    }

    public void init(){
        cash = getIntent().getFloatExtra("cash", 0);
        coins = getIntent().getIntExtra("coins", 0);
        cantidad = getIntent().getIntExtra("cantidad", 0);
    }

    public void setDetail(){

        mCash.setText("S/."+cash);
        if (coins == 0){
            mCoins.setText("No Aplica");
        }else {
            mCoins.setText(""+coins);
        }


    }

    public void setCurrentDate(){

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        date = df.format(Calendar.getInstance().getTime());

        mDate.setText("Fecha del Pedido: "+date);
    }

    public void initButterKinife(){
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @OnClick({R.id.btn_register_order, R.id.iv_back})
    public void onCllick(View view) {
        switch (view.getId()){
            case R.id.btn_register_order:
                verifyShoppingCar();
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    public void verifyShoppingCar(){
        mPD.showPD();
        User user = PreferencesHelper.getMyUserPref(ctx);
        if (verifyFields()){
            if(NetworkHelper.isNetworkAvailable(ctx)){
                ShoppingCarInterface mInterface = ApiRetrofitClient.getRetrofitClient().create(ShoppingCarInterface.class);
                Call<JsonRequest> mCall = mInterface.getShoppingCarByUser(user.getId_usuario());
                mCall.enqueue(new Callback<JsonRequest>() {
                    @Override
                    public void onResponse(Call<JsonRequest> call, Response<JsonRequest> response) {
                        mPD.dimissPD();
                        Log.d(TAG, "Retrofit Response verifyShoppingCar: "+JsonPretty.getPrettyJson(response));

                        if(response.body().getResults().getShopping_car() != null){
                            if(response.body().getResults().getShopping_car().size() > 0){
                                Log.d(TAG, "-------> TIENE CARRITO DE COMPRAS");
                                int id_carrito_compra = response.body().getResults().getShopping_car().get(0).getId_carrito_compra();
                                registerService(id_carrito_compra);
                            }else {
                                mPD.dimissPD();
                                Log.d(TAG, "NO TIENE CARRITO DE COMPRAS");
                            }

                        }else {
                            mPD.dimissPD();
                            Log.d(TAG, "NO DATA");
                        }

                    }

                    @Override
                    public void onFailure(Call<JsonRequest> call, Throwable t) {
                        Toast.makeText(ctx, getResources().getString(R.string.server_problems), Toast.LENGTH_SHORT).show();
                        mPD.dimissPD();
                    }
                });
            }else{
                mPD.dimissPD();
                Toast.makeText(ctx, getResources().getString(R.string.network_problems), Toast.LENGTH_SHORT).show();
            }
        }else{
            mPD.dimissPD();
            if (coins == 0){
                if (state_cash == 0 || state_card == 0 ){
                    Toast.makeText(ctx, "Debe escoger un método de pago", Toast.LENGTH_SHORT).show();
                }
            }else {
                if (state_coins == 0) {
                    Toast.makeText(ctx, "Debe escoger un método de pago", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

    public void registerService(int id_carrito_compra){
        mPD.showPD();
        if (verifyFields()){
            mPD.dimissPD();
            Toast.makeText(ctx, "Ok", Toast.LENGTH_SHORT).show();

            if(NetworkHelper.isNetworkAvailable(ctx)){
                final User user = PreferencesHelper.getMyUserPref(ctx);

                Order order = new Order();
                order.setId_usuario(user.getId_usuario());
                order.setId_carrito_compra(id_carrito_compra);
                order.setPago_fichas(coins);
                order.setPago_efectivo(cash);
                order.setCantidad(cantidad);
                order.setTipo_pago(getPaymentType());
                order.setFecha(date);
                order.setDireccion(getAddress());
                order.setTelefono_contacto(getContactPhone());

                OrderInterface mInterface = ApiRetrofitClient.getRetrofitClient().create(OrderInterface.class);
                Call<JsonRequest> mCall = mInterface.registerOrder(order);

                mCall.enqueue(new Callback<JsonRequest>() {
                    @Override
                    public void onResponse(Call<JsonRequest> call, Response<JsonRequest> response) {
                        mPD.dimissPD();
                        Log.d(TAG, "Retrofit Response registerService: "+ JsonPretty.getPrettyJson(response));

                        if(response.body().getStatus().getCode() == 200){
                            Log.d(TAG, "-------> Pedido registrado");
                            Toast.makeText(ctx, "Pedido registrado", Toast.LENGTH_SHORT).show();
                            user.setTotal_fichas(user.getTotal_fichas()-coins);
                            PreferencesHelper.setMyUserPref(ctx, user);
                            Toast.makeText(ctx, "Fichas actuales: "+user.getTotal_fichas(), Toast.LENGTH_SHORT).show();
                            ReserveActivity.activity.finish();
                            finish();
                        }else {
                            Log.d(TAG, "Pedido no Registrado");
                            Toast.makeText(ctx, "Pedido no Registrado", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<JsonRequest> call, Throwable t) {
                        mPD.dimissPD();
                        Toast.makeText(ctx, getResources().getString(R.string.server_problems), Toast.LENGTH_SHORT).show();
                    }
                });
            }else{
                mPD.dimissPD();
                Toast.makeText(ctx, getResources().getString(R.string.network_problems), Toast.LENGTH_SHORT).show();
            }

        }else {
            mPD.dimissPD();
            if (coins == 0){
                if (state_cash == 0 || state_card == 0 ){
                    Toast.makeText(ctx, "Debe escoger un método de pago", Toast.LENGTH_SHORT).show();
                }
            }else {
                if (state_coins == 0) {
                    Toast.makeText(ctx, "Debe escoger un método de pago", Toast.LENGTH_SHORT).show();
                }
            }
            //Toast.makeText(ctx, "Complete los campos obligatorios", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public String getAddress(){
        return mAddress.getText().toString();
    }

    public String getContactPhone(){
        return mContactPhone.getText().toString();
    }

    public boolean verifyFields(){
        if(getAddress().equals("")){
            mAddress.setError("Debe ingresar una dirección");
            return false;
        }

        if(getAddress().length() == 0){
            mAddress.setError("Debe ingresar una dirección");
            return false;
        }

        if (getAddress().length() < 6){
            mAddress.setError("Mínimo 6 caracteres");
            return false;
        }

        if(getContactPhone().length() == 0){
            mContactPhone.setError("Debe ingresar un teléfono de contacto");
            return false;
        }

        if(getContactPhone().length() < 7){
            mContactPhone.setError("El teléfono de contacto debe contener 7 o 9 dígitos");
            return false;
        }

        if(getContactPhone().length() == 8){
            mContactPhone.setError("El teléfono de contacto debe contener 7 o 9 dígitos");
            return false;
        }

        if(coins > 0 && cash > 0){

            if (state_cash == 0) {
                return false;
            }else if(state_cash == 1){
                return true;
            }else if (state_card == 0){
                return false;
            }else if (state_card == 1){
                return true;
            }

        }else if (cash == 0){
            if (state_coins == 0){
                return false;
            }else{
                return true;
            }
        }

        return true;
    }

    public String getPaymentType(){

        String pay = "";

        if (cash != 0 && coins != 0){
            if(state_cash == 1){
                pay = Constant.ORDER_PAY_EFFECTIVE_COINS;
            }else if (state_card == 1){
                pay = Constant.ORDER_PAY_CREDIT_CARD_COINS;
            }
        }else if (cash != 0){
            if(state_cash == 1){
                pay = Constant.ORDER_PAY_EFFECTIVE;
            }else if (state_card == 1){
                pay = Constant.ORDER_PAY_CREDIT_CARD;
            }
        }else {
            pay = Constant.ORDER_PAY_COINS;
        }
        return pay;
    }

    public void initSwitches(){

        if (cash == 0){
            mLLPayCoins.setVisibility(View.VISIBLE);
            mLLPayCash.setVisibility(View.GONE);
            mLLPayCard.setVisibility(View.GONE);
        }else {
            mLLPayCash.setVisibility(View.VISIBLE);
            mLLPayCard.setVisibility(View.VISIBLE);
            mLLPayCoins.setVisibility(View.GONE);
        }

        mSWitchCoins.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    state_coins = 1;
                }else{
                    state_coins = 0;
                }
            }
        });

        mSWitchCash.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    mSWitchCard.setChecked(false);
                    state_cash = 1;
                }else{
                    state_cash = 0;
                }
            }
        });

        mSWitchCard.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    mSWitchCash.setChecked(false);
                    state_card = 1;
                }else{
                    state_card = 0;
                }
            }
        });

    }

}
