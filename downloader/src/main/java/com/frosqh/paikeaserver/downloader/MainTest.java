package com.frosqh.paikeaserver.downloader;

import com.github.kiulian.downloader.OnYoutubeDownloadListener;
import com.github.kiulian.downloader.YoutubeDownloader;
import com.github.kiulian.downloader.YoutubeException;
import com.github.kiulian.downloader.model.YoutubeVideo;
import com.github.kiulian.downloader.model.formats.AudioVideoFormat;
import com.mpatric.mp3agic.*;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.builder.FFmpegBuilder;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;

public class MainTest {

    public static void main(String[] args) throws YoutubeException, IOException, InterruptedException, ExecutionException, TimeoutException {
        YoutubeDownloader youtubeDownloader = new YoutubeDownloader();
        String id = "VpAgslp8xew";
        YoutubeVideo video = youtubeDownloader.getVideo(id);
        List<AudioVideoFormat> videoWithAudioFormats = video.videoWithAudioFormats();
        System.out.println(videoWithAudioFormats);

        File outputDir = new File("vids/");
        Future<File> future = video.downloadAsync(videoWithAudioFormats.get(0), outputDir, new OnYoutubeDownloadListener() {
            @Override
            public void onDownloading(int progress) {
                System.out.printf("Downloaded %d%%\n", progress);
            }

            @Override
            public void onFinished(File file) {
                System.out.println("Finished file: " + file);
                String osName = System.getProperty("os.name");
                String ffmpeg;
                String mp3Name = file.getAbsolutePath().substring(0,file.getAbsolutePath().lastIndexOf("."))+".mp3";
                if (osName.contains("win"))
                    ffmpeg = "ffmpeg.exe";
                else
                    ffmpeg = "ffmpeg";
                try {
                    FFmpeg ffmpeg1 = new FFmpeg(ffmpeg);
                    FFmpegBuilder fFmpegBuilder = new FFmpegBuilder()
                            .setInput(file.getAbsolutePath())
                            .overrideOutputFiles(true)
                            .addOutput(mp3Name)
                                .setFormat("mp3")
                                .disableSubtitle()
                                .disableVideo()
                                .done();
                    FFmpegExecutor executor = new FFmpegExecutor(ffmpeg1, null);
                    executor.createTwoPassJob(fFmpegBuilder).run();
                    Mp3File mp3File = new Mp3File(mp3Name);
                    ID3v2 id3V2 = new ID3v24Tag();
                    mp3File.setId3v2Tag(id3V2);
                    id3V2.setTitle("Never Gonna Give You Up");
                    id3V2.setArtist("Rick Astley");
                    id3V2.setAudiofileUrl("https://youtube.com/"+id);
                    id3V2.setLyrics("Never gonna give you a rick roll <3");
                    mp3File.save("vids/"+id3V2.getTitle()+".mp3");
                    File file1 = new File(mp3Name);
                    if (file1.exists())
                        file1.delete();

                } catch (IOException | InvalidDataException | UnsupportedTagException | NotSupportedException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("Error: " + throwable.getLocalizedMessage());
            }
        });
        new Thread(() -> {
            try {
                File file = future.get();
                file.delete();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
