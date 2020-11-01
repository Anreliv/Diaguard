package com.faltenreich.diaguard.feature.entry.search;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import androidx.annotation.Nullable;

import com.faltenreich.diaguard.R;
import com.faltenreich.diaguard.databinding.ActivityEntrySearchBinding;
import com.faltenreich.diaguard.shared.data.database.entity.Tag;
import com.faltenreich.diaguard.shared.data.primitive.Vector2D;
import com.faltenreich.diaguard.shared.view.ViewUtils;
import com.faltenreich.diaguard.shared.view.activity.BaseActivity;

public class EntrySearchActivity extends BaseActivity<ActivityEntrySearchBinding> {

    private static final String ARGUMENT_REVEAL_X = "revealX";
    private static final String ARGUMENT_REVEAL_Y = "revealY";

    private int revealX;
    private int revealY;

    private ViewGroup rootLayout;

    public static void show(Context context, @Nullable View source) {
        Intent intent = new Intent(context, EntrySearchActivity.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && source != null) {
            Vector2D position = ViewUtils.getPositionOnScreen(source);
            intent.putExtra(ARGUMENT_REVEAL_X, position.x + (source.getWidth() / 2));
            intent.putExtra(ARGUMENT_REVEAL_Y, position.y + (source.getHeight() / 2));
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        }
        context.startActivity(intent);
    }

    public static void show(Context context, Tag tag) {
        Intent intent = new Intent(context, EntrySearchActivity.class);
        intent.putExtra(EntrySearchFragment.EXTRA_TAG_ID, tag.getId());
        context.startActivity(intent);
    }

    public EntrySearchActivity() {
        super(R.layout.activity_entry_search);
    }

    @Override
    protected ActivityEntrySearchBinding createBinding(LayoutInflater layoutInflater) {
        return ActivityEntrySearchBinding.inflate(layoutInflater);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootLayout = getBinding().root;
        if (savedInstanceState == null) {
            reveal();
        }
    }

    @Override
    public void finish() {
        unreveal();
    }

    private void reveal() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            revealX = getIntent().getIntExtra(ARGUMENT_REVEAL_X, -1);
            revealY = getIntent().getIntExtra(ARGUMENT_REVEAL_Y, -1);
            if (revealX >= 0 && revealY >= 0) {
                rootLayout.setVisibility(View.INVISIBLE);
                ViewTreeObserver viewTreeObserver = rootLayout.getViewTreeObserver();
                if (viewTreeObserver.isAlive()) {
                    viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                        @Override
                        public void onGlobalLayout() {
                            rootLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                            rootLayout.setVisibility(View.VISIBLE);
                            ViewUtils.reveal(rootLayout, revealX, revealY, true, new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    onViewShown();
                                }
                            });
                        }
                    });
                }
            } else {
                onViewShown();
            }
        } else {
            onViewShown();
        }
    }

    private void unreveal() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && revealX >= 0 && revealY >= 0) {
            ViewUtils.reveal(rootLayout, revealX, revealY, false, new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    rootLayout.setVisibility(View.INVISIBLE);
                    EntrySearchActivity.super.finish();
                    overridePendingTransition(0, 0);
                }
            });
        } else {
            super.finish();
        }
    }
}