package com.damao.bilibilishop;

import com.damao.bilibilishop.dao.TicketDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SearchTest {
    @Autowired
    TicketDao ticketDao;

    @Test
    public void listTicketsBySearch(){
        ticketDao.listTicketsBySearch("上海%",1*16,16);
    }
}
