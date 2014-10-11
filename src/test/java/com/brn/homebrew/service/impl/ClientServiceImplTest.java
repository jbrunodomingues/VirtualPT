package com.brn.homebrew.service.impl;

import com.brn.homebrew.dao.ClientDao;
import com.brn.homebrew.dao.PtClientAssociationDao;
import com.brn.homebrew.model.Client;
import com.brn.homebrew.model.PtClientAssociation;
import org.junit.Test;

import java.util.Collections;

import static org.mockito.Mockito.*;

/**
 * @author Bruno Domingues
 */
public class ClientServiceImplTest {

    @Test
    public void shouldDelete() throws Exception {
        //given
        Client client = createClient();
        ClientDao clientDaoMock = mock(ClientDao.class);
        PtClientAssociationDao ptClientAssociationDaoMock = mock(PtClientAssociationDao.class);
        ClientServiceImpl clientService = new ClientServiceImpl();
        clientService.setDao(clientDaoMock);
        clientService.setPtClientAssociationDao(ptClientAssociationDaoMock);
        //when
        clientService.delete(client);
        //then
        verify(clientDaoMock).delete(client);
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowExceptionIfThereAreStillAssociations() {
        //given
        Client client = createClient();
        PtClientAssociation ptClientAssociation = new PtClientAssociation();
        ptClientAssociation.setClient(client);
        ClientDao clientDaoMock = mock(ClientDao.class);
        PtClientAssociationDao ptClientAssociationDaoMock = mock(PtClientAssociationDao.class);
        when(ptClientAssociationDaoMock.readAllFromClient(client))
                .thenReturn(Collections.singletonList(ptClientAssociation));
        ClientServiceImpl clientService = new ClientServiceImpl();
        clientService.setDao(clientDaoMock);
        clientService.setPtClientAssociationDao(ptClientAssociationDaoMock);
        //when
        clientService.delete(client);
        //then exception
    }

    private Client createClient() {
        Client client = new Client();
        client.setId(1L);
        client.setFirstName("John");
        client.setLastName("Doe");
        return client;
    }
}