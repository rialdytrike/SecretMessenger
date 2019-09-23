package com.trikersdev.secret;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.trikersdev.secret.common.ActivityBase;
import com.trikersdev.secret.dialogs.CommentActionDialog;
import com.trikersdev.secret.dialogs.CommentDeleteDialog;
import com.trikersdev.secret.dialogs.MyCommentActionDialog;
import com.trikersdev.secret.dialogs.MyPostActionDialog;
import com.trikersdev.secret.dialogs.PostActionDialog;
import com.trikersdev.secret.dialogs.PostDeleteDialog;
import com.trikersdev.secret.dialogs.PostReportDialog;


public class ViewItemActivity extends ActivityBase implements CommentDeleteDialog.AlertPositiveListener, PostDeleteDialog.AlertPositiveListener, PostReportDialog.AlertPositiveListener, MyPostActionDialog.AlertPositiveListener, PostActionDialog.AlertPositiveListener, CommentActionDialog.AlertPositiveListener, MyCommentActionDialog.AlertPositiveListener {

    Toolbar mToolbar;

    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        if (savedInstanceState != null) {

            fragment = getSupportFragmentManager().getFragment(savedInstanceState, "currentFragment");

        } else {

            fragment = new ViewItemFragment();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container_body, fragment).commit();
    }

    @Override
    protected void onSaveInstanceState (Bundle outState) {

        super.onSaveInstanceState(outState);

        getSupportFragmentManager().putFragment(outState, "currentFragment", fragment);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        fragment.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onPause() {

        ViewItemFragment p = (ViewItemFragment) fragment;
        p.hideEmojiKeyboard();

        super.onPause();
    }

    @Override
    public void onPostDelete(int position) {

        ViewItemFragment p = (ViewItemFragment) fragment;
        p.onPostDelete(position);
    }

    @Override
    public void onPostReport(int position, int reasonId) {

        ViewItemFragment p = (ViewItemFragment) fragment;
        p.onPostReport(position, reasonId);
    }

    @Override
    public void onPostRemove(int position) {

        ViewItemFragment p = (ViewItemFragment) fragment;
        p.onPostRemove(position);
    }

    @Override
    public void onPostShare(int position) {

        ViewItemFragment p = (ViewItemFragment) fragment;
        p.onPostShare(position);
    }

    @Override
    public void onPostReportDialog(int position) {

        ViewItemFragment p = (ViewItemFragment) fragment;
        p.report(position);
    }

    @Override
    public void onCommentRemove(int position) {

        ViewItemFragment p = (ViewItemFragment) fragment;
        p.onCommentRemove(position);
    }

    @Override
    public void onCommentDelete(int position) {

        ViewItemFragment p = (ViewItemFragment) fragment;
        p.onCommentDelete(position);
    }

    @Override
    public void onCommentReply(int position) {

        ViewItemFragment p = (ViewItemFragment) fragment;
        p.onCommentReply(position);
    }

    @Override
    public void onCommentCreateChat(int position) {

        ViewItemFragment p = (ViewItemFragment) fragment;
        p.onCommentCreateChat(position);
    }

    @Override
    public void onPostCreateChat(int position) {

        ViewItemFragment p = (ViewItemFragment) fragment;
        p.onPostCreateChat(position);
    }

    @Override
    public void onPostFollow(int position) {

        ViewItemFragment p = (ViewItemFragment) fragment;
        p.onPostFollow(position);
    }

    @Override
    public void onBackPressed(){

        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {

            case android.R.id.home: {

                finish();
                return true;
            }

            default: {

                return super.onOptionsItemSelected(item);
            }
        }
    }
}
