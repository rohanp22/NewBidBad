package com.example.bidbadnew;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.bidbadnew.Activities.MainActivity;
import com.example.bidbadnew.Fragments.ConfirmFragment;
import com.example.bidbadnew.Fragments.ProductsViewModel;
import com.example.bidbadnew.Model.Current_Product;
import com.example.bidbadnew.Model.FreeBid;
import com.example.bidbadnew.Others.RequestHandler;
import com.example.bidbadnew.Others.SharedPrefManager;
import com.example.bidbadnew.repositories.CurrentProductsRepo;
import com.example.bidbadnew.repositories.RetrofitClient;
import com.example.bidbadnew.ui.home.HomeViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.switchmaterial.SwitchMaterial;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class ActionBottomDialogFragment extends BottomSheetDialogFragment
        implements View.OnClickListener {
    public static final String TAG = "ActionBottomDialog";
    ProgressBar progressBar;
    String category;
    String imageurl, title, sp, productId;
    MaterialCheckBox bonus, freebid;
    HomeViewModel homeViewModel;
    Current_Product current_product;
    String isFreebid, bonusPoints;
    ImageView cancel;
    TextView bonusMoney, total, bonusbalance;
    int bp;
    int bonusApplied = 0;

    public ActionBottomDialogFragment() {

    }

    View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Current_Product current_product = (Current_Product) getArguments().getSerializable("Current_product");
            this.imageurl = current_product.getImageUrl();
            this.title = current_product.getTitle();
            this.sp = current_product.getSp();
            this.category = current_product.getCategory();
            this.productId = current_product.getCurrentid();
            this.isFreebid = current_product.getFreebid();
            this.bonusPoints = current_product.getBonus();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.bottom_sheet, container, false);
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        cancel = view.findViewById(R.id.close);
        progressBar = view.findViewById(R.id.bottomSheetProgress);
        bonusMoney = view.findViewById(R.id.bonusmoney);
        total = view.findViewById(R.id.totalamount);
        bonus = view.findViewById(R.id.bonuscheckbox);
        bonusbalance = view.findViewById(R.id.youhavexbonus);

        total.setText((Integer.parseInt(sp) - Integer.parseInt(bonusMoney.getText().toString())) + "");

        freebid = view.findViewById(R.id.rewardscheckbox);

        bonus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(Integer.parseInt(bonusPoints) > 0) {
                    if (isChecked) {
                        if (bp / 10 < Integer.parseInt(sp) / 10) {
                            bonusMoney.setText("- " + bp / 10);
                            total.setText((Integer.parseInt(sp) - bp / 10) + "");
                            bonusApplied = bp / 10;
                        } else {
                            bonusMoney.setText("- " + Integer.parseInt(sp) / 10);
                            total.setText((Integer.parseInt(sp) - Integer.parseInt(sp) / 10) + "");
                            bonusApplied = Integer.parseInt(sp) / 10;
                        }
                    }
                    if (!isChecked) {
                        bonusMoney.setText("-0");
                        total.setText((Integer.parseInt(sp)) + "");
                        bonusApplied = 0;
                    }
                } else {
                    
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        view.findViewById(R.id.infobtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("10 bonus points = 1 bonus points\nYou can use only bonus worth 10% of the bidamount");
                AlertDialog alert = builder.create();
                alert.show();

            }
        });

        if (Integer.parseInt(bonusPoints) > 0) {
            Call call = RetrofitClient.getInstance().getMyApi().getRewardPoints(SharedPrefManager.getInstance(view.getContext()).getUser().getId());
            call.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, retrofit2.Response response) {
                    bp = Integer.parseInt(response.body().toString());
                    if(bp/10 < Integer.parseInt(sp)/10)
                        bonus.setText("Use " + (bp - bp%10) + " Bonus points");
                    else
                        bonus.setText("Use " + (Integer.parseInt(sp) - Integer.parseInt(sp)%10) + " Bonus points");
                    bonusbalance.setText("(You have "+response.body()+" bonus points)");
                }

                @Override
                public void onFailure(Call call, Throwable t) {

                }
            });
        }
        if (Integer.parseInt(isFreebid) > 0) {

        }

        Call<List<FreeBid>> call1 = RetrofitClient.getInstance().getMyApi().getFreeBids(SharedPrefManager.getInstance(view.getContext()).getUser().getId());
        call1.enqueue(new Callback<List<FreeBid>>() {
            @Override
            public void onResponse(Call<List<FreeBid>> call, retrofit2.Response<List<FreeBid>> response) {
            }

            @Override
            public void onFailure(Call<List<FreeBid>> call, Throwable t) {

            }
        });

        Call<String> rewardPointsCall = RetrofitClient.getInstance().getMyApi().getRewardPoints(SharedPrefManager.getInstance(view.getContext()).getUser().getId());
        rewardPointsCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
            }
        });

        TextView entry = view.findViewById(R.id.entryprice);
        entry.setText("" + sp);

        bidamount = view.findViewById(R.id.biddingButton);

        final TextView placeBid = view.findViewById(R.id.bottomSheetPlaceBid);
        placeBid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("BottomSheet", "button clicked");
                placeBid.setEnabled(false);

                id = SharedPrefManager.getInstance(view.getContext()).getUser().getId();
                StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://easyvela.esy.es/AndroidAPI/checkbalance.php?id=" + id,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    progressBar.setVisibility(View.VISIBLE);
                                    JSONObject obj = new JSONObject(response);
                                    bal = Integer.parseInt(obj.getString("balance"));
                                    if (bal - bonusApplied > Integer.parseInt(sp)) {
                                        StringRequest stringRequest2 = new StringRequest(Request.Method.GET, "http://easyvela.esy.es/AndroidAPI/updatewallet.php?id=" + id + "&value=" + (Integer.parseInt(sp) * -1 + bonusApplied) + "&type=Paid to enter the bidding for " + title + "&image_url=" + imageurl,
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
                                    } else {
                                        ((MainActivity) getActivity()).navController.navigate(R.id.action_actionBottomDialogFragment_to_bidNotPlacedFragment);
                                    }
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

        return view;
    }

    int id, bal;
    EditText bidamount;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ItemClickListener) {
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ItemClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View view) {
        TextView tvSelected = (TextView) view;
        Button placeBid = view.findViewById(R.id.bottomSheetPlaceBid);
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

            public View view1;

            public RegisterUser(View view) {
                this.view1 = view;
            }

            @SuppressLint("WrongThread")
            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("id", SharedPrefManager.getInstance(getView().getContext()).getUser().getId() + "");
                params.put("bidamount", bidamount.getText().toString());
                params.put("productid", productId);
                params.put("type", "Paid to enter bidding for " + title);
                params.put("bonuspoints", bonusApplied + "");

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

                switch (category) {
                    case "Electronics":
                        CurrentProductsRepo.getInstance().getProducts(0, id);
                        break;
                    case "Appliances":
                        CurrentProductsRepo.getInstance().getProducts(1, id);
                        break;
                    case "Accessories":
                        CurrentProductsRepo.getInstance().getProducts(2, id);
                        break;
                    case "Personal Care":
                        CurrentProductsRepo.getInstance().getProducts(3, id);
                        break;
                    case "Home":
                        CurrentProductsRepo.getInstance().getProducts(4, id);
                        break;
                    case "Fitness":
                        CurrentProductsRepo.getInstance().getProducts(5, id);
                        break;
                    case "Others":
                        CurrentProductsRepo.getInstance().getProducts(6, id);
                        break;
                    case "Apparel":
                        CurrentProductsRepo.getInstance().getProducts(7, id);
                        break;
                }

                homeViewModel.init(id);
                Log.d("addtomybids", "Executed");

                ((MainActivity) getActivity()).navController.navigate(R.id.action_actionBottomDialogFragment_to_confirmFragment);
//                ((MainActivity) getActivity()).navController.navigateUp();
//                dismiss();
                //HomeItems.isLoaded = false
                Toast.makeText(getView().getContext(), "Bid placed successfully", Toast.LENGTH_LONG);
            }
        }

        //executing the async task
        RegisterUser ru = new RegisterUser(view);
        ru.execute();
    }

    @Override
    public int getTheme() {
        return R.style.CustomBottomSheetDialog;
    }
}
