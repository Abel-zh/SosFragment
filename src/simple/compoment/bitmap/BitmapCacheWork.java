/*
 * Copyright (C) 2013  ethanchiu@126.com
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
package simple.compoment.bitmap;

import simple.compoment.cache.FileCache;
import simple.compoment.cache.FileCache.CacheParams;
import simple.compoment.cache.FileCacheWork;
import android.content.Context;
import android.content.res.Resources;
import android.widget.ImageView;

public class BitmapCacheWork extends FileCacheWork<ImageView>
{

	protected Resources mResources;
	private CacheParams mCacheParams;
	private static final String TAG = "TABitmapCacheWork";
	private Context mContext;

	public BitmapCacheWork(Context context)
	{
		mResources = context.getResources();
		this.mContext = context;
	}

	@Override
	public void loadFormCache(Object data, ImageView responseObject)
	{
		if (getCallBackHandler() == null)
		{
			BitmapCallBackHanlder callBackHanlder = new BitmapCallBackHanlder();
			setCallBackHandler(callBackHanlder);
		}
		if (getProcessDataHandler() == null)
		{
			DownloadBitmapHandler downloadBitmapFetcher = new DownloadBitmapHandler(
					mContext, 100);
			setProcessDataHandler(downloadBitmapFetcher);
		}
		super.loadFormCache(data, responseObject);
	}

	/**
	 * 设置图片缓存
	 * 
	 * @param cacheParams
	 *            响应参数
	 */
	public void setBitmapCache(CacheParams cacheParams)
	{
		mCacheParams = cacheParams;
		setFileCache(new FileCache(cacheParams));
	}

	@Override
	protected void initDiskCacheInternal()
	{
		DownloadBitmapHandler downloadBitmapFetcher = (DownloadBitmapHandler) getProcessDataHandler();
		super.initDiskCacheInternal();
		if (downloadBitmapFetcher != null)
		{
			downloadBitmapFetcher.initDiskCacheInternal();
		}
	}

	protected void clearCacheInternal()
	{
		super.clearCacheInternal();
		DownloadBitmapHandler downloadBitmapFetcher = (DownloadBitmapHandler) getProcessDataHandler();
		if (downloadBitmapFetcher != null)
		{
			downloadBitmapFetcher.clearCacheInternal();
		}
	}

	protected void flushCacheInternal()
	{
		super.flushCacheInternal();
		DownloadBitmapHandler downloadBitmapFetcher = (DownloadBitmapHandler) getProcessDataHandler();
		if (downloadBitmapFetcher != null)
		{
			downloadBitmapFetcher.flushCacheInternal();
		}
	}

	protected void closeCacheInternal()
	{
		super.closeCacheInternal();
		DownloadBitmapHandler downloadBitmapFetcher = (DownloadBitmapHandler) getProcessDataHandler();
		if (downloadBitmapFetcher != null)
		{
			downloadBitmapFetcher.closeCacheInternal();
		}
	}
}
