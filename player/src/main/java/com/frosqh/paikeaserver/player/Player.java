package com.frosqh.paikeaserver.player;

import com.frosqh.daolibrary.DAO;
import com.frosqh.paikeaserver.database.Song;
import com.frosqh.paikeaserver.database.SongByPlayList;
import com.frosqh.paikeaserver.locale.Locale;
import com.frosqh.paikeaserver.player.exceptions.EmptyHistoryException;
import com.frosqh.paikeaserver.player.exceptions.PauseException;
import com.frosqh.paikeaserver.player.exceptions.PlayException;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class Player {

    private MediaPlayer mediaPlayer;
    private final Stack<Song> history;
    private final Deque<Song> queue;
    private double volume = 0.02;
    private boolean autoPlay = true;
    private boolean isPlaying = false;
    private PlayMode mode = PlayMode.NORMAL;
    private final Locale locale;
    private MethodCall methodCall;
    private final String sep;
    private boolean repeat = false;


    @FunctionalInterface
    public interface MethodCall{
        void call();
    }

    public Player(Locale locale, String sep){
        history = new Stack<>();
        queue = new ArrayDeque<>();
        this.locale = locale;
        this.sep = sep;
    }

    private double getVolume(){
        return volume;
    }

    public void setVolume(double vol){
        volume = vol/100;
        mediaPlayer.setVolume(volume);
    }

    public PlayMode getMode() {
        return mode;
    }

    public void setPlayMode(PlayMode mode){
        this.mode = mode;
        updateGroup();
    }

    public Song getPlaying(){
        if (!queue.isEmpty())
            return queue.peek();
        return null;
    }

    public void add(Song song){
        queue.add(song);
    }

    private double getDuration(){
        return mediaPlayer.getTotalDuration().toMillis();
    }

    private double getTimeCode(){
        return mediaPlayer.getCurrentTime().toMillis();
    }

    private void initPlayer(){
        mediaPlayer.setVolume(volume);
        if (autoPlay)
            mediaPlayer.setOnEndOfMedia(this::next);
        else
            mediaPlayer.setOnEndOfMedia(null);
    }

    public void next(){
        if (mediaPlayer != null)
            mediaPlayer.stop();
        isPlaying = false;
        if (!queue.isEmpty())
            history.add(queue.poll());
        if (queue.isEmpty()){
            DAO<Song> songDAO  = DAO.construct(Song.class);
            List<Song> songs  = songDAO.getList();
            int rand = (int) (Math.random()*(songs.size()));
            Song nextSong = songs.get(rand);
            queue.add(nextSong);
        }
        Song nextSong = queue.peek();
        assert nextSong != null;
        playSong(nextSong);
    }

    public void prev() throws EmptyHistoryException {
        if (history.isEmpty())
            throw new EmptyHistoryException();
        if (mediaPlayer != null)
            mediaPlayer.stop();
        isPlaying = false;
        queue.addFirst(history.pop());
        Song nextSong = queue.peek();
        assert nextSong != null;
        playSong(nextSong);
    }

    private void playSong(Song song){
        File file = new File(song.getLocalurl());
        Media media = new Media(file.toURI().toString());
        if (mediaPlayer != null)
            mediaPlayer.dispose();
        mediaPlayer = new MediaPlayer(media);
        initPlayer();
        mediaPlayer.play();
        isPlaying = true;
        methodCall.call();
    }

    public void play() throws PlayException {
        if (mediaPlayer == null || isPlaying)
            throw new PlayException();
        mediaPlayer.play();
        isPlaying = true;
    }

    public void pause() throws PauseException {
        if (mediaPlayer==null || !isPlaying)
            throw new PauseException();
        mediaPlayer.pause();
        isPlaying = false;
    }

    public void seekback() throws PlayException {
        if (mediaPlayer == null || !isPlaying){
            throw new PlayException();
        }
        mediaPlayer.seek(mediaPlayer.getCurrentTime().subtract(Duration.seconds(5)));
    }

    public void seekfor() throws PlayException {
        if (mediaPlayer == null || !isPlaying){
            throw new PlayException();
        }
        mediaPlayer.seek(mediaPlayer.getCurrentTime().add(Duration.seconds(5)));
    }

    public void seekto(float seekTime) throws PlayException {
        if (mediaPlayer == null || !isPlaying){
            throw new PlayException();
        }
        mediaPlayer.seek(mediaPlayer.getTotalDuration().multiply(seekTime));
    }

    private void updateOnEndOfMedia(){
        if (repeat){
            mediaPlayer.setOnEndOfMedia(() -> {
                try {
                    seekto(0);
                } catch (PlayException e) {
                    e.printStackTrace();
                }
            });
        } else {
            if (autoPlay)
                mediaPlayer.setOnEndOfMedia(this::next);
            else
                mediaPlayer.setOnEndOfMedia(null);
        }
    }

    //TODO Handle this !
    public void toggleAutoPlay(){
        autoPlay = !autoPlay;
        updateOnEndOfMedia();

    }

    public void toggleRepeat() {
        repeat = !repeat;
        updateOnEndOfMedia();
    }

    public boolean isAutoPlay(){
        return autoPlay;
    }

    public String getInfos(){
        if (!isPlaying)
            return locale.nothingPlaying();
        Song songPl = getPlaying();
        int timeCode = (int) getTimeCode()/1000;
        int vol = (int) (getVolume()*100);
        int duration = (int) getDuration()/1000;
        String current = "";
        int mC = timeCode/60;
        int sC = timeCode%60;
        if (mC > 0){
            current = mC +"m";
        }
        current += sC + "s";
        String total = "";
        int mD = duration/60;
        int sD = duration%60;
        if (mD > 0){
            total = mD + "m";
        }
        total += sD + "s";
        return locale.nowPlaying(songPl.getTitle(),songPl.getArtist())+"  ("+current+"/"+total+")"+"\n\t\tVolume : "+vol;
    }

    public String getInfosSendable(){

        return "info"+sep+isPlaying+sep+getPlaying()+sep+getTimeCode()+sep+getVolume()+sep+getDuration()+sep+isAutoPlay()+sep+isRepeat();
    }

    public String getPrevNext() {
        String s = " ";
        if (queue.size()>1) {
            Song firstQueue = queue.pop();
            Song secondQueue = queue.peek();
            assert secondQueue != null;
            s = secondQueue.title;
            queue.push(firstQueue);
        }
        return (history.empty()?"":history.peek().title)+sep+s;
    }

    public void playPlaylist(int playListid) {
        next();
        queue.removeIf(x -> true);
        DAO<SongByPlayList> songByPlayListDAO = DAO.construct(SongByPlayList.class);
        DAO<Song> songDAO = DAO.construct(Song.class);
        List<SongByPlayList> songsID = songByPlayListDAO.filter("playList_id", String.valueOf(playListid));
        queue.add(new Song(0));
        queue.addAll(songsID.stream().map(t -> songDAO.find(t.song_id)).collect(Collectors.toList()));
        next();
        history.pop();
    }

    public void setMethod(MethodCall toCall) {
        this.methodCall = toCall;
    }

    public void updateGroup() {
        methodCall.call();
    }


    public boolean isPlaying() {
        return isPlaying;
    }

    public boolean isRepeat() {
        return repeat;
    }
}
