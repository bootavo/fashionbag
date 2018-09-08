package info.fashion.bag.modules.auth.register;

import android.Manifest;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
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
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import info.fashion.bag.R;
import info.fashion.bag.apis.ApiRetrofitClient;
import info.fashion.bag.interfaces.UserInterface;
import info.fashion.bag.models.Token;
import info.fashion.bag.utilities.Constant;
import info.fashion.bag.utilities.ImagePicker;
import info.fashion.bag.utilities.JsonPretty;
import info.fashion.bag.utilities.NetworkHelper;
import info.fashion.bag.utilities.ProgressDialogHelper;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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

    private String TAG = RegisterActivity.class.getSimpleName();
    private ProgressDialogHelper mPD;
    private Context ctx = this;

    private static final String AUTHORITY="info.fashion.bag";
    private static final int PICK_IMAGE_ID = 100;
    private Uri fileUri = null;

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

        return true;

    }

    public void openGallery(){
        Intent chooseImageIntent = ImagePicker.getPickImageIntent(this);
        startActivityForResult(chooseImageIntent, PICK_IMAGE_ID);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case PICK_IMAGE_ID:
                Bitmap bitmap = ImagePicker.getImageFromResult(this, resultCode, data);
                if(bitmap != null){
                    bitmap.recycle();
                    mPicture.setImageBitmap(bitmap);
                    fileUri = data.getData();
                    //service();
                }else {
                    mPicture.setImageResource(R.drawable.ic_user);
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }

    }

    public void service(){
        mPD.showPD();
        if(verifyRegister()){
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
                RequestBody tipo_registro = RequestBody.create(MediaType.parse("text/plain"), Constant.REGISTER_APP);

                UserInterface mInterface = ApiRetrofitClient.getRetrofitClient().create(UserInterface.class);
                Call<Token> mCall = null;
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
                            tipo_registro);
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
                            tipo_registro);
                }

                Log.d(TAG, "------> email: "+getEmail());
                Log.d(TAG, "------> pass : "+ getPassword());
                mCall.enqueue(new Callback<Token>() {
                    @Override
                    public void onResponse(Call<Token> call, Response<Token> response) {
                        Log.d(TAG, "Retrofit Response: "+JsonPretty.getPrettyJson(response));
                    }

                    @Override
                    public void onFailure(Call<Token> call, Throwable t) {
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
                service();
                Toast.makeText(ctx, "Registrando", Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_picture:
                permissions();
                break;
        }
    }

}
