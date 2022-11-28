package com.netwisd.base.common.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.netwisd.base.common.mdm.vo.MdmResourceVo;
import com.netwisd.base.common.mdm.vo.MdmUserVo;
import com.netwisd.base.common.user.vo.ResourceDetailsVO;
import com.netwisd.base.common.user.vo.SysVO;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * spring security当前登录对象
 */
@Data
public class LoginAppUser extends MdmUserVo implements UserDetails {

    private static final long serialVersionUID = 1753977564987556640L;

    private List<String> roles;

    private List<MdmResourceVo> resources;

    private List<SysVO> sysList;

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new HashSet<>();
        if (!CollectionUtils.isEmpty(roles)) {
            roles.forEach(role -> {
                collection.add(new SimpleGrantedAuthority("ROLE_" + role));
            });
        }

        if (!CollectionUtils.isEmpty(resources)) {
            resources.forEach(res -> {
                collection.add(new SimpleGrantedAuthority(res.getResourceCode())); // getPermission
            });
        }
        return collection;
    }

    /**
     * 由于mdm名称方式跟security UserDetails的方式不同，做下覆盖处理
     * @return
     */
    @Override
    public String getPassword() {
        return super.getPassWord();
    }

    @Override
    public String getUsername() {
        return super.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        //判断启用停用状态
        /*Date loginValidDate = this.getLoginValidDate();
        if (loginValidDate != null) {
            //当Date1大于Date2时，返回TRUE，当小于等于时，返回false；
            return loginValidDate.after(new Date()) ? true : false;
        }*/
        //return getEnabled();
        return true;
    }
}
