package com.rlc.rlccmdbapi.modules.tool.controller;

import com.rlc.rlcbase.persistence.web.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author rlc_zyc
 * @version 1.0
 * @description: TODO
 * @date 2021/5/17 10:07
 */
@Controller
@RequestMapping("/cmdb/api")
public class SwaggerController extends BaseController {
    @GetMapping()
    public String index()
    {
        return redirect("/swagger-ui.html");
    }
}
