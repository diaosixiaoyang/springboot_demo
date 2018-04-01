package com.fangxuele.ocs.web.coms.web;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author rememberber(https : / / github.com / rememberber)
 */

@Controller
public class MainsiteErrorController implements ErrorController {

    private static final String ERROR_PATH = "/error";

    @RequestMapping(value = ERROR_PATH)
    public String handleError(HttpServletRequest request, Model model) {
        String statusCode = request.getAttribute("javax.servlet.error.status_code").toString();
        model.addAttribute("statusCode", statusCode);
        return "error_page";
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}
