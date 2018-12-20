package cn.chy.lms.controller;

import cn.chy.lms.bean.user.Reader;
import cn.chy.lms.mapper.user.ReaderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/reader")
public class ReaderController {

    @Autowired
    private ReaderMapper readerMapper;

    @RequestMapping("/dropTable")
    public boolean dropTable() {
        return readerMapper.dropTable();
    }

    @RequestMapping("/createTable")
    public boolean createTable() {
        return readerMapper.createTable();
    }

    @RequestMapping("/insert")
    public boolean add(@RequestParam Reader reader) {
        return readerMapper.add(reader);
    }

    @RequestMapping("/delete")
    public boolean delete(@RequestParam String username) {
        return readerMapper.delete(username);
    }

    @RequestMapping("/findByUsername")
    public Reader findByUsername(@RequestParam String username) {
        return readerMapper.findByUsername(username);
    }

    @RequestMapping("/update")
    public boolean update(@RequestParam Reader reader) {
        return readerMapper.updateByUsername(reader);
    }


}
