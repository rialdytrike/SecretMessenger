package com.trikersdev.secret.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import com.trikersdev.secret.R;
import com.trikersdev.secret.app.App;

public class NavDrawerAdapter extends RecyclerView.Adapter<NavDrawerAdapter.ViewHolder> {

    String[] titles;
    TypedArray icons;
    Context context;

    ImageLoader imageLoader = App.getInstance().getImageLoader();

    // The default constructor to receive titles,icons and context from MainActivity.
    public NavDrawerAdapter(String[] titles , TypedArray icons , Context context){

        this.titles = titles;
        this.icons = icons;
        this.context = context;
    }

    /**
     *Its a inner class to NavDrawerAdapter Class.
     *This ViewHolder class implements View.OnClickListener to handle click events.
     *If the itemType==1 ; it implies that the view is a single row_item with TextView and ImageView.
     *This ViewHolder describes an item view with respect to its place within the RecyclerView.
     *For every item there is a ViewHolder associated with it .
     */

    public class ViewHolder extends RecyclerView.ViewHolder  {

        TextView  navTitle, navCounter;
        ImageView navIcon, drawerImage;
        Context context;

        public ViewHolder(View drawerItem, int itemType, Context context){

            super(drawerItem);

            this.context = context;

            if (itemType == 1) {

                navCounter = (TextView) itemView.findViewById(R.id.tv_NavCounter);
                navTitle = (TextView) itemView.findViewById(R.id.tv_NavTitle);
                navIcon = (ImageView) itemView.findViewById(R.id.iv_NavIcon);

            } else {

                drawerImage = (ImageView) itemView.findViewById(R.id.drawerImage);
            }
        }
    }

    /**
     *This is called every time when we need a new ViewHolder and a new ViewHolder is required for every item in RecyclerView.
     *Then this ViewHolder is passed to onBindViewHolder to display items.
     */

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (viewType == 1) {

            View itemLayout = layoutInflater.inflate(R.layout.nav_drawer_row, null);
            return new ViewHolder(itemLayout, viewType, context);

        } else if (viewType == 0) {

            View itemHeader = layoutInflater.inflate(R.layout.header_navigation_drawer, null);

            return new ViewHolder(itemHeader, viewType, context);
        }

        return null;
    }

    /**
     *This method is called by RecyclerView.Adapter to display the data at the specified position.
     *This method should update the contents of the itemView to reflect the item at the given position.
     *So here , if position!=0 it implies its a row_item and we set the title and icon of the view.
     */

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if (imageLoader == null) {

            imageLoader = App.getInstance().getImageLoader();
        }

        if (position != 0) {

            switch (position) {

                case 5: {

                    holder.navCounter.setVisibility(View.GONE);

                    if (App.getInstance().getNotificationsCount() > 0) {

                        holder.navCounter.setText(Integer.toString(App.getInstance().getNotificationsCount()));
                        holder.navCounter.setVisibility(View.VISIBLE);
                    }

                    holder.navTitle.setText(titles[position - 1]);
                    holder.navIcon.setImageResource(icons.getResourceId(position-1, -1));

                    break;
                }

                case 6: {

                    holder.navCounter.setVisibility(View.GONE);

                    if (App.getInstance().getMessagesCount() > 0) {

                        holder.navCounter.setText(Integer.toString(App.getInstance().getMessagesCount()));
                        holder.navCounter.setVisibility(View.VISIBLE);
                    }

                    holder.navTitle.setText(titles[position - 1]);
                    holder.navIcon.setImageResource(icons.getResourceId(position-1, -1));

                    break;
                }

                default: {

                    holder.navCounter.setVisibility(View.GONE);
                    holder.navTitle.setText(titles[position - 1]);
                    holder.navIcon.setImageResource(icons.getResourceId(position-1, -1));

                    break;
                }
            }

        }
    }

    /**
     *It returns the total no. of items . We +1 count to include the header view.
     *So , it the total count is 5 , the method returns 6.
     *This 6 implies that there are 5 row_items and 1 header view with header at position zero.
     */

    @Override
    public int getItemCount() {

        return titles.length + 1;
    }


    /**
     *This methods returns 0 if the position of the item is '0'.
     *If the position is zero its a header view and if its anything else
     *its a row_item with a title and icon.
     */

    @Override
    public int getItemViewType(int position) {

        if (position == 0 ) {

            return 0;

        } else {

            return 1;
        }
    }


}