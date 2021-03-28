package com.example.bidbadnew.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.bidbadnew.Model.Address;
import com.example.bidbadnew.Model.WonItem;
import com.example.bidbadnew.Others.SharedPrefManager;
import com.example.bidbadnew.R;
import com.example.bidbadnew.repositories.AddressRepo;
import com.example.bidbadnew.repositories.RetrofitClient;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.radiobutton.MaterialRadioButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageAddressFragment extends Fragment {


    MaterialRadioButton materialRadioButton1, materialRadioButton2, materialRadioButton3;
    MaterialCardView card1, card2, card3;
    String a1, a2, a3, a4;
    TextView addressField1, addressField2, addressField3;
    int addresscount = 0;
    ImageView overflow, overflow2, overflow3;
    Toolbar toolbar;
    Button addAddress, next;
    View view;
    ManageAddressViewModel manageAddressViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_manage_address, container, false);

        manageAddressViewModel = new ViewModelProvider(this).get(ManageAddressViewModel.class);
        
        card1 = view.findViewById(R.id.card1);
        card2 = view.findViewById(R.id.card2);
        materialRadioButton1 = view.findViewById(R.id.radio1);
        materialRadioButton2 = view.findViewById(R.id.radio2);
        card3 = view.findViewById(R.id.card3);
        toolbar = view.findViewById(R.id.toolbar);
        materialRadioButton3 = view.findViewById(R.id.radio3);
        addAddress = view.findViewById(R.id.addaddressbtn);

        addressField1 = view.findViewById(R.id.address1);
        addressField2 = view.findViewById(R.id.address2);
        addressField3 = view.findViewById(R.id.address3);

        manageAddressViewModel.init(SharedPrefManager.getInstance(view.getContext()).getUser().getId());
        manageAddressViewModel.getAddressMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Address>() {
            @Override
            public void onChanged(Address address) {
                a1 = address.getAddress();
                a2 = address.getAddress2();
                a3 = address.getAddress3();
                //a4 = heroObject.getString("address4");
                if(address.getAddress() != null)
                    if (!address.getAddress().equals("") && !address.getAddress().equals("null")) {
                        addressField1.setText(a1);
                        card1.setVisibility(View.VISIBLE);
                        addresscount++;
                    } else {
                        card1.setVisibility(View.GONE);
                    }
                if(address.getAddress2() != null)
                    if (!address.getAddress2().equals("") && !address.getAddress2().equals("null")) {
                        addressField2.setText(a2);
                        card2.setVisibility(View.VISIBLE);
                        addresscount++;
                    } else {
                        card2.setVisibility(View.GONE);
                    }
                if(address.getAddress3() != null)
                    if (!address.getAddress3().equals("") && !address.getAddress3().equals("null")) {
                        addressField3.setText(a3);
                        card3.setVisibility(View.VISIBLE);
                        addresscount++;
                    } else {
                        card3.setVisibility(View.GONE);
                    }
            }
        });

        addAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (card1.getVisibility() == View.GONE) {
                    AddressDialog.display(getChildFragmentManager(), 1);
                } else if (card2.getVisibility() == View.GONE) {
                    AddressDialog.display(getChildFragmentManager(), 2);
                } else if (card3.getVisibility() == View.GONE) {
                    AddressDialog.display(getChildFragmentManager(), 3);
                } else {
                    Toast.makeText(getContext(), "No more addresses can be added", Toast.LENGTH_LONG).show();
                }
            }
        });

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialRadioButton1.setChecked(true);
                materialRadioButton2.setChecked(false);
                materialRadioButton3.setChecked(false);
//                if(materialRadioButton1.isChecked() && card1.getVisibility() != View.GONE){
//                    Bundle b = new Bundle();
//                    b.putSerializable("item", wonItem);
//                    b.putString("address", addressField1.getText().toString());
//                    setAddress(addressField1.getText().toString());
//                    Navigation.findNavController(view).popBackStack();
//                } else if(materialRadioButton2.isChecked() && card2.getVisibility() != View.GONE){
//                    Bundle b = new Bundle();
//                    b.putSerializable("item", wonItem);
//                    b.putString("address", addressField2.getText().toString());
//                    setAddress(addressField2.getText().toString());
//                    Navigation.findNavController(view).popBackStack();
//                } else if(materialRadioButton3.isChecked() && card3.getVisibility() != View.GONE){
//                    Bundle b = new Bundle();
//                    b.putSerializable("item", wonItem);
//                    b.putString("address", addressField3.getText().toString());
//                    setAddress(addressField3.getText().toString());
//                    Navigation.findNavController(view).popBackStack();
//                }
            }
        });

        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialRadioButton2.setChecked(true);
                materialRadioButton1.setChecked(false);
                materialRadioButton3.setChecked(false);
//                if(materialRadioButton1.isChecked() && card1.getVisibility() != View.GONE){
//                    Bundle b = new Bundle();
//                    b.putSerializable("item", wonItem);
//                    b.putString("address", addressField1.getText().toString());
//                    setAddress(addressField1.getText().toString());
//                    Navigation.findNavController(view).popBackStack();
//                } else if(materialRadioButton2.isChecked() && card2.getVisibility() != View.GONE){
//                    Bundle b = new Bundle();
//                    b.putSerializable("item", wonItem);
//                    b.putString("address", addressField2.getText().toString());
//                    setAddress(addressField2.getText().toString());
//                    Navigation.findNavController(view).popBackStack();
//                } else if(materialRadioButton3.isChecked() && card3.getVisibility() != View.GONE){
//                    Bundle b = new Bundle();
//                    b.putSerializable("item", wonItem);
//                    b.putString("address", addressField3.getText().toString());
//                    setAddress(addressField3.getText().toString());
//                    Navigation.findNavController(view).popBackStack();
//                }
            }
        });

        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialRadioButton3.setChecked(true);
                materialRadioButton2.setChecked(false);
                materialRadioButton1.setChecked(false);
//                if(materialRadioButton1.isChecked() && card1.getVisibility() != View.GONE){
//                    Bundle b = new Bundle();
//                    b.putSerializable("item", wonItem);
//                    b.putString("address", addressField1.getText().toString());
//                    setAddress(addressField1.getText().toString());
//                    Navigation.findNavController(view).popBackStack();
//                } else if(materialRadioButton2.isChecked() && card2.getVisibility() != View.GONE){
//                    Bundle b = new Bundle();
//                    b.putSerializable("item", wonItem);
//                    b.putString("address", addressField2.getText().toString());
//                    setAddress(addressField2.getText().toString());
//                    Navigation.findNavController(view).popBackStack();
//                } else if(materialRadioButton3.isChecked() && card3.getVisibility() != View.GONE){
//                    Bundle b = new Bundle();
//                    b.putSerializable("item", wonItem);
//                    b.putString("address", addressField3.getText().toString());
//                    setAddress(addressField3.getText().toString());
//                    Navigation.findNavController(view).popBackStack();
//                }
            }
        });

        overflow = view.findViewById(R.id.overflow);
        overflow2 = view.findViewById(R.id.overflow2);
        overflow3 = view.findViewById(R.id.overflow3);

        overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(overflow, 1);
            }
        });

        overflow2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(overflow2, 2);
            }
        });


        overflow3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(overflow3, 3);
            }
        });

        return view;
    }

    public void setAddress(String address){
        Call<Void> call = RetrofitClient.getInstance().getMyApi().setDefaultAddress(address, SharedPrefManager.getInstance(view.getContext()).getUser().getId());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("Resopnse", "Adress updated");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("Resume", "hello");
    }

    public void showPopup(ImageView overflow, int addressNumber) {
        PopupMenu popup = new PopupMenu(view.getContext(), overflow);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.actions, popup.getMenu());
        popup.show();

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.edit:
                        Toast.makeText(view.getContext(), "Edit" + addressNumber, Toast.LENGTH_SHORT).show();
                        AddressDialog.display(getChildFragmentManager(), addressNumber);
                        break;

                    case R.id.delete:
                        Toast.makeText(view.getContext(), "Delete" + addressNumber, Toast.LENGTH_SHORT).show();
                        Call<Void> addressCall = RetrofitClient.getInstance().getMyApi().addAddress("", addressNumber + "", SharedPrefManager.getInstance(getContext()).getUser().getId());
                        addressCall.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                AddressRepo.getInstance(SharedPrefManager.getInstance(getContext()).getUser().getId()).getAddress(SharedPrefManager.getInstance(getContext()).getUser().getId());
                                addresscount--;
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {

                            }
                        });
                        break;
                }
                return true;
            }
        });
    }

}
