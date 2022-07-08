package com.example.soapboxandroidstudioproject;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.soapboxandroidstudioproject.model.ResponseObject;
import com.example.soapboxandroidstudioproject.model.WordObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public static final String SOAPBOX_VERIFICATION_URI = "https://api.soapboxlabs.com/";
    public static final String SOAPBOX_APP_KEY = "df61baa8-fc56-11ec-82e6-3677b1124422";
    public static final String SOAPBOX_USER_TOKEN = "abc123";
    public static final String JWT_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ5djRRTW85SnJ6ekE5bEwyXC9yWUVlNVV1OEFaWXFjekwwV1BZRzZrVStnNVZRaG9CenI1TkpNTzVyaWNicDNlZSIsImlzcyI6IlNvYXBCb3hMYWJzIiwiZXhwIjoxNjU3MjMwNzk1fQ.8yEivCQ0C0NfU0sOT7ZCzG7uj4oCFZdrC_HbQhfXACI";
    public static final String CATEGORY = "ROBOT";

    private TextView tvSoapBoxResult;
    private TextView tvSoapBoxProbability;
    private TextView tvGoogleAPIResult;

    private Button btnRec;
    private Button btnStop;
    private Button btnPlay;
    private Button btnSpeak;
    private Button btnToText;

    private MediaRecorder mr;

    private String path = "";

    private SoapBoxApi soapBoxApi;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 111 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            btnRec.setEnabled(true);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD_MR1) {
            path = getExternalFilesDir(Environment.DIRECTORY_DCIM).toString();
        } else {
            path = Environment.getExternalStorageDirectory().toString();
        }

        WavClass wavObj = new WavClass(path);

        mr = new MediaRecorder();

        tvSoapBoxResult = findViewById(R.id.tvSoapBoxResult);
        tvSoapBoxProbability = findViewById(R.id.textViewSoapBoxProbability);
        tvGoogleAPIResult = findViewById(R.id.tvGoogleAPIResult);

        btnRec = findViewById(R.id.btnRec);
        btnStop = findViewById(R.id.btnStop);
        btnPlay = findViewById(R.id.btnPlay);
        btnToText = findViewById(R.id.btnToText);
        btnSpeak = findViewById(R.id.btnSpeak);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    111);
            btnRec.setEnabled(true);
        }


        //Start Rec
        btnRec.setOnClickListener(v -> {
            if (checkWritePermission()) {
                wavObj.startRecording();
            }
            if (!checkWritePermission()) {
                requestWritePermission();
            }
        });


        //Stop Rec
        btnStop.setOnClickListener(v -> wavObj.stopRecording());


        //Play Recording
        String finalPath1 = path + "/" + "final_record.wav";
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MediaPlayer mp = new MediaPlayer();

                try {
                    mp.setDataSource(finalPath1);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    mp.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                mp.start();

            }
        });


        //to set NOT to ignore null fields in jsons
        Gson gson = new GsonBuilder().serializeNulls().create();

        //Перехватчик
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @NonNull
                    @Override
                    public okhttp3.Response intercept(@NonNull Chain chain) throws IOException {
                        Request originalRequest = chain.request();

                        Request newRequest = originalRequest.newBuilder()
                                .header("X-App-Key", SOAPBOX_APP_KEY)
                                .header("Authorization", "Bearer " + JWT_TOKEN)
                                .build();

                        //подменяем реквест его копией с хэддером
                        return chain.proceed(newRequest);
                    }
                })
                .addInterceptor(loggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SOAPBOX_VERIFICATION_URI)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();

        soapBoxApi = retrofit.create(SoapBoxApi.class);

        //Send wav-file to SoapBox
        btnToText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyWav();
            }
        });


        //GoogleAPI
        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Start Speaking");
                intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 60000);
                startActivityForResult(intent, 100);
            }
        });
    }

    //GoogleAPI
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK) {

            String detectedText = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).get(0);
            String content = "Detected Text:" + "\n";
            content += detectedText + "\n";
            content += "\n";

            boolean containsWord = detectedText.toLowerCase().contains(CATEGORY.toLowerCase(Locale.ROOT));
            if (containsWord) {
                content += "Found Word: " + CATEGORY + "\n";
            } else {
                content += "NOT Found Word: " + CATEGORY + "\n";
            }

            tvGoogleAPIResult.setText(content);
        }
    }

    private boolean checkWritePermission() {
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int result2 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO);
        return result1 == PackageManager.PERMISSION_GRANTED && result2 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestWritePermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.MODIFY_AUDIO_SETTINGS, WRITE_EXTERNAL_STORAGE}, 1);
    }

    private void verifyWav() {
        String user_token = SOAPBOX_USER_TOKEN;
        String category = CATEGORY;

        String finalPath1 = path + "/" + "final_record.wav";


        File file = new File(finalPath1);
        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse("audio/*"),
                        file
                );

        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        Call<ResponseObject> call = soapBoxApi.verifyWav(user_token, category, body);

        call.enqueue(new Callback<ResponseObject>() {
            @Override
            public void onResponse(Call<ResponseObject> call, Response<ResponseObject> response) {

                if (!response.isSuccessful()) {
                    tvSoapBoxResult.setText("Code: " + response.code());
                    return;
                }

                ResponseObject postResponse = response.body();


                String content = "";
                content += "Code: " + response.code() + "\n";
                content += "results: " + postResponse.getResults() + "\n";
                content += "language_code: " + postResponse.getLanguage_code() + "\n";
                content += "result_id: " + postResponse.getResult_id() + "\n";
                content += "time: " + postResponse.getTime() + "\n";
                content += "\n";

                String probability = "Probability(%): ";
                List<WordObject> wordObjects = postResponse.getResults().get(0).getWord_breakdown();
                List<Double> qualityScores = new ArrayList<>();
                double maxQualityScore = 0;
                for (WordObject wordObject : wordObjects) {
                    double qualityScore = wordObject.getQuality_score();
                    qualityScores.add(qualityScore);
                    if (maxQualityScore < qualityScore) {
                        maxQualityScore = qualityScore;
                    }
                }

                probability += maxQualityScore;

                tvSoapBoxResult.setText(content);
                tvSoapBoxProbability.setText(probability);
            }

            @Override
            public void onFailure(Call<ResponseObject> call, Throwable t) {
                tvSoapBoxResult.setText(t.getMessage());
            }
        });
    }
}