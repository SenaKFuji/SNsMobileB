package jp.ac.meijou.android.s231205089;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Optional;

import jp.ac.meijou.android.s231205089.databinding.ActivityMain3Binding;

public class MainActivity3 extends AppCompatActivity {

    private ActivityMain3Binding binding;

    // 3日目 p.53 移行後画面から戻ってきた際の処理
    private final ActivityResultLauncher<Intent> getActivityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                switch (result.getResultCode()) { // ここでRESULT_OKやCancelの-1とか0を受け取って以下で操作
                    case RESULT_OK -> {
                        // OKボタンの場合
                        Optional.ofNullable(result.getData())
                                .map(data -> data.getStringExtra("okR"))
                                .map(ret -> "Result: " + ret)
                                .ifPresent(text -> binding.textR.setText(text));
                    }
                    case RESULT_CANCELED -> {
                        // Cancelボタンの場合
                        binding.textR.setText("Result: Canceled");
                    }
                    default -> {
                        // 想定外の場合
                        binding.textR.setText("Result: Unknown(" + result.getResultCode() + ")");
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMain3Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.button.setOnClickListener(view -> {
            var intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        binding.buttonY.setOnClickListener(view -> {
            var intentY = new Intent();
            intentY.setData(Uri.parse("https://www.yahoo.co.jp"));
            startActivity(intentY);
        });

        binding.buttonS.setOnClickListener(view -> {
            var intentS = new Intent(this, MainActivity.class);
            var text = binding.sendText.getText().toString();
            intentS.putExtra("sendContent", text);
            startActivity(intentS);
        });

        binding.buttonR.setOnClickListener(view -> {
            var intentR = new Intent(this, MainActivity.class);
            // Line 24: 帰ってきた際の処理を開始する。(launch)
            getActivityResult.launch(intentR);
        });

    }
}