/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package paddle.blockm;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.orm.SugarContext;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import paddle.R;
import paddle.blockm.adapters.BlacklistAdapter;
import paddle.blockm.models.BlockedNumber;

public class BlacklistActivity extends AppCompatActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.hiddenAdd)
    LinearLayout hiddenView;
    @Bind(R.id.blacklist_view)
    RecyclerView blacklistView;
    @Bind(R.id.numberEdit)
    TextView numberEdit;
    BlacklistAdapter blacklistAdapter;
    private boolean isHiddenViewVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SugarContext.init(this);
        setContentView(R.layout.activity_blacklist);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        blacklistAdapter = new BlacklistAdapter(this);
        blacklistAdapter.update();
        blacklistView.setAdapter(blacklistAdapter);
        blacklistView.setLayoutManager(new LinearLayoutManager(this));

        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
        blacklistView.addItemDecoration(itemDecoration);

        hiddenView.setVisibility(View.INVISIBLE);
        isHiddenViewVisible = false;
    }

    @OnClick(R.id.fab)
    public void onFabClick() {
        if (isHiddenViewVisible) {
            List<BlockedNumber> numberList = Select.from(BlockedNumber.class)
                    .where(Condition.prop("number").eq(numberEdit.getText()))
                    .list();
            if (numberList.size() == 0) {
                BlockedNumber number = new BlockedNumber();
                number.setNumber(numberEdit.getText().toString());
                number.save();
                blacklistAdapter.clear();
                blacklistAdapter.update();
            }
            hideEditText(hiddenView);
        } else {
            revealEditText(hiddenView);
        }
    }

    private void revealEditText(LinearLayout view) {
        int cx = view.getRight() - 30;
        int cy = view.getBottom() - 60;
        int finalRadius = Math.max(view.getWidth(), view.getHeight());
        Animator anim = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, finalRadius);
        }
        view.setVisibility(View.VISIBLE);
        isHiddenViewVisible = true;
        if (anim != null) {
            anim.start();
        }
    }

    private void hideEditText(final LinearLayout view) {
        int cx = view.getRight() - 30;
        int cy = view.getBottom() - 60;
        int initialRadius = view.getWidth();
        Animator anim = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, initialRadius, 0);
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    view.setVisibility(View.INVISIBLE);
                }
            });
            isHiddenViewVisible = false;
            anim.start();
        } else {
            view.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}