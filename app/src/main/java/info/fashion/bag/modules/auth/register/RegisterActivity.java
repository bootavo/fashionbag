package info.fashion.bag.modules.auth.register;

import android.Manifest;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import info.fashion.bag.R;
import info.fashion.bag.apis.ApiRetrofitClient;
import info.fashion.bag.interfaces.UserInterface;
import info.fashion.bag.models.JsonRequest;
import info.fashion.bag.utilities.Constant;
import info.fashion.bag.utilities.JsonPretty;
import info.fashion.bag.utilities.NetworkHelper;
import info.fashion.bag.utilities.ProgressDialogHelper;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pl.aprilapps.easyphotopicker.EasyImage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gtufinof on 3/12/18.
 */

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.iv_picture) CircleImageView mPicture;
    @BindView(R.id.et_email) EditText mEmail;
    @BindView(R.id.et_first_name) EditText mFirrstName;
    @BindView(R.id.et_last_name) EditText mLastName;
    @BindView(R.id.et_dni) EditText mDni;
    @BindView(R.id.et_password) EditText mPassword;

    @BindView(R.id.btn_register) Button btnRegister;

    @BindView(R.id.sw_friend_code) Switch mSwitchFriendCode;
    @BindView(R.id.et_friend_code) EditText mFriendCode;

    private String TAG = RegisterActivity.class.getSimpleName();
    private ProgressDialogHelper mPD;
    private Context ctx = this;

    private static final String AUTHORITY="info.fashion.bag";
    private static final int PICK_IMAGE_ID = 100;
    private Uri fileUri = null;
    private int state_friend_code = 0;

    private static final String PHOTOS_KEY = "easy_image_photos_list";
    private ArrayList<File> photos = new ArrayList<>();

    public static final int REQUEST_CODE_CAMERA = 0012;
    public static final int REQUEST_CODE_GALLERY = 0013;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initButterKnide();
        init();
        initEventUI();
    }

    public void initButterKnide(){
        ButterKnife.bind(this);
    }

    public void init(){
        mToolbar.setTitle("");

        btnRegister.setOnClickListener(this);
        mPicture.setOnClickListener(this);
        mPD = new ProgressDialogHelper(ctx);

        setSupportActionBar(mToolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mFirrstName.setText("Prueba");
        mLastName.setText("Prueba");
        mPassword.setText("prueba");
        mDni.setText("48269083");
        mEmail.setText("tavo.tf@gmail.com");

        mSwitchFriendCode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    state_friend_code = 1;
                    mFriendCode.setVisibility(View.VISIBLE);
                }else{
                    state_friend_code = 0;
                    mFriendCode.setVisibility(View.GONE);
                }
            }
        });

    }

    public void initEventUI(){
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public String getEmail(){
        return mEmail.getText().toString().trim();
    }

    public String getFirstName(){
        return mFirrstName.getText().toString().trim();
    }

    public String getLastName(){
        return mLastName.getText().toString().trim();
    }

    public String getDni(){
        return mDni.getText().toString().trim();
    }

    public String getPassword(){
        return mPassword.getText().toString().trim();
    }

    public String getFriendCode(){
        return mFriendCode.getText().toString();
    }

    public boolean verifyRegister(){

        if(getEmail().equals("") || getEmail() == null){
            mEmail.setError("Ingrese su email");
            return false;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(getEmail()).matches()){
            mEmail.setError("Ingrese un correo valido");
            return false;
        }

        if(getPassword().equals("") || getPassword() == null){
            mPassword.setError("Ingrese su password");
            return false;
        }

        if(getFirstName().equals("") || getPassword() == null){
            mFirrstName.setError("Ingrese su nombre");
            return false;
        }

        if(getLastName().equals("") || getPassword() == null){
            mLastName.setError("Ingrese sus apellidos");
            return false;
        }

        if(getDni().equals("") || getPassword() == null){
            mDni.setError("Ingrese su dni");
            return false;
        }

        if (state_friend_code == 1){
            if (getFriendCode().equals("")){
                mFriendCode.setError("Debe ingresar el código de amigo");
                return false;
            }
        }

        return true;

    }

    public void openGallery(){
        //EasyImage.openChooserWithGallery(RegisterActivity.this, "Selecciona", 0);
        EasyImage.openGallery(RegisterActivity.this, REQUEST_CODE_GALLERY);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(PHOTOS_KEY, photos);
    }

    public void permissions(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
            }else{
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        1);
            }
            return;
        } else {
            openGallery();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        Bitmap bitmap = ImagePicker.getImageFromResult(this, requestCode, resultCode, data);
//        if(bitmap != null){
////            bitmap.recycle();
//            mPicture.setImageBitmap(bitmap);
//            fileUri = data.getData();
//            Log.d(TAG, "NO NULLLLLLLL ----------->");
//            //service();
//        }else {
//            mPicture.setImageResource(R.drawable.ic_user);
//        }

        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new EasyImage.Callbacks() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                e.printStackTrace();
            }

            @Override
            public void onImagesPicked(@NonNull List<File> imageFiles, EasyImage.ImageSource source, int type) {
                if(source.name().equals("CAMERA")){
                    File file = new File(Environment.getExternalStorageDirectory().getPath(), imageFiles.get(0).getName());
                    fileUri = Uri.fromFile(file);
                    Glide.with(RegisterActivity.this)
                            .load(file)
                            .into(mPicture);

                }else{
                    Glide.with(RegisterActivity.this)
                            .load(imageFiles.get(0))
                            .into(mPicture);
                    fileUri = data.getData();
                }
            }

            @Override
            public void onCanceled(EasyImage.ImageSource source, int type) {
                if (source == EasyImage.ImageSource.CAMERA) {
                    File photoFile = EasyImage.lastlyTakenButCanceledPhoto(RegisterActivity.this);
                    if (photoFile != null) photoFile.delete();
                }
            }
        });

    }

    private void onPhotosReturned(List<File> returnedPhotos) {
        photos.addAll(returnedPhotos);

    }

    @Override
    protected void onDestroy() {
        // Clear any configuration that was done!
        EasyImage.clearConfiguration(this);
        super.onDestroy();
    }

    public void service(){
        mPD.showPD();
        if(NetworkHelper.isNetworkAvailable(this)){
            //Create Data
            RequestBody nombres = RequestBody.create(MediaType.parse("text/plain"), getFirstName());
            RequestBody apellidos = RequestBody.create(MediaType.parse("text/plain"), getLastName());
            RequestBody usuario = RequestBody.create(MediaType.parse("text/plain"), getEmail());
            RequestBody clave = RequestBody.create(MediaType.parse("text/plain"), getPassword());
            RequestBody direccion = RequestBody.create(MediaType.parse("text/plain"), "Direccion");
            RequestBody dni = RequestBody.create(MediaType.parse("text/plain"), getDni());
            RequestBody telefono_contacto = RequestBody.create(MediaType.parse("text/plain"), "Telefono");
            RequestBody correo = RequestBody.create(MediaType.parse("text/plain"), getEmail());
            RequestBody id_rol = RequestBody.create(MediaType.parse("text/plain"), "3");

            String type_register = Constant.REGISTER_APP;
            if(state_friend_code == 1) {
                type_register = Constant.REGISTER_SHARE_APP;
            }

            RequestBody tipo_registro = RequestBody.create(MediaType.parse("text/plain"), type_register);

            RequestBody codigo_app = RequestBody.create(MediaType.parse("text/plain"), getFriendCode());

            UserInterface mInterface = ApiRetrofitClient.getRetrofitClient().create(UserInterface.class);
            Call<JsonRequest> mCall = null;
            if(fileUri != null){
                File file = new File(getRealPathFromUri(fileUri));
                RequestBody requestFile = RequestBody.create(
                        MediaType.parse(getApplicationContext().getContentResolver().getType(fileUri)),
                        file);
                MultipartBody.Part body =
                        MultipartBody.Part.createFormData("imagen", file.getName(), requestFile);

                mCall = mInterface.createUser(body,
                        nombres,
                        apellidos,
                        usuario,
                        clave,
                        direccion,
                        dni,
                        telefono_contacto,
                        correo,
                        id_rol,
                        tipo_registro,
                        codigo_app);
            }else {
                mCall = mInterface.createUser2(nombres,
                        apellidos,
                        usuario,
                        clave,
                        direccion,
                        dni,
                        telefono_contacto,
                        correo,
                        id_rol,
                        tipo_registro,
                        codigo_app);
            }

            Log.d(TAG, "------> email: "+getEmail());
            Log.d(TAG, "------> pass : "+ getPassword());
            mCall.enqueue(new Callback<JsonRequest>() {
                @Override
                public void onResponse(Call<JsonRequest> call, Response<JsonRequest> response) {
                    Log.d(TAG, "Retrofit Response: "+JsonPretty.getPrettyJson(response));
                    if (response.body().getStatus().getCode() == 200){
                        Toast.makeText(ctx, "Usuario registrado", Toast.LENGTH_SHORT).show();
                        mPD.dimissPD();
                        finish();
                    }else{
                        mPD.dimissPD();
                        Toast.makeText(ctx, "No se ha podido registrar, intentelo nuevamente", Toast.LENGTH_SHORT).show();
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
    }

    public void controlService(){
        Log.d(TAG, "state_friend_code: "+state_friend_code);
        if(verifyRegister()){
            if (state_friend_code == 1){
                Log.d(TAG, "1");
                getUserByPromoCode();
            }else{
                verifyUser();
            }
        }
    }

    public void getUserByPromoCode(){
        Log.d(TAG, "getUserByPromoCode");
        mPD.showPD();
        if(NetworkHelper.isNetworkAvailable(this)){
            UserInterface mInterface = ApiRetrofitClient.getRetrofitClient().create(UserInterface.class);
            Call<JsonRequest> mCall = mInterface.getUserbyPromoCode(getFriendCode());

            mCall.enqueue(new Callback<JsonRequest>() {
                @Override
                public void onResponse(Call<JsonRequest> call, Response<JsonRequest> response) {
                    Log.d(TAG, "Retrofit Response: "+JsonPretty.getPrettyJson(response));
                    if (response.body().getStatus().getCode() == 200){
                        Toast.makeText(ctx, "Código correcto", Toast.LENGTH_SHORT).show();
                        verifyUser();
                    }else{
                        mPD.dimissPD();
                        Toast.makeText(ctx, "Código de amigo incorrecto", Toast.LENGTH_SHORT).show();
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
    }

    public void verifyUser(){
        Log.d(TAG, "verifyUser");
        mPD.showPD();
        if(NetworkHelper.isNetworkAvailable(this)){
            UserInterface mInterface = ApiRetrofitClient.getRetrofitClient().create(UserInterface.class);
            Call<JsonRequest> mCall = mInterface.verifyUser(getEmail());

            mCall.enqueue(new Callback<JsonRequest>() {
                @Override
                public void onResponse(Call<JsonRequest> call, Response<JsonRequest> response) {
                    Log.d(TAG, "Retrofit Response: "+JsonPretty.getPrettyJson(response));
                    if (response.body().getStatus().getCode() == 200){
                        mPD.dimissPD();
                        Toast.makeText(ctx, "Email reggistrado, ingrese otro email", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(ctx, "Usuario correcto", Toast.LENGTH_SHORT).show();
                        service();
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
    }

    public String getRealPathFromUri(Uri contentUri){
        String[] proj = {MediaStore.Images.Media.DATA};

        CursorLoader loader = new CursorLoader(ctx, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();

        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        String result = cursor.getString(column_index);
        cursor.close();

        return result;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //permission accepted.
                    //Toast.makeText(ctx, "If", Toast.LENGTH_SHORT).show();
                    openGallery();
                } else {
                    //Toast.makeText(ctx, "If", Toast.LENGTH_SHORT).show();
                    //permission denied.
                }
                return;
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_register:
                controlService();
                break;
            case R.id.iv_picture:
                permissions();
                break;
        }
    }

}
