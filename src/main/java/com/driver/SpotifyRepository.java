package com.driver;
import java.util.*;
import org.springframework.stereotype.Repository;
@Repository
public class SpotifyRepository {
    public HashMap<Artist, List<Album>> artistAlbumMap;
    public HashMap<Album, List<Song>> albumSongMap;
    public HashMap<Playlist, List<Song>> playlistSongMap;
    public HashMap<Playlist, List<User>> playlistListenerMap;
    public HashMap<User, Playlist> creatorPlaylistMap;
    public HashMap<User, List<Playlist>> userPlaylistMap;
    public HashMap<Song, List<User>> songLikeMap;

    public List<User> users;
    public List<Song> songs;
    public List<Playlist> playlists;
    @@ -22,25 +23,12 @@ public class SpotifyRepository {
        public SpotifyRepository(){
            //To avoid hitting apis multiple times, initialize all the hashmaps here with some dummy data
            artistAlbumMap = new HashMap<>();
            artistAlbumMap.put(new Artist(),new ArrayList<>());

            albumSongMap = new HashMap<>();
            albumSongMap.put(new Album(), new ArrayList<>());

            playlistSongMap = new HashMap<>();
            playlistSongMap.put(new Playlist(),new ArrayList<>());

            playlistListenerMap = new HashMap<>();
            playlistListenerMap.put(new Playlist(), new ArrayList<>());

            creatorPlaylistMap = new HashMap<>();
            creatorPlaylistMap.put(new User(), new Playlist());

            userPlaylistMap = new HashMap<>();
            userPlaylistMap.put(new User(), new ArrayList<>());

            songLikeMap = new HashMap<>();
            songLikeMap.put(new Song(), new ArrayList<>());

            users = new ArrayList<>();
            songs = new ArrayList<>();
            @@ -50,233 +38,226 @@ public SpotifyRepository(){
            }

            public User createUser(String name, String mobile) {
                User user=new User(name,mobile);
                users.add(user);

                User newUser =  new User(name, mobile);
                users.add(newUser);
                return newUser;

                userPlaylistMap.put(user,new ArrayList<>());
                return user;
            }

            public Artist createArtist(String name) {
                Artist artist=new Artist(name);
                artists.add(artist);
                artistAlbumMap.put(artist,new ArrayList<>());

                Artist newArtist = new Artist(name);
                artists.add(newArtist);
                return newArtist;
                return artist;
            }

            public Album createAlbum(String title, String artistName) {
                //check artist present or not
                boolean present = false;
                for(Artist a:artists){
                    if(a.getName().equals(artistName)){
                        present = true;
                        Artist artist=null;
                        for(Artist artist1:artists){
                            if(artist1.getName().equals(artistName)){
                                artist=artist1;
                                break;
                            }
                        }
                        if(!present){
                            Artist newArtist = new Artist(artistName);
                            artists.add(newArtist);
                        }
                        if(artist == null)
                            artist=createArtist(artistName);

                        Album album=new Album(title);
                        albums.add(album);

                        artistAlbumMap.get(artist).add(album);
                        albumSongMap.put(album,new ArrayList<>());

                        return album;

                        Album newAlbum  =  new Album(title,artistName);
                        albums.add(newAlbum);
                        return newAlbum;
                    }

                    public Song createSong(String title, String albumName, int length) throws Exception{
                        boolean present = false;
                        for(Album a:albums){
                            if(a.getTitle().equals(albumName)){
                                present = true;
                                Album album=null;
                                for(Album album1:albums){
                                    if(album1.getTitle().equals(albumName)){
                                        album=album1;
                                        break;
                                    }
                                }
                                if(!present){
                                    return null;
                                    if(album == null){
                                        throw new Exception("Album does not exist");
                                    }
                                    Song song=new Song(title,length);
                                    song.setLikes(0);
                                    songs.add(song);

                                    Song newSong = new Song(title,albumName,length);
                                    songs.add(newSong);
                                    albumSongMap.get(album).add(song);

                                    // add song to the album map
                                    Album album = findAlbum(albumName);
                                    List<Song> albumSongs = albumSongMap.getOrDefault(album,new ArrayList<>());
                                    albumSongs.add(newSong);
                                    albumSongMap.put(album,albumSongs);
                                    songLikeMap.put(song,new ArrayList<>());

                                    return song;

                                    return newSong;
                                }

                                public User findUser(String mobile){
                                    // finds user from users list
                                    for(User u:users){
                                        if (u.getMobile().equals(mobile)){
                                            return u;
                                            public Playlist createPlaylistOnLength(String mobile, String title, int length) throws Exception {
                                                User user=null;
                                                for(User user1:users){
                                                    if(user1.getMobile().equals(mobile)){
                                                        user=user1;
                                                        break;
                                                    }
                                                }
                                                if(user == null)
                                                    throw new Exception("User does not exist");

                                                return null;
                                            }

                                            public void addListener(User user, Playlist pl){
                                                // gets the listeners of playlist and adds the given user as listener of the playlist
                                                List<User> listeners = playlistListenerMap.getOrDefault(pl, new ArrayList<>());
                                                listeners.add(user);
                                                playlistListenerMap.put(pl,listeners);
                                            }

                                            public Playlist createPlaylistOnLength(String mobile, String title, int length) throws Exception {


                                                //find user add it as listener
                                                User u = findUser(mobile);
                                                if(u==null) return null;
                                                Playlist playlist=new Playlist(title);
                                                playlists.add(playlist);

                                                // make list of all the songs of given length from songs list
                                                List<Song> SongsOfEqualLength = new ArrayList<>();
                                                playlistSongMap.put(playlist,new ArrayList<>());
                                                playlistListenerMap.put(playlist,new ArrayList<>());
                                                //  userPlaylistMap.put(user,new ArrayList<>());

                                                for (Song s:songs){
                                                    if(s.getLength()==length){
                                                        SongsOfEqualLength.add(s);
                                                    }
                                                    for(Song song:songs){
                                                        if(song.getLength() == length)
                                                            playlistSongMap.get(playlist).add(song);
                                                    }
                                                    // create new playlist
                                                    Playlist newPlayList = new Playlist(mobile,title,SongsOfEqualLength);

                                                    playlistListenerMap.get(playlist).add(user);   //current listener of the playlist
                                                    creatorPlaylistMap.put(user,playlist);         //creator of the playlist
                                                    userPlaylistMap.get(user).add(playlist);   //user and his list of playlist

                                                    // if user exists
                                                    addListener(u,newPlayList);
                                                    playlists.add(newPlayList);

                                                    return newPlayList;
                                                    return playlist;

                                                }

                                                public Playlist createPlaylistOnName(String mobile, String title, List<String> songTitles) throws Exception {
                                                    User user=null;
                                                    for(User user1:users){
                                                        if(user1.getMobile().equals(mobile)){
                                                            user=user1;
                                                            break;
                                                        }
                                                    }
                                                    if(user == null)
                                                        throw new Exception("User does not exist");

                                                    //find user add it as listener
                                                    User u = findUser(mobile);
                                                    if(u==null) return null;

                                                    Playlist playlist=new Playlist(title);
                                                    playlists.add(playlist);

                                                    // make list of all the songs of given names from songs list
                                                    List<Song> SongsOfGivenNames = new ArrayList<>();
                                                    playlistSongMap.put(playlist,new ArrayList<>());
                                                    playlistListenerMap.put(playlist,new ArrayList<>());
                                                    // userPlaylistMap.put(user,new ArrayList<>());

                                                    for(String songName:songTitles){
                                                        Song s = findSong(songName);
                                                        SongsOfGivenNames.add(s);
                                                        for(Song song:songs){
                                                            if(songTitles.contains(song.getTitle()))
                                                                playlistSongMap.get(playlist).add(song);
                                                        }

                                                        // create new playlist
                                                        Playlist newPlayList = new Playlist(mobile,title,SongsOfGivenNames);

                                                        // if user exists
                                                        addListener(u,newPlayList);
                                                        playlists.add(newPlayList);
                                                        playlistListenerMap.get(playlist).add(user);   //current listener of the playlist
                                                        creatorPlaylistMap.put(user,playlist);         //creator of the playlist
                                                        userPlaylistMap.get(user).add(playlist);   //user and his list of playlist

                                                        return newPlayList;

                                                        return playlist;
                                                    }

                                                    public Playlist findPlaylist(String mobile, String playlistTitle) throws Exception {
                                                        User user=null;
                                                        for(User user1:users){
                                                            if(user1.getMobile().equals(mobile)){
                                                                user=user1;
                                                                break;
                                                            }
                                                        }
                                                        if(user == null)
                                                            throw new Exception("User does not exist");

                                                        //find the user
                                                        User u = findUser(mobile);
                                                        if(u==null) return null;

                                                        // find the playlist
                                                        Playlist playlist = null;
                                                        for (Playlist pl:playlists){
                                                            if(pl.getPlaylistTitle().equals(playlistTitle)){
                                                                playlist = pl;
                                                                Playlist playlist=null;
                                                                for(Playlist playlist1:playlists){
                                                                    if(playlist1.getTitle().equals(playlistTitle)){
                                                                        playlist=playlist1;
                                                                        break;
                                                                    }
                                                                }
                                                                if(playlist == null)
                                                                    throw new Exception("Playlist does not exist");

                                                                if(playlist==null) return null;
                                                                if(creatorPlaylistMap.containsKey(user) && creatorPlaylistMap.get(user) == playlist ||
                                                                        playlistListenerMap.get(playlist).contains(user)){

                                                                    // add user in the listening map
                                                                    addListener(u,playlist);
                                                                    return playlist;
                                                                }
                                                                playlistListenerMap.get(playlist).add(user);

                                                                return playlist;
                                                                if(!userPlaylistMap.get(user).contains(playlist)){
                                                                    userPlaylistMap.get(user).add(playlist);
                                                                }

                                                            }

                                                            public Song findSong(String songtitle){
                                                                return playlist;
                                                            }

                                                            for(Song s:songs){
                                                                if(s.getTitle().equals(songtitle)){
                                                                    return s;
                                                                    public Song likeSong(String mobile, String songTitle) throws Exception {
                                                                        User user=null;
                                                                        for(User user1:users){
                                                                            if(user1.getMobile().equals(mobile)){
                                                                                user=user1;
                                                                                break;
                                                                            }
                                                                        }
                                                                        return null;
                                                                    }
                                                                    if(user == null)
                                                                        throw new Exception("User does not exist");

                                                                    public Album findAlbum(String albumName){
                                                                        for(Album al:albums){
                                                                            if (al.getTitle().equals(albumName)){
                                                                                return al;
                                                                                Song song=null;
                                                                                for(Song song1:songs){
                                                                                    if(song1.getTitle().equals(songTitle)){
                                                                                        song=song1;
                                                                                        break;
                                                                                    }
                                                                                }
                                                                                if(song == null)
                                                                                    throw new Exception("Song does not exist");

                                                                                return null;
                                                                            }
                                                                            public  Artist findArtist(String artistName){
                                                                                for(Artist a:artists){
                                                                                    if (a.getName().equals(artistName)){
                                                                                        return a;
                                                                                        if(songLikeMap.get(song).contains(user)){
                                                                                            return song;
                                                                                        }
                                                                                        song.setLikes(song.getLikes()+1);
                                                                                        songLikeMap.get(song).add(user);

                                                                                        for(Album album:albumSongMap.keySet()){
                                                                                            if(albumSongMap.get(album).contains(song)){
                                                                                                for(Artist artist:artistAlbumMap.keySet()){
                                                                                                    if(artistAlbumMap.get(artist).contains(album)){
                                                                                                        artist.setLikes(artist.getLikes()+1);
                                                                                                        break;
                                                                                                    }
                                                                                                }
                                                                                                break;
                                                                                            }
                                                                                        }

                                                                                        return null;
                                                                                    }
                                                                                    public Song likeSong(String mobile, String songTitle) throws Exception {

                                                                                        User u = findUser(mobile);
                                                                                        Song s = findSong(songTitle);
                                                                                        List<User> usersWhoLikeThisSong = songLikeMap.getOrDefault(s,new ArrayList<>());

                                                                                        // if the user does not already like the song only then add user in the list
                                                                                        if(!usersWhoLikeThisSong.contains(u))
                                                                                            usersWhoLikeThisSong.add(u);

                                                                                        songLikeMap.put(s,usersWhoLikeThisSong);

                                                                                        // auto like artist of the song
                                                                                        //  -> find album -> find artist of this song -> increment the like count

                                                                                        Album al = findAlbum(s.getAlbumName());
                                                                                        String artistName = al.getArtistName();

                                                                                        Artist artist = findArtist(artistName);
                                                                                        artist.setLikes(artist.getLikes()+1);

                                                                                        return s;
                                                                                        return song;
                                                                                    }

                                                                                    public String mostPopularArtist() {

                                                                                        int maxLikes = -1;
                                                                                        String popularArtist = "";
                                                                                        for(Artist a:artists){
                                                                                            if(maxLikes<a.getLikes()){
                                                                                                popularArtist = a.getName();
                                                                                                maxLikes = a.getLikes();
                                                                                                int countLikes=Integer.MIN_VALUE;
                                                                                                String popularArtist="";
                                                                                                for(Artist artist:artists){
                                                                                                    if(artist.getLikes() > countLikes){
                                                                                                        popularArtist=artist.getName();
                                                                                                        countLikes=artist.getLikes();
                                                                                                    }
                                                                                                }

                                                                                                return popularArtist;
                                                                                            }

                                                                                            public String mostPopularSong() {
                                                                                                int maxLikes = -1;
                                                                                                String popularSong = "";
                                                                                                for(Song s:songs){
                                                                                                    if(maxLikes<s.getLikes()){
                                                                                                        popularSong = s.getTitle();
                                                                                                        maxLikes = s.getLikes();
                                                                                                        int countLikes=Integer.MIN_VALUE;
                                                                                                        String popularSong="";
                                                                                                        for(Song song:songs){
                                                                                                            if(song.getLikes() > countLikes){
                                                                                                                popularSong=song.getTitle();
                                                                                                                countLikes=song.getLikes();
                                                                                                            }
                                                                                                        }
                                                                                                        return popularSong;
                                                                                                    }
                                                                                                }
                                                                                            }