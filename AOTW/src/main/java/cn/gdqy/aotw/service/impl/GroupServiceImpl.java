package cn.gdqy.aotw.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import cn.gdqy.aotw.common.GlobalConstant;
import cn.gdqy.aotw.common.ResultView;
import cn.gdqy.aotw.mapper.GroupMapper;
import cn.gdqy.aotw.mapper.GroupmemberMapper;
import cn.gdqy.aotw.pojo.Group;
import cn.gdqy.aotw.pojo.GroupExample;
import cn.gdqy.aotw.pojo.GroupmemberExample;
import cn.gdqy.aotw.service.GroupService;
import cn.gdqy.aotw.utils.UploadFileHelper;

@Service
@Transactional
public class GroupServiceImpl implements GroupService {

	@Autowired
	private GroupMapper groupMapper;
	@Autowired
	private GroupmemberMapper groupmemberMapper;
	
	public ResultView createGroup(String userName, Group group, MultipartFile imageFile) {
		ResultView result = new ResultView();
		String url = UploadFileHelper.saveFile(imageFile);
		group.setImage(url);
		group.setUsername(userName);
		group.setCreatetime(new Date());
		group.setStatus(GlobalConstant.GroupStatus.ENABLE);
		groupMapper.insert(group);
		return result;
	}

	public ResultView dissolveGroup(Integer groupId) {
		ResultView result = new ResultView();
		GroupmemberExample example = new GroupmemberExample();
		example.createCriteria().andGroupidEqualTo(groupId);
		groupmemberMapper.deleteByExample(example);
		groupMapper.deleteByPrimaryKey(groupId);
		return result;
	}

	public ResultView updateGroupData(Group group) {
		ResultView result = new ResultView();
		groupMapper.updateByPrimaryKeySelective(group);
		return result;
	}

	public ResultView updateGroupStatus(Integer groupId, Byte status) {
		ResultView result = new ResultView();
		Group group = new Group();
		group.setId(groupId);
		group.setStatus(status);
		groupMapper.updateByPrimaryKeySelective(group);
		return result;
	}

	@Transactional(propagation=Propagation.SUPPORTS)
	public ResultView fuzzySearchGroupsByName(String name) {
		GroupExample example = new GroupExample();
		example.setOrderByClause("name");
		example.createCriteria().andNameLike("%"+name+"%");
		List<Group> groupList = groupMapper.selectByExample(example);
		ResultView result = new ResultView();
		result.putData("groupList", groupList);
		return result;
	}

}
