import com.wsm.order.dao.mapper.OrderDOMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author wangsenmu
 * @date 24/11/2020
 */
@SpringBootApplication(scanBasePackages = {"com.wsm.order.*"})
@RestController
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Resource
    private OrderDOMapper orderDOMapper;

    @RequestMapping(value = "/hello" ,method = RequestMethod.GET)
    public String test(){

//        OrderDO orderDOList =orderDOMapper.selectByPrimaryKey(15L);

        return "success";



    }
}
