package com.brn.homebrew.service.impl;

import com.brn.homebrew.dao.ClientDao;
import com.brn.homebrew.entity.Client;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * @author Bruno Domingues
 */
public class ClientServiceImplTest {

    @Test
    public void shouldCreate() throws Exception {
        //given
        Client client = new Client();
        client.setId(1L);
        client.setFirstName("John");
        client.setLastName("Doe");
        ClientDao clientDaoMock = mock(ClientDao.class);
        ClientServiceImpl clientService = new ClientServiceImpl(clientDaoMock);
        //when
        clientService.create(client);
        //then
        verify(clientDaoMock).create(client);
    }

    @Test
    public void shouldRead() throws Exception {

    }
}
