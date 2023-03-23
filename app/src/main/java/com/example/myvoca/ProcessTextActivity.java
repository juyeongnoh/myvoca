package com.example.myvoca;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class ProcessTextActivity extends Activity {
    String word;
    String getResult;
    String space = " ";

    DBHelper mydb;
    SettingsFragment settingsFragment;

    String[] getRidOf = {"a", "an", "the",                                                                                  // 관사
                        "at", "in", "on", "upon", "for", "by", "of", "with",                                                // 기본 전치사
                        "during", "before", "after", "following", "until", "since", "within", "over", "behind", "ahead",    // 시간 전치사
                        "because", "despite", "about",                                                                      // 이유, 양보, 대상을 의미하는 전치사
                        "from", "to", "into", "through", "throughout", "toward", "towards",                                 // 장소, 방향의 전치사
                        "among", "between", "across", "around", "opposite", "past", "near", "along", "alongside",
                        "except", "besides", "instead", "without",                                                          // 예외, 추가 또는 대체나 교환의 의미를 갖는 전치사
                        "as", "under", "beneath", "beyond", "above", "below", "out", "like", "unlike", "amid",              // 기타 전치사
                        "against", "per", "up", "according", "worth",
                        ",", ".", "!", "?", "*", "^", "%", "$", "#", "@", "(", ")",                                        // 기타 특수문자
                        "and", "what", "who", "when", "where", "how", "why",
                        "I", "my", "me", "mine",
                        "you", "your", "yours",
                        "he", "his", "him",
                        "she", "her", "hers",
                        "we", "our", "us", "ours",
                        "it",
                        "is", "was", "will", "are", "were", "am"};                                                          // be동사


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        for(int a = 0; a < getRidOf.length; a++) {
            System.out.println("getridof[" + a + "]: " + getRidOf[a]);
        }

        mydb = new DBHelper(getApplicationContext());

        CharSequence charSequence = getIntent().getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT);

        word = charSequence.toString();
        System.out.println("word: " + word);

        final Translate translate = new Translate();
        translate.execute();
        WaitForTheResult(); // 번역 결과 받아오는 시간이 좀 걸려서 임의로 sleep method 추가함

        finish();
    }

    void WaitForTheResult () {
        try {
            Thread.sleep(500);
        } catch(InterruptedException e) {
            System.out.println("error");
        }
    }

    class Translate extends AsyncTask<String, Void, String> {   // ASYNCTASK를 사용

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override

        public String doInBackground(String... strings) {

            //////네이버 API
            String clientId = "B1zvvUAFl47SmG2IwNx6";     //애플리케이션 클라이언트 아이디값";
            String clientSecret = "O8Nw0lXKGh";      //애플리케이션 클라이언트 시크릿값";

            if(word.contains(space)) {
                try {
                    String text = URLEncoder.encode(word.toString(), "UTF-8");

                    // 띄어쓰기 단위로 split
                    String[] textAry = word.split(" ");
                    ArrayList<String> arrayList = new ArrayList<String>();
                    ArrayList<String> arrayListTemp = new ArrayList<String>();
                    for(String temp: textAry) {
                        arrayList.add(temp);
                    }
                    for(String temp: textAry) {
                        arrayListTemp.add(temp);
                    }
                    System.out.println(arrayList);

                    textAry = new String[arrayList.size()];
                    textAry = arrayList.toArray(textAry);
                    System.out.println(textAry[0]);


                    for(int i = 0; i < textAry.length; i++) {
                        boolean isPreposition = false;

                        for(String s: getRidOf) {
                            if((textAry[i].toLowerCase()).equals(s)) {
                                isPreposition = true;
                                break;
                            }
                        }

                        if(isPreposition == true) continue;

                        String apiURL = "https://openapi.naver.com/v1/papago/n2mt";
                        URL url = new URL(apiURL);
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        con.setRequestMethod("POST");
                        con.setRequestProperty("X-Naver-Client-Id", clientId);
                        con.setRequestProperty("X-Naver-Client-Secret", clientSecret);

                        // post request
                        String postParams = "source=en&target=ko&text=" + textAry[i];
                        con.setDoOutput(true);
                        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                        wr.writeBytes(postParams);
                        wr.flush();
                        wr.close();

                        int responseCode = con.getResponseCode();
                        BufferedReader br;
                        if (responseCode == 200) { // 정상 호출
                            br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                        } else {  // 에러 발생
                            br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                        }

                        String inputLine;
                        StringBuffer response = new StringBuffer();
                        while ((inputLine = br.readLine()) != null) {
                            response.append(inputLine);
                        }
                        br.close();
                        System.out.println(response.toString());
                        //        textView.setText(response.toString());
                        getResult = response.toString();

                        getResult = getResult.split("\"")[15];   //스플릿으로 번역된 결과값만 가져오기

                        //if(settingsFragment.autoAdd == true) {
                            mydb.insertData(textAry[i], getResult, "naver.com");
                            WaitForTheResult();
                        //}
                    }

                } catch (Exception e) {
                    System.out.println(e);
                }
            } else {
                try {
                    String text = URLEncoder.encode(word.toString(), "UTF-8");

                    String apiURL = "https://openapi.naver.com/v1/papago/n2mt";
                    URL url = new URL(apiURL);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("POST");
                    con.setRequestProperty("X-Naver-Client-Id", clientId);
                    con.setRequestProperty("X-Naver-Client-Secret", clientSecret);

                    // post request
                    String postParams = "source=en&target=ko&text=" + text;
                    con.setDoOutput(true);
                    DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                    wr.writeBytes(postParams);
                    wr.flush();
                    wr.close();

                    int responseCode = con.getResponseCode();
                    BufferedReader br;
                    if (responseCode == 200) { // 정상 호출
                        br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    } else {  // 에러 발생
                        br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                    }

                    String inputLine;
                    StringBuffer response = new StringBuffer();
                    while ((inputLine = br.readLine()) != null) {
                        response.append(inputLine);
                    }
                    br.close();
                    System.out.println(response.toString());
                    //        textView.setText(response.toString());
                    getResult = response.toString();

                    getResult = getResult.split("\"")[15];   //스플릿으로 번역된 결과값만 가져오기
                    // textView.setText(getResult); //  텍스트뷰에  SET해주기

                    ProcessTextActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ProcessTextActivity.this, word.toLowerCase() + " : " + getResult, Toast.LENGTH_LONG).show();
                            if(settingsFragment.autoAdd == true) {
                                mydb.insertData(word.toLowerCase(), getResult, "naver.com");
                            }
                            WaitForTheResult();
                        }
                    });
                } catch (Exception e) {
                    System.out.println(e);
                }
            }

            return null;
        }
    }
}



