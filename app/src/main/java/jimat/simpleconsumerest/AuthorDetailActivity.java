package jimat.simpleconsumerest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;

import jimat.simpleconsumerest.model.Author;
import jimat.simpleconsumerest.util.ImageLoader;

/**
 * Author :) WURIYANTO on 24/03/2016.
 * JMAT UMP.INC
 */
public class AuthorDetailActivity extends Activity {
    
    private TextView txtAuthorName, txtAuthorWebsite, txtAuthorEmail;
    private ImageView imageViewAvatar, imageViewHeader;

    private ImageLoader imageLoader;

    private String idAuthor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_detail);

        Intent intent = getIntent();
        idAuthor = intent.getStringExtra("id_author");

        txtAuthorName = (TextView) findViewById(R.id.txt_author_name_on_detail);
        txtAuthorWebsite = (TextView) findViewById(R.id.txt_author_website_on_detail);
        txtAuthorEmail = (TextView) findViewById(R.id.txt_author_email_on_detail);
        imageViewAvatar = (ImageView) findViewById(R.id.avatar);
        imageViewHeader = (ImageView) findViewById(R.id.header_imageview);

        imageLoader = new ImageLoader(AuthorDetailActivity.this);
        new LoadProfile().execute();
    }

    private Author getAuthor(){
        Author a = null;
        try{
            final String URL_BASE = getString(R.string.url_base_app);
//            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//            params.add("id", idAuthor);

            URI targetUrl = UriComponentsBuilder.fromUriString(URL_BASE).path("api/one_author_json/"+idAuthor).build().toUri();

            Log.i("SEND REQUEST TO", targetUrl.toString());

            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            ResponseEntity<Author> authorResponseEntity = restTemplate.exchange(targetUrl, HttpMethod.GET, requestEntity, Author.class);
            a = authorResponseEntity.getBody();


        }catch(Exception ex){

        }
        return a;
    }

    class LoadProfile extends AsyncTask<Void, Void, Author>{

        private ProgressDialog progressDialog = new ProgressDialog(AuthorDetailActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setTitle("Play blog");
            progressDialog.setMessage("Please wait..");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected Author doInBackground(Void... params) {
            return getAuthor();
        }

        @Override
        protected void onPostExecute(Author author) {
            progressDialog.dismiss();
            txtAuthorName.setText(author.getAuthorName());
            txtAuthorWebsite.setText("My Site : "+author.getWebsite());
            txtAuthorEmail.setText("My Email : "+author.getEmail());
            String urlImageBase = getString(R.string.url_author_image);
            String urlImageAuthor = urlImageBase+author.getPictureLocation();
            imageLoader.DisplayImage(urlImageAuthor, imageViewAvatar);
            imageLoader.DisplayImage(urlImageAuthor, imageViewHeader);
        }
    }
}
