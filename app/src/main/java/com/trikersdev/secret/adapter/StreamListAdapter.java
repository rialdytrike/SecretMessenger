package com.trikersdev.secret.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import github.ankushsachdeva.emojicon.EmojiconTextView;
import com.trikersdev.secret.HashtagsActivity;
import com.trikersdev.secret.PhotoViewActivity;
import com.trikersdev.secret.R;
import com.trikersdev.secret.ViewItemActivity;
import com.trikersdev.secret.app.App;
import com.trikersdev.secret.constants.Constants;
import com.trikersdev.secret.model.Item;
import com.trikersdev.secret.util.CustomRequest;
import com.trikersdev.secret.util.ItemInterface;
import com.trikersdev.secret.util.TagClick;
import com.trikersdev.secret.util.TagSelectingTextview;
import com.trikersdev.secret.view.ResizableImageView;

public class StreamListAdapter extends BaseAdapter implements Constants, TagClick {

	private Activity activity;
	private LayoutInflater inflater;
	private List<Item> itemsList;

    private ItemInterface responder;


    TagSelectingTextview mTagSelectingTextview;

    public static int hashTagHyperLinkEnabled = 1;
    public static int hashTagHyperLinkDisabled = 0;

    ImageLoader imageLoader = App.getInstance().getImageLoader();

	public StreamListAdapter(Activity activity, List<Item> itemsList, ItemInterface responder) {

		this.activity = activity;
		this.itemsList = itemsList;
        this.responder = responder;

        mTagSelectingTextview = new TagSelectingTextview();
	}

	@Override
	public int getCount() {

		return itemsList.size();
	}

	@Override
	public Object getItem(int location) {

		return itemsList.get(location);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}
	
	static class ViewHolder {

        public ResizableImageView itemImg;
        public EmojiconTextView itemPost;
        public TextView itemTimeAgo;
        public TextView itemLikesCount;
        public TextView itemCommentsCount;
        public ImageView itemLike;
        public ImageView itemComment;
        public ImageView itemAction;
        public TextView itemCity;
        public TextView itemCountry;
        public LinearLayout locationContainer;

        public TextView itemCategory;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		ViewHolder viewHolder = null;

		if (inflater == null) {

            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

		if (convertView == null) {
			
			convertView = inflater.inflate(R.layout.stream_list_row, null);
			
			viewHolder = new ViewHolder();

            viewHolder.itemImg = (ResizableImageView) convertView.findViewById(R.id.itemImg);
            viewHolder.itemPost = (EmojiconTextView) convertView.findViewById(R.id.itemPost);
            viewHolder.itemTimeAgo = (TextView) convertView.findViewById(R.id.itemTimeAgo);
            viewHolder.itemLikesCount = (TextView) convertView.findViewById(R.id.itemLikesCount);
            viewHolder.itemCommentsCount = (TextView) convertView.findViewById(R.id.itemCommentsCount);
            viewHolder.itemLike = (ImageView) convertView.findViewById(R.id.itemLike);
            viewHolder.itemComment = (ImageView) convertView.findViewById(R.id.itemComment);
            viewHolder.itemAction = (ImageView) convertView.findViewById(R.id.itemAction);
            viewHolder.itemCity = (TextView) convertView.findViewById(R.id.itemCity);
            viewHolder.itemCountry = (TextView) convertView.findViewById(R.id.itemCountry);
            viewHolder.locationContainer = (LinearLayout) convertView.findViewById(R.id.locationContainer);

            viewHolder.itemCategory = (TextView) convertView.findViewById(R.id.itemCategory);

            convertView.setTag(viewHolder);

		} else {
			
			viewHolder = (ViewHolder) convertView.getTag();
		}

        if (imageLoader == null) {

            imageLoader = App.getInstance().getImageLoader();
        }

        viewHolder.itemImg.setTag(position);
        viewHolder.itemPost.setTag(position);
        viewHolder.itemTimeAgo.setTag(position);
        viewHolder.itemLikesCount.setTag(position);
        viewHolder.itemCommentsCount.setTag(position);
        viewHolder.itemLike.setTag(position);
        viewHolder.itemComment.setTag(position);
        viewHolder.itemAction.setTag(position);
        viewHolder.itemCity.setTag(position);
        viewHolder.itemCountry.setTag(position);
        viewHolder.locationContainer.setTag(position);

        viewHolder.itemCategory.setTag(position);
		
		final Item item = itemsList.get(position);

        if ((item.getCity() != null && item.getCity().length() > 0) || (item.getCountry() != null && item.getCountry().length() > 0)) {

            if (item.getCity() != null && item.getCity().length() > 0) {

                viewHolder.itemCity.setText(item.getCity());
                viewHolder.itemCity.setVisibility(View.VISIBLE);

            } else {

                viewHolder.itemCity.setVisibility(View.GONE);
            }

            if (item.getCountry() != null && item.getCountry().length() > 0) {

                viewHolder.itemCountry.setText(item.getCountry());
                viewHolder.itemCountry.setVisibility(View.VISIBLE);

            } else {

                viewHolder.itemCountry.setVisibility(View.GONE);
            }

            viewHolder.locationContainer.setVisibility(View.VISIBLE);

        } else {

            viewHolder.locationContainer.setVisibility(View.GONE);
        }

        viewHolder.itemAction.setImageResource(R.drawable.ic_action_collapse);

        viewHolder.itemAction.setVisibility(View.VISIBLE);

        viewHolder.itemAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final int getPosition = (Integer) view.getTag();

                responder.action(getPosition);
            }
        });

        viewHolder.itemLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final int getPosition = (Integer) view.getTag();

                if (App.getInstance().isConnected()) {

                    CustomRequest jsonReq = new CustomRequest(Request.Method.POST, METHOD_ITEMS_LIKE, null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {

                                    try {

                                        if (!response.getBoolean("error")) {

                                            item.setLikesCount(response.getInt("likesCount"));
                                            item.setMyLike(response.getBoolean("myLike"));
                                        }

                                    } catch (JSONException e) {

                                        e.printStackTrace();

                                    } finally {

                                        notifyDataSetChanged();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toast.makeText(activity.getApplicationContext(), activity.getString(R.string.error_data_loading), Toast.LENGTH_LONG).show();
                        }
                    }) {

                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("accountId", Long.toString(App.getInstance().getId()));
                            params.put("accessToken", App.getInstance().getAccessToken());
                            params.put("itemId", Long.toString(item.getId()));

                            return params;
                        }
                    };

                    App.getInstance().addToRequestQueue(jsonReq);

                } else {

                    Toast.makeText(activity.getApplicationContext(), activity.getText(R.string.msg_network_error), Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (item.isMyLike()) {

            viewHolder.itemLike.setImageResource(R.drawable.perk_active);

        } else {

            viewHolder.itemLike.setImageResource(R.drawable.perk);
        }

        viewHolder.itemComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(activity, ViewItemActivity.class);
                intent.putExtra("itemId", item.getId());
                activity.startActivity(intent);
            }
        });

        viewHolder.itemCommentsCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(activity, ViewItemActivity.class);
                intent.putExtra("itemId", item.getId());
                activity.startActivity(intent);
            }
        });

        if (item.getLikesCount() > 0) {

            viewHolder.itemLikesCount.setText(Integer.toString(item.getLikesCount()));
            viewHolder.itemLikesCount.setVisibility(View.VISIBLE);

        } else {

            viewHolder.itemLikesCount.setText(Integer.toString(item.getLikesCount()));
            viewHolder.itemLikesCount.setVisibility(View.GONE);
        }

        if (item.getCommentsCount() > 0) {

            viewHolder.itemCommentsCount.setText(Integer.toString(item.getCommentsCount()));
            viewHolder.itemCommentsCount.setVisibility(View.VISIBLE);

        } else {

            viewHolder.itemCommentsCount.setText(Integer.toString(item.getCommentsCount()));
            viewHolder.itemCommentsCount.setVisibility(View.GONE);
        }

        viewHolder.itemTimeAgo.setText(item.getTimeAgo());
        viewHolder.itemTimeAgo.setVisibility(View.VISIBLE);

        viewHolder.itemCategory.setText(getCategory(item.getCategory()));


        if (item.getPost().length() > 0) {

            viewHolder.itemPost.setText(item.getPost().replaceAll("<br>", "\n"));

            viewHolder.itemPost.setVisibility(View.VISIBLE);

            viewHolder.itemPost.setMovementMethod(LinkMovementMethod.getInstance());

            String textHtml = item.getPost();

            viewHolder.itemPost.setText(mTagSelectingTextview.addClickablePart(Html.fromHtml(textHtml).toString(), this, hashTagHyperLinkDisabled, HASHTAGS_COLOR), TextView.BufferType.SPANNABLE);

        } else {

            viewHolder.itemPost.setVisibility(View.GONE);
        }

        if (item.getImgUrl().length() > 0) {

            imageLoader.get(item.getImgUrl(), ImageLoader.getImageListener(viewHolder.itemImg, R.drawable.img_loading, R.drawable.img_loading));
            viewHolder.itemImg.setVisibility(View.VISIBLE);

            viewHolder.itemImg.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    Intent i = new Intent(activity, PhotoViewActivity.class);
                    i.putExtra("imgUrl", item.getImgUrl());
                    activity.startActivity(i);
                }
            });

        } else {

            viewHolder.itemImg.setVisibility(View.GONE);
        }

		return convertView;
	}

    @Override
    public void clickedTag(CharSequence tag) {
        // TODO Auto-generated method stub

        Intent i = new Intent(activity, HashtagsActivity.class);
        i.putExtra("hashtag", tag);
        activity.startActivity(i);
    }

    private String getCategory(int category) {

        return activity.getResources().getStringArray(R.array.navSpinnerItems)[category];
    }
}