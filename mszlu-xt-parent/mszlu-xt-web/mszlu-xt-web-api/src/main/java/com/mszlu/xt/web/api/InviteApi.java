package com.mszlu.xt.web.api;

import com.mszlu.xt.common.model.CallResult;
import com.mszlu.xt.web.model.params.BillParam;
import com.mszlu.xt.web.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("i")
public class InviteApi {
    @Autowired
    private BillService billService;

    @RequestMapping("all")
    @ResponseBody
    public CallResult all() {
        return billService.all(new BillParam());
    }

    @RequestMapping("gen")
    @ResponseBody
    public CallResult gen(@RequestBody BillParam billParam){
        return billService.gen(billParam);
    }

    @RequestMapping("u/{billType}/{id}")
    public String url(HttpServletRequest request,
                      HttpServletResponse response,
                      @PathVariable("billType") String billType,
                      @PathVariable("id") String id) {
        if (billType != null && id != null) {
            //将其信息埋入cookie 后续此用户做任何操作，我们可以判断cookie当中是否有推荐人信息
            Cookie cookie = new Cookie("_i_ga_b_" + billType, id);
            cookie.setMaxAge(3 * 24 * 60 * 60);  //cookie过期时间是3天
            cookie.setPath("/");
            response.addCookie(cookie);
        }
        //当用户访问推广链接的时候，需要跳转到应用首页
        String ua = request.getHeader("user-agent").toLowerCase();
        if (ua.indexOf("micromessenger") > 0){
            //微信浏览器 跳转微信登录
            return "redirect:/api/sso/login/authorize";
        }
        return "redirect:http://www.mszlu.com";
    }
}
