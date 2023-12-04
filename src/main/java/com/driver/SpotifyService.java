package com.driver;
import java.util.*;
import org.springframework.stereotype.Service;
@Service
public class SpotifyService {
    //Auto-wire will not work in this case, no need to change this and add autowire
    SpotifyRepository spotifyRepository = new SpotifyRepository();

    public User createUser(String name, String mobile){
        return spotifyRepository.createUser(name,mobile);
        return spotifyRepository.createUser(name,mobile);
    }

    public Artist createArtist(String name) {
        @@ -24,19 +24,19 @@ public Album createAlbum(String title, String artistName) {
        }

        public Song createSong(String title, String albumName, int length) throws Exception {
            return  spotifyRepository.createSong(title,albumName,length);
            return spotifyRepository.createSong(title, albumName, length);
        }

        public Playlist createPlaylistOnLength(String mobile, String title, int length) throws Exception {
            return spotifyRepository.createPlaylistOnLength(mobile,title, length);
            return spotifyRepository.createPlaylistOnLength(mobile, title, length);
        }

        public Playlist createPlaylistOnName(String mobile, String title, List<String> songTitles) throws Exception {
            return spotifyRepository.createPlaylistOnName(mobile,title,songTitles);
            return spotifyRepository.createPlaylistOnName(mobile, title, songTitles);
        }

        public Playlist findPlaylist(String mobile, String playlistTitle) throws Exception {
            return spotifyRepository.findPlaylist(mobile,playlistTitle);
            return spotifyRepository.findPlaylist(mobile, playlistTitle);
        }

        public Song likeSong(String mobile, String songTitle) throws Exception {
            @@ -50,12 +50,4 @@ public String mostPopularArtist() {
                public String mostPopularSong() {
                    return spotifyRepository.mostPopularSong();
                }

                public User findUser(String mobile) {
                    return spotifyRepository.findUser(mobile);
                }

                public Song findSong(String songTitle) {
                    return spotifyRepository.findSong(songTitle);
                }
            }
        }