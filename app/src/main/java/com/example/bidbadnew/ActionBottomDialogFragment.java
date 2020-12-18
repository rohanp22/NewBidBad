package com.example.bidbadnew;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.bidbadnew.Adapter.ProductAdapter1;
import com.example.bidbadnew.Model.Current_Product;
import com.example.bidbadnew.Model.HomeProductModel;
import com.example.bidbadnew.Others.RequestHandler;
import com.example.bidbadnew.Others.SharedPrefManager;
import com.example.bidbadnew.repositories.CurrentProductsRepo;
import com.example.bidbadnew.ui.home.HomeFragment;
import com.example.bidbadnew.ui.home.HomeViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ActionBottomDialogFragment extends BottomSheetDialogFragment
        implements View.OnClickListener {
    public static final String TAG = "ActionBottomDialog";
    private ItemClickListener mListener;
    FragmentManager fragmentManager;
    ProgressBar progressBar;
    int position;
    String imageurl, title, sp, productId;
    ProductAdapter1 productAdapter1;
    Current_Product current_product;
    HomeViewModel homeViewModel;

    public ActionBottomDialogFragment(String productId, String imageurl, String title, String sp, FragmentManager fragmentManager, int position) {
        this.imageurl = imageurl;
        this.title = title;
        this.sp = sp;
        this.productAdapter1 = productAdapter1;
        this.position = position;
        this.fragmentManager = fragmentManager;
        this.productId = productId;
    }

    public static ActionBottomDialogFragment newInstance(String productId, String imageurl, String title, String sp, FragmentManager fragmentManager, int position) {
        return new ActionBottomDialogFragment(productId, imageurl, title, sp, fragmentManager, position);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet, container, false);
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        return view;
    }

    int id, bal;
    EditText bidamount;

    @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView img  = view.findViewById(R.id.imageBottomsheet);
        Glide.with(view.getContext())
                .load(imageurl)
                .into(img);

        progressBar = view.findViewById(R.id.bottomSheetProgress);

        TextView entry = view.findViewById(R.id.entryprice);
        entry.setText("Entry price : "+sp);

        bidamount = view.findViewById(R.id.biddingButton);

        final Button placeBid = view.findViewById(R.id.bottomSheetPlaceBid);
        placeBid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("BottomSheet", "button clicked");
                mListener.onItemClick(bidamount.getText().toString());

                id = SharedPrefManager.getInstance(view.getContext()).getUser().getId();
                StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://easyvela.esy.es/AndroidAPI/checkbalance.php?id="+id,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    progressBar.setVisibility(View.VISIBLE);
                                    JSONObject obj = new JSONObject(response);
                                    bal = Integer.parseInt(obj.getString("balance"));
                                    if(bal > Integer.parseInt(sp)){
                                        StringRequest stringRequest2 = new StringRequest(Request.Method.GET, "http://easyvela.esy.es/AndroidAPI/updatewallet.php?id="+id+"&value="+Integer.parseInt(sp)*-1+"&type=Paid to enter the bidding for "+title+"&image_url="+imageurl,
                                                new Response.Listener<String>() {
                                                    @Override
                                                    public void onResponse(String response) {
                                                        sendOrderToDB();
                                                        progressBar.setVisibility(View.GONE);
                                                    }
                                                },
                                                new Response.ErrorListener() {
                                                    @Override
                                                    public void onErrorResponse(VolleyError error) {
                                                        Toast.makeText(getActivity().getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                });

                                        RequestQueue requestQueue2 = Volley.newRequestQueue(getActivity());
                                        requestQueue2.add(stringRequest2);
                                    }
                                    else
                                        Toast.makeText(getActivity(),"Insuffecient Balance, add money to wallet",Toast.LENGTH_LONG).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });

                RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                requestQueue.add(stringRequest);
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ItemClickListener) {
            mListener = (ItemClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ItemClickListener");
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    @Override public void onClick(View view) {
        TextView tvSelected = (TextView) view;
        Button placeBid = view.findViewById(R.id.bottomSheetPlaceBid);
        mListener.onItemClick(placeBid.getText().toString());
        mListener.onItemClick(tvSelected.getText().toString());
        progressBar.setVisibility(View.GONE);
        dismiss();
    }
    public interface ItemClickListener {
        void onItemClick(String item);
    }

    private void sendOrderToDB() {

        if (TextUtils.isEmpty(bidamount.getText().toString())) {
            bidamount.setError("Please enter amount");
            bidamount.requestFocus();
            return;
        }

        class RegisterUser extends AsyncTask<Void, Void, String> {

            private ProgressBar progressBar;

            @SuppressLint("WrongThread")
            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("id", SharedPrefManager.getInstance(getView().getContext()).getUser().getId()+"");
                params.put("bidamount", bidamount.getText().toString());
                params.put("productid", productId);
                params.put("type", "Paid to enter bidding for "+title);

                //returing the response
                return requestHandler.sendPostRequest("http://easyvela.esy.es/AndroidAPI/addtomybids.php", params);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //hiding the progressbar after completion
                homeViewModel.init(id);
                dismiss();
                Log.d("addtomybids", s);
                //HomeItems.isLoaded = false
                Toast.makeText(getView().getContext(), "Bid placed successfully", Toast.LENGTH_LONG);
            }
        }

        //executing the async task
        RegisterUser ru = new RegisterUser();
        ru.execute();
    }
}
