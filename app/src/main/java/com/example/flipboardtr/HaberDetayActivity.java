package com.example.flipboardtr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import static com.example.flipboardtr.HomeFragment.EXTRA_BASLIK;
import static com.example.flipboardtr.HomeFragment.EXTRA_ICERIK;
import static com.example.flipboardtr.HomeFragment.EXTRA_KAYNAK;
import static com.example.flipboardtr.HomeFragment.EXTRA_SITEURL;
import static com.example.flipboardtr.HomeFragment.EXTRA_URL;

public class HaberDetayActivity extends AppCompatActivity {

    private String siteurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_haber_detay);

        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra(EXTRA_URL);
        String baslik = intent.getStringExtra(EXTRA_BASLIK);
        String kaynak = intent.getStringExtra(EXTRA_KAYNAK);
        String icerik = intent.getStringExtra(EXTRA_ICERIK);
        siteurl = intent.getStringExtra(EXTRA_SITEURL);


        ImageView imageView = findViewById(R.id.image_view_detay);
        TextView baslikView = findViewById(R.id.text_view_baslik_detay);
        TextView kaynakView = findViewById(R.id.text_view_kaynak_detay);
        TextView icerikView = findViewById(R.id.text_view_icerik_detay);

        Picasso.get().load(imageUrl).fit().centerInside().into(imageView);
        baslikView.setText(baslik);
        kaynakView.setText(kaynak);
        icerikView.setText(icerik);



    }

    public void Siteye_Git(View view){
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(siteurl));
        startActivity(i);
    }
}
