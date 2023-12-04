package com.driver;
public class Song {
    private String title;
    private int length;
    private int likes;
    private String albumName;
    private String mobile;

    public Song(){

        @@ -16,37 +14,6 @@ public Song(String title, int length){
            this.length = length;
        }

    public Song(String title, String albumName, int length) {
            this.title = title;
            this.albumName= albumName;
            this.length = length;
        }

    public Song(String mobile, String songTitle) {
            this.mobile = mobile;
            this.title = songTitle;
        }



        // getters and setters

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }


        public String getAlbumName() {
            return albumName;
        }

        public void setAlbumName(String albumName) {
            this.albumName = albumName;
        }
        public String getTitle() {
            return title;
        }
        @@ -70,4 +37,4 @@ public int getLikes() {
            public void setLikes(int likes) {
                this.likes = likes;
            }
        }
    }