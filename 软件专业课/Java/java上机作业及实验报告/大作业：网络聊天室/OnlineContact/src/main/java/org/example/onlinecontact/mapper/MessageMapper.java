package org.example.onlinecontact.mapper;

import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.onlinecontact.dto.MessageDTO;

@Mapper
public interface MessageMapper {
    @Insert("insert into message(send_username, get_username, message, send_time,get_user_id,send_user_id) VALUES " +
            "(#{sendUsername},#{getUsername},#{message},#{sendTime},#{getUserId},#{sendUserId})")
    void insert(MessageDTO messageDTO);
    @Select("select * from message where (send_user_id=#{id} and get_user_id=#{sendUserId}) or" +
            "(send_user_id=#{sendUserId} and get_user_id=#{id})  ")
    Page<MessageDTO> page(Integer id, Integer sendUserId);
}
