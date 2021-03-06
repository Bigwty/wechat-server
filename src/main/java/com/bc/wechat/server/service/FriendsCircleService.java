package com.bc.wechat.server.service;

import com.bc.wechat.server.entity.FriendsCircle;
import com.bc.wechat.server.entity.FriendsCircleComment;
import com.bc.wechat.server.entity.User;

import java.util.List;
import java.util.Map;

/**
 * 朋友圈业务类接口
 *
 * @author zhou
 */
public interface FriendsCircleService {

    /**
     * 新增朋友圈实体
     *
     * @param friendsCircle 朋友圈实体
     */
    void addFriendsCircle(FriendsCircle friendsCircle);

    /**
     * 查找某个用户的朋友圈列表
     *
     * @param paramMap 参数map
     * @return 朋友圈列表
     */
    List<FriendsCircle> getFriendsCircleListByUserId(Map<String, Object> paramMap);

    /**
     * 获取点赞用户列表
     *
     * @param circleId 朋友圈ID
     * @return 点赞用户列表
     */
    List<User> getLikeUserListByCircleId(String circleId);

    /**
     * 获取最近n张朋友圈图片
     *
     * @param userId 用户ID
     * @return 最近n张朋友圈图片
     */
    List<String> getLastestCirclePhotosByUserId(String userId);

    /**
     * 点赞
     *
     * @param paramMap 参数map
     */
    void likeFriendsCircle(Map<String, Object> paramMap);

    /**
     * 取消点赞
     *
     * @param paramMap 参数map
     */
    void unLikeFriendsCircle(Map<String, Object> paramMap);

    /**
     * 获取某个朋友圈下的评论
     *
     * @param circleId 朋友圈ID
     * @return 评论列表
     */
    List<FriendsCircleComment> getFriendsCircleCommentListByCircleId(String circleId);

    /**
     * 朋友圈发表评论
     *
     * @param friendsCircleComment 朋友圈评论
     */
    void addFriendsCircleComment(FriendsCircleComment friendsCircleComment);

    /**
     * 查找某个用户的朋友圈列表
     *
     * @param paramMap 参数map
     * @return 某个用户的朋友圈列表
     */
    List<FriendsCircle> getFriendsCircleListByPublishUserId(Map<String, Object> paramMap);
}
