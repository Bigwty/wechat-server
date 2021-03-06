package com.bc.wechat.server.entity;

import com.bc.wechat.server.utils.CommonUtil;

import java.util.List;

/**
 * 朋友圈
 *
 * @author zhou
 */
public class FriendsCircle {

    private String circleId;
    private String userId;
    private String userNickName;
    private String userAvatar;
    private String circleContent;
    private String circlePhotos;
    private String createTime;
    private Long timestamp;

    /**
     * 点赞用户列表
     */
    private List<User> likeUserList;

    /**
     * 评论列表
     */
    private List<FriendsCircleComment> friendsCircleCommentList;

    public FriendsCircle() {

    }

    public FriendsCircle(String userId, String circleContent, String circlePhotos) {
        this.circleId = CommonUtil.generateId();
        this.userId = userId;
        this.circleContent = circleContent;
        this.circlePhotos = circlePhotos;
        this.createTime = CommonUtil.now();
        this.timestamp = System.currentTimeMillis();

    }

    public String getCircleId() {
        return circleId;
    }

    public void setCircleId(String circleId) {
        this.circleId = circleId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getCircleContent() {
        return circleContent;
    }

    public void setCircleContent(String circleContent) {
        this.circleContent = circleContent;
    }

    public String getCirclePhotos() {
        return circlePhotos;
    }

    public void setCirclePhotos(String circlePhotos) {
        this.circlePhotos = circlePhotos;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public List<User> getLikeUserList() {
        return likeUserList;
    }

    public void setLikeUserList(List<User> likeUserList) {
        this.likeUserList = likeUserList;
    }

    public List<FriendsCircleComment> getFriendsCircleCommentList() {
        return friendsCircleCommentList;
    }

    public void setFriendsCircleCommentList(List<FriendsCircleComment> friendsCircleCommentList) {
        this.friendsCircleCommentList = friendsCircleCommentList;
    }
}
