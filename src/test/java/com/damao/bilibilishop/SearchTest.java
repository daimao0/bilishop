package com.damao.bilibilishop;

import com.damao.bilibilishop.dao.TicketDao;
import com.damao.bilibilishop.service.TicketService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class SearchTest {
    @Autowired
    TicketDao ticketDao;
    @Autowired
    TicketService ticketService;
//    @Test
//    public void listTicketsBySearch(){
//        ticketDao.listTicketsBySearch("上海%",1*16,16);
//    }
    @Test
    public void searchTicketsByEs() throws IOException {
        ticketService.searchTicketsByEs("猫",1,16);
    }
}
