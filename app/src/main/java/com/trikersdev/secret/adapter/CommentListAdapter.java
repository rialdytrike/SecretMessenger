package com.trikersdev.secret.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.pkmmte.view.CircularImageView;

import java.util.List;

import github.ankushsachdeva.emojicon.EmojiconTextView;
import com.trikersdev.secret.R;
import com.trikersdev.secret.app.App;
import com.trikersdev.secret.constants.Constants;
import com.trikersdev.secret.model.Comment;
import com.trikersdev.secret.util.CommentInterface;
import com.trikersdev.secret.util.TagSelectingTextview;
import com.trikersdev.secret.view.ResizableImageView;


public class CommentListAdapter extends BaseAdapter implements Constants {

	private Activity activity;
	private LayoutInflater inflater;
	private List<Comment> commentsList;

    private CommentInterface responder;

    private Boolean myPost = false;

    TagSelectingTextview mTagSelectingTextview;

    public static int hashTagHyperLinkEnabled = 1;
    public static int hashTagHyperLinkDisabled = 0;

    ImageLoader imageLoader = App.getInstance().getImageLoader();

	public CommentListAdapter(Activity activity, List<Comment> commentsList, CommentInterface responder) {

		this.activity = activity;
		this.commentsList = commentsList;
        this.responder = responder;

        mTagSelectingTextview = new TagSelectingTextview();
	}

    public void setMyPost(Boolean myPost) {

        this.myPost = myPost;
    }

    public Boolean getMyPost() {

        return this.myPost;
    }

	@Override
	public int getCount() {

		return commentsList.size();
	}

	@Override
	public Object getItem(int location) {

		return commentsList.get(location);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}
	
	static class ViewHolder {

        public CircularImageView commentAuthorPhoto;
        public EmojiconTextView commentText;
        public TextView commentTimeAgo;
        public TextView commentLikesCount;
        public ImageView commentLike;
        public ImageView commentAction;
        public ResizableImageView commentImg;
	        
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		ViewHolder viewHolder = null;

		if (inflater == null) {

            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

		if (convertView == null) {
			
			convertView = inflater.inflate(R.layout.comment_list_row, null);
			
			viewHolder = new ViewHolder();

            viewHolder.commentAuthorPhoto = (CircularImageView) convertView.findViewById(R.id.commentAuthorPhoto);
            viewHolder.commentImg = (ResizableImageView) convertView.findViewById(R.id.commentImg);
            viewHolder.commentLike = (ImageView) convertView.findViewById(R.id.commentLike);
            viewHolder.commentAction = (ImageView) convertView.findViewById(R.id.commentAction);
            viewHolder.commentLikesCount = (TextView) convertView.findViewById(R.id.commentLikesCount);
			viewHolder.commentText = (EmojiconTextView) convertView.findViewById(R.id.commentText);
            viewHolder.commentTimeAgo = (TextView) convertView.findViewById(R.id.commentTimeAgo);

//            viewHolder.questionRemove.setTag(position);
            convertView.setTag(viewHolder);

		} else {
			
			viewHolder = (ViewHolder) convertView.getTag();
		}

        if (imageLoader == null) {

            imageLoader = App.getInstance().getImageLoader();
        }

        viewHolder.commentAuthorPhoto.setTag(position);
        viewHolder.commentText.setTag(position);
        viewHolder.commentTimeAgo.setTag(position);
        viewHolder.commentImg.setTag(position);
        viewHolder.commentAction.setTag(position);
        viewHolder.commentLikesCount.setTag(position);
        viewHolder.commentLike.setTag(position);
		
		final Comment comment = commentsList.get(position);

        if (comment.getImage().length() != 0) {

            viewHolder.commentAuthorPhoto.setVisibility(View.VISIBLE);

            imageLoader.get(comment.getImage(), ImageLoader.getImageListener(viewHolder.commentAuthorPhoto, R.drawable.profile_default_photo, R.drawable.profile_default_photo));

        } else {

            viewHolder.commentAuthorPhoto.setVisibility(View.VISIBLE);
            viewHolder.commentAuthorPhoto.setImageResource(R.drawable.profile_default_photo);
        }


        viewHolder.commentAction.setVisibility(View.VISIBLE);

        viewHolder.commentAction.setImageResource(R.drawable.ic_action_collapse);

        viewHolder.commentAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final int getPosition = (Integer) view.getTag();

                responder.commentAction(getPosition);
            }
        });

        viewHolder.commentText.setText(comment.getText().replaceAll("<br>", "\n"));

        viewHolder.commentText.setMovementMethod(LinkMovementMethod.getInstance());

        String textHtml = comment.getText();

        viewHolder.commentText.setText(textHtml);

        viewHolder.commentText.setVisibility(View.VISIBLE);

        String timeAgo;

        timeAgo = comment.getTimeAgo();

        viewHolder.commentTimeAgo.setVisibility(View.VISIBLE);


        if (comment.getFromUserId() == comment.getPostFromUserId()) {

            viewHolder.commentTimeAgo.setText(timeAgo + " - " + activity.getString(R.string.label_author));

        } else {

            viewHolder.commentTimeAgo.setText(timeAgo);
        }

        viewHolder.commentImg.setVisibility(View.GONE);

        viewHolder.commentLike.setVisibility(View.GONE);
        viewHolder.commentLikesCount.setVisibility(View.GONE);
        viewHolder.commentLike.setImageResource(R.drawable.perk);

		return convertView;
	}
}