package cn.chy.lms.util;

import org.springframework.web.servlet.ModelAndView;

import java.util.Map;


public class ModelAndViewUtils {

    public static ModelAndView getEmptyModelAndView() {
        return new ModelAndView();
    }

    public static ModelAndView result(String result) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("result");
        modelAndView.addObject("result", result);
        return modelAndView;
    }

    public static ModelAndView jump(String url) {
        return new ModelAndView(url);
    }

    public static ModelAndView jump(String url, Map<String, Object> param) {
        ModelAndView modelAndView = jump(url);
        if (param != null) {
            for (String key : param.keySet()) {
                modelAndView.addObject(key, param.get(key));
            }
        }
        return modelAndView;
    }

    public static ModelAndView jump(String url, String[] key, Object[] value) {
        ModelAndView modelAndView = jump(url);
        if (key != null && value != null) {
            for (int i = 0; i < key.length; i++) {
                modelAndView.addObject(key[i], value[i]);
            }
        }
        return modelAndView;
    }

    public static ModelAndView jump(String url, String key, Object value) {
        ModelAndView modelAndView = jump(url);
        if (key != null && value != null) {
            modelAndView.addObject(key, value);
        }
        return modelAndView;
    }
}
