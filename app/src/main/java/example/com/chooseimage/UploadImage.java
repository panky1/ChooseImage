package example.com.chooseimage;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import retrofit.RestAdapter;
import retrofit.RetrofitError;

/**
 * Created by dilip on 4/29/16.
 */
public class UploadImage extends AppCompatActivity implements View.OnClickListener{
    private static final String UPLOAD_URL = " ";
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final String PREF_NAME = "LoginPreference";
    ProgressDialog progressDialog;
    ImageView profile_image;
    int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    public static final String LoginPreference = "LoginPreference";
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    int IS_BASE_64 = 0;
    Boolean isInternetPresent = false;
    String base_64_string = null;
    Bitmap LAST_STORED = null ;
    Button btupload;
    SharedPreferences sharedpreferences;
    private TextView username;
    private TextView mobile;
//    private Uri uri;
    private String KEY_IMAGE = "image";
    private String KEY_NAME = "name";
    private Bitmap bitmap;
    String user_id;
    String image_path = null;
    private String image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        btupload=(Button)findViewById(R.id.btupload);
        profile_image = (ImageView)findViewById(R.id.profilePicture);
        username = (TextView)findViewById(R.id.UserName);
        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        btupload.setOnClickListener(this);
//        sharedpreferences = getSharedPreferences(LoginPreference, Context.MODE_PRIVATE);
//        sharedpreferences.contains("user_id");
//            user_id =  sharedpreferences.getString("user_id", "");
//        Log.d("CheckUserId",""+user_id);
        SharedPreferences shared = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        user_id = (shared.getString("user_id", ""));
        Log.d("CheckUserId",""+user_id);
    }
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[item].equals("Choose from Library")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            SELECT_FILE);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (resultCode == Activity.RESULT_OK) {
//            if (requestCode == SELECT_FILE)
//                onSelectFromGalleryResult(data);
////                getBitmapFromUri(uri);
//            else if (requestCode == REQUEST_CAMERA)
//                onCaptureImageResult(data);
//        }
//    }

//    private void onCaptureImageResult(Intent data) {
//        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
//
//        File destination = new File(Environment.getExternalStorageDirectory(),
//                "SkipWait_"+System.currentTimeMillis() + ".jpg");
//
//        FileOutputStream fo;
//        try {
//            destination.createNewFile();
//            fo = new FileOutputStream(destination);
//            fo.write(bytes.toByteArray());
//            fo.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        profile_image.setImageBitmap(thumbnail);
//        IS_BASE_64 = 1;
//        LAST_STORED = thumbnail;
//    }
    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        profile_image.setImageBitmap(thumbnail);
        IS_BASE_64 = 1;
        LAST_STORED = thumbnail;
    }
//    private void onSelectFromGalleryResult(Intent data) {
//        Uri selectedImageUri = data.getData();
//        String[] projection = { MediaStore.MediaColumns.DATA };
//        Cursor cursor = this.managedQuery(selectedImageUri, projection, null, null,
//                null);
//        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
//        cursor.moveToFirst();
//        String selectedImagePath = cursor.getString(column_index);
//        Bitmap bm;
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//        BitmapFactory.decodeFile(selectedImagePath, options);
//        final int REQUIRED_SIZE = 200;
//        int scale = 1;
//        while (options.outWidth / scale / 2 >= REQUIRED_SIZE
//                && options.outHeight / scale / 2 >= REQUIRED_SIZE)
//            scale *= 2;
//        options.inSampleSize = scale;
//        options.inJustDecodeBounds = false;
//        bm = BitmapFactory.decodeFile(selectedImagePath, options);
//
//        profile_image.setImageBitmap(bm);
//        IS_BASE_64 = 1;
//        LAST_STORED = bm;
//    }
    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Uri selectedImageUri = data.getData();
        String[] projection = { MediaStore.MediaColumns.DATA };
        Cursor cursor = managedQuery(selectedImageUri, projection, null, null,
                null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        Log.d("CheckINDEX",""+column_index);
        cursor.moveToFirst();

        String selectedImagePath = cursor.getString(column_index);

        Bitmap bm;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(selectedImagePath, options);
        final int REQUIRED_SIZE = 200;
        int scale = 1;
        while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                && options.outHeight / scale / 2 >= REQUIRED_SIZE)
            scale *= 2;
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(selectedImagePath, options);

        profile_image.setImageBitmap(bm);
        IS_BASE_64 = 1;
        LAST_STORED = bm;
//        Uri uri = data.getData();
//        String[] projection = {MediaStore.Images.Media.DATA};
//
//        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
//        cursor.moveToFirst();
//
//        int columnIndex = cursor.getColumnIndex(projection[0]);
//        String filePath = cursor.getString(columnIndex);
//        cursor.close();
//        Bitmap image = BitmapFactory.decodeFile(filePath);
//        profile_image.setImageBitmap(image);
    }

    @Override
    public void onClick(View view) {
        if(view == btupload){
            uploadImage();
        }
    }

    private void uploadImage() {
//Showing the progress dialog

        if(IS_BASE_64==1){
            image_path = Convert_BitMap_base64(LAST_STORED);
        }
//        ShowDialog("Updating profile please wait...");
        RestAdapter restAdapter = (RestAdapter) new RestAdapter.Builder().setEndpoint(getResources().getString(R.string.master_url)).build();
        final MyAPI post = restAdapter.create(MyAPI.class);

       /* Log.d("debug", user_id+"---"+ image_path+"---"+ String.valueOf(ProfileName.getText())+"---"+String.valueOf(ProfileEmail.getText())+"---"+
                String.valueOf(DOB.getText())+"---"+ String.valueOf(image_status));*/
        post.userEditProfile1(user_id,image_path, new retrofit.Callback<User>() {

            @Override
                    public void success(User res, retrofit.client.Response response) {
                        if (!res.getError()) {
//                            DismissDialog();
                            Toast.makeText(UploadImage.this, res.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                            Log.d("Checkres",""+res);
//                            ComplexPreferences complexPrefenreces = objectPreference.getComplexPreference();
//                            if (complexPrefenreces != null) {
//                                complexPrefenreces.putObject("user", res);
//                                complexPrefenreces.commit();
//                            } else {
//                                android.util.Log.e("profile Update", "Preference is null");
//                            }
//                            Intent returnIntent = new Intent();
//                            MyProfileEditActivity.this.setResult(Activity.RESULT_OK, returnIntent);
//                            MyProfileEditActivity.this.supportFinishAfterTransition();
                        } else {
                            Toast.makeText(UploadImage.this, res.getMessage(),
                                    Toast.LENGTH_SHORT).show();
//                            DismissDialog();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
//                        DismissDialog();
                        Log.d("reterror", "" + error.getCause());
                        Toast.makeText(UploadImage.this, "Profile update failed. Please try again later",
                                Toast.LENGTH_SHORT).show();
//                                failOnUnexpectedError(error);
                    }
                });
    }

    private String Convert_BitMap_base64(Bitmap bitmapm) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmapm.compress(Bitmap.CompressFormat.JPEG, 100, baos);

            byte[] byteArrayImage = baos.toByteArray();
            String imagebase64string = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
            return  imagebase64string;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
//                getBitmapFromUri(uri);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
//        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
//            Uri filePath = data.getData();
//            try {
//                //Getting the Bitmap from Gallery
//                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
//                //Setting the Bitmap to ImageView
//                profile_image.setImageBitmap(bitmap);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
    }

//    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
//        ParcelFileDescriptor parcelFileDescriptor =
//                getContentResolver().openFileDescriptor(uri, "r");
//        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
//        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
//        parcelFileDescriptor.close();
//        return image;
//    }

