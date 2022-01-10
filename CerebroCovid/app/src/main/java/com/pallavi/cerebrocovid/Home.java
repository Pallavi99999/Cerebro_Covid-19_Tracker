package com.pallavi.cerebrocovid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.Response;
import com.google.androidgamesdk.gametextinput.Listener;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

    TextView logout;
    RecyclerView recyclerView;
    ProgressDialog dialog;
    EditText search_bar;
    MyAdapter myadapter;


    private static String JSON_URL = "https://data.covid19india.org/district_wise.json";

    List<Model> modelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        logout = findViewById(R.id.logout);
        search_bar = findViewById(R.id.search_bar);





        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(),login.class);
                startActivity(intent);
            }
        });

        search_bar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });

        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading....");
        dialog.show();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        modelList = new ArrayList<>();

        StringRequest request = new StringRequest(JSON_URL, string -> parseJsonData(string), new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Some error occurred!!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(Home.this);
        rQueue.add(request);

    }

    void parseJsonData(String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("districts");

            for (int i = 0; i < jsonArray.length(); ++i) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                Model model = new Model();
                model.setLoc(jsonObject1.getString("district"));
                model.setTotalConfirmed(jsonObject1.getString("confirmed"));

                modelList.add(model);
            }

            //MyAdapter myAdapter = new MyAdapter(getApplicationContext(), modelList);
            myadapter = new MyAdapter(Home.this,modelList);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            recyclerView.setAdapter(myadapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        dialog.dismiss();
    }

    private void filter(String text){
        List<Model> filteredlist = new ArrayList<>();
        for(Model loc:modelList){
            if(loc.getLoc().toLowerCase().contains(text.toLowerCase())){
                filteredlist.add(loc);
            }
        }
        myadapter.filterList(filteredlist);
    }

}