package jp.ac.meijou.android.s231205089;

import android.net.Uri;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Button;

import android.content.Intent;

import java.util.Optional;

import jp.ac.meijou.android.s231205089.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private PrefDataStore prefDataStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        prefDataStore = PrefDataStore.getInstance(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // OK
        binding.buttonOK.setOnClickListener(view -> {
            var intentOK = new Intent();
            intentOK.putExtra("okR", "OK");
            setResult(RESULT_OK, intentOK);
            finish();
        });

        // Cancel
        binding.buttonC.setOnClickListener(view -> {
            setResult(RESULT_CANCELED);
            finish();
        });
    }



    @Override
    protected void onStart() {
        super.onStart();
        prefDataStore.getString("name")
                .ifPresent(name -> binding.text.setText(name));
        /*
        Optional.ofNullable(getIntent().getStringExtra("sendContent"))
                .ifPresent(textR -> binding.text.setText(textR));

         */

        Intent intentR = getIntent();
        Bundle bundle = intentR.getExtras();
        String textR = getIntent().getStringExtra("sendContent");
        binding.text.setText(textR);
    }

}