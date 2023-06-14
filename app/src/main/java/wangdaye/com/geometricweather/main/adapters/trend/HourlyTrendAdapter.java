package nowsci.com.temperateweather.main.adapters.trend;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import nowsci.com.temperateweather.common.basic.GeoActivity;
import nowsci.com.temperateweather.common.basic.models.Location;
import nowsci.com.temperateweather.common.basic.models.options.unit.PrecipitationUnit;
import nowsci.com.temperateweather.common.basic.models.options.unit.SpeedUnit;
import nowsci.com.temperateweather.common.basic.models.options.unit.TemperatureUnit;
import nowsci.com.temperateweather.main.adapters.trend.hourly.HourlyTemperatureAdapter;
import nowsci.com.temperateweather.main.adapters.trend.hourly.HourlyUVAdapter;
import nowsci.com.temperateweather.main.adapters.trend.hourly.HourlyWindAdapter;
import nowsci.com.temperateweather.theme.resource.providers.ResourceProvider;
import nowsci.com.temperateweather.common.ui.widgets.trend.TrendRecyclerView;
import nowsci.com.temperateweather.main.adapters.trend.hourly.AbsHourlyTrendAdapter;
import nowsci.com.temperateweather.main.adapters.trend.hourly.HourlyPrecipitationAdapter;

public class HourlyTrendAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private @Nullable AbsHourlyTrendAdapter mAdapter;

    public HourlyTrendAdapter() {
        mAdapter = null;
    }

    public void temperature(GeoActivity activity, TrendRecyclerView parent, Location location,
                            ResourceProvider provider, TemperatureUnit unit) {
        mAdapter = new HourlyTemperatureAdapter(activity, parent, location, provider, unit);
    }

    public void precipitation(GeoActivity activity, TrendRecyclerView parent, Location location,
                              ResourceProvider provider, PrecipitationUnit unit) {
        mAdapter = new HourlyPrecipitationAdapter(activity, parent, location, provider, unit);
    }

    public void wind(GeoActivity activity, TrendRecyclerView parent, Location location, SpeedUnit unit) {
        mAdapter = new HourlyWindAdapter(activity, parent, location, unit);
    }

    public void uv(GeoActivity activity, TrendRecyclerView parent, Location location) {
        mAdapter = new HourlyUVAdapter(activity, parent, location);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        assert mAdapter != null;
        return mAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        assert mAdapter != null;
        mAdapter.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return mAdapter == null ? 0 : mAdapter.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        if (mAdapter == null) {
            return 0;
        } else if (mAdapter instanceof HourlyTemperatureAdapter) {
            return 1;
        } else if (mAdapter instanceof HourlyPrecipitationAdapter) {
            return 2;
        } else if (mAdapter instanceof HourlyWindAdapter) {
            return 3;
        } else if (mAdapter instanceof HourlyUVAdapter) {
            return 4;
        }
        return -1;
    }
}
