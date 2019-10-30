package wang.ismy.edu.ucenter.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MenuMapperTest {

    @Autowired
    MenuMapper menuMapper;

    @Test
    public void selectMenu() {

        menuMapper.selectMenu("48").forEach(System.out::println);
    }
}