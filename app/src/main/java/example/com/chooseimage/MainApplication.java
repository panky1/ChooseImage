//package example.com.chooseimage;
//
//import android.content.Context;
//import android.support.multidex.MultiDex;
//
//import com.android.volley.toolbox.ImageLoader;
//import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
//import com.nostra13.universalimageloader.core.DisplayImageOptions;
//
//import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
//import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
//
//import java.util.List;
//
//import dagger.ObjectGraph;
//import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
//
///**
// * Created by dilip on 4/29/16.
// */
//public class MainApplication extends MCXApplication{
//    public static final String LOG_TAG = "CHEF";
//
//    private boolean syncAdapterRunning = false;
//    private static final String TAG = "ObjectPreference";
//    private ComplexPreferences complexPrefenreces = null;
//
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        this.injectSelf();
//        complexPrefenreces = ComplexPreferences.getComplexPreferences(getBaseContext(), "Nitish", MODE_PRIVATE);
//        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
//                        .setDefaultFontPath("fonts/Roboto-Light.ttf")
//                        .setFontAttrId(R.attr.fontPath)
//                        .build()
//        );
//
//        // Enable tutorial sync
//
//        // Initialize Universal Image Loader
//        // Create global configuration and initialize ImageLoader with this configuration
//        DisplayImageOptions options = new DisplayImageOptions.Builder()
//                //.showImageOnLoading(R.drawable.ic_stub) // resource or drawable
//                //.showImageForEmptyUri(R.drawable.ic_empty) // resource or drawable
//                //.showImageOnFail(R.drawable.ic_error) // resource or drawable
//                //.resetViewBeforeLoading(false)  // default
//                //.delayBeforeLoading(1000)
//                .cacheInMemory(true) // default
//                .cacheOnDisc(true) // default
//                        //.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) // default
//                        //.bitmapConfig(Bitmap.Config.ARGB_8888) // default
//                        //.decodingOptions(...)
//                .displayer(new FadeInBitmapDisplayer(5000)) // default SimpleBitmapDisplayer,RoundedBitmapDisplayer(10),FadeInBitmapDisplayer
//                        //.handler(new Handler()) // default
//                .build();
//
////        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
//                //.memoryCacheExtraOptions(480, 800) // default = device screen dimensions
//                //.discCacheExtraOptions(480, 800, CompressFormat.JPEG, 75, null)
//                //.taskExecutor(...)
//                //.taskExecutorForCachedImages(...)
//                //.threadPoolSize(3) // default
//                //.threadPriority(Thread.NORM_PRIORITY - 1) // default
//                //.tasksProcessingOrder(QueueProcessingType.FIFO) // default
//                //.denyCacheImageMultipleSizesInMemory()
////                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
//                        //.memoryCacheSize(2 * 1024 * 1024)
////                .memoryCacheSizePercentage(13) // default
//                        //.discCache(new UnlimitedDiscCache(cacheDir)) // default
//                        //.discCacheSize(50 * 1024 * 1024)
//                        //.discCacheFileCount(100)
//                        //.discCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
//                        //.imageDownloader(new BaseImageDownloader(context)) // default
//                        //.imageDecoder(new BaseImageDecoder()) // default
////                .defaultDisplayImageOptions(options) // default
//                        //.writeDebugLogs()
////                .build();
//
////        com.nostra13.universalimageloader.core.ImageLoader.getInstance().init(config);
////    }
////    @Override
////    protected void attachBaseContext(Context base) {
////        super.attachBaseContext(base);
////        MultiDex.install(this);
////    }
//
////    @Override
////    public void buildDaggerModules(List<Object> modules) {
////        modules.add(new ChefModule());
////    }
//
////    @Override
////    public void onObjectGraphCreated(ObjectGraph objectGraph) {
////        super.onObjectGraphCreated(objectGraph);
//////        BitmapCacheHolder bitmapCacheHolder = objectGraph.get(BitmapCacheHolder.class);
//////        bitmapCacheHolder.init(this, 50000000);
////    }
//    public ComplexPreferences getComplexPreference() {
//        if(complexPrefenreces != null) {
//            return complexPrefenreces;
//        }
//        return null;
//    }
//}
