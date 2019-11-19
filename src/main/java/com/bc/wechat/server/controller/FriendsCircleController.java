package com.bc.wechat.server.controller;

import com.bc.wechat.server.entity.FriendsCircle;
import com.bc.wechat.server.service.FriendsCircleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 朋友圈控制器
 *
 * @author zhou
 */
@RestController
@RequestMapping("/friendsCircle")
public class FriendsCircleController {

    @Resource
    private FriendsCircleService friendsCircleService;

    /**
     * 查找某个用户的朋友圈列表
     *
     * @param userId 用户ID
     * @return 朋友圈列表
     */
    @ApiOperation(value = "查找朋友圈列表", notes = "查找朋友圈列表")
    @PostMapping(value = "")
    public ResponseEntity<List<FriendsCircle>> addFriendApply(
            @RequestParam String userId) {
        ResponseEntity<List<FriendsCircle>> responseEntity;
        try {
            List<FriendsCircle> friendsCircleList = friendsCircleService.getFriendsCircleListByUserId(userId);
            responseEntity = new ResponseEntity<>(friendsCircleList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
}
