package com.frosqh.paikeaserver.downloader;

import com.frosqh.daolibrary.ConnectionNotInitException;
import com.frosqh.paikeaserver.database.PaikeaDataBase;
import com.frosqh.paikeaserver.settings.Settings;
import com.github.kiulian.downloader.OnYoutubeDownloadListener;
import com.github.kiulian.downloader.YoutubeDownloader;
import com.github.kiulian.downloader.YoutubeException;
import com.github.kiulian.downloader.model.YoutubeVideo;
import com.github.kiulian.downloader.model.formats.AudioVideoFormat;
import com.mpatric.mp3agic.*;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Downloader extends YoutubeDownloader {

    private final Map<String, Integer> percentageDownloads = new HashMap<>();
    private static Downloader instance;

    private Downloader(){
        super();
    }

    public static Downloader getInstance(){
        if (instance == null)
            instance = new Downloader();
        return instance;
    }

    public void downloadFromYoutube(String id, String title, String artist){
        System.out.println(id + "-" + title + "-" + artist);
        try {
            YoutubeVideo video = this.getVideo(id);
            System.out.println(video.audioFormats());
            List<AudioVideoFormat> videoWithAudioFormats = video.videoWithAudioFormats();
            File outputDir = new File(Settings.get("dirDL"));
            Future<File> future = video.downloadAsync(videoWithAudioFormats.get(0), outputDir, new MyYoutubeDLListener(title, id, artist));
            new Thread(() -> {
                try {
                    File file = future.get();
                    file.delete();
                    new PaikeaDataBase("BotPaikea.db").refreshSongs();
                } catch (InterruptedException | ExecutionException | ConnectionNotInitException |
                        UnsupportedTagException | IOException | InvalidDataException e) {
                    e.printStackTrace();
                }
            }).start();
        } catch (YoutubeException | IOException e) {
            e.printStackTrace();
        }
    }

    private class MyYoutubeDLListener implements OnYoutubeDownloadListener{

        String title;
        String id;
        String artist;

        public MyYoutubeDLListener(String title, String id, String artist){
            this.title = title;
            this.id = id;
            this.artist = artist;
            percentageDownloads.put(title, 0);
        }

        @Override
        public void onDownloading(int progress) {
            percentageDownloads.replace(title, progress);
            // System.out.println("Downloading "+title+" : "+progress+"%");
        }

        @Override
        public void onFinished(File file) {
            String osName = System.getProperty("os.name");
            String ffmpeg;
            String mp3Name = file.getAbsolutePath().substring(0,file.getAbsolutePath().lastIndexOf("."))+".mp3";
            if (osName.toLowerCase().contains("win"))
                ffmpeg = "ffmpeg.exe path"; //TODO -> Get that one ?
            else
                ffmpeg = "/usr/bin/ffmpeg";
            try {
                // System.out.println(ffmpeg);
                // System.out.println(file.getAbsolutePath());
                // System.out.println(mp3Name);
                FFmpeg ffmpeg1 = new FFmpeg(ffmpeg);
                FFmpegBuilder fFmpegBuilder = new FFmpegBuilder()
                        .setInput(/*"\""+*/file.getAbsolutePath()/*+"\""*/)
                        .overrideOutputFiles(true)
                        .addOutput(/*"\""+*/mp3Name/*+"\""*/)
                        .setFormat("mp3")
                        .disableSubtitle()
                        .disableVideo()
                        .done();
                FFmpegExecutor executor = new FFmpegExecutor(ffmpeg1, new FFprobe());
                executor.createJob(fFmpegBuilder).run();
                Mp3File mp3File = new Mp3File(mp3Name);
                ID3v2 id3V2 = new ID3v24Tag();
                mp3File.setId3v2Tag(id3V2);
                id3V2.setTitle(title);
                id3V2.setArtist(artist);
                id3V2.setAudiofileUrl("https://youtube.com/watch?v="+id);
                mp3File.save(Settings.get("dirDL")+"/"+id3V2.getTitle()+".mp3");
                File file1 = new File(mp3Name);
                if (file1.exists())
                    file1.delete();

            } catch (IOException | InvalidDataException | UnsupportedTagException | NotSupportedException e) {
                System.out.println("WHOOPS");
                e.printStackTrace();
            }
        }

        @Override
        public void onError(Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
