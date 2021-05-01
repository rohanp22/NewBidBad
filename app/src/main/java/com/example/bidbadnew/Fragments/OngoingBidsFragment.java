package com.example.bidbadnew.Fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.bidbadnew.Adapter.OngoingBidsAdapter;
import com.example.bidbadnew.Model.OngoingItems;
import com.example.bidbadnew.Others.SharedPrefManager;
import com.example.bidbadnew.R;

import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class OngoingBidsFragment extends Fragment implements OngoingBidsAdapter.OngoingBidsAdapterListener {
    private final int RC_STORAGE = 1;
    // Keep track of current share item.
    private OngoingItems currentOnGoingItem;
    private RecyclerView cartList;
    private OngoingViewModel ongoingViewModel;
    OnGoingFragmentListener onGoingFragmentListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ongoingViewModel = new ViewModelProvider(this).get(OngoingViewModel.class);
        ongoingViewModel.init();
        View view = inflater.inflate(R.layout.fragment_my_bids, container, false);
        cartList = view.findViewById(R.id.myBidsList);

        ongoingViewModel.getCurrent_productsmutable().observe(getViewLifecycleOwner(), new Observer<List<OngoingItems>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onChanged(List<OngoingItems> ongoingItems) {
                if (ongoingItems != null) {
                    ongoingItems.sort(new sortTime());
                    OngoingBidsAdapter walletAdapter = new OngoingBidsAdapter(view.getContext(), ongoingItems, OngoingBidsFragment.this);
                    view.findViewById(R.id.progressBar).setVisibility(View.GONE);

                    if (ongoingItems.size() == 0) {
                        view.findViewById(R.id.noBidsMsg).setVisibility(View.VISIBLE);
                        view.findViewById(R.id.btn).setVisibility(View.VISIBLE);
                    }
                    cartList.setAdapter(walletAdapter);
                    cartList.setLayoutManager(new LinearLayoutManager(view.getContext()));
                }
            }
        });
        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RC_STORAGE && grantResults.length > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            shareBid(currentOnGoingItem);
        }
    }

    public OngoingBidsFragment(OnGoingFragmentListener allBidsFragmentListener) {
        this.onGoingFragmentListener = allBidsFragmentListener;
    }

    public interface OnGoingFragmentListener {
        public void onItemClick(OngoingItems pastProducts);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onItemClickListener(OngoingItems pastProduct) {
        onGoingFragmentListener.onItemClick(pastProduct);
    }

    @Override
    public void onShareButtonClick(OngoingItems onGoingItem) {
        currentOnGoingItem = onGoingItem;
        if (verifyPermissions())
            shareBid(onGoingItem);
    }

    private boolean verifyPermissions() {
        if (!(ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, RC_STORAGE);
            return false;
        }
        return true;
    }

    private void shareBid(OngoingItems onGoingItem) {
        final Bitmap[] icon = new Bitmap[1];
        Glide.with(requireContext())
                .asBitmap()
                .load(onGoingItem.getImageUrl())
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        icon[0] = resource;
                        Intent intent = new Intent(android.content.Intent.ACTION_SEND);

                        Intent share = new Intent(Intent.ACTION_SEND);
                        share.setType("image/jpeg");

                        ContentValues values = new ContentValues();
                        values.put(MediaStore.Images.Media.TITLE, "title");
                        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
                        Uri uri = requireContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                values);

                        OutputStream outstream;
                        try {
                            outstream = requireContext().getContentResolver().openOutputStream(uri);
                            icon[0].compress(Bitmap.CompressFormat.JPEG, 100, outstream);
                            outstream.close();
                        } catch (Exception e) {
                            System.err.println(e.toString());
                        }

                        intent.putExtra(Intent.EXTRA_STREAM, uri);
                        //startActivity(Intent.createChooser(share, "Share Image"));

                        /*Create an ACTION_SEND Intent*/
                        /*This will be the actual content you wish you share.*/
                        String shareBody = "Hey! Bid using this link to get the bonus points http://easyvela.esy.es/newproduct?id1=" + onGoingItem.getCurrentid() + "&id2=" + SharedPrefManager.getInstance(requireContext()).getUser().getId();
                        /*The type of the content is text, obviously.*/
                        intent.setType("text/plain");
                        /*Applying information Subject and Body.*/
                        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Share bid");
                        intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                        /*Fire!*/
                        requireContext().startActivity(Intent.createChooser(intent, "Share via"));
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });
    }

    class sortTime implements Comparator<OngoingItems> {

        public int compare(OngoingItems a, OngoingItems b) {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
            Date a1 = null, b1 = null;
            try {
                a1 = simpleDateFormat.parse(a.getEndtime());
                b1 = simpleDateFormat.parse(b.getEndtime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return a1.compareTo(b1);
        }
    }
}
