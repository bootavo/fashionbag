package com.tarjetic.store.modules.menu.reserve.register_order;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.tarjetic.store.R;
import com.tarjetic.store.apis.ApiRetrofitClient;
import com.tarjetic.store.interfaces.OrderInterface;
import com.tarjetic.store.interfaces.ShoppingCarInterface;
import com.tarjetic.store.models.Districts;
import com.tarjetic.store.models.JsonRequest;
import com.tarjetic.store.models.Order;
import com.tarjetic.store.models.User;
import com.tarjetic.store.modules.menu.reserve.ReserveActivity;
import com.tarjetic.store.utilities.BaseActivity;
import com.tarjetic.store.utilities.Constant;
import com.tarjetic.store.utilities.JsonPretty;
import com.tarjetic.store.utilities.NetworkHelper;
import com.tarjetic.store.utilities.PreferencesHelper;
import com.tarjetic.store.utilities.ProgressDialogHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderRegisterOne extends BaseActivity {

    @BindView(R.id.tv_date) TextView mDate;
    @BindView(R.id.et_address) EditText mAddress;
    @BindView(R.id.et_contact_phone) EditText mContactPhone;
    @BindView(R.id.et_total_price) EditText mPaymentPrice;

    @BindView(R.id.btn_register_order) Button btnRegister;
    @BindView(R.id.iv_back) ImageView btnBack;

    @BindView(R.id.tv_cash) TextView mCash;
    @BindView(R.id.tv_coins) TextView mCoins;
    @BindView(R.id.tv_retrieve_coins) TextView mRetrieveCoins;

    @BindView(R.id.rl_pay_cash) LinearLayout mLLPayCash;
    @BindView(R.id.sw_pay_cash) Switch mSWitchCash;

    @BindView(R.id.rl_pay_card) LinearLayout mLLPayCard;
    @BindView(R.id.sw_pay_card) Switch mSWitchCard;

    @BindView(R.id.rl_pay_coins) LinearLayout mLLPayCoins;
    @BindView(R.id.sw_pay_coins) Switch mSWitchCoins;

    @BindView(R.id.spinner_district) Spinner spinner;

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
    String time = "";
    int district_id = 0;
    String district = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_register_one);
        initButterKinife();
        init();
        setDetail();
        initSwitches();

        setCurrentDate();
//        mAddress.setText("Miguel de Cervantes Saavedra 148");
//        mContactPhone.setText("956573855");

        mPD = new ProgressDialogHelper(ctx);

        activity = this;

        final List<Districts> listDistricts = Districts.getDistricts();
        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(ctx, listDistricts);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(context, "ID_BANCO: "+bancoEntities.get(position).getId(), Toast.LENGTH_SHORT).show();
                district_id = listDistricts.get(position).getDistrict_id();
                district = listDistricts.get(position).getDistrict();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                district_id = listDistricts.get(0).getDistrict_id();
            }
        });
    }

    public void init(){
        cash = getIntent().getFloatExtra("cash", 0);
        coins = getIntent().getIntExtra("coins", 0);
        cantidad = getIntent().getIntExtra("cantidad", 0);
    }

    public void setDetail(){

        mCash.setText("S/."+cash);
        mRetrieveCoins.setText(""+Constant.getCoinsByOrderDone(cash));
        if (coins == 0){
            mCoins.setText("No Aplica");
        }else {
            mCoins.setText(""+coins);
        }

    }

    public void setCurrentDate(){

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        date = df.format(Calendar.getInstance().getTime());

        Locale locale = new Locale("es", "pe");
        SimpleDateFormat format = new SimpleDateFormat("HH", locale);
        String hour = format.format(new Date());

        SimpleDateFormat format2 = new SimpleDateFormat("mm", locale);
        String minutes = format2.format(new Date());

        time = " - "+hour+":"+minutes;

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
            if(coins > 0 || cash > 0){

                if (state_cash == 1 || state_card == 1 || state_coins == 1) {

                }else if(state_cash == 0 || state_card == 0){
                    Toast.makeText(ctx, "Debe escoger un método de pago", Toast.LENGTH_SHORT).show();
                }

            }else if (cash == 0){
                if (state_coins == 0){
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
                order.setFecha(date+time);
                order.setDireccion(getDistrict()+" - "+getAddress());
                order.setMonto_total(getPaymentPrice());
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
            if(coins > 0 || cash > 0){

                if (state_cash == 1 || state_card == 1 || state_coins == 1) {

                }else if(state_cash == 0 || state_card == 0){
                    Toast.makeText(ctx, "Debe escoger un método de pago", Toast.LENGTH_SHORT).show();
                }

            }else if (cash == 0){
                if (state_coins == 0){
                    Toast.makeText(ctx, "Debe escoger un método de pago", Toast.LENGTH_SHORT).show();
                }
            }
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

    public String getDistrict(){
        return district;
    }

    public float getPaymentPrice() {
        float aux = 0;
        if (mPaymentPrice.getText().toString() != null){
            if (!mPaymentPrice.getText().toString().equals("")){
                aux = Float.parseFloat(mPaymentPrice.getText().toString());
            }
        }else{
            aux = 0;
        }
        return aux;
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

        if(cash > 0){
            if (state_card == 1){
                Toast.makeText(ctx, "Debe escoger un metodo de pago", Toast.LENGTH_SHORT).show();
                return true;
            }else {
                if (getPaymentPrice() <= 0) {
                    mPaymentPrice.setError("Debes ingresar el monto con el cual pagarás");
                    return false;
                }else if(getPaymentPrice() < cash){
                    mPaymentPrice.setError("El monto ingresado es menor al monto total del pedido" +
                            "");
                    return false;
                }
            }
        }

        if (district_id <= 0) {
            Toast.makeText(ctx, "Debe escoger un distrito de entrega", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(coins > 0 || cash > 0){

            if (state_cash == 1 || state_card == 1 || state_coins == 1) {
                return true;
            }else if(state_cash == 0 || state_card == 0){
                return false;
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
                    mPaymentPrice.setVisibility(View.VISIBLE);
                }else{
                    state_cash = 0;
                    mPaymentPrice.setVisibility(View.GONE);
                }
            }
        });

        mSWitchCard.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    mSWitchCash.setChecked(false);
                    state_card = 1;
                    mPaymentPrice.setVisibility(View.GONE);
                }else{
                    state_card = 0;
                }
            }
        });

    }

}
