package com.example.soapboxandroidstudioproject;

import com.example.soapboxandroidstudioproject.model.ResponseObject;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface SoapBoxApi {

    public static final String SOAPBOX_VERIFICATION_URI = "https://api.soapboxlabs.com/v1/speech/verification/";
    public static final String SOAPBOX_APP_KEY = "df61baa8-fc56-11ec-82e6-3677b1124422";
    public static final String SOAPBOX_USER_TOKEN = "abc123";


    /*
    file_data = open(f'{sys.path[0]}/test.wav', 'rb')
    files_obj = {
        'file': file_data
    }
    form_data = {
        "user_token": SOAPBOX_USER_TOKEN,
        #"category": "stripes"
        "category": "robot"
    }
    headers = {
        "X-App-Key": SOAPBOX_APP_KEY,
        "Authorization": "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ5djRRTW85SnJ6ekE5bEwyXC9yWUVlNVV1OEFaWXFjekwwV1BZRzZrVStnNVZRaG9CenI1TkpNTzVyaWNicDNlZSIsImlzcyI6IlNvYXBCb3hMYWJzIiwiZXhwIjoxNjU3MTgzNTAxfQ.lc0NkJVG7938OEyPkFO4-NDpFXsvAPh_sNyHOaGHOIs"
    }

    response = requests.post(
        url=SOAPBOX_VERIFICATION_URI,
        headers=headers,
        data=form_data,
        files=files_obj
    )
     */


    @Multipart
    @POST("v1/speech/verification")
    Call<ResponseObject> verifyWav(@Part("user_token") String user_token, @Part("category") String category, @Part MultipartBody.Part file);
}
