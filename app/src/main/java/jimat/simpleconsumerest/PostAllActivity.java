package jimat.simpleconsumerest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jimat.simpleconsumerest.custom.PostAdapter;
import jimat.simpleconsumerest.model.Post;

/**
 * Author :) WURIYANTO on 23/03/2016.
 * JMAT UMP.INC
 */
public class PostAllActivity extends Activity {

    private ListView listView;

    private static final String TAG_ID = "id";
    private static final String TAG_AUTHOR = "author";
    private static final String TAG_POSTING_DATE = "postingDate";
    private static final String TAG_POST_TITLE = "postTitle";
    private static final String TAG_POST_CONTENT = "postContent";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_all);
        listView = (ListView) findViewById(R.id.list_post);

        new LoadData().execute();
    }

    public List<Map<String, Object>> loadListPost(){
        List<Map<String, Object>> mapData = new ArrayList<>();
        try{
            String url = getString(R.string.url_all_post);
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            ResponseEntity<Post[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET,requestEntity,Post[].class);
            Post[] postArray = responseEntity.getBody();
            for(int i=0;i<postArray.length;i++){
                Post p = postArray[i];
                Map<String,Object> map = new HashMap<>();
                map.put(TAG_ID, p.getId());
                map.put(TAG_AUTHOR, p.getAuthor());
                map.put(TAG_POSTING_DATE, p.getPostingDate());
                map.put(TAG_POST_TITLE, p.getPostTitle());
                map.put(TAG_POST_CONTENT, p.getPostContent());
                mapData.add(map);
            }
        }catch (Exception ex){
            Log.e("ERROR_BLA_BLA", ex.getMessage());
        }

        return mapData;
    }


    private class LoadData extends AsyncTask<Void,Void,List<Map<String, Object>>>{

        private ProgressDialog progressDialog = new ProgressDialog(PostAllActivity.this);

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
        protected List<Map<String, Object>> doInBackground(Void... params) {
            return loadListPost();
        }

        @Override
        protected void onPostExecute(final List<Map<String, Object>> posts) {
            progressDialog.dismiss();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
//                    ListAdapter listAdapter = new SimpleAdapter(PostAllActivity.this, posts, R.layout.list_post_single,new String[]{TAG_POST_TITLE, TAG_POST_CONTENT}, new int[]{R.id.txt_post_title, R.id.txt_post_content});
//                    listView.setAdapter(listAdapter);
                    PostAdapter postAdapter = new PostAdapter(PostAllActivity.this, posts);
                    listView.setAdapter(postAdapter);
                }
            });
        }
    }
}
