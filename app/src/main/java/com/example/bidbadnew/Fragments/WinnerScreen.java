package com.example.bidbadnew.Fragments;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.bidbadnew.R;

import java.io.OutputStream;

public class WinnerScreen extends Fragment {
    private final int RC_STORAGE = 69;
    private View winnerRootView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    Bitmap icon;
    ConstraintLayout constraintLayout;

    TextView share;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        winnerRootView = inflater.inflate(R.layout.fragment_winner_screen, container, false);
        constraintLayout = winnerRootView.findViewById(R.id.constraintLayout);

        share = winnerRootView.findViewById(R.id.sharebid);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkStoragePermission())
                    shareBid(winnerRootView);
            }
        });
        return winnerRootView;
    }

    boolean checkStoragePermission() {
        if (ContextCompat.checkSelfPermission(requireActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    RC_STORAGE);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == RC_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                shareBid(winnerRootView);
            else
                // Permission Denied
                Toast.makeText(requireContext(), "Storage permission is needed for sharing", Toast.LENGTH_SHORT).show();
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void shareBid(View view) {
        icon = Bitmap.createBitmap(constraintLayout.getWidth(), constraintLayout.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(icon);
        constraintLayout.draw(canvas);

        Intent intent = new Intent(android.content.Intent.ACTION_SEND);

        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "title");
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        Uri uri = view.getContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                values);

        OutputStream outstream;
        try {
            outstream = view.getContext().getContentResolver().openOutputStream(uri);
            icon.compress(Bitmap.CompressFormat.JPEG, 100, outstream);
            outstream.close();
        } catch (Exception e) {
            System.err.println(e.toString());
        }

        intent.putExtra(Intent.EXTRA_STREAM, uri);
        //startActivity(Intent.createChooser(share, "Share Image"));

        /*Create an ACTION_SEND Intent*/
        /*This will be the actual content you wish you share.*/
        String shareBody = "Hey! I won this crazy product for my price";
        /*The type of the content is text, obviously.*/
        intent.setType("text/plain");
        /*Applying information Subject and Body.*/
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Share bid");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        /*Fire!*/
        view.getContext().startActivity(Intent.createChooser(intent, "Share via"));
    }
}