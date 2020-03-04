package com.smona.btwriter.download;

import com.smona.btwriter.common.exception.AppContext;
import com.smona.btwriter.download.model.DownloadModel;
import com.smona.btwriter.util.CommonUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class PltDownload {

    private DownloadModel model = new DownloadModel();
    private IDownloadView mView;

    public PltDownload(IDownloadView downloadView) {
        this.mView = downloadView;
    }

    public void downloadPlt(String name, String pltUrl, String md5) {
        model.get(pltUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (mView != null) {
                    mView.onFailure();
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;//输入流
                FileOutputStream fos = null;//输出流
                try {
                    is = response.body().byteStream();//获取输入流
                    //long total = response.body().contentLength();//获取文件大小
                    if (mView != null) {
                        mView.onStart();
                    }

                    File file = new File(AppContext.getAppContext().getExternalCacheDir(), name);// 设置路径
                    fos = new FileOutputStream(file);
                    byte[] buf = new byte[1024];
                    int ch = -1;
                    int process = 0;
                    while ((ch = is.read(buf)) != -1) {
                        fos.write(buf, 0, ch);
                        process += ch;
                        if (mView != null) {
                            mView.onProcess(process);
                        }
                    }
                    fos.flush();
                    File realFile = new File(AppContext.getAppContext().getExternalCacheDir(), CommonUtil.CURRENT_USE_PLT);
                    file.renameTo(realFile);
                    String fileMD5 = CommonUtil.getFileMD5(realFile);
                    if (mView != null) {
                        if (md5 != null && md5.equalsIgnoreCase(fileMD5)) {
                            mView.onComplete();
                        } else {
                            mView.onFailure();
                        }
                    }
                } catch (Exception e) {
                    if (mView != null) {
                        mView.onFailure();
                    }
                } finally {
                    try {
                        if (is != null)
                            is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        if (fos != null)
                            fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public interface IDownloadView {
        void onStart();

        void onProcess(int process);

        void onComplete();

        void onFailure();
    }
}
