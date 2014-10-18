package com.brn.homebrew.service.impl;

import com.brn.homebrew.dao.ClientDao;
import com.brn.homebrew.dao.PersonalTrainerDao;
import com.brn.homebrew.dao.PtClientAssociationDao;
import com.brn.homebrew.model.Client;
import com.brn.homebrew.model.PersonalTrainer;
import com.brn.homebrew.model.PtClientAssociation;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * @author Bruno Domingues
 */
public class PtClientAssociationServiceImplTest {

    @Test
    public void shouldCreate() throws Exception {
        //given
        PersonalTrainer personalTrainer = new PersonalTrainer();
        personalTrainer.setId(1l);
        personalTrainer.setFirstName("firstName");
        personalTrainer.setLastName("lastName");
        Client client = new Client();
        client.setId(2l);
        client.setFirstName("firstName");
        client.setLastName("lastName");
        PtClientAssociation ptClientAssociation = new PtClientAssociation();
        ptClientAssociation.setClient(client);
        ptClientAssociation.setPersonalTrainer(personalTrainer);
        PtClientAssociationDao ptClientAssociationDaoMock = mock(PtClientAssociationDao.class);
        PersonalTrainerDao personalTrainerDaoMock = mock(PersonalTrainerDao.class);
        ClientDao clientDaoMock = mock(ClientDao.class);
        PtClientAssociationServiceImpl ptClientAssociationService = new PtClientAssociationServiceImpl();
        ptClientAssociationService.setPtClientAssociationDao(ptClientAssociationDaoMock);
        ptClientAssociationService.setPersonalTrainerDao(personalTrainerDaoMock);
        ptClientAssociationService.setClientDao(clientDaoMock);
        when(ptClientAssociationDaoMock.create(ptClientAssociation)).thenReturn(1l);
        when(personalTrainerDaoMock.read(1l)).thenReturn(personalTrainer);
        when(clientDaoMock.read(2l)).thenReturn(client);
        //when
        Long actualId = ptClientAssociationService.create(1l, 2l);
        //then
        Long expectedId = 1l;
        assertEquals(expectedId, actualId);
        verify(ptClientAssociationDaoMock).create(ptClientAssociation);
    }

    @Test
    public void shouldReadAllAssociationsFromPt() throws Exception {
        //given
        PersonalTrainer personalTrainer = new PersonalTrainer();
        personalTrainer.setId(1l);
        personalTrainer.setFirstName("John");
        personalTrainer.setLastName("Doe");
        List<Client> clientList = new ArrayList<>();
        clientList.add(createClient(2l, "Fat", "Joe"));
        clientList.add(createClient(3l, "Fatty", "McFatty"));
        clientList.add(createClient(4l, "Louise", "Blober"));
        List<PtClientAssociation> expected = new ArrayList<>();
        for (int i = 0; i < clientList.size(); ++i) {
            PtClientAssociation ptClientAssociation = new PtClientAssociation();
            ptClientAssociation.setId((long) (i + 1));
            ptClientAssociation.setPersonalTrainer(personalTrainer);
            ptClientAssociation.setClient(clientList.get(i));
            expected.add(ptClientAssociation);
        }
        PtClientAssociationDao daoMock = mock(PtClientAssociationDao.class);
        when(daoMock.readAllFromPersonalTrainer(personalTrainer)).thenReturn(expected);
        PtClientAssociationServiceImpl service = new PtClientAssociationServiceImpl();
        service.setDao(daoMock);
        //when
        List<PtClientAssociation> actual = service.readAllFromPersonalTrainer(personalTrainer);
        //then
        assertEquals(expected, actual);
    }

    private Client createClient(long id, String firstName, String lastName) {
        Client client = new Client();
        client.setId(id);
        client.setFirstName(firstName);
        client.setLastName(lastName);
        return client;
    }
}
