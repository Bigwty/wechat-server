package com.bc.wechat.server.controller;

import com.alibaba.fastjson.JSON;
import com.bc.wechat.server.cons.Constant;
import com.bc.wechat.server.entity.FriendsCircle;
import com.bc.wechat.server.entity.FriendsCircleComment;
import com.bc.wechat.server.entity.User;
import com.bc.wechat.server.enums.ResponseMsg;
import com.bc.wechat.server.service.FriendsCircleService;
import com.bc.wechat.server.service.UserService;
import com.bc.wechat.server.utils.CommonUtil;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 朋友圈控制器
 *
 * @author zhou
 */
@RestController
@RequestMapping("/friendsCircle")
public class FriendsCircleController {

    private static final Logger logger = LoggerFactory.getLogger(FriendsCircleController.class);

    @Resource
    private FriendsCircleService friendsCircleService;

    @Resource
    private UserService userService;

    /**
     * 发朋友圈
     *
     * @param userId  用户ID
     * @param content 朋友圈内容
     * @param photos  朋友圈图片
     * @return ResponseEntity
     */
    @ApiOperation(value = "发朋友圈", notes = "发朋友圈")
    @PostMapping(value = "")
    public ResponseEntity<String> addFriendsCircle(
            @RequestParam String userId,
            @RequestParam String content,
            @RequestParam String photos) {
        ResponseEntity<String> responseEntity;
        try {
            FriendsCircle friendsCircle = new FriendsCircle(userId, content, photos);
            friendsCircleService.addFriendsCircle(friendsCircle);

            // 更新该用户最新n张朋友圈照片
            List<String> lastestCirclePhotoList = friendsCircleService.getLastestCirclePhotosByUserId(userId);
            Map<String, String> paramMap = new HashMap<>(Constant.DEFAULT_HASH_MAP_CAPACITY);
            paramMap.put("userId", userId);
            paramMap.put("userLastestCirclePhotos", JSON.toJSONString(lastestCirclePhotoList));
            userService.updateUserLastestCirclePhotos(paramMap);

            responseEntity = new ResponseEntity<>(ResponseMsg.ADD_FRIENDS_CIRCLE_SUCCESS.getResponseCode(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new ResponseEntity<>(ResponseMsg.ADD_FRIENDS_CIRCLE_ERROR.getResponseCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }


    /**
     * 查找某个用户的朋友圈列表
     *
     * @param userId    用户ID
     * @param pageSize  每页数量
     * @param timestamp 时间戳
     * @return 朋友圈列表
     */
    @ApiOperation(value = "查找朋友圈列表", notes = "查找朋友圈列表")
    @GetMapping(value = "")
    public ResponseEntity<List<FriendsCircle>> getFriendsCircleListByUserId(
            @RequestParam String userId,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false, defaultValue = "0") Long timestamp) {
        long beginTimeStamp = System.currentTimeMillis();
        ResponseEntity<List<FriendsCircle>> responseEntity;
        try {
            if (0L == timestamp || null == timestamp) {
                timestamp = System.currentTimeMillis();
            }

            Map<String, Object> paramMap = new HashMap<>(Constant.DEFAULT_HASH_MAP_CAPACITY);
            paramMap.put("userId", userId);
            paramMap.put("pageSize", pageSize);
            paramMap.put("timestamp", timestamp);
            List<FriendsCircle> friendsCircleList = friendsCircleService.getFriendsCircleListByUserId(paramMap);
            for (FriendsCircle friendsCircle : friendsCircleList) {
                List<User> likeUserList = friendsCircleService.getLikeUserListByCircleId(friendsCircle.getCircleId());
                friendsCircle.setLikeUserList(likeUserList);

                List<FriendsCircleComment> friendsCircleCommentList =
                        friendsCircleService.getFriendsCircleCommentListByCircleId(friendsCircle.getCircleId());
                friendsCircle.setFriendsCircleCommentList(friendsCircleCommentList);
            }

            responseEntity = new ResponseEntity<>(friendsCircleList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        long endTimeStamp = System.currentTimeMillis();
        logger.info("getFriendsCircleListByUserId, userId: " + userId + ", cost: " + (endTimeStamp - beginTimeStamp) + "ms");
        return responseEntity;
    }

    /**
     * 点赞
     *
     * @param circleId 朋友圈ID
     * @param userId   用户ID
     * @return ResponseEntity
     */
    @PostMapping(value = "/{circleId}/like")
    public ResponseEntity<String> likeFriendsCircle(
            @PathVariable String circleId,
            @RequestParam String userId) {
        ResponseEntity<String> responseEntity;

        try {
            Map<String, Object> paramMap = new HashMap<>(Constant.DEFAULT_HASH_MAP_CAPACITY);
            paramMap.put("likeId", CommonUtil.generateId());
            paramMap.put("userId", userId);
            paramMap.put("circleId", circleId);
            friendsCircleService.likeFriendsCircle(paramMap);

            responseEntity = new ResponseEntity<>(ResponseMsg.LIKE_FRIENDS_CIRCLE_SUCCESS.getResponseCode(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new ResponseEntity<>(ResponseMsg.LIKE_FRIENDS_CIRCLE_ERROR.getResponseCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }


    /**
     * 取消点赞
     *
     * @param circleId 朋友圈ID
     * @param userId   用户ID
     * @return ResponseEntity
     */
    @DeleteMapping(value = "/{circleId}/like")
    public ResponseEntity<String> unLikeFriendsCircle(
            @PathVariable String circleId,
            @RequestParam String userId) {
        ResponseEntity<String> responseEntity;

        try {
            Map<String, Object> paramMap = new HashMap<>(Constant.DEFAULT_HASH_MAP_CAPACITY);
            paramMap.put("likeId", CommonUtil.generateId());
            paramMap.put("userId", userId);
            paramMap.put("circleId", circleId);
            friendsCircleService.unLikeFriendsCircle(paramMap);

            responseEntity = new ResponseEntity<>(ResponseMsg.UNLIKE_FRIENDS_CIRCLE_SUCCESS.getResponseCode(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new ResponseEntity<>(ResponseMsg.UNLIKE_FRIENDS_CIRCLE_ERROR.getResponseCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 朋友圈添加评论
     *
     * @param circleId 朋友圈ID
     * @param userId   用户ID
     * @param content  评论内容
     * @return ResponseEntity
     */
    @PostMapping(value = "/{circleId}/comment")
    public ResponseEntity<FriendsCircleComment> addFriendsCircleComment(
            @PathVariable String circleId,
            @RequestParam String userId,
            @RequestParam String content) {
        ResponseEntity<FriendsCircleComment> responseEntity;

        try {
            FriendsCircleComment friendsCircleComment = new FriendsCircleComment();
            friendsCircleComment.setCommentId(CommonUtil.generateId());
            friendsCircleComment.setCommentUserId(userId);
            friendsCircleComment.setCommentCircleId(circleId);
            friendsCircleComment.setCommentContent(content);
            friendsCircleService.addFriendsCircleComment(friendsCircleComment);

            responseEntity = new ResponseEntity<>(friendsCircleComment, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new ResponseEntity<>(new FriendsCircleComment(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
}
