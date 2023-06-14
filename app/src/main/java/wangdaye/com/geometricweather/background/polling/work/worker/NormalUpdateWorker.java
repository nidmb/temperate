package nowsci.com.temperateweather.background.polling.work.worker;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.hilt.work.HiltWorker;
import androidx.work.WorkerParameters;
import androidx.work.impl.utils.futures.SettableFuture;

import java.util.List;

import dagger.assisted.Assisted;
import dagger.assisted.AssistedInject;
import nowsci.com.temperateweather.common.basic.models.Location;
import nowsci.com.temperateweather.location.LocationHelper;
import nowsci.com.temperateweather.remoteviews.NotificationHelper;
import nowsci.com.temperateweather.remoteviews.WidgetHelper;
import nowsci.com.temperateweather.weather.WeatherHelper;

@HiltWorker
public class NormalUpdateWorker extends AsyncUpdateWorker {

    @AssistedInject
    public NormalUpdateWorker(@Assisted @NonNull Context context,
                              @Assisted @NonNull WorkerParameters workerParams,
                              LocationHelper locationHelper,
                              WeatherHelper weatherHelper) {
        super(context, workerParams, locationHelper, weatherHelper);
    }

    @Override
    public void updateView(Context context, Location location) {
        WidgetHelper.updateWidgetIfNecessary(context, location);
    }

    @Override
    public void updateView(Context context, List<Location> locationList) {
        WidgetHelper.updateWidgetIfNecessary(context, locationList);
        NotificationHelper.updateNotificationIfNecessary(context, locationList);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void handleUpdateResult(SettableFuture<Result> future, boolean failed) {
        future.set(failed ? Result.retry() : Result.success());
    }
}
