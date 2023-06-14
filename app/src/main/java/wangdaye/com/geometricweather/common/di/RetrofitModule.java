package nowsci.com.temperateweather.common.di;

import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import nowsci.com.temperateweather.TemperateWeather;
import nowsci.com.temperateweather.common.retrofit.TLSCompactHelper;
import nowsci.com.temperateweather.common.retrofit.interceptors.GzipInterceptor;

@InstallIn(SingletonComponent.class)
@Module
public class RetrofitModule {

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient(GzipInterceptor gzipInterceptor,
                                            HttpLoggingInterceptor loggingInterceptor) {
        return TLSCompactHelper.getClientBuilder()
                .addInterceptor(gzipInterceptor)
                .addInterceptor(loggingInterceptor)
                .build();
    }

    @Provides
    @Singleton
    public GsonConverterFactory provideGsonConverterFactory() {
        return GsonConverterFactory.create(
                new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()
        );
    }

    @Provides
    @Singleton
    public RxJava2CallAdapterFactory provideRxJava2CallAdapterFactory() {
        return RxJava2CallAdapterFactory.create();
    }

    @Provides
    @Singleton
    public HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        return new HttpLoggingInterceptor().setLevel(
                TemperateWeather.getInstance().getDebugMode()
                        ? HttpLoggingInterceptor.Level.BODY
                        : HttpLoggingInterceptor.Level.NONE
        );
    }
}
