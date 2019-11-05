package com.bc.wechat.server.mapper;

import com.bc.wechat.server.entity.Group;
import com.bc.wechat.server.entity.GroupMembers;

import java.util.List;

/**
 * 群组
 *
 * @author zhou
 */
public interface GroupMapper {
    /**
     * 新增群组
     *
     * @param group 群组信息
     */
    void addGroup(Group group);

    /**
     * 添加群组成员
     *
     * @param groupMembersList 群组成员
     */
    void addGroupMembers(List<GroupMembers> groupMembersList);
}