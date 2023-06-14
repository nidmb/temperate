package nowsci.com.temperateweather.background.polling.work.worker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.work.WorkerParameters;
import androidx.work.impl.utils.futures.SettableFuture;

import java.util.List;

import nowsci.com.temperateweather.background.polling.PollingUpdateHelper;
import nowsci.com.temperateweather.common.basic.models.Location;
import nowsci.com.temperateweather.common.basic.models.weather.Weather;
import nowsci.com.temperateweather.common.utils.helpers.ShortcutsHelper;
import nowsci.com.temperateweather.location.LocationHelper;
import nowsci.com.temperateweather.remoteviews.NotificationHelper;
import nowsci.com.temperateweather.weather.WeatherHelper;

public abstract class AsyncUpdateWorker extends AsyncWorker
        implements PollingUpdateHelper.OnPollingUpdateListener {

    private final PollingUpdateHelper mPollingUpdateHelper;

    private SettableFuture<Result> mFuture;
    private boolean mFailed;

    public AsyncUpdateWorker(@NonNull Context context,
                             @NonNull WorkerParameters workerParams,
                             LocationHelper locationHelper,
                             WeatherHelper weatherHelper) {
        super(context, workerParams);

        mPollingUpdateHelper = new PollingUpdateHelper(context, locationHelper, weatherHelper);
        mPollingUpdateHelper.setOnPollingUpdateListener(this);
    }

    @Override
    public void doAsyncWork(SettableFuture<Result> f) {
        mFuture = f;
        mFailed = false;

        mPollingUpdateHelper.pollingUpdate();
    }

    // control.

    public abstract void updateView(Context context, Location location);

    public abstract void updateView(Context context, List<Location> locationList);

    /**
     * Call {@link SettableFuture#set(Object)} here.
     * */
    public abstract void handleUpdateResult(SettableFuture<Result> future, boolean failed);

    // interface.

    // on polling update listener.

    @Override
    public void onUpdateCompleted(@NonNull Location location, @Nullable Weather old,
                                  boolean succeed, int index, int total) {
        if (index == 0) {
            updateView(getApplicationContext(), location);
            if (succeed) {
                NotificationHelper.checkAndSendAlert(getApplicationContext(), location, old);
                NotificationHelper.checkAndSendPrecipitationForecast(getApplicationContext(), location);
            } else {
                mFailed = true;
            }
        }
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onPollingCompleted(List<Location> locationList) {
        updateView(getApplicationContext(), locationList);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            ShortcutsHelper.refreshShortcutsInNewThread(getApplicationContext(), locationList);
        }
        handleUpdateResult(mFuture, mFailed);
    }
}
