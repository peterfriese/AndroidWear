package de.peterfriese.wearablelistviewsample;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.wearable.view.CircledImageView;
import android.support.wearable.view.WatchViewStub;
import android.support.wearable.view.WearableListView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MyActivity extends Activity implements WearableListView.ClickListener {

    private WearableListView mListView;
    private MyListAdapter mAdapter;

    private float mDefaultCircleRadius;
    private float mSelectedCircleRadius;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        mDefaultCircleRadius = getResources().getDimension(R.dimen.default_settings_circle_radius);
        mSelectedCircleRadius = getResources().getDimension(R.dimen.selected_settings_circle_radius);
        mAdapter = new MyListAdapter();

        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mListView = (WearableListView) stub.findViewById(R.id.sample_list_view);
                mListView.setAdapter(mAdapter);
                mListView.setClickListener(MyActivity.this);
            }
        });
    }

    private static ArrayList<Integer> items;
    static {
        items = new ArrayList<Integer>();
        items.add(R.drawable.ic_action_attach);
        items.add(R.drawable.ic_action_call);
        items.add(R.drawable.ic_action_copy);
        items.add(R.drawable.ic_action_cut);
        items.add(R.drawable.ic_action_delete);
        items.add(R.drawable.ic_action_done);
        items.add(R.drawable.ic_action_edit);
        items.add(R.drawable.ic_action_locate);
        items.add(R.drawable.ic_action_mail);
        items.add(R.drawable.ic_action_mail_add);
        items.add(R.drawable.ic_action_microphone);
        items.add(R.drawable.ic_action_photo);
    }

    @Override
    public void onClick(WearableListView.ViewHolder viewHolder) {
        Toast.makeText(this, String.format("You selected item #%s", viewHolder.getPosition()), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTopEmptyRegionClick() {
        Toast.makeText(this, "You tapped into the empty area above the list", Toast.LENGTH_SHORT).show();
    }

    public class MyListAdapter extends WearableListView.Adapter {

        @Override
        public WearableListView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new WearableListView.ViewHolder(new MyItemView(MyActivity.this));
        }

        @Override
        public void onBindViewHolder(WearableListView.ViewHolder viewHolder, int i) {
            MyItemView myItemView = (MyItemView) viewHolder.itemView;

            TextView textView = (TextView) myItemView.findViewById(R.id.text);
            textView.setText(String.format("Line %d", i));

            Integer resourceId = items.get(i);
            CircledImageView imageView = (CircledImageView) myItemView.findViewById(R.id.image);
            imageView.setImageResource(resourceId);
        }

        @Override
        public int getItemCount() {
            return items.size();
        }
    }

    private final class MyItemView extends FrameLayout implements WearableListView.Item {

        final CircledImageView image;
        final TextView text;
        private float mScale;

        public MyItemView(Context context) {
            super(context);
            View.inflate(context, R.layout.wearablelistview_item, this);
            image = (CircledImageView) findViewById(R.id.image);
            text = (TextView) findViewById(R.id.text);
        }

        @Override
        public float getProximityMinValue() {
            return mDefaultCircleRadius;
        }

        @Override
        public float getProximityMaxValue() {
            return mSelectedCircleRadius;
        }

        @Override
        public float getCurrentProximityValue() {
            return mScale;
        }

        @Override
        public void setScalingAnimatorValue(float value) {
            mScale = value;
            image.setCircleRadius(mScale);
            image.setCircleRadiusPressed(mScale);
        }

        @Override
        public void onScaleUpStart() {
            image.setAlpha(1f);
            text.setAlpha(1f);
        }

        @Override
        public void onScaleDownStart() {
            image.setAlpha(0.5f);
            text.setAlpha(0.5f);
        }
    }
}
