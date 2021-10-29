package com.ruoyi.web.controller.tool;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.Rest;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * swagger 用户测试方法
 *
 * @author ruoyi
 */
@Api("用户信息管理")
@RestController
@RequestMapping("/test/user")
public class TestController extends BaseController {

	private final static Map<Integer, UserEntity> users = new LinkedHashMap<Integer, UserEntity>();

	{
		users.put(1, new UserEntity(1, "admin", "admin123", "15888888888"));
		users.put(2, new UserEntity(2, "ry", "admin123", "15666666666"));
	}

	@ApiOperation("获取用户列表")
	@ApiOperationSupport(author = "lijingxiang")
	@GetMapping("/list")
	public TableDataInfo<List<UserEntity>> userList() {
		List<UserEntity>userList = new ArrayList<>(users.values());
		return getDataTable(userList);
	}

	@ApiOperation("获取用户详细")
	@ApiOperationSupport(author = "lijingxiang")
	@ApiImplicitParam(name = "userId", value = "用户ID", required = true)
	@GetMapping("/{userId}")
	public Rest<UserEntity> getUser(@PathVariable Integer userId) {
		Rest<UserEntity> userEntityRest;
		if (!users.isEmpty() && users.containsKey(userId)) {
			userEntityRest = Rest.success(users.get(userId));
		} else {
			userEntityRest = Rest.error("用户不存在");
		}
		return userEntityRest;
	}

	@ApiOperation("新增用户")
	@ApiOperationSupport(author = "lijingxiang")
//	@ApiImplicitParams({
//			@ApiImplicitParam(name = "userId", value = "用户id"),
//			@ApiImplicitParam(name = "username", value = "用户名称"),
//			@ApiImplicitParam(name = "password", value = "用户密码"),
//			@ApiImplicitParam(name = "mobile", value = "用户手机")
//	})
	@PostMapping("/save")
	public Rest<UserEntity> save(UserEntity user) {
		if (StringUtils.isNull(user) || StringUtils.isNull(user.getUserId())) {
			return error("用户ID不能为空");
		}
		return Rest.success(users.put(user.getUserId(), user));
	}

	@ApiOperation("更新用户")
	@ApiOperationSupport(author = "lijingxiang")
	@PutMapping("/update")
	public Rest<UserEntity> update(@RequestBody UserEntity user) {
		if (StringUtils.isNull(user) || StringUtils.isNull(user.getUserId())) {
			return error("用户ID不能为空");
		}
		if (users.isEmpty() || !users.containsKey(user.getUserId())) {
			return error("用户不存在");
		}
		users.remove(user.getUserId());
		return Rest.success(users.put(user.getUserId(), user));
	}

	@ApiOperation("删除用户信息")
	@ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "int", paramType = "path")
	@ApiOperationSupport(author = "lijingxiang")
	@DeleteMapping("/{userId}")
	public Rest delete(@PathVariable Integer userId) {
		if (!users.isEmpty() && users.containsKey(userId)) {
			users.remove(userId);
			return success();
		} else {
			return error("用户不存在");
		}
	}
}

@ApiModel(value = "UserEntity", description = "用户实体")
class UserEntity {

	@ApiModelProperty(value = "用户ID", example = "1")
	private Integer userId;

	@ApiModelProperty(value = "用户名称", example = "小明")
	private String username;

	@ApiModelProperty(value = "用户密码", example = "mimaxxxxx234")
	private String password;

	@ApiModelProperty(value = "用户手机", example = "15888939728")
	private String mobile;

	public UserEntity() {

	}

	public UserEntity(Integer userId, String username, String password, String mobile) {
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.mobile = mobile;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}
