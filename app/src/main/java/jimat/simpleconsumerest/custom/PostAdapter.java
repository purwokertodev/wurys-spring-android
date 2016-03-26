package jimat.simpleconsumerest.custom;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import jimat.simpleconsumerest.AuthorDetailActivity;
import jimat.simpleconsumerest.PostAllActivity;
import jimat.simpleconsumerest.R;
import jimat.simpleconsumerest.model.Author;
import jimat.simpleconsumerest.util.ImageLoader;

/**
 * Author :) WURIYANTO on 24/03/2016.
 * JMAT UMP.INC
 */
public class PostAdapter extends ArrayAdapter<Map<String, Object>> {

    private static final String TAG_ID = "id";
    private static final String TAG_AUTHOR = "author";
    private static final String TAG_POSTING_DATE = "postingDate";
    private static final String TAG_POST_TITLE = "postTitle";
    private static final String TAG_POST_CONTENT = "postContent";


    private ImageLoader imageLoader;
    private Activity activity;

    public PostAdapter(Activity activity, List<Map<String, Object>> maps) {
        super(activity.getApplicationContext(), 0, maps);
        imageLoader = new ImageLoader(activity.getApplicationContext());
        this.activity = activity;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        Map<String, Object> map = getItem(position);
        if(v == null){
            v = LayoutInflater.from(getContext()).inflate(R.layout.list_post_single, parent, false);
        }
        TextView txtTitle = (TextView) v.findViewById(R.id.txt_post_title);
        TextView txtContent = (TextView) v.findViewById(R.id.txt_post_content);
        TextView txtAuthorName = (TextView) v.findViewById(R.id.txt_author_name);
        ImageView authorPic = (ImageView) v.findViewById(R.id.img_author);

        Author a = (Author) map.get(TAG_AUTHOR);
        String urlImageAuthor = activity.getApplicationContext().getString(R.string.url_author_image)+a.getPictureLocation();
        final String id = a.getId().toString();

        txtTitle.setText(map.get(TAG_POST_TITLE).toString());
        txtContent.setText(map.get(TAG_POST_CONTENT).toString());
        txtAuthorName.setText("By "+a.getAuthorName().toString());
        imageLoader.DisplayImage(urlImageAuthor, authorPic);

        txtAuthorName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(activity.getApplicationContext(), AuthorDetailActivity.class);
                in.putExtra("id_author", id);
                activity.startActivityForResult(in, 100);
            }
        });
        return v;
    }
}
