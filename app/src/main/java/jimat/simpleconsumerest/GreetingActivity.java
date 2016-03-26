package jimat.simpleconsumerest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import jimat.simpleconsumerest.model.Greeting;

/**
 * Author :) WURIYANTO on 23/03/2016.
 * JMAT UMP.INC
 */
public class GreetingActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greeting);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceHolderFragment())
                    .commit();
        }

        new LoadData().execute();
    }

    public static class PlaceHolderFragment extends android.support.v4.app.Fragment{

        public PlaceHolderFragment(){}

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View myView = inflater.inflate(R.layout.fragment_main, container, false);
            return myView;
        }
    }

    private class LoadData extends AsyncTask<Void, Void, Greeting> {

        private ProgressDialog progressDialog = new ProgressDialog(GreetingActivity.this);


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setTitle("Spring Rest");
            progressDialog.setMessage("Please wait..");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected Greeting doInBackground(Void... params) {
            Greeting greeting = null;
            try{
                final String URL_DATA = getString(R.string.url_greetings);

                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                greeting = restTemplate.getForObject(URL_DATA, Greeting.class);

            }catch (Exception ex){

            }
            return greeting;
        }

        @Override
        protected void onPostExecute(Greeting greeting) {
            progressDialog.dismiss();
            TextView textId = (TextView) findViewById(R.id.id_value);
            TextView textContent = (TextView) findViewById(R.id.content_value);
            textId.setText(greeting.getId());
            textContent.setText(greeting.getContent());
        }
    }
}
