package common.base;

import java.lang.Thread.UncaughtExceptionHandler;

import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import simple.compoment.cache.FileCache;
import simple.compoment.cache.FileCache.CacheParams;
import simple.compoment.db.SQLiteDatabasePool;
import simple.compoment.http.BaseAsyncHttpClient;
import simple.compoment.netstate.NetChangeObserver;
import simple.compoment.netstate.NetworkStateReceiver;
import simple.compoment.resoperate.IConfig;
import simple.compoment.resoperate.PreferenceConfig;
import simple.compoment.resoperate.PropertiesConfig;
import simple.framework.mvc.BaseApp;

import common.compoment.universalimageloader.cache.memory.impl.LruMemoryCache;
import common.compoment.universalimageloader.core.DisplayImageOptions;
import common.compoment.universalimageloader.core.ImageLoader;
import common.compoment.universalimageloader.core.ImageLoaderConfiguration;
import common.compoment.universalimageloader.core.assist.QueueProcessingType;
import common.compoment.universalimageloader.core.decode.BaseImageDecoder;
import common.compoment.universalimageloader.core.download.BaseImageDownloader;
import common.exception.AppException;
import common.globe.ReleaseConfig;
import common.util.check.NetWorkUtil.NetType;

/**
 * @Description: App
 * @author: ethanchiu@126.com
 * @date: Dec 20, 2013
 */
public abstract class AbsBaseApp extends BaseApp {

	/** 配置器 为Preference */
	public final static int PREFERENCE_CONFIG = 0;
	/** 配置器 为properties config */
	public final static int PROPERTIES_CONFIG = 1;
	/** 配置器 */
	private IConfig mCurrentConfig;

	/** 文件缓存 */
	private FileCache mFileCache;
	private static final String SYSTEMCACHE = ReleaseConfig.SYSTEM_CACHE;

	/** 数据库链接池 */
	private SQLiteDatabasePool mSQLiteDatabasePool;

	/** 网络监听 */
	private Boolean networkAvailable = false;
	private NetChangeObserver netChangeObserver;

	/** App异常崩溃处理器 */
	private UncaughtExceptionHandler uncaughtExceptionHandler;

	private BaseAsyncHttpClient mSyncHttpClient;

	@Override
	public void onCreate() {
		super.onCreate();
		// 注册App异常崩溃处理器
		// Thread.setDefaultUncaughtExceptionHandler(getUncaughtExceptionHandler());

		netChangeObserver = new NetChangeObserver() {
			@Override
			public void onConnect(NetType type) {
				super.onConnect(type);
				AbsBaseApp.this.onConnect(type);
			}

			@Override
			public void onDisConnect() {
				super.onDisConnect();
				AbsBaseApp.this.onDisConnect();
			}
		};
		NetworkStateReceiver.registerObserver(netChangeObserver);

		mSyncHttpClient = new BaseAsyncHttpClient();

		// michael start
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true)
				.build();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).memoryCacheExtraOptions(480, 800)
				.diskCacheExtraOptions(480, 800, null).threadPoolSize(3)
				// default
				.threadPriority(Thread.NORM_PRIORITY - 1)
				// default
				.tasksProcessingOrder(QueueProcessingType.FIFO)
				// default
				.denyCacheImageMultipleSizesInMemory().memoryCache(new LruMemoryCache(2 * 1024 * 1024))
				.memoryCacheSize(2 * 1024 * 1024).memoryCacheSizePercentage(13) // default
				.imageDownloader(new BaseImageDownloader(this)) // default
				.imageDecoder(new BaseImageDecoder(true)) // default
				.defaultDisplayImageOptions(defaultOptions) // default
				.writeDebugLogs().build();
		ImageLoader.getInstance().init(config);
		// michael end

	}

	@Override
	public void onDisConnect() {
		super.onDisConnect();
		networkAvailable = false;
	}

	@Override
	protected void onConnect(NetType type) {
		super.onConnect(type);
		networkAvailable = true;
	}

	/**
	 * @Description: 网络是否可用
	 * @author: ethanchiu@126.com
	 * @date: May 5, 2014
	 * @return
	 */
	public Boolean isNetworkAvailable() {
		return networkAvailable;
	}

	// ==========Preference
	public IConfig getPreferenceConfig() {
		return getConfig(PREFERENCE_CONFIG);
	}

	public IConfig getPropertiesConfig() {
		return getConfig(PROPERTIES_CONFIG);
	}

	public IConfig getConfig(int confingType) {
		if (confingType == PREFERENCE_CONFIG) {
			mCurrentConfig = PreferenceConfig.getPreferenceConfig(this);

		} else if (confingType == PROPERTIES_CONFIG) {
			mCurrentConfig = PropertiesConfig.getPropertiesConfig(this);
		} else {
			mCurrentConfig = PropertiesConfig.getPropertiesConfig(this);
		}
		if (!mCurrentConfig.isLoadConfig()) {
			mCurrentConfig.loadConfig();
		}
		return mCurrentConfig;
	}

	public IConfig getCurrentConfig() {
		if (mCurrentConfig == null) {
			getPreferenceConfig();
		}
		return mCurrentConfig;
	}

	// ==========FileCache
	public FileCache getFileCache() {
		if (mFileCache == null) {
			CacheParams cacheParams = new CacheParams(this, SYSTEMCACHE);
			FileCache fileCache = new FileCache(cacheParams);
			this.setFileCache(fileCache);
		}
		return mFileCache;
	}

	public void setFileCache(FileCache fileCache) {
		this.mFileCache = fileCache;
	}

	// ==========datebase
	public SQLiteDatabasePool getSQLiteDatabasePool() {
		if (mSQLiteDatabasePool == null) {
			mSQLiteDatabasePool = SQLiteDatabasePool.getInstance(this);
			mSQLiteDatabasePool.createPool();
		}
		return mSQLiteDatabasePool;
	}

	public void setSQLiteDatabasePool(SQLiteDatabasePool sqliteDatabasePool) {
		this.mSQLiteDatabasePool = sqliteDatabasePool;
	}

	// ==========App异常崩溃处理器
	public void setUncaughtExceptionHandler(UncaughtExceptionHandler uncaughtExceptionHandler) {
		this.uncaughtExceptionHandler = uncaughtExceptionHandler;
	}

	private UncaughtExceptionHandler getUncaughtExceptionHandler() {
		if (uncaughtExceptionHandler == null) {
			uncaughtExceptionHandler = AppException.getInstance(this);
		}
		return uncaughtExceptionHandler;
	}

	private HttpClient httpClient;

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		this.shutdownHttpClient();
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
		this.shutdownHttpClient();
	}

	// 创建HttpClient实例
	private HttpClient createHttpClient() {
		HttpParams params = new BasicHttpParams();
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(params, HTTP.DEFAULT_CONTENT_CHARSET);
		HttpProtocolParams.setUseExpectContinue(params, true);
		HttpConnectionParams.setConnectionTimeout(params, 20 * 1000);
		HttpConnectionParams.setSoTimeout(params, 20 * 1000);
		HttpConnectionParams.setSocketBufferSize(params, 8192);
		SchemeRegistry schReg = new SchemeRegistry();
		schReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
		schReg.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));

		ClientConnectionManager connMgr = new ThreadSafeClientConnManager(params, schReg);

		return new DefaultHttpClient(connMgr, params);
	}

	// 关闭连接管理器并释放资源
	private void shutdownHttpClient() {
		if (httpClient != null && httpClient.getConnectionManager() != null) {
			httpClient.getConnectionManager().shutdown();
		}
	}

	// 对外提供HttpClient实例
	public HttpClient getImageHttpClient() {
		return httpClient;
	}

	public BaseAsyncHttpClient getHttpClient() {
		if (mSyncHttpClient == null) {
			mSyncHttpClient = new BaseAsyncHttpClient();
		}
		return mSyncHttpClient;
	}

}
