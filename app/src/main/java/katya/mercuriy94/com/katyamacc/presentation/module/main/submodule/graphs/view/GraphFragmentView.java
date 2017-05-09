package katya.mercuriy94.com.katyamacc.presentation.module.main.submodule.graphs.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Random;

import butterknife.BindView;
import katya.mercuriy94.com.katyamacc.R;
import katya.mercuriy94.com.katyamacc.presentation.common.annotations.Layout;
import katya.mercuriy94.com.katyamacc.presentation.module.app.MyApp;
import katya.mercuriy94.com.katyamacc.presentation.module.main.MainModuleContract;
import katya.mercuriy94.com.katyamacc.presentation.module.main.submodule.graphs.GraphModuleContract;
import katya.mercuriy94.com.katyamacc.presentation.module.main.submodule.graphs.presenter.GraphPresneter;

@Layout(R.layout.graph_layout)
public class GraphFragmentView extends GraphModuleContract.AbstractGraphView {

    public static final String TAG = "GraphFragmentView";


    @InjectPresenter
    GraphModuleContract.AbstractGraphPresenter mPresenter;

    @BindView(R.id.graph)
    GraphView mGraphView;


    private LineGraphSeries<DataPoint> mSeries1;
    private LineGraphSeries<DataPoint> mSeries2;

    double mLastRandom = 2;
    Random mRand = new Random();

    private double getRandom() {
        return mLastRandom += mRand.nextDouble() * 0.5 - 0.25;
    }

    public static GraphFragmentView newInstance() {
        return new GraphFragmentView();
    }


    @ProvidePresenter
    GraphModuleContract.AbstractGraphPresenter providePresenter() {
        return new GraphPresneter(MyApp.get(getActivity()));
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSeries1 = new LineGraphSeries<>(generateData());
        mSeries2 = new LineGraphSeries<>(generateData());
        mSeries1.setColor(ContextCompat.getColor(getActivity(), R.color.colorAccent));
        mGraphView.addSeries(mSeries1);
        mGraphView.addSeries(mSeries2);
        mGraphView.getViewport().setScalable(true);
        mGraphView.getViewport().setScalableY(true);
        mGraphView.getViewport().setScrollable(true);
        mGraphView.getViewport().setScrollableY(true);

    }

    @Override
    public void setInitialState() {

    }

    @NonNull
    @Override
    public GraphModuleContract.AbstractGraphPresenter getPresenter() {
        return mPresenter;
    }

    @NonNull
    @Override
    protected MainModuleContract.AbstractMainRouter getRouter() {
        return ((MainModuleContract.AbstractMainView) getActivity()).getPresenter().getRouter();
    }


    private DataPoint[] generateData() {
        int count = 60;
        DataPoint[] values = new DataPoint[count];
        for (int i = 0; i < count; i++) {
            double f = mRand.nextDouble() * 0.15 + 0.3;
            double y = Math.sin(i * f + 2) + mRand.nextDouble() * 0.3;
            DataPoint v = new DataPoint((double) i, y);
            values[i] = v;
        }
        return values;
    }
}
