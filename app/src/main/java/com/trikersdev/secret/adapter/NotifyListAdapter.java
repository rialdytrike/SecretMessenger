package com.trikersdev.secret.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.pkmmte.view.CircularImageView;

import java.util.List;

import com.trikersdev.secret.R;
import com.trikersdev.secret.app.App;
import com.trikersdev.secret.constants.Constants;
import com.trikersdev.secret.model.Notify;

public class NotifyListAdapter extends BaseAdapter implements Constants {

	private Activity activity;
	private LayoutInflater inflater;
	private List<Notify> notifyList;

    ImageLoader imageLoader = App.getInstance().getImageLoader();

	public NotifyListAdapter(Activity activity, List<Notify> notifyList) {

		this.activity = activity;
		this.notifyList = notifyList;
	}

	@Override
	public int getCount() {

		return notifyList.size();
	}

	@Override
	public Object getItem(int location) {

		return notifyList.get(location);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}
	
	static class ViewHolder {

        public TextView notifyTitle;
        public TextView notifyTimeAgo;
		public CircularImageView notifyAuthor;
        public CircularImageView notifyType;
	        
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder viewHolder = null;

		if (inflater == null) {

            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

		if (convertView == null) {
			
			convertView = inflater.inflate(R.layout.notify_list_row, null);
			
			viewHolder = new ViewHolder();

            viewHolder.notifyAuthor = (CircularImageView) convertView.findViewById(R.id.notifyAuthor);
            viewHolder.notifyType = (CircularImageView) convertView.findViewById(R.id.notifyType);
            viewHolder.notifyTitle = (TextView) convertView.findViewById(R.id.notifyTitle);
			viewHolder.notifyTimeAgo = (TextView) convertView.findViewById(R.id.notifyTimeAgo);

            convertView.setTag(viewHolder);

		} else {
			
			viewHolder = (ViewHolder) convertView.getTag();
		}

        if (imageLoader == null) {

            imageLoader = App.getInstance().getImageLoader();
        }

        viewHolder.notifyTitle.setTag(position);
        viewHolder.notifyTimeAgo.setTag(position);
        viewHolder.notifyAuthor.setTag(position);
        viewHolder.notifyType.setTag(position);
        viewHolder.notifyAuthor.setTag(R.id.notifyAuthor, viewHolder);
		
		final Notify notify = notifyList.get(position);

        viewHolder.notifyType.setVisibility(View.GONE);

        if (notify.getType() == NOTIFY_TYPE_LIKE) {

            viewHolder.notifyTitle.setText(activity.getText(R.string.label_gcm_like));
            viewHolder.notifyType.setImageResource(R.drawable.notify_like);

            viewHolder.notifyAuthor.setImageResource(R.drawable.notify_like);

        } else if (notify.getType() == NOTIFY_TYPE_COMMENT) {

            viewHolder.notifyTitle.setText(activity.getText(R.string.label_gcm_comment));
            viewHolder.notifyType.setImageResource(R.drawable.notify_comment);

            viewHolder.notifyAuthor.setImageResource(R.drawable.notify_comment);

        } else if (notify.getType() == NOTIFY_TYPE_COMMENT_REPLY) {

            viewHolder.notifyTitle.setText(activity.getText(R.string.label_gcm_comment_reply));
            viewHolder.notifyType.setImageResource(R.drawable.notify_reply);

            viewHolder.notifyAuthor.setImageResource(R.drawable.notify_reply);

        } else {

            viewHolder.notifyTitle.setText(activity.getText(R.string.label_follow_you));
            viewHolder.notifyType.setImageResource(R.drawable.notify_follower);

            viewHolder.notifyAuthor.setImageResource(R.drawable.notify_follower);
        }

        viewHolder.notifyTimeAgo.setText(notify.getTimeAgo());

		return convertView;
	}
}