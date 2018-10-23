package cn.gdqy.aotw.mapper;

import cn.gdqy.aotw.pojo.GroupmemberExample;
import cn.gdqy.aotw.pojo.GroupmemberKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GroupmemberMapper {
    int countByExample(GroupmemberExample example);

    int deleteByExample(GroupmemberExample example);

    int deleteByPrimaryKey(GroupmemberKey key);

    int insert(GroupmemberKey record);

    int insertSelective(GroupmemberKey record);

    List<GroupmemberKey> selectByExample(GroupmemberExample example);

    int updateByExampleSelective(@Param("record") GroupmemberKey record, @Param("example") GroupmemberExample example);

    int updateByExample(@Param("record") GroupmemberKey record, @Param("example") GroupmemberExample example);
}