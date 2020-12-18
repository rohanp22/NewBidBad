package com.example.bidbadnew.Fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.example.bidbadnew.Model.Address;
import com.example.bidbadnew.Others.SharedPrefManager;
import com.example.bidbadnew.R;
import com.example.bidbadnew.repositories.AddressRepo;
import com.example.bidbadnew.repositories.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressDialog extends DialogFragment {

    public static final String TAG = "example_dialog";
    EditText name, hno, area, city, state, pin, email, phone;

    private Toolbar toolbar;
    public int addressNumber;
    Button save;

    public AddressDialog(int addressNumber) {
        this.addressNumber = addressNumber;
    }

    public static AddressDialog display(FragmentManager fragmentManager, int addressNumber) {
        AddressDialog addressDialog = new AddressDialog(addressNumber);
        addressDialog.show(fragmentManager, TAG);
        return addressDialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_FullScreenDialog);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.address_form, container, false);

        name = view.findViewById(R.id.nameedittext);
        hno = view.findViewById(R.id.addressline1);
        area = view.findViewById(R.id.addressline2);
        city = view.findViewById(R.id.addressCity);
        state = view.findViewById(R.id.addressState);
        pin = view.findViewById(R.id.addresspin);
        toolbar = view.findViewById(R.id.toolbar);
        email = view.findViewById(R.id.addressemail);
        phone = view.findViewById(R.id.mobile_no);
        save = view.findViewById(R.id.saveaddress);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(name.getText())) {
                    name.setError("Enter name");
                } else if (TextUtils.isEmpty(hno.getText())) {
                    hno.setError("Enter house no");
                } else if (TextUtils.isEmpty(area.getText())) {
                    area.setError("Enter area");
                } else if (TextUtils.isEmpty(city.getText())) {
                    city.setError("Enter city");
                } else if (TextUtils.isEmpty(state.getText())) {
                    state.setError("Enter state");
                } else if (TextUtils.isEmpty(pin.getText())) {
                    pin.setError("Enter PIN");
                } else if (TextUtils.isEmpty(email.getText())) {
                    email.setError("Enter email");
                } else if (TextUtils.isEmpty(phone.getText())) {
                    phone.setError("Enter phone no");
                } else {
                    String a = name.getText() + ",\n" + hno.getText() + ", "+area.getText()+", "+city.getText()+"\n"+state.getText()+"-"+pin.getText()+"\nEmail - "+email.getText()+"\nMobile - "+phone.getText();
                    Call<Void> addressCall = RetrofitClient.getInstance().getMyApi().addAddress(a, addressNumber + "", SharedPrefManager.getInstance(getContext()).getUser().getId());
                    addressCall.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            AddressRepo.getInstance(SharedPrefManager.getInstance(getContext()).getUser().getId()).getAddress(SharedPrefManager.getInstance(getContext()).getUser().getId());
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {

                        }
                    });
                }
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar.setNavigationOnClickListener(v -> dismiss());
        toolbar.setTitle("Edit address");
        toolbar.inflateMenu(R.menu.address_menu);
        toolbar.setOnMenuItemClickListener(item -> {
            dismiss();
            return true;
        });
    }
}