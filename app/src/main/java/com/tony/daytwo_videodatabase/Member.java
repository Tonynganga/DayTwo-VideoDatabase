package com.tony.daytwo_videodatabase;

public class Member {
    String VideoName, VideoDesc, VideoUrl;

    public Member() {
    }

    public Member(String videoName, String videoDesc, String videoUrl) {
        if (videoName.trim().equals("")){
            videoName = "Not Available";
        }
        VideoName = videoName;
        VideoDesc = videoDesc;
        VideoUrl = videoUrl;
    }

    public String getVideoName() {
        return VideoName;
    }

    public void setVideoName(String videoName) {
        VideoName = videoName;
    }

    public String getVideoDesc() {
        return VideoDesc;
    }

    public void setVideoDesc(String videoDesc) {
        VideoDesc = videoDesc;
    }

    public String getVideoUrl() {
        return VideoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        VideoUrl = videoUrl;
    }
}
