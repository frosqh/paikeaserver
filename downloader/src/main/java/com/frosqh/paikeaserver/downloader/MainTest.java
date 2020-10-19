package com.frosqh.paikeaserver.downloader;

import com.github.kiulian.downloader.OnYoutubeDownloadListener;
import com.github.kiulian.downloader.YoutubeDownloader;
import com.github.kiulian.downloader.YoutubeException;
import com.github.kiulian.downloader.model.YoutubeVideo;
import com.github.kiulian.downloader.model.formats.AudioVideoFormat;
import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.ToolFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;

public class MainTest {

    public static void main(String[] args) throws YoutubeException, IOException, InterruptedException, ExecutionException, TimeoutException {
        YoutubeDownloader youtubeDownloader = new YoutubeDownloader();
        String id = "dQw4w9WgXcQ";
        YoutubeVideo video = youtubeDownloader.getVideo(id);
        List<AudioVideoFormat> videoWithAudioFormats = video.videoWithAudioFormats();
        /*videoWithAudioFormats.forEach(it -> {
            System.out.println(it.audioQuality() + " : " + it.url());
        });*/

        File outputDir = new File("vids/");
        Future<File> future = video.downloadAsync(videoWithAudioFormats.get(0), outputDir, new OnYoutubeDownloadListener() {
            @Override
            public void onDownloading(int progress) {
                System.out.printf("Downloaded %d%%\n", progress);
            }

            @Override
            public void onFinished(File file) {
                System.out.println("Finished file: " + file);
                IMediaReader reader = ToolFactory.makeReader(file.getAbsolutePath());
                reader.addListener(ToolFactory.makeWriter("C:\\Users\\Frosq\\Desktop\\music.mp3", reader));
                while (reader.readPacket() == null){};
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("Error: " + throwable.getLocalizedMessage());
            }
        });
        new Thread(() -> {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
