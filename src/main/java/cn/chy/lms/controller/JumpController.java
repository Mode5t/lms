package cn.chy.lms.controller;

import cn.chy.lms.util.ModelAndViewUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class JumpController {

    @RequestMapping(value = "jump", method = RequestMethod.GET)
    public ModelAndView jump(@RequestParam String url) {
        return ModelAndViewUtils.jump(url);
    }

    @RequestMapping(value = "jumpWithUsername", method = RequestMethod.GET)
    public ModelAndView jumpWithParam(@RequestParam String url, @RequestParam String username) {
        return ModelAndViewUtils.jump(url, "username", username);
    }
}
