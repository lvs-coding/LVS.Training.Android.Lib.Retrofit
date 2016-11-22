package lvsandroid.simpleretrofitlibproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import lvsandroid.simpleretrofitlibproject.interfaces.StackOverflowAPI;
import lvsandroid.simpleretrofitlibproject.models.Question;
import lvsandroid.simpleretrofitlibproject.models.StackOverflowQuestions;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity  implements Callback<StackOverflowQuestions> {

    ArrayAdapter<Question> arrayAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.listView);

        arrayAdapter =
                new ArrayAdapter<>(this,
                        android.R.layout.simple_list_item_1,
                        android.R.id.text1,
                        new ArrayList<Question>());

        listView.setAdapter(arrayAdapter);

    }

    @Override
    public void onResponse(Response<StackOverflowQuestions> response, Retrofit retrofit) {
        arrayAdapter.clear();
        arrayAdapter.addAll(response.body().getItems());
        arrayAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFailure(Throwable t) {
        Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }

    public void buttonClick(View view) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.stackexchange.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // prepare call in Retrofit 2.0
        StackOverflowAPI stackOverflowAPI = retrofit.create(StackOverflowAPI.class);

        Call<StackOverflowQuestions> call = stackOverflowAPI.loadQuestions("android");
        //asynchronous call
        call.enqueue(this);

        // synchronous call would be with execute, in this case you
        // would have to perform this outside the main thread
        // call.execute()

        // to cancel a running request
        // call.cancel();
        // calls can only be used once but you can easily clone them
        //Call<StackOverflowQuestions> c = call.clone();
        //c.enqueue(this);
    }
}
