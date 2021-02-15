package com.example.bidbadnew.Fragments;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.ObjectKey;
import com.example.bidbadnew.Activities.MainActivity;
import com.example.bidbadnew.Others.SharedPrefManager;
import com.example.bidbadnew.R;
import com.example.bidbadnew.ui.profile.ProfileFragment;
import com.google.android.material.appbar.MaterialToolbar;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import static android.app.Activity.RESULT_OK;

public class EditProfile extends Fragment {

    private EditProfileViewModel mViewModel;
    ImageView imageView;

    public static EditProfile newInstance() {
        return new EditProfile();
    }

    Dialog dialog;
    String ImagePath = "image_path";
    boolean check = true;
    String ServerUploadPath = "http://easyvela.esy.es/AndroidAPI/uploadProfileImage.php";

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().findViewById(R.id.bar).setVisibility(View.GONE);
        getActivity().findViewById(R.id.fabhome).setVisibility(View.GONE);

        View view = inflater.inflate(R.layout.edit_profile_fragment, container, false);
        MaterialToolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).popBackStack();
            }
        });
        dialog = new Dialog(view.getContext());
        imageView = view.findViewById(R.id.editImage);
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_account_circle_24));

        try {
            Glide.with(getContext())
                    .load("http://easyvela.esy.es/AndroidAPI/images/"+SharedPrefManager.getInstance(getContext()).getUser().getId()+".png")
                    .skipMemoryCache(true)
                    .placeholder(getResources().getDrawable(R.drawable.ic_baseline_account_circle_24))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(imageView);
        } catch (Exception e){
            e.printStackTrace();
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();

                intent.setType("image/*");

                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 1);
            }
        });

        RecyclerView editRecycler = view.findViewById(R.id.editRecycler);
        RecyclerView editPrivate = view.findViewById(R.id.editRecycler1);
        ArrayList<Item> items = new ArrayList<>();
        ArrayList<Item> items1 = new ArrayList<>();

        items.add(new Item("Name", SharedPrefManager.getInstance(view.getContext()).getUser().getFirstname()));
        items.add(new Item("Referral code", SharedPrefManager.getInstance(view.getContext()).getUser().getFirstname() + SharedPrefManager.getInstance(view.getContext()).getUser().getId()));
        items.add(new Item("Manage Address", ""));
        items.add(new Item("Change password", ""));

        items1.add(new Item("Gender", SharedPrefManager.getInstance(view.getContext()).getUser().getGender()));
        items1.add(new Item("Date of birth", SharedPrefManager.getInstance(view.getContext()).getUser().getDob()));
        items1.add(new Item("Mobile", SharedPrefManager.getInstance(view.getContext()).getUser().getMobile()));
        items1.add(new Item("E-mail", SharedPrefManager.getInstance(view.getContext()).getUser().getEmail()));

        DividerItemDecoration itemDecorator = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divideredit));
        editRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        editRecycler.addItemDecoration(itemDecorator);

        editPrivate.setLayoutManager(new LinearLayoutManager(view.getContext()));
        editPrivate.addItemDecoration(itemDecorator);

        EditAdapter e = new EditAdapter(view.getContext(), items);
        EditAdapter e1 = new EditAdapter(view.getContext(), items1);
        editPrivate.setAdapter(e1);
        editRecycler.setAdapter(e);

        return view;
    }

    Bitmap bitmap;
    ProgressDialog progressDialog;

    public void ImageUploadToServerFunction() {

        ByteArrayOutputStream byteArrayOutputStreamObject;

        byteArrayOutputStreamObject = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStreamObject);

        byte[] byteArrayVar = byteArrayOutputStreamObject.toByteArray();

        final String ConvertImage = Base64.encodeToString(byteArrayVar, Base64.DEFAULT);

        class AsyncTaskUploadClass extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {

                super.onPreExecute();

                progressDialog = ProgressDialog.show(getContext(),"Image is Uploading","Please Wait",false,false);
            }

            @Override
            protected void onPostExecute(String string1) {

                super.onPostExecute(string1);

                // Dismiss the progress dialog after done uploading.
                progressDialog.dismiss();

                // Printing uploading success message coming from server on android app.
                // Toast.makeText(MainActivity.this,string1,Toast.LENGTH_LONG).show();

                // Setting image as transparent after done uploading.
                //imageView.setImageResource(android.R.color.transparent);

            }

            @Override
            protected String doInBackground(Void... params) {

                ImageProcessClass imageProcessClass = new ImageProcessClass();

                HashMap<String, String> HashMapParams = new HashMap<String, String>();

                HashMapParams.put("ImageName", "Profile pic");

                HashMapParams.put(ImagePath, ConvertImage);

                HashMapParams.put("id", SharedPrefManager.getInstance(getContext()).getUser().getId().toString());

                String FinalData = imageProcessClass.ImageHttpRequest(ServerUploadPath, HashMapParams);

                return FinalData;
            }
        }
        AsyncTaskUploadClass AsyncTaskUploadClassOBJ = new AsyncTaskUploadClass();

        AsyncTaskUploadClassOBJ.execute();
    }

    public class ImageProcessClass {

        public String ImageHttpRequest(String requestURL, HashMap<String, String> PData) {

            StringBuilder stringBuilder = new StringBuilder();

            try {

                URL url;
                HttpURLConnection httpURLConnectionObject;
                OutputStream OutPutStream;
                BufferedWriter bufferedWriterObject;
                BufferedReader bufferedReaderObject;
                int RC;

                url = new URL(requestURL);

                httpURLConnectionObject = (HttpURLConnection) url.openConnection();

                httpURLConnectionObject.setReadTimeout(19000);

                httpURLConnectionObject.setConnectTimeout(19000);

                httpURLConnectionObject.setRequestMethod("POST");

                httpURLConnectionObject.setDoInput(true);

                httpURLConnectionObject.setDoOutput(true);

                OutPutStream = httpURLConnectionObject.getOutputStream();

                bufferedWriterObject = new BufferedWriter(

                        new OutputStreamWriter(OutPutStream, "UTF-8"));

                bufferedWriterObject.write(bufferedWriterDataFN(PData));

                bufferedWriterObject.flush();

                bufferedWriterObject.close();

                OutPutStream.close();

                RC = httpURLConnectionObject.getResponseCode();

                if (RC == HttpsURLConnection.HTTP_OK) {

                    bufferedReaderObject = new BufferedReader(new InputStreamReader(httpURLConnectionObject.getInputStream()));

                    stringBuilder = new StringBuilder();

                    String RC2;

                    while ((RC2 = bufferedReaderObject.readLine()) != null) {

                        stringBuilder.append(RC2);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }

        private String bufferedWriterDataFN(HashMap<String, String> HashMapParams) throws UnsupportedEncodingException {

            StringBuilder stringBuilderObject;

            stringBuilderObject = new StringBuilder();

            for (Map.Entry<String, String> KEY : HashMapParams.entrySet()) {

                if (check)

                    check = false;
                else
                    stringBuilderObject.append("&");

                stringBuilderObject.append(URLEncoder.encode(KEY.getKey(), "UTF-8"));

                stringBuilderObject.append("=");

                stringBuilderObject.append(URLEncoder.encode(KEY.getValue(), "UTF-8"));
            }

            return stringBuilderObject.toString();
        }

    }

    @Override
    public void onActivityResult(int RC, int RQC, Intent I) {

        super.onActivityResult(RC, RQC, I);

        if (RC == 1 && RQC == RESULT_OK && I != null && I.getData() != null) {

            Uri uri = I.getData();

            try {

                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);

                imageView.setImageBitmap(bitmap);

                ImageUploadToServerFunction();

            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

    class EditAdapter extends RecyclerView.Adapter<EditAdapter.BidHistoryViewHolder> {

        Context context;
        ArrayList<Item> heroList;

        EditAdapter(Context context, ArrayList<Item> heroList) {
            this.context = context;
            this.heroList = heroList;
        }

        @NonNull
        @Override
        public EditAdapter.BidHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new EditAdapter.BidHistoryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.edit_profile_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull BidHistoryViewHolder holder, int position) {
            if(heroList.get(position).getTitle().equals("Change password") || heroList.get(position).getTitle().equals("Manage Address")){
                holder.arrow.setVisibility(View.VISIBLE);
            } else {
                holder.arrow.setVisibility(View.GONE);
            }
            holder.Title.setText(heroList.get(position).getTitle());
            holder.Value.setText(heroList.get(position).getValue());
        }


        @Override
        public int getItemCount() {
            return heroList.size();
        }

        class BidHistoryViewHolder extends RecyclerView.ViewHolder {

            TextView Title;
            TextView Value;
            ImageView arrow;

            BidHistoryViewHolder(View itemView) {
                super(itemView);
                Title = itemView.findViewById(R.id.editItemTitle);
                Value = itemView.findViewById(R.id.editItemValue);
                arrow = itemView.findViewById(R.id.editItemImage);
                itemView.setClickable(true);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (heroList.get(getAdapterPosition()).getTitle()){
                            case "Change password":
                                Navigation.findNavController(view).navigate(R.id.action_editProfile_to_changePasswordFragment);
                                break;
                            case "Manage Address":
                                Navigation.findNavController(view).navigate(R.id.action_editProfile_to_manageAddressFragment);
                                break;
                        }
                    }
                });
            }
        }
    }

    class Item {
        String title;
        String value;

        public Item(String title, String value) {
            this.title = title;
            this.value = value;
        }

        public String getTitle() {
            return title;
        }

        public String getValue() {
            return value;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(EditProfileViewModel.class);
        // TODO: Use the ViewModel
    }

}